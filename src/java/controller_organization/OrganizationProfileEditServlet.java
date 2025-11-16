/*
 * A friendly reminder to drink enough water
 */
package controller_organization;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.OrganizationProfileDAO;
import model.User;
import service.OrganizationProfileService;
import service.UnifiedImageUploadService;

/**
 *
 * @author Mirinae
 */
@WebServlet(name = "OrganizationProfileEditServlet", urlPatterns = {"/OrganizationProfileEdit"})
@MultipartConfig
public class OrganizationProfileEditServlet extends HttpServlet {

    private final OrganizationProfileService service = new OrganizationProfileService();
    private final UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
    private final OrganizationProfileDAO dao = new OrganizationProfileDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = service.loadForEdit(request); // loads by ?id=
            request.setAttribute("user", user);
            request.getRequestDispatcher("/organization/edit_org_profile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/OrganizationProfileDetail?error=load");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // First attempt to validate and update text fields
        boolean textUpdated = service.updateProfileText(request);
        if (textUpdated) {
            // parse id safely
            int userId = -1;
            try {
                userId = Integer.parseInt(request.getParameter("id"));
            } catch (Exception ignored) {
            }
            // Upload avatar using UnifiedImageUploadService
            Map<String, Object> uploadResult = uploadService.uploadAvatar(request, userId, "avatar");
            
            if ((boolean) uploadResult.get("success")) {
                String fileName = (String) uploadResult.get("fileName");
                dao.updateAvatar(userId, fileName);
                response.sendRedirect(request.getContextPath() + "/OrganizationProfileDetail?id=" + userId + "&success=updated");
                return;
            } else {
                String errorMsg = (String) uploadResult.get("error");
                Map<String, String> errors = new java.util.HashMap<>();
                errors.put("avatar", errorMsg);
                request.setAttribute("errors", errors);
                // avatar validation failed -> forward back, but preserve submitted textual values
                try {
                    // load DB user so avatarPreview can show existing avatar (not strictly necessary)
                    User user = service.loadForEdit(request);
                    request.setAttribute("user", user);
                } catch (Exception ignored) {
                }

                // preserve submitted values so form is repopulated (service.updateProfileText
                // sets attributes only on text validation failure; here text succeeded so copy now)
                request.setAttribute("full_name", request.getParameter("full_name"));
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("phone", request.getParameter("phone"));
                request.setAttribute("address", request.getParameter("address"));
                request.setAttribute("job_title", request.getParameter("job_title"));
                request.setAttribute("bio", request.getParameter("bio"));
                request.setAttribute("dob", request.getParameter("dob"));

                request.getRequestDispatcher("/organization/edit_org_profile.jsp").forward(request, response);
                return;
            }
        } else {
            // text validation failed -> updateProfileText already attached "errors" and request attributes for submitted fields
            try {
                User user = service.loadForEdit(request);
                request.setAttribute("user", user);
            } catch (Exception ignored) {
            }
            request.getRequestDispatcher("/organization/edit_org_profile.jsp").forward(request, response);
            return;
        }
    }
}