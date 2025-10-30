/*
 * A friendly reminder to drink enough water
 */
package controller_admin;

import dao.AdminUserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.AdminUserService;

/**
 *
 * @author Mirinesa
 */
@WebServlet(name = "AdminUserDetailServlet", urlPatterns = {"/AdminUserDetailServlet"})
public class AdminUserDetailServlet extends HttpServlet {

	private final AdminUserService service = new AdminUserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		try {
			User user = service.loadUserDetail(request);
			if (user == null) {
				response.sendRedirect("AdminUserServlet?error=UserNotFound");
				return;
			}
			request.setAttribute("user", user);
			request.getRequestDispatcher("/admin/detail_user_admin.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("AdminUserServlet?error=InvalidRequest");
		}
	}
}
