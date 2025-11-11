package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.Event;
import model.EventVolunteer;
import model.User;
import service.AdminUserService;
import service.DisplayEventService;
import service.VolunteerApplyService;

@WebServlet(name = "VolunteerApplyEventServlet", urlPatterns = {"/VolunteerApplyEventServlet"})

public class VolunteerApplyEventServlet extends HttpServlet {

    private VolunteerApplyService volunteerApplyService;
    private DisplayEventService displayEventService;
    private AdminUserService adminUserService;

    @Override
    public void init() {
        volunteerApplyService = new VolunteerApplyService();
        displayEventService = new DisplayEventService();
        adminUserService = new AdminUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        User user = adminUserService.getUserByAccountId(acc.getId());

        // Lấy eventId từ request (từ link chi tiết sự kiện)
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam == null) {
            // Nếu không có eventId, có thể redirect về danh sách event
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(eventIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        int volunteerId = acc.getId();

        // Lấy thông tin sự kiện từ DB
        Event event = displayEventService.getEventById(eventId);
        if (event == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sự kiện không tồn tại");
            return;
        }

        boolean isRegistered = volunteerApplyService.hasApplied(volunteerId, eventId);
        int rejectedCount = volunteerApplyService.countRejected(eventId, volunteerId);
        boolean isFull = volunteerApplyService.isEventFull(eventId);

        // Lấy danh sách các sự kiện mà volunteer đã apply
        List<EventVolunteer> myApplications = volunteerApplyService.getMyApplications(volunteerId);

        // Lấy message từ session (nếu có)
        String message = (String) session.getAttribute("message");
        String messageType = (String) session.getAttribute("messageType");

        // Gửi dữ liệu sang JSP
        session.removeAttribute("message");
        session.removeAttribute("messageType");

        request.setAttribute("isRegistered", isRegistered);
        request.setAttribute("rejectedCount", rejectedCount);
        request.setAttribute("isFull", isFull);
        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);
        request.setAttribute("event", event);
        request.setAttribute("volunteerId", volunteerId);
        request.setAttribute("myApplications", myApplications);
        request.setAttribute("user", user);

        request.getRequestDispatcher("/volunteer/apply_event_volunteer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");

        // Validate và parse parameters
        String eventIdParam = request.getParameter("eventId");
        String note = request.getParameter("note");

        // Validation
        if (eventIdParam == null) {
            session.setAttribute("message", "Thiếu thông tin bắt buộc!");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }
        int eventId = Integer.parseInt(eventIdParam);

        try {
            int rejectedCount = volunteerApplyService.countRejected(eventId, acc.getId());
            if (rejectedCount >= 3) {
                session.setAttribute("message", "Bạn đã bị từ chối 3 lần. Không thể đăng ký lại!");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/VolunteerApplyEventServlet?eventId=" + eventId);
                return;
            }
            boolean success = volunteerApplyService.applyToEvent(acc.getId(), eventId, note);

            if (success) {
                session.setAttribute("message", "Đăng ký sự kiện thành công!");
                session.setAttribute("messageType", "success");
                response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
                return;
            } else {
                session.setAttribute("message", "Bạn đã đăng ký sự kiện này rồi!");
                session.setAttribute("messageType", "warning");
                response.sendRedirect(request.getContextPath() + "/VolunteerApplyEventServlet?eventId=" + eventId);
                return;
            }

        } catch (IllegalArgumentException e) {
            session.setAttribute("message", e.getMessage());
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }
    }
}
