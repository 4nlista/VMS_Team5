//package controller_volunteer;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.util.List;
//import model.Account;
//import model.Event;
//import model.EventVolunteer;
//import model.User;
//import service.AdminUserService;
//import service.DisplayEventService;
//import service.VolunteerApplyService;
//
//@WebServlet(name = "VolunteerApplyEventServlet", urlPatterns = {"/VolunteerApplyEventServlet"})
//
//public class VolunteerApplyEventServlet extends HttpServlet {
//
//    private VolunteerApplyService volunteerApplyService;
//    private DisplayEventService displayEventService;
//    private AdminUserService adminUserService;
//
//    @Override
//    public void init() {
//        volunteerApplyService = new VolunteerApplyService();
//        displayEventService = new DisplayEventService();
//        adminUserService = new AdminUserService();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("account") == null) {
//            response.sendRedirect(request.getContextPath() + "/LoginServlet");
//            return;
//        }
//
//        Account acc = (Account) session.getAttribute("account");
//        User user = adminUserService.getUserByAccountId(acc.getId());
//
//        // Lấy eventId từ request (từ link chi tiết sự kiện)
//        String eventIdParam = request.getParameter("eventId");
//        if (eventIdParam == null) {
//            // Nếu không có eventId, có thể redirect về danh sách event
//            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
//            return;
//        }
//        int eventId = Integer.parseInt(eventIdParam);
//        int volunteerId = acc.getId();
//        // Lấy thông tin sự kiện từ DB
//        Event event = displayEventService.getEventById(eventId);
//        if (event == null) {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sự kiện không tồn tại");
//            return;
//        }
//
//        // Lấy danh sách các sự kiện mà volunteer đã apply
//        List<EventVolunteer> myApplications = volunteerApplyService.getMyApplications(volunteerId);
//        // Lấy message từ session (nếu có)
//        String message = (String) session.getAttribute("message");
//        String messageType = (String) session.getAttribute("messageType");
//
//        // Gửi dữ liệu sang JSP
//        session.removeAttribute("message");
//        session.removeAttribute("messageType");
//
//        request.setAttribute("message", message);
//        request.setAttribute("messageType", messageType);
//
//        request.setAttribute("event", event);                    // Thông tin event đang xem
//        request.setAttribute("volunteerId", volunteerId);        // ID volunteer để pre-fill form
//        request.setAttribute("myApplications", myApplications);  // Lịch sử apply nếu cần hiển thị
//        request.setAttribute("user", user);
//        request.getRequestDispatcher("/volunteer/apply_event_volunteer.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("account") == null) {
//            response.sendRedirect(request.getContextPath() + "/LoginServlet");
//            return;
//        }
//
//        Account acc = (Account) session.getAttribute("account");
//
//        // Lấy các param từ form
//        int eventId = Integer.parseInt(request.getParameter("eventId"));
//        int hours = Integer.parseInt(request.getParameter("hours"));
//        String note = request.getParameter("note");
//        String applyDateStr = request.getParameter("applyDate"); // nếu muốn dùng ngày đăng ký
//        java.sql.Date applyDate = null;
//        if (applyDateStr != null && !applyDateStr.isEmpty()) {
//            applyDate = java.sql.Date.valueOf(applyDateStr);
//        }
//
//        try {
//            boolean success = volunteerApplyService.applyToEvent(acc.getId(), eventId, hours, note);
//
//            if (success) {
//                session.setAttribute("message", "Đăng ký sự kiện thành công!");
//                session.setAttribute("messageType", "success");
//                response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
//                return;
//            } else {
//                session.setAttribute("message", "Bạn đã đăng ký sự kiện này rồi!");
//                session.setAttribute("messageType", "warning");
//                response.sendRedirect(request.getContextPath() + "/VolunteerApplyEventServlet?eventId=" + eventId);
//                return;
//            }
//
//        } catch (IllegalArgumentException e) {
//            session.setAttribute("message", e.getMessage());
//            session.setAttribute("messageType", "error");
//            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
//            return;
//        }
//
//    }
//}
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

        // Lấy danh sách các sự kiện mà volunteer đã apply
        List<EventVolunteer> myApplications = volunteerApplyService.getMyApplications(volunteerId);

        // Lấy message từ session (nếu có)
        String message = (String) session.getAttribute("message");
        String messageType = (String) session.getAttribute("messageType");

        // Gửi dữ liệu sang JSP
        session.removeAttribute("message");
        session.removeAttribute("messageType");

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
        String hoursParam = request.getParameter("hours");
        String note = request.getParameter("note");

        // Validation
        if (eventIdParam == null || hoursParam == null) {
            session.setAttribute("message", "Thiếu thông tin bắt buộc!");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        int eventId;
        int hours;

        try {
            eventId = Integer.parseInt(eventIdParam);
            hours = Integer.parseInt(hoursParam);

            // Validate hours phải là số dương
            if (hours <= 0) {
                session.setAttribute("message", "Số giờ tình nguyện phải lớn hơn 0!");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/VolunteerApplyEventServlet?eventId=" + eventIdParam);
                return;
            }

        } catch (NumberFormatException e) {
            session.setAttribute("message", "Dữ liệu không hợp lệ!");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        try {
            boolean success = volunteerApplyService.applyToEvent(acc.getId(), eventId, hours, note);

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
