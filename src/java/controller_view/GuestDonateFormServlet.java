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
 * Servlet dùng để hiển thị form quyên góp cho khách (guest)
 * URL: /donate_form.jsp (có thể truy cập trực tiếp bằng JSP)
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

        // Lấy `eventId` từ tham số URL
        String eventIdParam = request.getParameter("eventId");

        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);

            // Lấy thông tin event bằng service
            Event event = donationService.getEventById(eventId);

            if (event == null) {
                request.setAttribute("error", "Không tìm thấy sự kiện!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // Đưa thông tin event vào request để JSP hiển thị
            request.setAttribute("event", event);
            request.setAttribute("eventId", eventId);

            // Chuyển tiếp đến trang form quyên góp
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
