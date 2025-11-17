package controller_view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Event;
import service.GuestDonationService;

import java.io.IOException;

/**
 * Servlet for displaying donation form for guests
 * URL: /donate_form.jsp (accessed directly as JSP)
 */
@WebServlet(name = "GuestDonateFormServlet", urlPatterns = {"/GuestDonateFormServlet"})
public class GuestDonateFormServlet extends HttpServlet {

    private GuestDonationService donationService;

    @Override
    public void init() {
        donationService = new GuestDonationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get eventId from URL parameter
        String eventIdParam = request.getParameter("eventId");

        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);

            // Get event information using service
            Event event = donationService.getEventById(eventId);

            if (event == null) {
                request.setAttribute("error", "Không tìm thấy sự kiện!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // Pass event information to JSP
            request.setAttribute("event", event);
            request.setAttribute("eventId", eventId);

            // Forward to donation form
            request.getRequestDispatcher("/donate_form.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        if (donationService != null) {
            donationService.close();
        }
    }
}
