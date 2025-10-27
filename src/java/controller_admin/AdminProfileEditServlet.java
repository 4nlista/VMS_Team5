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
			try {
				int userId = Integer.parseInt(request.getParameter("id"));
				profileService.handleAvatarUpload(request, userId);
				response.sendRedirect("AdminUserServlet?success=updated");
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("AdminUserServlet?error=updateFailed");
			}
		} else {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				User user = profileService.loadProfileById(request);
				request.setAttribute("user", user);
			} catch (Exception ignored) {
			}
			request.getRequestDispatcher("/admin/profile_admin.jsp").forward(request, response);
		}
	}
}
