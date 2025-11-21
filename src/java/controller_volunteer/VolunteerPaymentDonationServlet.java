package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.VolunteerDonationService;
import utils.PaymentConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servlet khởi tạo thanh toán donate qua cổng thanh toán cho VOLUNTEER
 * Xử lý donation của volunteer không cần phê duyệt
 */
@WebServlet(name = "VolunteerPaymentDonationServlet", urlPatterns = {"/volunteer-payment-donation"})
public class VolunteerPaymentDonationServlet extends HttpServlet {

    private VolunteerDonationService donationService;

    @Override
    public void init() {
        donationService = new VolunteerDonationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // --- Luồng chính POST cho Volunteer ---
            // 1) Kiểm tra session/đăng nhập (account volunteer)
            // 2) Lấy thông tin volunteer từ bảng Users để tiền tố trên form (fullname, email, phone)
            // 3) Validate input (eventId, amount)
            // 4) Tạo hoặc lấy donor record (loại 'volunteer')
            // 5) Chuẩn bị tham số VNPay, tạo chữ ký và build paymentUrl
            // 6) Lưu record Payment_Donations (pending) và lưu vài info vào session
            // 7) Redirect volunteer tới VNPay để thanh toán

            // Kiểm tra xác thực volunteer đã đăng nhập chưa
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                response.sendRedirect(request.getContextPath() + "/LoginServlet");
                return;
            }

            model.Account acc = (model.Account) session.getAttribute("account");
            int accountId = acc.getId();

            // Lấy thông tin chi tiết volunteer từ bảng Users
            dao.UserDAO userDAO = new dao.UserDAO();
            model.User user = userDAO.getUserByAccountId(accountId);
            String fullName = user.getFull_name();
            String email = user.getEmail();
            String phone = user.getPhone();

            // Lấy các tham số từ form
            String eventIdStr = request.getParameter("eventId");
            String amountStr = request.getParameter("amount");
            String note = request.getParameter("note");

            // Kiểm tra tham số bắt buộc
            if (eventIdStr == null || amountStr == null) {
                request.setAttribute("error", "Thiếu tham số bắt buộc");
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            int eventId = Integer.parseInt(eventIdStr);
            long amountVND = Long.parseLong(amountStr);

            // Validate số tiền donate (tối thiểu 10,000 VND)
            String amountError = donationService.validateDonationAmount(amountVND);
            if (amountError != null) {
                request.setAttribute("error", amountError);
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            // Tạo hoặc lấy bản ghi donor cho volunteer trong bảng `Donors`
            // (Donor type = 'volunteer', liên kết tới accountId)
            int donorId;
            try {
                donorId = donationService.createOrGetDonor(accountId, fullName, phone, email);
            } catch (SQLException e) {
                request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            // Chuẩn bị các tham số gửi tới VNPay (amount đã nhân 100, txnRef, returnUrl,...)
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";

            // Chuyển đổi số tiền sang đơn vị xu (nhân 100 theo định dạng VNPay)
            long amount = amountVND * 100;

            // Tạo mã tham chiếu giao dịch duy nhất
            String paymentTxnRef = "DONATE" + eventId + "_" + PaymentConfig.getRandomNumber(8);
            String vnp_IpAddr = PaymentConfig.getIpAddress(request);
            String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

            // Gom các tham số vào `vnp_Params` để tạo query string và chữ ký
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", paymentTxnRef);
            
            vnp_Params.put("vnp_OrderInfo", "Donation for Event #" + eventId + " - " + fullName);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", PaymentConfig.getVolunteerDonationReturnUrl(request));
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            // Thiết lập thời gian hết hạn giao dịch (15 phút)
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Tạo chuỗi để ký (hashData) và queryUrl (URL-encoded), sau đó sinh HMAC SHA512
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            
            List<String> hashDataList = new ArrayList<>();
            List<String> queryList = new ArrayList<>();

            for (String fieldName : fieldNames) {
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    hashDataList.add(fieldName + "=" + URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    queryList.add(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()) 
                                + "=" 
                                + URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                }
            }

            String hashData = String.join("&", hashDataList);
            String queryUrl = String.join("&", queryList);
            
            String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData);
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;

            // Lưu bản ghi Payment_Donations với trạng thái 'pending' để sau này callback biết bản ghi liên quan
            try {
                donationService.createPaymentDonation(donorId, eventId, paymentTxnRef, amount, vnp_Params.get("vnp_OrderInfo"), "VNPay");

                // Lưu thông tin vào session để sử dụng sau khi VNPay callback
                session.setAttribute("donation_donor_id", donorId);
                session.setAttribute("donation_event_id", eventId);
                session.setAttribute("donation_note", note);
                session.setAttribute("donation_txn_ref", paymentTxnRef);

            } catch (SQLException e) {
                request.setAttribute("error", "Không thể tạo bản ghi thanh toán: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            // Redirect volunteer đến VNPay để thực hiện thanh toán
            response.sendRedirect(paymentUrl);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Định dạng đầu vào không hợp lệ");
            response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
    }

    @Override
    public void destroy() {
        if (donationService != null) {
            donationService.close();
        }
    }
}

