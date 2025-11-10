package controller_organization;

import dao.ProfileVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;
import model.ProfileVolunteer;
import service.AccountService;

@WebServlet(name = "OrganizationVolunteerDetailServlet", urlPatterns = {"/OrganizationVolunteerDetailServlet"})
public class OrganizationVolunteerDetailServlet extends HttpServlet {

    private AccountService accountService;
    private ProfileVolunteerDAO profileVolunteerDAO;
// oke em nhé 
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

        String volunteerIdParam = request.getParameter("volunteerId");
        if (volunteerIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu volunteerId");
            return;
        }
        int volunteerAccountId = Integer.parseInt(volunteerIdParam);
        String eventIdParam = request.getParameter("eventId");

        ProfileVolunteer profile = profileVolunteerDAO.getProfileByAccountIdAndOrganization(
                volunteerAccountId,
                acc.getId()
        );
        if (profile == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hồ sơ");
            return;
        }

        // Lấy toàn bộ tiêu đề sự kiện volunteer này đã tham gia trong tổ chức hiện tại
        java.util.List<String> eventsTitles = profileVolunteerDAO.getEventTitlesForVolunteerInOrganization(
                volunteerAccountId, acc.getId());
        request.setAttribute("profile", profile);
        request.setAttribute("eventsTitles", eventsTitles);
        request.getRequestDispatcher("/organization/profile_org.jsp").forward(request, response);
    }
}


