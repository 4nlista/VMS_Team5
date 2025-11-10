package controller_volunteer;

import dao.EventVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.EventVolunteer;
import service.VolunteerCancelService;

@WebServlet(name = "CancelApplicationServlet", urlPatterns = {"/CancelApplicationServlet"})
public class VolunteerCancelApplicationServlet extends HttpServlet {

    private final VolunteerCancelService volunteerCancelService = new VolunteerCancelService();
    private final EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();

    /**
     * Hiển thị thông tin đơn đăng ký để xác nhận hủy
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            int volunteerId = Integer.parseInt(request.getParameter("volunteerId"));

            // Lấy thông tin đơn đăng ký
            EventVolunteer registration = eventVolunteerDAO.getRegistrationByEventAndVolunteer(eventId, volunteerId);

            if (registration == null) {
                request.getSession().setAttribute("message", "Không tìm thấy đơn đăng ký!");
                response.sendRedirect(request.getContextPath() + "/volunteer/history_event.jsp");
                return;
            }

            // Đưa dữ liệu qua JSP
            request.setAttribute("registration", registration);
            request.setAttribute("event", registration); // có thể đổi nếu bạn có model Event riêng
            request.getRequestDispatcher("/volunteer/cancel_application_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Dữ liệu không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
        }
    }

    /**
     * Xử lý hành động hủy đăng ký
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            int volunteerId = Integer.parseInt(request.getParameter("volunteerId"));

            // Gọi service hủy đơn
            String message = volunteerCancelService.cancelApplication(eventId, volunteerId);

            // Lưu thông báo
            request.getSession().setAttribute("message", message);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Dữ liệu không hợp lệ!");
            e.printStackTrace();
        }

        // Quay lại lịch sử sau khi xử lý
        response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
    }
}
