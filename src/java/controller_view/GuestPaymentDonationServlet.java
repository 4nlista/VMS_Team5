package controller_view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.GuestDonationService;
import utils.PaymentConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servlet khởi tạo thanh toán donate qua cổng thanh toán cho GUEST (khách vãng lai)
 * Xử lý donation ẩn danh và không ẩn danh của guest
 */
@WebServlet(name = "GuestPaymentDonationServlet", urlPatterns = {"/guest-payment-donation"})
public class GuestPaymentDonationServlet extends HttpServlet {

    private GuestDonationService donationService;

    @Override
    public void init() {
        donationService = new GuestDonationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy các tham số cơ bản từ form
            String eventIdStr = request.getParameter("eventId");
            String amountStr = request.getParameter("amount");
            String note = request.getParameter("note");

            // Lấy các tham số đặc thù của guest
            String isAnonymousStr = request.getParameter("isAnonymous");
            String guestName = request.getParameter("guestName");
            String guestPhone = request.getParameter("guestPhone");
            String guestEmail = request.getParameter("guestEmail");

            // Kiểm tra tham số bắt buộc
            if (eventIdStr == null || amountStr == null) {
                request.setAttribute("error", "Thiếu tham số bắt buộc");
                response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
                return;
            }

            int eventId = Integer.parseInt(eventIdStr);
            long amountVND = Long.parseLong(amountStr);

            // Validate số tiền donate (tối thiểu 10,000 VND)
            String amountError = donationService.validateDonationAmount(amountVND);
            if (amountError != null) {
                request.setAttribute("error", amountError);
                request.setAttribute("eventId", eventId);
                request.getRequestDispatcher("/donate_form.jsp").forward(request, response);
                return;
            }

            // Xử lý thông tin donor
            String fullName = null;
            String phone = null;
            String email = null;
            boolean isAnonymous = "true".equals(isAnonymousStr);

            // Validate thông tin guest (nếu không ẩn danh phải có ít nhất 1 field)
            String guestInfoError = donationService.validateGuestInfo(isAnonymous, guestName, guestPhone, guestEmail);
            if (guestInfoError != null) {
                request.setAttribute("error", guestInfoError);
                request.getRequestDispatcher("/donate_form.jsp").forward(request, response);
                return;
            }

            if (!isAnonymous) {
                fullName = guestName;
                phone = guestPhone;
                email = guestEmail;
            }

            // Tạo bản ghi donor cho guest trong bảng Donors
            int donorId;
            try {
                donorId = donationService.createOrGetDonor(fullName, phone, email, isAnonymous);
            } catch (SQLException e) {
                request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
                return;
            }

            // Các tham số cấu hình VNPay
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";

            // Chuyển đổi số tiền sang đơn vị xu (nhân 100 theo định dạng VNPay)
            long amount = amountVND * 100;

            // Tạo mã tham chiếu giao dịch duy nhất
            String paymentTxnRef = "DONATE" + eventId + "_" + PaymentConfig.getRandomNumber(8);
            String vnp_IpAddr = PaymentConfig.getIpAddress(request);
            String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

            // Xây dựng các tham số gửi đến VNPay
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", paymentTxnRef);
            
            String donorDisplayName = isAnonymous ? "Anonymous Donor" : (fullName != null ? fullName : "Guest Donor");
            vnp_Params.put("vnp_OrderInfo", "Donation for Event #" + eventId + " - " + donorDisplayName);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", PaymentConfig.getGuestDonationReturnUrl(request));
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            // Thiết lập thời gian hết hạn giao dịch (15 phút)
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Xây dựng chuỗi query và tạo chữ ký bảo mật
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

            // Lưu bản ghi thanh toán với trạng thái pending vào bảng Payment_Donations
            try {
                donationService.createPaymentDonation(donorId, eventId, paymentTxnRef, amount, vnp_Params.get("vnp_OrderInfo"), "VNPay");

                // Lưu thông tin vào session để sử dụng sau khi VNPay callback
                HttpSession session = request.getSession();
                session.setAttribute("donation_donor_id", donorId);
                session.setAttribute("donation_event_id", eventId);
                session.setAttribute("donation_note", note);
                session.setAttribute("donation_txn_ref", paymentTxnRef);

            } catch (SQLException e) {
                request.setAttribute("error", "Không thể tạo bản ghi thanh toán: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
                return;
            }

            // Chuyển hướng guest đến cổng thanh toán VNPay
            response.sendRedirect(paymentUrl);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Định dạng đầu vào không hợp lệ");
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
    }

    @Override
    public void destroy() {
        if (donationService != null) {
            donationService.close();
        }
    }
}

