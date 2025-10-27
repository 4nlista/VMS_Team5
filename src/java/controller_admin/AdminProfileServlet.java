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

/**
 *
 * @author Mirinesa
 */
@WebServlet(name = "AdminProfileServlet", urlPatterns = {"/AdminProfileServlet"})
public class AdminProfileServlet extends HttpServlet {

	private AdminUserDAO userDAO = new AdminUserDAO();

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			User user = userDAO.getUserDetailById(id);

			if (user == null) {
				response.sendRedirect("AdminProfileServlet?error=UserNotFound");
				return;
			}

			request.setAttribute("user", user);
			request.getRequestDispatcher("/admin/profile_admin.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("AdminProfileServlet?error=InvalidRequest");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		processRequest(request, response);
	}
}
