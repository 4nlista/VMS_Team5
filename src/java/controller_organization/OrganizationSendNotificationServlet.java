package controller_organization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import service.OrganizationSendNotificationService;

@WebServlet(name = "OrganizationSendNotificationServlet", urlPatterns = {"/OrganizationSendNotificationServlet"})

public class OrganizationSendNotificationServlet extends HttpServlet {

    private OrganizationSendNotificationService service;

    @Override
    public void init() throws ServletException {
        service = new OrganizationSendNotificationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy params để hiển thị form
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String volunteerId = request.getParameter("volunteerId");
        String sendType = request.getParameter("sendType"); // "individual" hoặc "all"
        
        
        // Log ra console để kiểm tra
        System.out.println("DEBUG - eventId: " + eventId + ", volunteerId: " + volunteerId);

        // Set lại params cho JSP
        request.setAttribute("eventId", eventId);
        request.setAttribute("volunteerId", volunteerId);
        request.setAttribute("sendType", sendType);

        // Forward đến form tương ứng
        if ("individual".equals(sendType)) {
            request.getRequestDispatcher("/organization/send_notification_org.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/organization/send_all_notification_org.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy senderId từ session
        HttpSession session = request.getSession();
        Integer senderId = (Integer) session.getAttribute("accountId");

        if (senderId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Lấy params
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String volunteerIdStr = request.getParameter("volunteerId");
        String sendType = request.getParameter("sendType");
        String message = request.getParameter("message");
        String type = request.getParameter("type"); // reminder, system, ...

        String resultMessage = "";

        // Log ra console để kiểm tra
        System.out.println("DEBUG - eventId: " + eventId + ", volunteerId: " + volunteerIdStr);
        try {

            // Case 1: Gửi cá nhân
            if ("individual".equals(sendType) && volunteerIdStr != null) {
                int volunteerId = Integer.parseInt(volunteerIdStr);
                resultMessage = service.sendIndividualNotification(senderId, eventId, volunteerId, message, type);
            } // Case 2: Gửi chung
            else if ("all".equals(sendType)) {
                resultMessage = service.sendAllNotification(senderId, eventId, message, type);
            } else {
                resultMessage = "Loại gửi không hợp lệ!";
            }

            // Set message và redirect về list volunteers
            session.setAttribute("message", resultMessage);
            response.sendRedirect(request.getContextPath() + "/OrganizationVolunteersServlet?eventId=" + eventId);

        } catch (NumberFormatException e) {
            session.setAttribute("message", "Dữ liệu không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
        }
    }
}
