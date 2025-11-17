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
 * Servlet for initiating payment gateway donation for VOLUNTEERS
 * Handles volunteer donations without approval requirement
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
            // Check volunteer authentication
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("account") == null) {
                response.sendRedirect(request.getContextPath() + "/LoginServlet");
                return;
            }

            model.Account acc = (model.Account) session.getAttribute("account");
            int accountId = acc.getId();

            // Get volunteer details
            dao.UserDAO userDAO = new dao.UserDAO();
            model.User user = userDAO.getUserByAccountId(accountId);
            String fullName = user.getFull_name();
            String email = user.getEmail();
            String phone = user.getPhone();

            // Get parameters
            String eventIdStr = request.getParameter("eventId");
            String amountStr = request.getParameter("amount");
            String note = request.getParameter("note");

            // Validation
            if (eventIdStr == null || amountStr == null) {
                request.setAttribute("error", "Thiếu tham số bắt buộc");
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            int eventId = Integer.parseInt(eventIdStr);
            long amountVND = Long.parseLong(amountStr);

            // Validate amount using service
            String amountError = donationService.validateDonationAmount(amountVND);
            if (amountError != null) {
                request.setAttribute("error", amountError);
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            // Create donor record for volunteer using service
            int donorId;
            try {
                donorId = donationService.createOrGetDonor(accountId, fullName, phone, email);
            } catch (SQLException e) {
                request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            // VNPay payment parameters
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";

            // Amount in VND cents (multiply by 100 for VNPay format)
            long amount = amountVND * 100;

            // Generate unique transaction reference
            String paymentTxnRef = "DONATE" + eventId + "_" + PaymentConfig.getRandomNumber(8);
            String vnp_IpAddr = PaymentConfig.getIpAddress(request);
            String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

            // Build VNPay parameters
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

            // Set expiry time (15 minutes)
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Build query string and hash
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

            // Save payment record as pending using service
            try {
                donationService.createPaymentDonation(donorId, eventId, paymentTxnRef, amount, vnp_Params.get("vnp_OrderInfo"), "VNPay");

                // Store donor info and note in session for later use
                session.setAttribute("donation_donor_id", donorId);
                session.setAttribute("donation_event_id", eventId);
                session.setAttribute("donation_note", note);
                session.setAttribute("donation_txn_ref", paymentTxnRef);

            } catch (SQLException e) {
                request.setAttribute("error", "Không thể tạo bản ghi thanh toán: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            // Redirect to VNPay payment gateway    -- điều hướng đến trang paymentv2/Ncb/Transaction/Index.html?token
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

