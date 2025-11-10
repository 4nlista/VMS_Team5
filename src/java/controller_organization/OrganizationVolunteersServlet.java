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
import model.ProfileVolunteer;
import dao.ProfileVolunteerDAO;
import service.AccountService;


@WebServlet(name = "OrganizationVolunteersServlet", urlPatterns = {"/OrganizationVolunteersServlet"})
public class OrganizationVolunteersServlet extends HttpServlet {

    private AccountService accountService;
    private ProfileVolunteerDAO profileVolunteerDAO;

    @Override
    public void init() {
        accountService = new AccountService();
        profileVolunteerDAO = new ProfileVolunteerDAO();
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
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        int organizationId = acc.getId();
        
        String eventIdParam = request.getParameter("eventId");
        String genderFilter = request.getParameter("gender");
        if (genderFilter == null || genderFilter.isEmpty()) {
            genderFilter = "all";
        }
        String nameQuery = request.getParameter("q");
        String eventTitleQuery = request.getParameter("eventTitle");

        List<ProfileVolunteer> profiles;
        Integer eventId = null;
        
        // Nếu có eventId, lấy volunteers của sự kiện đó
        if (eventIdParam != null && !eventIdParam.isEmpty()) {
            try {
                eventId = Integer.parseInt(eventIdParam);
                profiles = profileVolunteerDAO.getProfilesByEvent(organizationId, eventId, genderFilter, nameQuery);
            } catch (NumberFormatException e) {
                // Nếu eventId không hợp lệ, lấy tất cả volunteers
                profiles = profileVolunteerDAO.getProfilesByOrganization(
                        organizationId, genderFilter, nameQuery, eventTitleQuery);
            }
        } else {
            // Không có eventId, lấy tất cả volunteers của organization
            profiles = profileVolunteerDAO.getProfilesByOrganization(
                    organizationId, genderFilter, nameQuery, eventTitleQuery);
        }

        request.setAttribute("profiles", profiles);
        request.setAttribute("gender", genderFilter);
        request.setAttribute("q", nameQuery);
        request.setAttribute("eventTitle", eventTitleQuery);
        request.setAttribute("eventId", eventId);
        request.setAttribute("account", acc);
        
        request.getRequestDispatcher("/organization/users_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/OrganizationVolunteersServlet");
    }
}


