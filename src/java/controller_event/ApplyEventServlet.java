package controller_event;

import dao.EventVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.EventVolunteer;

@WebServlet(name = "ApplyEventServlet", urlPatterns = {"/ApplyEventServlet"})
public class ApplyEventServlet extends HttpServlet {

    private EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        // Nếu chưa đăng nhập, chuyển hướng sang login
        if (accountId == null) {
            response.sendRedirect("/auth/login.jsp");
            return;
        }

        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            // Tạo đối tượng EventVolunteer
            EventVolunteer ev = new EventVolunteer();
            ev.setEventId(eventId);
            ev.setVolunteerId(accountId);

            // Gọi DAO để apply
            boolean success = eventVolunteerDAO.applyForEvent(ev);

            if (success) {
                request.setAttribute("message", "Đã gửi yêu cầu tham gia sự kiện thành công! Vui lòng chờ phê duyệt.");
            } else {
                request.setAttribute("message", "️ Bạn đã apply sự kiện này rồi hoặc có lỗi xảy ra!");
            }

            // Quay lại trang danh sách sự kiện
            request.getRequestDispatcher("apply_event_volunteer.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", " Lỗi hệ thống khi apply sự kiện!");
            request.getRequestDispatcher("apply_event_volunteer.jsp").forward(request, response);
        }
    }
}
