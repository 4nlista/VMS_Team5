/*
 * A friendly reminder to drink enough water
 */
package controller_admin;

import dao.UserDAO;
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
		UserDAO dao = new UserDAO();
		User user = dao.getUserById(userId);

		if (user == null) {
			response.sendRedirect("AdminUserServlet?error=notfound");
			return;
		}

		request.setAttribute("user", user);
		request.getRequestDispatcher("/admin/edit_user.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String fullName = request.getParameter("full_name");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String jobTitle = request.getParameter("job_title");
		String bio = request.getParameter("bio");

		UserDAO dao = new UserDAO();
		boolean updated = dao.updateUser(id, fullName, gender, phone, email, address, jobTitle, bio);

		if (updated) {
			response.sendRedirect("AdminUserServlet?success=updated");
		} else {
			response.sendRedirect("AdminEditUserServlet?id=" + id + "&error=failed");
		}
	}
}
