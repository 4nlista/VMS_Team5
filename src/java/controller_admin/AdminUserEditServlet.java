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
 * @author Mirinesa
 */
@WebServlet(name = "AdminUserEditServlet", urlPatterns = {"/AdminUserEditServlet"})
public class AdminUserEditServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		AdminUserDAO dao = new AdminUserDAO();
		User user = dao.getUserDetailById(userId);

		if (user == null) {
			response.sendRedirect("AdminUserServlet?error=notfound");
			return;
		}

		request.setAttribute("user", user);
		request.getRequestDispatcher("/admin/edit_user_admin.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		AdminUserService service = new AdminUserService();
		boolean updated = service.adminUserEdit(request);

		if (updated) {
			response.sendRedirect("AdminUserServlet?success=updated");
		} else {
			int id = Integer.parseInt(request.getParameter("id"));
			response.sendRedirect("AdminUserEditServlet?id=" + id + "&error=failed");
		}
	}
}
