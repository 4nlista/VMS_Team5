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
import service.NewsManagementService;

/**
 *
 * @author Mirinae
 */
@WebServlet(name = "OrganizationManageNews", urlPatterns = {"/OrganizationManageNews"})
public class OrganizationManageNewsServlet extends HttpServlet {

	private final NewsManagementService service = new NewsManagementService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		try {
			service.loadNewsList(request);
			request.getRequestDispatcher("/organization/manage_new_org.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException("Error loading news", e);
		}
	}
}
