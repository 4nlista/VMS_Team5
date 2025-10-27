/*
 * A friendly reminder to drink enough water
 */
package controller_admin;

import service.AdminUserService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import jakarta.servlet.annotation.MultipartConfig;

/**
 *
 * @author Mirinesa
 */
@MultipartConfig
@WebServlet(name = "AdminUserEditServlet", urlPatterns = {"/AdminUserEditServlet"})
public class AdminUserEditServlet extends HttpServlet {

	private final AdminUserService service = new AdminUserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		try {
			User user = service.loadUserDetail(request);
			if (user == null) {
				response.sendRedirect("AdminUserServlet?error=notfound");
				return;
			}
			request.setAttribute("user", user);
			request.getRequestDispatcher("/admin/edit_user_admin.jsp").forward(request, response);
		} catch (Exception e) {
			response.sendRedirect("AdminUserServlet?error=invalid");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		AdminUserService service = new AdminUserService();
		boolean textUpdated = service.adminUserEdit(request);

		if (textUpdated) {
			try {
				int userId = Integer.parseInt(request.getParameter("id"));
				service.handleAvatarUpload(request, userId);
				response.sendRedirect("AdminUserServlet?success=updated");
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("AdminUserServlet?error=updateFailed");
			}
		} else {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				User user = service.loadUserDetail(request);
				request.setAttribute("user", user);
			} catch (Exception ignored) {
			}
			request.getRequestDispatcher("/admin/edit_user_admin.jsp").forward(request, response);
		}
	}
}
