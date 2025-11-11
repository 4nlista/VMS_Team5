package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import model.Feedback;
import service.VolunteerFeedbackService;

@WebServlet(name = "VolunteerFeedbackServlet", urlPatterns = {"/VolunteerFeedbackServlet"})

public class VolunteerFeedbackServlet extends HttpServlet {

    private VolunteerFeedbackService feedbackService;

    @Override
    public void init() throws ServletException {
        feedbackService = new VolunteerFeedbackService();
    }

    /**
     * GET: Hiển thị form feedback (tạo mới hoặc xem/sửa)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session và kiểm tra đăng nhập
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null || !"volunteer".equals(account.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy eventId từ request
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect("VolunteerEventServlet");
            return;
        }

        int eventId = Integer.parseInt(eventIdParam);
        int volunteerId = account.getId();

        // Kiểm tra điều kiện được feedback
        if (!feedbackService.canVolunteerFeedback(eventId, volunteerId)) {
            request.setAttribute("errorMessage", "Bạn chưa đủ điều kiện để đánh giá sự kiện này!");
            request.getRequestDispatcher("VolunteerEventServlet").forward(request, response);
            return;
        }

        // Lấy thông tin feedback hoặc event info
        Feedback feedback = feedbackService.getFeedbackOrEventInfo(eventId, volunteerId);

        if (feedback == null) {
            request.setAttribute("errorMessage", "Không tìm thấy thông tin sự kiện!");
            request.getRequestDispatcher("VolunteerEventServlet").forward(request, response);
            return;
        }

        // Kiểm tra đã feedback chưa
        boolean hasFeedback = feedbackService.hasFeedback(eventId, volunteerId);

        // Set attributes
        request.setAttribute("feedback", feedback);
        request.setAttribute("hasFeedback", hasFeedback);

        // Forward đến JSP
        request.getRequestDispatcher("volunteer/feedback_volunteer.jsp").forward(request, response);
    }

    /**
     * POST: Xử lý tạo mới, cập nhật, hoặc xóa feedback
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Lấy session và kiểm tra đăng nhập
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null || !"volunteer".equals(account.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy action từ form
        String action = request.getParameter("action");
        String eventIdParam = request.getParameter("eventId");

        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect("VolunteerEventServlet");
            return;
        }

        int eventId = Integer.parseInt(eventIdParam);
        int volunteerId = account.getId();

        boolean success = false;
        String message = "";

        // Xử lý theo action
        switch (action) {
            case "create":
                success = handleCreate(request, eventId, volunteerId);
                message = success ? "Gửi đánh giá thành công!" : "Gửi đánh giá thất bại!";
                break;

            case "update":
                success = handleUpdate(request, eventId, volunteerId);
                message = success ? "Cập nhật đánh giá thành công!" : "Cập nhật đánh giá thất bại!";
                break;

            case "delete":
                success = handleDelete(eventId, volunteerId);
                message = success ? "Xóa đánh giá thành công!" : "Xóa đánh giá thất bại!";
                break;

            default:
                response.sendRedirect("VolunteerEventServlet");
                return;
        }

        // Set message vào session và redirect
        session.setAttribute("feedbackMessage", message);
        session.setAttribute("feedbackSuccess", success);
        response.sendRedirect("VolunteerEventServlet");
    }

    /**
     * Xử lý tạo feedback mới
     */
    private boolean handleCreate(HttpServletRequest request, int eventId, int volunteerId) {
        try {
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            return feedbackService.createFeedback(eventId, volunteerId, rating, comment);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xử lý cập nhật feedback
     */
    private boolean handleUpdate(HttpServletRequest request, int eventId, int volunteerId) {
        try {
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            return feedbackService.updateFeedback(eventId, volunteerId, rating, comment);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xử lý xóa feedback
     */
    private boolean handleDelete(int eventId, int volunteerId) {
        return feedbackService.deleteFeedback(eventId, volunteerId);
    }

    @Override
    public String getServletInfo() {
        return "Volunteer Feedback Servlet";
    }
}
