package controller_admin;

import service.AdminEventsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminEventDetailServlet", urlPatterns = {"/AdminEventDetailServlet"})
public class AdminEventDetailServlet extends HttpServlet {

    private final AdminEventsService service = new AdminEventsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            service.loadEventDetail(request);
            request.getRequestDispatcher("/admin/detail_events_admin.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading event detail", e);
        }
    }
}

