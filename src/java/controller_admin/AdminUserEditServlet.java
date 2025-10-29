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
		AdminUserService userService = new AdminUserService();
		boolean textUpdated = userService.adminUserEdit(request);

		if (textUpdated) {
			// parse id safely
			int userId = -1;
			try {
				userId = Integer.parseInt(request.getParameter("id"));
			} catch (Exception ignored) {
			}

			// handle avatar: this returns false on validation failure and sets request.errors
			boolean avatarOk = userService.handleAvatarUpload(request, userId);

			if (avatarOk) {
				response.sendRedirect("AdminUserServlet?id=" + userId);
			} else {
				// avatar validation failed -> forward back to edit with errors (do not redirect)
				try {
					User user = userService.loadUserDetail(request);
					request.setAttribute("user", user);
				} catch (Exception ignored) {
				}
				request.getRequestDispatcher("/admin/edit_user_admin.jsp").forward(request, response);
			}
		} else {
			// text validation failed -> updateProfileText already attached "errors"
			try {
				User user = userService.loadUserDetail(request);
				request.setAttribute("user", user);
			} catch (Exception ignored) {
			}
			request.getRequestDispatcher("/admin/edit_user_admin.jsp").forward(request, response);
		}
	}
}
