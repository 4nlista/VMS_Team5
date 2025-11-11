/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.EventVolunteer;
import service.EventVolunteerService;
/**
 *
 * @author HP
 */
@WebServlet(name = "VolunteerEventServlet", urlPatterns = {"/VolunteerEventServlet"})
public class VolunteerHistoryEventServlet extends HttpServlet {

     private EventVolunteerService service = new EventVolunteerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer volunteerId = (Integer) session.getAttribute("accountId");

        if (volunteerId != null) {
            List<EventVolunteer> registrations = service.getEventRegistrations(volunteerId);
            request.setAttribute("eventRegistrations", registrations);
        }

        request.getRequestDispatcher("/volunteer/history_event_volunteer.jsp").forward(request, response);
    }
}
