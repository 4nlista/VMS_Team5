package controller_organization;

import dao.EventDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Event;

@WebServlet("/OrganizationEventServlet")
public class OrganizationEventServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch(action) {
            case "list":
                showEventList(request, response);
                break;
            case "createForm":
                request.getRequestDispatcher("/organization/events_create_org.jsp").forward(request, response);
                break;
            case "delete":
                deleteEvent(request, response);
                break;
            default:
                showEventList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("create".equals(action)) {
            createEvent(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/OrganizationEventServlet?action=list");
        }
    }

    // ===== METHODS =====
    private void showEventList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer orgAccountId = (Integer) request.getSession().getAttribute("accountId");
        if (orgAccountId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        List<Event> events = eventDAO.getEventsByOrganization(orgAccountId);
        request.setAttribute("events", events);
        request.getRequestDispatcher("/organization/events_org.jsp").forward(request, response);
    }

    private void deleteEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer orgAccountId = (Integer) request.getSession().getAttribute("accountId");
        if (orgAccountId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("eventId"));
        eventDAO.deleteEvent(id);
        response.sendRedirect(request.getContextPath() + "/OrganizationEventServlet?action=list");
    }

    private void createEvent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        Integer orgAccountId = (Integer) request.getSession().getAttribute("accountId");
        if (orgAccountId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        Event e = new Event();
        e.setTitle(request.getParameter("title"));
        e.setDescription(request.getParameter("description"));
        e.setStartDate(java.sql.Timestamp.valueOf(request.getParameter("start_date") + " 00:00:00"));
        e.setEndDate(java.sql.Timestamp.valueOf(request.getParameter("end_date") + " 23:59:59"));
        e.setLocation(request.getParameter("location"));
        e.setNeededVolunteers(Integer.parseInt(request.getParameter("needed_volunteers")));
        e.setStatus("active");
        e.setOrganizationId(orgAccountId);
        e.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        e.setTotalDonation(Double.parseDouble(request.getParameter("total_donation")));

        eventDAO.addEvent(e);
        response.sendRedirect(request.getContextPath() + "/OrganizationEventServlet?action=list");
    }
}
