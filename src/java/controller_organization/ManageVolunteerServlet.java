package controller_organization;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EventService;
import java.io.IOException;

@WebServlet("/organization/manageVolunteer")
public class ManageVolunteerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int evId = Integer.parseInt(request.getParameter("ev_id"));
        String action = request.getParameter("action");  // "approve" hoặc "reject"
        String newStatus = "approved".equals(action) ? "approved" : "rejected";

        EventService service = new EventService();
        boolean success = service.updateVolunteerStatus(evId, newStatus);
        if (success) {
            // Gửi thông báo (sử dụng EmailService hoặc Notifications)
            // Ví dụ: service.sendNotification(volunteerId, message);
           
        }
        response.sendRedirect("viewEvent?id=" + request.getParameter("event_id"));
    }
}