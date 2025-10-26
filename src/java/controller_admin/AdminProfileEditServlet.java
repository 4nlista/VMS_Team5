/*
 * A friendly reminder to drink enough water
 */
package controller_admin;

import dao.AdminUserDAO;
import service.AdminUserService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Mirinae
 */
@WebServlet(name = "AdminProfileEditServlet", urlPatterns = {"/AdminProfileEditServlet"})
public class AdminProfileEditServlet extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		AdminUserDAO dao = new AdminUserDAO();
		User user = dao.getUserDetailById(userId);

		if (user == null) {
			response.sendRedirect("AdminProfileServlet?error=notfound");
			return;
		}

		request.setAttribute("user", user);
		request.getRequestDispatcher("/admin/profile_edit_admin.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		AdminUserService service = new AdminUserService();
		boolean updated = service.adminUserEdit(request);

		if (updated) {
			response.sendRedirect("AdminProfileServlet?id=1");
			return;
		} else {
			// validation failed or DAO update failed: forward back to edit page and show errors
			// try to fetch user so JSP can still show avatar/id etc.
			String idStr = request.getParameter("id");
			if (idStr != null) {
				try {
					int id = Integer.parseInt(idStr);
					AdminUserDAO dao = new AdminUserDAO();
					User user = dao.getUserDetailById(id);
					if (user != null) {
						request.setAttribute("user", user);
					}
				} catch (Exception e) {
					// ignore: if we cannot fetch user, the JSP will still render using request params
					e.printStackTrace();
				}
			}

			// Forward (not redirect) so request attributes (errors + submitted fields) are available
			request.getRequestDispatcher("/admin/profile_edit_admin.jsp").forward(request, response);
		}
	}
}
