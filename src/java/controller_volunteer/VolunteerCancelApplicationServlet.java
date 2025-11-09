package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import service.VolunteerCancelService;

@WebServlet(name = "CancelApplicationServlet", urlPatterns = {"/CancelApplicationServlet"})
public class VolunteerCancelApplicationServlet extends HttpServlet {

   private final VolunteerCancelService volunteerCancelService = new VolunteerCancelService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Lấy eventId và volunteerId từ form
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            int volunteerId = Integer.parseInt(request.getParameter("volunteerId"));

            // Gọi service hủy đơn
            String message = volunteerCancelService.cancelApplication(eventId, volunteerId);

            // Lưu thông báo vào session để JSP hiển thị
            request.getSession().setAttribute("message", message);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Dữ liệu không hợp lệ!");
            e.printStackTrace();
        }

        // Điều hướng trở lại trang lịch sử đăng ký
        response.sendRedirect("volunteer_application_history.jsp");
    }
}