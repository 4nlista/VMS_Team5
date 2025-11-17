package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import dao.ViewEventsDAO;
import model.Account;
import model.Event;
import service.DisplayEventService;
import service.VolunteerApplyService;

/**
 * Servlet for volunteers to explore/browse all available events
 * Similar to GuessEventServlet but specifically for volunteers
 */
@WebServlet(name = "VolunteerExploreEventServlet", urlPatterns = {"/VolunteerExploreEventServlet"})
public class VolunteerExploreEventServlet extends HttpServlet {

    private DisplayEventService displayService;
    private VolunteerApplyService volunteerapplyService;
    private ViewEventsDAO viewEventsDAO;

    @Override
    public void init() {
        displayService = new DisplayEventService();
        volunteerapplyService = new VolunteerApplyService();
        viewEventsDAO = new ViewEventsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in as volunteer
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        if (acc == null || !"volunteer".equals(acc.getRole())) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Integer volunteerId = acc.getId();

        int page = 1;
        int limit = 3;
        String pageParam = request.getParameter("page");

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int offset = (page - 1) * limit;
        
        List<Event> events = displayService.getActiveEventsPagedWithStatus(offset, limit, volunteerId);
        
        // Set volunteer-specific information for each event
        // Note: hasDonated is already set by getActiveEventsPagedWithStatus
        for (Event e : events) {
            boolean hasApplied = volunteerapplyService.hasApplied(volunteerId, e.getId());
            int rejectedCount = volunteerapplyService.countRejected(e.getId(), volunteerId);
            boolean isFull = volunteerapplyService.isEventFull(e.getId());
            
            // hasDonated is already set by getActiveEventsPagedWithStatus, but we ensure it's set
            if (!e.isHasDonated()) {
                boolean hasDonated = viewEventsDAO.hasVolunteerDonated(volunteerId, e.getId());
                e.setHasDonated(hasDonated);
            }

            e.setHasApplied(hasApplied);
            e.setRejectedCount(rejectedCount);
            e.setIsFull(isFull);
        }

        int totalEvents = displayService.getTotalActiveEvents();
        int totalPages = (int) Math.ceil((double) totalEvents / limit);

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("events", events);
        request.getRequestDispatcher("event.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

