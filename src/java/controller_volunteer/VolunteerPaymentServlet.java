package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import model.Event;
import service.VolunteerDonationService;

@WebServlet(name = "VolunteerPaymentServlet", urlPatterns = {"/VolunteerPaymentServlet"})
public class VolunteerPaymentServlet extends HttpServlet {

    private VolunteerDonationService donationService;

    @Override
    public void init() {
        donationService = new VolunteerDonationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy eventId từ URL
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Lấy volunteerId từ session
        HttpSession session = request.getSession();
        Integer volunteerId = (Integer) session.getAttribute("accountId");
        String volunteerName = (String) session.getAttribute("username");

        // FIX: Ưu tiên lấy từ Account object nếu có
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            volunteerId = account.getId();
            volunteerName = account.getUsername();
        }

        if (volunteerId == null) {
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);

            // Kiểm tra volunteer đã donate chưa
            boolean alreadyDonated = donationService.hasVolunteerDonated(volunteerId, eventId);
            if (alreadyDonated) {
                session.setAttribute("errorMessage", "Bạn đã donate cho sự kiện này rồi!");
                response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
                return;
            }

            // Lấy thông tin event
            Event event = donationService.getEventById(eventId);

            if (event == null) {
                request.setAttribute("errorMessage", "Không tìm thấy sự kiện!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // Truyền thông tin sang JSP
            request.setAttribute("event", event);
            request.setAttribute("volunteerId", volunteerId);
            request.setAttribute("volunteerName", volunteerName);

            request.getRequestDispatcher("/volunteer/payment_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String eventIdParam = request.getParameter("eventId");
        String amountParam = request.getParameter("amount");
        String note = request.getParameter("note");

        // Lấy volunteerId từ session
        HttpSession session = request.getSession();
        Integer volunteerId = (Integer) session.getAttribute("accountId");

        // Ưu tiên lấy từ Account object nếu có
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            volunteerId = account.getId();
        }

        if (volunteerId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);
            double amount = Double.parseDouble(amountParam);

            // FIX: Validate số tiền (< 10000, không phải <=)
            if (amount < 10000) {
                session.setAttribute("errorMessage", "Số tiền phải lớn hơn hoặc bằng 10.000 VNĐ!");
                response.sendRedirect(request.getContextPath() + "/VolunteerPaymentServlet?eventId=" + eventId);
                return;
            }

            // Sinh mã QR
            String qrCode = donationService.generateQRCode(volunteerId, amount);

            // Lưu donation vào DB
            boolean success = donationService.createDonation(
                    eventId, volunteerId, amount,
                    "QR", qrCode, note
            );

            if (success) {
                session.setAttribute("successMessage", "Gửi đơn donate thành công! Vui lòng chờ duyệt.");
                response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
            } else {
                session.setAttribute("errorMessage", "Gửi đơn thất bại! Vui lòng thử lại.");
                response.sendRedirect(request.getContextPath() + "/VolunteerPaymentServlet?eventId=" + eventId);
            }

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Số tiền không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
        }

    }

    @Override
    public void destroy() {
        // Đóng service khi servlet bị hủy (shutdown server)
        if (donationService != null) {
            donationService.close();
        }
    }
}
