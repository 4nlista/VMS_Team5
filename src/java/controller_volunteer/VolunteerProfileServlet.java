package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import model.Account;
import model.ProfileVolunteer;
import service.VolunteerProfileService;

@WebServlet(name = "VolunteerProfileServlet", urlPatterns = {"/VolunteerProfileServlet"})
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 6 * 1024 * 1024)
public class VolunteerProfileServlet extends HttpServlet {

    private VolunteerProfileService volunteerProfileService;

    @Override
    public void init() throws ServletException {
        volunteerProfileService = new VolunteerProfileService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = resolveVolunteerAccount(request, response);
        if (account == null) {
            return;
        }

        ProfileVolunteer profile = volunteerProfileService.loadProfile(account.getId());
        if (profile == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hồ sơ tình nguyện viên.");
            return;
        }

        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("cancel".equalsIgnoreCase(action)) {
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        Account account = resolveVolunteerAccount(request, response);
        if (account == null) {
            return;
        }

        ProfileVolunteer existingProfile = volunteerProfileService.loadProfile(account.getId());
        if (existingProfile == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hồ sơ tình nguyện viên.");
            return;
        }

        VolunteerProfileService.UpdateResult result = volunteerProfileService.processUpdate(request, existingProfile);
        if (result.isSuccess()) {
            HttpSession session = request.getSession();
            session.setAttribute("message", result.getMessage());
            session.setAttribute("messageType", "success");
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        request.setAttribute("profile", result.getProfile());
        Map<String, String> errors = result.getErrors();
        if (errors != null && !errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }
        request.setAttribute("message", result.getMessage());
        request.setAttribute("messageType", "danger");
        request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
    }

    private Account resolveVolunteerAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return null;
        }
        Account account = (Account) session.getAttribute("account");
        if (account == null || !"volunteer".equalsIgnoreCase(account.getRole())) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return null;
        }
        return account;
    }
}
