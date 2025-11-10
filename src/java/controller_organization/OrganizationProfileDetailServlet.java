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
import model.User;
import service.OrganizationProfileService;

/**
 *
 * @author Mirinae
 */
@WebServlet(name="OrganizationProfileDetailServlet", urlPatterns={"/OrganizationProfileDetail"})
public class OrganizationProfileDetailServlet extends HttpServlet {
	private final OrganizationProfileService service = new OrganizationProfileService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            User profile = service.loadProfile(request);
            request.setAttribute("profile", profile);
	 request.getSession().setAttribute("user", profile);
            request.getRequestDispatcher("/organization/profile_org.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
