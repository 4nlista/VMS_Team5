package controller_view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.Event;
import service.DisplayEventService;
import service.VolunteerApplyService;

@WebServlet(name = "GuessEventServlet", urlPatterns = {"/GuessEventServlet"})
public class GuessEventServlet extends HttpServlet {

    private DisplayEventService displayService;
    private VolunteerApplyService volunteerapplyService;

    @Override
    public void init() {
        displayService = new DisplayEventService();
        volunteerapplyService = new VolunteerApplyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        HttpSession session = request.getSession(false);
        Integer volunteerId = null;

        if (session != null) {
            Account acc = (Account) session.getAttribute("account");
            if (acc != null && "volunteer".equals(acc.getRole())) {
                volunteerId = acc.getId();
            }
        }
        
        List<Event> events = displayService.getActiveEventsPagedWithStatus(offset, limit, volunteerId);
        if (volunteerId != null) {
            for (Event e : events) {
                boolean hasApplied = volunteerapplyService.hasApplied(volunteerId, e.getId());
                int rejectedCount = volunteerapplyService.countRejected(e.getId(), volunteerId);
                boolean isFull = volunteerapplyService.isEventFull(e.getId());  

                e.setHasApplied(hasApplied);
                e.setRejectedCount(rejectedCount);
                e.setIsFull(isFull);  // ✅ THÊM
            }
        } else {
            // Nếu chưa login thì vẫn check full
            for (Event e : events) {
                boolean isFull = volunteerapplyService.isEventFull(e.getId());
                e.setIsFull(isFull);
            }
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
