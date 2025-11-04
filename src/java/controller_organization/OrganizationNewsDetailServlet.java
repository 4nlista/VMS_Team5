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
import model.New;
import service.OrganizationNewsManagementService;

/**
 *
 * @author Mirinae
 */
@WebServlet(name="OrganizationNewsDetailServlet", urlPatterns={"/OrganizationNewsDetail"})
public class OrganizationNewsDetailServlet extends HttpServlet {
		private final OrganizationNewsManagementService service = new OrganizationNewsManagementService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
	    try {
			New news = service.loadNewsDetail(request);
			if (news == null) {
				response.sendRedirect("OrganizationManageNews?error=NewsNotFound");
				return;
			}
			request.setAttribute("news", news);
			request.getRequestDispatcher("/organization/detail_news_org.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("OrganizationManageNews?error=InvalidRequest");
		}
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

}
