package controller_volunteer;

import dao.DonationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Donation;

@WebServlet("/DonateServlet")
public class DonateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lấy accountId từ session
        Integer accountId = (Integer) session.getAttribute("accountId");
        if (accountId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Lấy thông tin eventId từ form
        String eventIdStr = request.getParameter("eventId");
        if (eventIdStr == null || eventIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Không xác định sự kiện để quyên góp.");
            request.getRequestDispatcher("/volunteer/payment_volunteer.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin amount từ form
        String amountStr = request.getParameter("amount");
        if (amountStr == null || amountStr.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập số tiền quyên góp.");
            request.getRequestDispatcher("/volunteer/payment_volunteer.jsp").forward(request, response);
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr.trim());
            double amount = Double.parseDouble(amountStr.trim());

            // Lấy các thông tin khác
            String paymentMethod = request.getParameter("paymentMethod");
            String note = request.getParameter("note");

            if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                paymentMethod = "QR"; // Mặc định
            }

            // Tạo donation object
            Donation donation = new Donation();
            donation.setEventId(eventId);
            donation.setVolunteerId(accountId);
            donation.setAmount(amount);
            donation.setStatus("pending");
            donation.setPaymentMethod(paymentMethod);
            donation.setNote(note != null ? note.trim() : "");
            donation.setQrCode("QR-" + System.currentTimeMillis());

            // Thêm vào database
            DonationDAO dao = new DonationDAO();
            boolean success = dao.insertDonation(donation);

            if (success) {
                request.setAttribute("message", "Quyên góp thành công! Đang chờ xác nhận.");
            } else {
                request.setAttribute("error", "Có lỗi xảy ra khi thực hiện quyên góp.");
            }

            // Forward về trang payment, giữ thông tin volunteer từ session
            request.setAttribute("volunteerName", session.getAttribute("volunteerName"));
            request.setAttribute("volunteerEmail", session.getAttribute("volunteerEmail"));
            request.setAttribute("volunteerPhone", session.getAttribute("volunteerPhone"));
            request.setAttribute("volunteerAddress", session.getAttribute("volunteerAddress"));
            request.setAttribute("eventId", eventId);

            request.getRequestDispatcher("/volunteer/payment_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Thông tin gửi lên không hợp lệ.");
            request.getRequestDispatcher("/volunteer/payment_volunteer.jsp").forward(request, response);
        }
    }
}
