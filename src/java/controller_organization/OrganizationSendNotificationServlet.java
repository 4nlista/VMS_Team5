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
        try {
            // Lấy params để hiển thị form
            String eventIdStr = request.getParameter("eventId");
            String volunteerId = request.getParameter("volunteerId");
            String sendType = request.getParameter("sendType"); // "individual" hoặc "all"
            
            // Validate eventId
            if (eventIdStr == null || eventIdStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
                return;
            }
            
            int eventId = Integer.parseInt(eventIdStr);
            
            // Log ra console để kiểm tra
            System.out.println("DEBUG doGet - eventId: " + eventId + ", volunteerId: " + volunteerId + ", sendType: " + sendType);

            // Set lại params cho JSP
            request.setAttribute("eventId", eventId);
            request.setAttribute("volunteerId", volunteerId);
            request.setAttribute("sendType", sendType);

            // Forward đến form tương ứng
            if ("individual".equals(sendType)) {
                if (volunteerId == null || volunteerId.trim().isEmpty()) {
                    System.out.println("ERROR - Missing volunteerId for individual notification");
                    response.sendRedirect(request.getContextPath() + "/OrganizationVolunteersServlet?eventId=" + eventId);
                    return;
                }
                request.getRequestDispatcher("/organization/send_notification_org.jsp").forward(request, response);
            } else if ("all".equals(sendType)) {
                request.getRequestDispatcher("/organization/send_all_notification_org.jsp").forward(request, response);
            } else {
                System.out.println("ERROR - Invalid sendType: " + sendType);
                response.sendRedirect(request.getContextPath() + "/OrganizationVolunteersServlet?eventId=" + eventId);
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR doGet - NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
        } catch (Exception e) {
            System.out.println("ERROR doGet - Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
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
        System.out.println("DEBUG doPost - eventId: " + eventId + ", volunteerId: " + volunteerIdStr + ", sendType: " + sendType);
        try {

            // Case 1: Gửi cá nhân
            if ("individual".equals(sendType) && volunteerIdStr != null && !volunteerIdStr.trim().isEmpty()) {
                int volunteerId = Integer.parseInt(volunteerIdStr);
                System.out.println("DEBUG - Sending individual notification to volunteerId: " + volunteerId);
                resultMessage = service.sendIndividualNotification(senderId, eventId, volunteerId, message, type);
            } // Case 2: Gửi chung
            else if ("all".equals(sendType)) {
                System.out.println("DEBUG - Sending notification to all volunteers");
                resultMessage = service.sendAllNotification(senderId, eventId, message, type);
            } else {
                resultMessage = "Loại gửi không hợp lệ hoặc thiếu thông tin volunteer!";
                System.out.println("ERROR - Invalid sendType or missing volunteerId");
            }

            // Set message và redirect về list volunteers
            session.setAttribute("message", resultMessage);
            response.sendRedirect(request.getContextPath() + "/OrganizationVolunteersServlet?eventId=" + eventId);

        } catch (NumberFormatException e) {
            System.out.println("ERROR - NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("message", "Dữ liệu không hợp lệ: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/OrganizationVolunteersServlet?eventId=" + eventId);
        } catch (Exception e) {
            System.out.println("ERROR - Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/OrganizationVolunteersServlet?eventId=" + eventId);
        }
    }
}
