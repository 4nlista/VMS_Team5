package controller_volunteer;

import dao.EventVolunteerDAO;
import model.EventVolunteer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "VolunteerEventDetailServlet", urlPatterns = {"/VolunteerEventDetailServlet"})
public class VolunteerEventDetailServlet extends HttpServlet {

    private EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy eventId và volunteerId từ param (hoặc session)
        String eventIdStr = request.getParameter("eventId");
        HttpSession session = request.getSession(false);
        Integer volunteerId = (session != null && session.getAttribute("account") != null)
                ? (Integer) ((model.Account) session.getAttribute("account")).getId()
                : null;

        if (eventIdStr == null || volunteerId == null) {
            request.setAttribute("message", "Thiếu thông tin sự kiện hoặc tình nguyện viên.");
            request.getRequestDispatcher("/VolunteerEventServlet").forward(request, response);
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);

            // Lấy thông tin chi tiết đơn đăng ký của volunteer
            EventVolunteer ev = eventVolunteerDAO.getRegistrationByEventAndVolunteer(eventId, volunteerId);

            List<EventVolunteer> eventDetails = new ArrayList<>();
            if (ev != null) {
                eventDetails.add(ev); // JSP của bạn dùng for each
            }

            request.setAttribute("eventDetails", eventDetails);
            request.getRequestDispatcher("/volunteer/detail_event_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "EventId không hợp lệ.");
            request.getRequestDispatcher("/VolunteerEventServlet").forward(request, response);
        }
    }
}
