package controller_organization;

import dao.EventDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Event;
import dao.CategoriesDAO;
import model.Category;
import java.util.List;

@WebServlet("/OrganizationEventEditServlet")
public class OrganizationEventEditServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();
    private CategoriesDAO categoriesDAO = new CategoriesDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventDAO.getEventById(eventId);

        if (event == null) {
            response.sendRedirect(request.getContextPath() + "/OrganizationEventServlet?action=list");
            return;
        }

        List<Category> categories = categoriesDAO.getAllCategories();

        request.setAttribute("event", event);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/organization/events_details_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Event e = new Event();
        e.setId(Integer.parseInt(request.getParameter("id")));
        e.setTitle(request.getParameter("title"));
        e.setDescription(request.getParameter("description"));
        e.setStartDate(java.sql.Timestamp.valueOf(request.getParameter("start_date") + " 00:00:00"));
        e.setEndDate(java.sql.Timestamp.valueOf(request.getParameter("end_date") + " 23:59:59"));
        e.setLocation(request.getParameter("location"));
        e.setNeededVolunteers(Integer.parseInt(request.getParameter("needed_volunteers")));
        e.setStatus(request.getParameter("status"));
        e.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        e.setTotalDonation(Double.parseDouble(request.getParameter("total_donation")));

        eventDAO.updateEvent(e);
        response.sendRedirect(request.getContextPath() + "/OrganizationEventServlet?action=list");
    }
}
