package controller_organization;

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
 * Servlet for displaying donation detail page for organizations
 */
@WebServlet(name = "OrganizationDonationDetailServlet", urlPatterns = {"/OrganizationDonationDetailServlet"})
public class OrganizationDonationDetailServlet extends HttpServlet {

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

        if (acc == null || !acc.getRole().equals("organization")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        // Get donation ID from parameter
        String donationIdParam = request.getParameter("donationId");
        String eventIdParam = request.getParameter("eventId");
        System.out.println("==> OrganizationDonationDetailServlet - donationId: " + donationIdParam + ", eventId: " + eventIdParam);

        if (donationIdParam == null || donationIdParam.isEmpty()) {
            session.setAttribute("errorMessage", "Không tìm thấy mã giao dịch!");
            response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventIdParam);
            return;
        }

        try {
            int donationId = Integer.parseInt(donationIdParam);
            int eventId = eventIdParam != null ? Integer.parseInt(eventIdParam) : 0;

            // Get event to verify organization owns this event
            Event event = displayEventService.getEventById(eventId);
            if (event == null || event.getOrganizationId() != acc.getId()) {
                session.setAttribute("errorMessage", "Bạn không có quyền xem giao dịch này!");
                response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
                return;
            }

            // Get donation detail - using volunteer's getDonationById with volunteer_id from donation
            Donation donation = displayDonateService.getDonationByIdForOrganization(donationId, eventId);

            if (donation == null) {
                session.setAttribute("errorMessage", "Không tìm thấy giao dịch!");
                response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
                return;
            }

            // Get return page parameter (for back button)
            String returnPage = request.getParameter("page");
            if (returnPage == null || returnPage.isEmpty()) {
                returnPage = "1";
            }

            // Set attributes
            request.setAttribute("donation", donation);
            request.setAttribute("event", event);
            request.setAttribute("returnPage", returnPage);

            request.getRequestDispatcher("/organization/detail_donation_org.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Mã giao dịch không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
