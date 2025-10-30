package controller_volunteer;

import dao.EventDAO;
import dao.EventVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Event;

@WebServlet(name = "EventDetailServlet", urlPatterns = {"/eventDetail"})
public class EventDetailServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();
    private EventVolunteerDAO evDao = new EventVolunteerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String eventIdParam = request.getParameter("id");
        String applyIdParam = request.getParameter("applyId");
        int eventId = -1;

        try {
            if (eventIdParam != null && !eventIdParam.isEmpty()) {
                // Nếu URL truyền thẳng eventId
                eventId = Integer.parseInt(eventIdParam);
            } else if (applyIdParam != null && !applyIdParam.isEmpty()) {
                // Nếu URL truyền applyId → lấy eventId từ DAO
                int applyId = Integer.parseInt(applyIdParam);
                int[] volunteerAndEvent = evDao.getVolunteerAndEventByApplyId(applyId);
                if (volunteerAndEvent != null && volunteerAndEvent.length == 2) {
                    eventId = volunteerAndEvent[1]; // index 1 là eventId
                }
            }

            if (eventId == -1) {
                // Không có eventId và applyId hợp lệ
                response.sendRedirect(request.getContextPath() + "/EventListServlet");
                return;
            }

            Event event = eventDAO.getEventById(eventId);
            if (event == null) {
                request.setAttribute("errorMessage", "Không tìm thấy sự kiện!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("event", event);
            request.getRequestDispatcher("/volunteer/detail_event_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/EventListServlet");
        }
    }
}
