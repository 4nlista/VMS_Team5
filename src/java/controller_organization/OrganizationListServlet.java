package controller_organization;

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
import service.AccountService;
import service.OrganizationEventsService;

@WebServlet(name = "OrganizationListServlet", urlPatterns = {"/OrganizationListServlet"})

public class OrganizationListServlet extends HttpServlet {

    private AccountService accountService;
    private OrganizationEventsService organizationEventsService;

    @Override
    public void init() {
        accountService = new AccountService();
        organizationEventsService = new OrganizationEventsService();
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
        acc = accountService.getAccountById(acc.getId()); // cập nhật từ DB
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }
        String category = request.getParameter("category");    // giá trị dropdown
        String status = request.getParameter("status");
        String visibility = request.getParameter("visibility");
        int organizationId = acc.getId();
        List<Event> eventsOrg = organizationEventsService.getEventsByOrganization(organizationId);
        List<Event> filterEvents = organizationEventsService.getEventsByOrganizationFiltered(organizationId, category, status, visibility);

        request.setAttribute("eventsOrg", filterEvents);
        request.setAttribute("filterEvents", filterEvents);
        // Gửi sang JSP
        request.setAttribute("account", acc);
        request.getRequestDispatcher("/organization/events_org.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
