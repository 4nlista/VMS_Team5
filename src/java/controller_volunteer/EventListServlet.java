package controller_volunteer;

import dao.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Event;

@WebServlet(name = "VolunteerEventListServlet", urlPatterns = {"/VolunteerEventListServlet"})
public class EventListServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Event> events = eventDAO.getAllAvailableEvents();
        request.setAttribute("events", events);

        request.getRequestDispatcher("detail_event_volunteer.jsp").forward(request, response);
    }
}
