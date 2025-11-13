package controller_volunteer;

import dao.AdminUserDAO;
import dao.NotificationDAO;
import dao.ViewEventsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import model.Event;
import model.Feedback;
import model.Notification;
import model.User;
import service.VolunteerFeedbackService;

@WebServlet(name = "VolunteerFeedbackServlet", urlPatterns = {"/VolunteerFeedbackServlet"})

public class VolunteerFeedbackServlet extends HttpServlet {

    private VolunteerFeedbackService feedbackService;

    @Override
    public void init() throws ServletException {
        feedbackService = new VolunteerFeedbackService();
    }

    //  GET: Hiển thị form feedback (tạo mới hoặc xem/sửa)
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

        // Kiểm tra điều kiện được feedback với thông báo chi tiết
        String errorMessage = feedbackService.checkFeedbackEligibilityMessage(eventId, volunteerId);
        if (errorMessage != null) {
            session.setAttribute("errorMessage", errorMessage);
            response.sendRedirect("VolunteerEventServlet");
            return;
        }

        // Lấy thông tin feedback hoặc event info
        Feedback feedback = feedbackService.getFeedbackOrEventInfo(eventId, volunteerId);

        if (feedback == null) {
            session.setAttribute("errorMessage", "Không tìm thấy thông tin sự kiện!");
            response.sendRedirect("VolunteerEventServlet");
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

    // POST: Xử lý tạo mới hoặc cập nhật feedback
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
     * Xử lý gửi feedback mới
     */
    private boolean handleCreate(HttpServletRequest request, int eventId, int volunteerId) {
        try {
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");
            boolean success = feedbackService.createFeedback(eventId, volunteerId, rating, comment);

            // GỬI THÔNG BÁO CHO ORG
            if (success) {
                try {
                    AdminUserDAO userDAO = new AdminUserDAO();
                    User volUser = userDAO.getUserByAccountId(volunteerId);
                    String volName = (volUser != null) ? volUser.getFull_name() : "Tình nguyện viên";

                    ViewEventsDAO eventDAO = new ViewEventsDAO();
                    Event event = eventDAO.getEventById(eventId);

                    if (event != null) {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(volunteerId);
                        noti.setReceiverId(event.getOrganizationId());
                        noti.setMessage("Tình nguyện viên " + volName + " đã gửi đánh giá " + rating + " sao về sự kiện \"" + event.getTitle() + "\"");
                        noti.setType("system");
                        noti.setEventId(eventId);

                        notiDAO.insertNotification(noti);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return success;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xử lý cập nhật feedback
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

    @Override
    public String getServletInfo() {
        return "Volunteer Feedback Servlet";
    }
}
