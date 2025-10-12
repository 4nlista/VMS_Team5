package controller_admin;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.User;

@WebServlet(name = "AdminUserServlet", urlPatterns = {"/AdminUserServlet"})

public class AdminUserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		UserDAO dao = new UserDAO();
		List<User> users = dao.getAllUsers();
		request.setAttribute("users", users);
		request.getRequestDispatcher("/admin/users_admin.jsp").forward(request, response);
	}

	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

	}
}
