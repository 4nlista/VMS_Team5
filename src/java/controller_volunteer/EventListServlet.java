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

@WebServlet("/EventListServlet")
public class EventListServlet extends HttpServlet {
    private EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Event> events = eventDAO.getAllEvents();
        request.setAttribute("events", events);
        request.getRequestDispatcher("event.jsp").forward(request, response);
    }
}