package controller_admin;

import service.AdminEventsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminEventLockServlet", urlPatterns = {"/AdminEventLockServlet"})
public class AdminEventLockServlet extends HttpServlet {

    private final AdminEventsService service = new AdminEventsService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            boolean success = service.lockEvent(eventId);
            
            // Preserve filter parameters and page number
            String status = request.getParameter("status");
            String category = request.getParameter("category");
            String visibility = request.getParameter("visibility");
            String page = request.getParameter("page");
            
            StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/AdminEventsServlet?");
            if (success) {
                redirectUrl.append("lockSuccess=true");
            } else {
                redirectUrl.append("lockError=true");
            }
            
            if (status != null && !status.isEmpty()) {
                redirectUrl.append("&status=").append(status);
            }
            if (category != null && !category.isEmpty()) {
                redirectUrl.append("&category=").append(category);
            }
            if (visibility != null && !visibility.isEmpty()) {
                redirectUrl.append("&visibility=").append(visibility);
            }
            if (page != null && !page.isEmpty()) {
                redirectUrl.append("&page=").append(page);
            }
            
            response.sendRedirect(redirectUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AdminEventsServlet?lockError=true");
        }
    }
}

