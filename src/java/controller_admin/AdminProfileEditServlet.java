/*
 * A friendly reminder to drink enough water
 */
package controller_admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.AdminProfileService;

/**
 *
 * @author Mirinae
 */
@MultipartConfig
@WebServlet(name = "AdminProfileEditServlet", urlPatterns = {"/AdminProfileEditServlet"})
public class AdminProfileEditServlet extends HttpServlet {

	private final AdminProfileService profileService = new AdminProfileService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		User user = profileService.loadDefaultProfile();
		request.setAttribute("user", user);
		request.getRequestDispatcher("/admin/profile_edit_admin.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

		boolean textUpdated = profileService.updateProfileText(request);

		if (textUpdated) {
			// parse id safely
			int userId = -1;
			try {
				userId = Integer.parseInt(request.getParameter("id"));
			} catch (Exception ignored) {
			}

			// handle avatar: this returns false on validation failure and sets request.errors
			boolean avatarOk = profileService.handleAvatarUpload(request, userId);

			if (avatarOk) {
				response.sendRedirect("AdminProfileServlet?id=1");
			} else {
				// avatar validation failed -> forward back to edit with errors (do not redirect)
				try {
					User user = profileService.loadProfileById(request);
					request.setAttribute("user", user);
				} catch (Exception ignored) {
				}
				request.getRequestDispatcher("/admin/profile_edit_admin.jsp").forward(request, response);
			}
		} else {
			// text validation failed -> updateProfileText already attached "errors"
			try {
				User user = profileService.loadProfileById(request);
				request.setAttribute("user", user);
			} catch (Exception ignored) {
			}
			request.getRequestDispatcher("/admin/profile_edit_admin.jsp").forward(request, response);
		}
	}
}
