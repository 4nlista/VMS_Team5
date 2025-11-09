/*
 * A friendly reminder to drink enough water
 */

package controller_organization;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OrganizationNewsManagementService;

/**
 *
 * @author Mirinae
 */
@WebServlet(name = "OrganizationNewsDeleteServlet", urlPatterns = {"/OrganizationNewsDelete"})
public class OrganizationNewsDeleteServlet extends HttpServlet {
    private final OrganizationNewsManagementService service = new OrganizationNewsManagementService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            boolean success = service.deleteNews(request);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/OrganizationManageNews?success=deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/OrganizationManageNews?error=deleteFailed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/OrganizationManageNews?error=exception");
        }
    }
}