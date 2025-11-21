package controller_view;

import dao.PaymentDonationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.PaymentConfig;
import utils.EmailUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

/**
 * Servlet xử lý callback trả về từ cổng thanh toán VNPay cho GUEST
 * Nhận kết quả thanh toán từ VNPay và cập nhật vào database
 */
@WebServlet(name = "GuestPaymentDonationReturnServlet", urlPatterns = {"/guest-payment-donation-return"})
public class GuestPaymentDonationReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get all parameters from VNPay
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                try {
                    String encodedName = java.net.URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString());
                    String encodedValue = java.net.URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString());
                    fields.put(encodedName, encodedValue);
                } catch (Exception e) {
                    fields.put(fieldName, fieldValue);
                }
            }
        }

        // Get secure hash from VNPay
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");

        // Remove hash fields before validating
        try {
            String encodedHashType = java.net.URLEncoder.encode("vnp_SecureHashType", StandardCharsets.UTF_8.toString());
            String encodedHash = java.net.URLEncoder.encode("vnp_SecureHash", StandardCharsets.UTF_8.toString());
            fields.remove(encodedHashType);
            fields.remove(encodedHash);
        } catch (Exception e) {
            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");
        }

        // Validate signature
        String signValue = PaymentConfig.hashAllFields(fields);
        boolean isValidSignature = signValue.equals(vnp_SecureHash);

        // Get payment details
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_Amount = request.getParameter("vnp_Amount");
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_BankCode = request.getParameter("vnp_BankCode");
        String vnp_PayDate = request.getParameter("vnp_PayDate");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
        String vnp_CardType = request.getParameter("vnp_CardType");

        // Process payment result
        PaymentDonationDAO dao;
        try {
            dao = new PaymentDonationDAO();
        } catch (SQLException e) {
            HttpSession currentSession = request.getSession();
            currentSession.setAttribute("message", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            currentSession.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String paymentStatus = "failed";
        String message = "";
        String donorEmail = null;

        try {
            // Get session data
            HttpSession session = request.getSession();
            Integer donorId = (Integer) session.getAttribute("donation_donor_id");
            Integer eventId = (Integer) session.getAttribute("donation_event_id");
            String note = (String) session.getAttribute("donation_note");
            
            // Get payment donation details
            PaymentDonationDAO.PaymentDonationDetail donationDetail = dao.getPaymentDonationByTxnRef(vnp_TxnRef);

            if (isValidSignature) {
                // Check transaction status
                if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                    // Payment successful
                    paymentStatus = "success";
                    message = "Cảm ơn bạn đã ủng hộ.";

                    // Update payment record
                    dao.updatePaymentDonation(
                        vnp_TxnRef,
                        vnp_BankCode,
                        vnp_CardType,
                        vnp_PayDate,
                        vnp_ResponseCode,
                        vnp_TransactionNo,
                        vnp_TransactionStatus,
                        vnp_SecureHash,
                        "success"
                    );

                    // Create donation record
                    if (donorId != null && eventId != null) {
                        System.out.println("=== DEBUG: GuestPaymentDonationReturnServlet ===");
                        System.out.println("vnp_Amount (raw): " + vnp_Amount);
                        long amountInVND = Long.parseLong(vnp_Amount) / 100;
                        System.out.println("amountInVND (after /100): " + amountInVND);
                        BigDecimal amount = new BigDecimal(amountInVND);
                        System.out.println("BigDecimal amount: " + amount);
                        System.out.println("Event ID: " + eventId);
                        System.out.println("Donor ID: " + donorId);
                        System.out.println("Txn Ref: " + vnp_TxnRef);
                        
                        dao.createDonation(eventId, null, donorId, amount, "success", "VNPay", vnp_TxnRef, note);
                        
                        // Get donor email for thank you email
                        System.out.println("=== DEBUG: Getting donor email ===");
                        System.out.println("Donor ID: " + donorId);
                        // Try to get email from donationDetail first (if available)
                        if (donationDetail != null && donationDetail.donorEmail != null && !donationDetail.donorEmail.isEmpty()) {
                            donorEmail = donationDetail.donorEmail;
                            System.out.println("Donor email from donationDetail: " + donorEmail);
                        } else {
                            donorEmail = dao.getDonorEmail(donorId);
                            System.out.println("Donor email from database: " + (donorEmail != null ? donorEmail : "NULL"));
                        }
                        if (donorEmail == null || donorEmail.isEmpty()) {
                            System.out.println("WARNING: Donor email is null or empty, cannot send thank you email");
                        }
                    }

                } else {
                    // Payment failed
                    paymentStatus = "failed";
                    message = "Ủng hộ thất bại: " + getPaymentErrorMessage(vnp_ResponseCode) + " (Mã lỗi: " + vnp_ResponseCode + ")";

                    // Update payment record
                    dao.updatePaymentDonation(
                        vnp_TxnRef,
                        vnp_BankCode,
                        vnp_CardType,
                        vnp_PayDate,
                        vnp_ResponseCode,
                        vnp_TransactionNo,
                        vnp_TransactionStatus,
                        vnp_SecureHash,
                        "failed"
                    );

                    // Create failed donation record
                    if (donorId != null && eventId != null) {
                        long amountInVND = Long.parseLong(vnp_Amount) / 100;
                        BigDecimal amount = new BigDecimal(amountInVND);
                        dao.createDonation(eventId, null, donorId, amount, "failed", "VNPay", vnp_TxnRef, note);
                    }
                }
            } else {
                // Invalid signature
                paymentStatus = "failed";
                message = "Chữ ký thanh toán không hợp lệ. Giao dịch này có thể gian lận. Vui lòng liên hệ hỗ trợ.";

                // Update payment record
                dao.updatePaymentDonation(
                    vnp_TxnRef,
                    vnp_BankCode,
                    vnp_CardType,
                    vnp_PayDate,
                    vnp_ResponseCode,
                    vnp_TransactionNo,
                    vnp_TransactionStatus,
                    vnp_SecureHash,
                    "failed"
                );

                // Create failed donation record
                if (donorId != null && eventId != null) {
                    long amountInVND = Long.parseLong(vnp_Amount) / 100;
                    BigDecimal amount = new BigDecimal(amountInVND);
                    dao.createDonation(eventId, null, donorId, amount, "failed", "VNPay", vnp_TxnRef, note);
                }
            }

            // Send thank you email if donation was successful and email is available
            System.out.println("=== DEBUG: Checking email sending conditions ===");
            System.out.println("Payment status: " + paymentStatus);
            System.out.println("Donor email: " + (donorEmail != null ? donorEmail : "NULL"));
            
            if ("success".equals(paymentStatus) && donorEmail != null && !donorEmail.isEmpty()) {
                try {
                    long amountInVND = Long.parseLong(vnp_Amount) / 100;
                    String eventTitle = donationDetail != null ? donationDetail.eventTitle : "Event";
                    System.out.println("=== DEBUG: Sending thank you email ===");
                    System.out.println("To: " + donorEmail);
                    System.out.println("Amount: " + amountInVND);
                    System.out.println("Event: " + eventTitle);
                    System.out.println("Txn Ref: " + vnp_TxnRef);
                    sendThankYouEmail(donorEmail, amountInVND, eventTitle, vnp_TxnRef);
                    System.out.println("✓ Thank you email sent successfully to: " + donorEmail);
                } catch (Exception e) {
                    System.err.println("✗ Failed to send thank you email: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("✗ Email not sent - Payment status: " + paymentStatus + 
                                 ", Donor email: " + (donorEmail != null ? donorEmail : "NULL"));
            }
            
            // GỬI THÔNG BÁO CHO ORGANIZATION KHI GUEST DONATE THÀNH CÔNG
            if ("success".equals(paymentStatus)) {
                try {
                    if (eventId != null && donationDetail != null) {
                        // Lấy thông tin organization từ event
                        dao.ViewEventsDAO eventDAO = new dao.ViewEventsDAO();
                        model.Event event = eventDAO.getEventById(eventId);
                        
                        if (event != null) {
                            long amountInVND = Long.parseLong(vnp_Amount) / 100;
                            
                            // Lấy tên donor (guest) để hiển thị trong thông báo
                            String donorName = "Một nhà hảo tâm";
                            if (donationDetail.donorFullName != null && !donationDetail.donorFullName.isEmpty()) {
                                donorName = donationDetail.donorFullName;
                            }
                            
                            // Gửi thông báo cho organization
                            dao.NotificationDAO notiDAO = new dao.NotificationDAO();
                            model.Notification noti = new model.Notification();
                            // Guest không có account_id, dùng organization_id làm sender để tránh FK constraint
                            noti.setSenderId(event.getOrganizationId()); 
                            noti.setReceiverId(event.getOrganizationId());
                            noti.setMessage(donorName + " đã ủng hộ " 
                                    + String.format("%,d", amountInVND) 
                                    + " VNĐ cho sự kiện \"" + event.getTitle() + "\" của bạn");
                            noti.setType("donation");
                            noti.setEventId(eventId);
                            
                            boolean inserted = notiDAO.insertNotification(noti);
                            if (inserted) {
                                System.out.println("[Guest Donation] ✓ Đã gửi thông báo cho organization " 
                                        + event.getOrganizationId() + " về donation từ guest: " + donorName);
                            } else {
                                System.err.println("[Guest Donation] ✗ FAILED to insert notification!");
                            }
                            
                            System.out.println("[Guest Donation] Đã gửi thông báo cho organization " 
                                    + event.getOrganizationId() + " về donation từ guest: " + donorName);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("[Guest Donation] Lỗi khi gửi thông báo cho organization: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // Clear session data
            session.removeAttribute("donation_donor_id");
            session.removeAttribute("donation_event_id");
            session.removeAttribute("donation_note");
            session.removeAttribute("donation_txn_ref");

        } catch (SQLException e) {
            System.err.println("ERROR: Database error in payment donation return: " + e.getMessage());
            e.printStackTrace();
            message = "Lỗi cơ sở dữ liệu: " + e.getMessage();
            paymentStatus = "failed";
        } finally {
            dao.close();
        }

        // Store message in session for display
        HttpSession currentSession = request.getSession();

        if ("success".equals(paymentStatus)) {
            currentSession.setAttribute("message", message);
            currentSession.setAttribute("messageType", "success");
        } else {
            currentSession.setAttribute("message", message);
            currentSession.setAttribute("messageType", "danger");
        }

        // Chuyển hướng guest về trang chủ
        response.sendRedirect(request.getContextPath() + "/home");
    }

    /**
     * Send thank you email to donor
     */
    private void sendThankYouEmail(String email, long amount, String eventTitle, String txnRef) {
        String subject = "[Hệ thống Tình nguyện] Cảm ơn bạn đã ủng hộ";

        StringBuilder content = new StringBuilder();
        content.append("<div style=\"font-family:Arial, Helvetica, sans-serif; line-height:1.6; max-width:600px; margin:0 auto; padding:20px; border:1px solid #ddd; border-radius:10px;\">")
               .append("<div style=\"text-align:center; margin-bottom:20px;\">")
               .append("<h2 style=\"color:#28a745; margin:0;\">🎉 Cảm ơn bạn đã ủng hộ!</h2>")
               .append("</div>")
               .append("<p style=\"font-size:16px;\">Kính gửi Quý nhà hảo tâm,</p>")
               .append("<p style=\"font-size:16px;\">Chúng tôi vô cùng biết ơn sự đóng góp của bạn cho chương trình tình nguyện của chúng tôi. ")
               .append("Sự hào phóng của bạn giúp chúng tôi tạo ra những tác động tích cực trong cộng đồng.</p>")
               .append("<div style=\"background:#f8f9fa; padding:20px; border-radius:8px; margin:20px 0;\">")
               .append("<h3 style=\"color:#333; margin-top:0;\">Chi tiết ủng hộ</h3>")
               .append("<table style=\"width:100%; border-collapse:collapse;\">")
               .append("<tr><td style=\"padding:8px 0; border-bottom:1px solid #ddd;\"><strong>Sự kiện:</strong></td>")
               .append("<td style=\"padding:8px 0; border-bottom:1px solid #ddd; text-align:right;\">").append(escapeHtml(eventTitle)).append("</td></tr>")
               .append("<tr><td style=\"padding:8px 0; border-bottom:1px solid #ddd;\"><strong>Số tiền:</strong></td>")
               .append("<td style=\"padding:8px 0; border-bottom:1px solid #ddd; text-align:right; color:#28a745; font-size:18px; font-weight:bold;\">")
               .append(String.format("%,d", amount)).append(" VNĐ</td></tr>")
               .append("<tr><td style=\"padding:8px 0; border-bottom:1px solid #ddd;\"><strong>Phương thức thanh toán:</strong></td>")
               .append("<td style=\"padding:8px 0; border-bottom:1px solid #ddd; text-align:right;\">VNPay</td></tr>")
               .append("<tr><td style=\"padding:8px 0;\"><strong>Mã giao dịch:</strong></td>")
               .append("<td style=\"padding:8px 0; text-align:right; font-family:monospace; font-size:12px;\">").append(escapeHtml(txnRef)).append("</td></tr>")
               .append("</table>")
               .append("</div>")
               .append("<p style=\"font-size:16px;\">Sự ủng hộ của bạn giúp chúng tôi:</p>")
               .append("<ul style=\"font-size:16px; line-height:1.8;\">")
               .append("<li>Tổ chức các sự kiện tình nguyện ý nghĩa</li>")
               .append("<li>Hỗ trợ các sáng kiến phát triển cộng đồng</li>")
               .append("<li>Tạo ra tác động xã hội tích cực</li>")
               .append("<li>Xây dựng một cộng đồng mạnh mẽ và gắn kết hơn</li>")
               .append("</ul>")
               .append("<p style=\"font-size:16px;\">Cảm ơn bạn đã đồng hành cùng chúng tôi trong sứ mệnh tạo ra sự khác biệt!</p>")
               .append("<div style=\"margin-top:30px; padding-top:20px; border-top:1px solid #ddd;\">")
               .append("<p style=\"margin:0; color:#666;\">Trân trọng,</p>")
               .append("<p style=\"margin:5px 0 0 0; font-weight:bold; color:#333;\">Đội ngũ Hệ thống Tình nguyện</p>")
               .append("</div>")
               .append("<div style=\"margin-top:20px; padding:15px; background:#fff3cd; border-radius:5px; font-size:14px; color:#856404;\">")
               .append("<strong>Lưu ý:</strong> Đây là email tự động. Vui lòng không trả lời email này. ")
               .append("Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với đội ngũ hỗ trợ của chúng tôi.")
               .append("</div>")
               .append("</div>");

        try {
            EmailUtil.sendEmail(email, subject, content.toString());
            System.out.println("Donation thank you email sent to: " + email);
        } catch (Exception e) {
            System.err.println("Error sending donation thank you email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get friendly error message for payment response codes
     */
    private String getPaymentErrorMessage(String responseCode) {
        if (responseCode == null) return "Lỗi không xác định";

        switch (responseCode) {
            case "07": return "Giao dịch thành công nhưng xác nhận bị từ chối";
            case "09": return "Thẻ/Tài khoản chưa đăng ký Internet Banking";
            case "10": return "Xác thực sai quá 3 lần";
            case "11": return "Giao dịch hết hạn. Vui lòng thử lại";
            case "12": return "Thẻ/Tài khoản bị khóa";
            case "13": return "OTP không chính xác. Vui lòng thử lại";
            case "24": return "Giao dịch bị hủy bởi người dùng";
            case "51": return "Tài khoản không đủ số dư";
            case "65": return "Vượt quá hạn mức giao dịch";
            case "75": return "Cổng thanh toán đang bảo trì";
            case "79": return "Giao dịch hết hạn, vui lòng thử lại";
            default: return "Giao dịch thất bại";
        }
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

