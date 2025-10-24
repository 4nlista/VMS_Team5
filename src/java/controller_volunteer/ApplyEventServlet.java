package controller_volunteer;

import dao.EventVolunteerDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.EventVolunteer;
import model.User;

@WebServlet(name = "ApplyEventServlet", urlPatterns = {"/ApplyEventServlet"})
public class ApplyEventServlet extends HttpServlet {

    private EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();
    private UserDAO userDAO = new UserDAO(); // 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            User user = userDAO.getUserByAccountId(accountId);
            if (user == null) {
                session.setAttribute("applyMessage", "Không tìm thấy thông tin volunteer!");
                response.sendRedirect("volunteer/apply_event_volunteer.jsp");
                return;
            }

            EventVolunteer ev = new EventVolunteer();
            ev.setEventId(eventId);
            ev.setVolunteerId(accountId);

            boolean success = eventVolunteerDAO.applyForEvent(ev);

            if (success) {
                session.setAttribute("applyMessage", "Đã gửi yêu cầu tham gia sự kiện thành công!");
            } else {
                session.setAttribute("applyMessage", "️Bạn đã apply sự kiện này rồi hoặc có lỗi xảy ra!");
            }

    
            response.sendRedirect("volunteer/apply_event_volunteer.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("applyMessage", "Lỗi hệ thống khi apply sự kiện!");
            response.sendRedirect("volunteer/apply_event_volunteer.jsp");
        }
    }

}
