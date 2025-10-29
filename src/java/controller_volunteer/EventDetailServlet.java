/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_volunteer;

import dao.EventDAO;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy eventId từ URL
        String eventIdParam = request.getParameter("id");
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect("EventListServlet");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);
            Event event = eventDAO.getEventById(eventId);

            if (event == null) {
                request.setAttribute("errorMessage", "Không tìm thấy sự kiện!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("event", event);
            request.getRequestDispatcher("/volunteer/detail_event_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("/eventDetail");
        }
    }
}
