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
    private UserDAO userDAO = new UserDAO(); // ✅ thêm DAO lấy thông tin volunteer

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
                request.setAttribute("message", "Không tìm thấy thông tin volunteer!");
                request.getRequestDispatcher("/volunteer/apply_event_volunteer.jsp").forward(request, response);
                return;
            }

    
            EventVolunteer ev = new EventVolunteer();
            ev.setEventId(eventId);
            ev.setVolunteerId(accountId);

            boolean success = eventVolunteerDAO.applyForEvent(ev);

            if (success) {
                request.setAttribute("message", "Đã gửi yêu cầu tham gia sự kiện thành công!");
            } else {
                request.setAttribute("message", "️Bạn đã apply sự kiện này rồi hoặc có lỗi xảy ra!");
            }

          
            request.setAttribute("user", user);
            request.setAttribute("eventId", eventId);


            request.getRequestDispatcher("/volunteer/apply_event_volunteer.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi hệ thống khi apply sự kiện!");
            request.getRequestDispatcher("volunteer/apply_event_volunteer.jsp").forward(request, response);
        }
    }
}
