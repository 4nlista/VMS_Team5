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
@WebServlet(name = "AdminUserDetailServlet", urlPatterns = {"/AdminUserDetailServlet"})
public class AdminUserDetailServlet extends HttpServlet {

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		processRequest(request, response);
	}
}
