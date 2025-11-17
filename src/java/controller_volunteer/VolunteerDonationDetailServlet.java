package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import model.Donation;
import model.Event;
import service.AccountService;
import service.DisplayDonateService;
import service.DisplayEventService;

/**
 * Servlet for displaying donation detail page for volunteers
 */
@WebServlet(name = "VolunteerDonationDetailServlet", urlPatterns = {"/VolunteerDonationDetailServlet"})
public class VolunteerDonationDetailServlet extends HttpServlet {

    private DisplayDonateService displayDonateService;
    private AccountService accountService;
    private DisplayEventService displayEventService;

    @Override
    public void init() {
        displayDonateService = new DisplayDonateService();
        accountService = new AccountService();
        displayEventService = new DisplayEventService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());

        if (acc == null || !acc.getRole().equals("volunteer")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        // Get donation ID from parameter
        String donationIdParam = request.getParameter("donationId");
        System.out.println("==> VolunteerDonationDetailServlet - donationIdParam: " + donationIdParam);

        if (donationIdParam == null || donationIdParam.isEmpty()) {
            session.setAttribute("errorMessage", "Không tìm thấy mã giao dịch!");
            response.sendRedirect(request.getContextPath() + "/VolunteerDonateServlet");
            return;
        }

        try {
            int donationId = Integer.parseInt(donationIdParam);
            int volunteerId = acc.getId();

            System.out.println("==> VolunteerDonationDetailServlet - Calling getDonationById with donationId: " + donationId + ", volunteerId: " + volunteerId);

            // Get donation detail (only if it belongs to this volunteer)
            Donation donation = displayDonateService.getDonationById(donationId, volunteerId);

            System.out.println("==> VolunteerDonationDetailServlet - donation result: " + (donation != null ? "Found" : "NULL"));

            if (donation == null) {
                session.setAttribute("errorMessage", "Không tìm thấy giao dịch hoặc bạn không có quyền xem giao dịch này!");
                response.sendRedirect(request.getContextPath() + "/VolunteerDonateServlet");
                return;
            }

            // Get event information
            Event event = displayEventService.getEventById(donation.getEventId());
            if (event == null) {
                session.setAttribute("errorMessage", "Không tìm thấy thông tin sự kiện!");
                response.sendRedirect(request.getContextPath() + "/VolunteerDonateServlet");
                return;
            }

            // Get return page and filter parameters (for back button)
            String returnPage = request.getParameter("page");
            if (returnPage == null || returnPage.isEmpty()) {
                returnPage = "1";
            }
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String statusFilter = request.getParameter("status");

            // Set attributes
            request.setAttribute("donation", donation);
            request.setAttribute("event", event);
            request.setAttribute("returnPage", returnPage);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("statusFilter", statusFilter);

            request.getRequestDispatcher("/volunteer/detail_history_payment_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Mã giao dịch không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/VolunteerDonateServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

