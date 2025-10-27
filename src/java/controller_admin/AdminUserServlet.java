package controller_admin;

import service.AdminUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminUserServlet", urlPatterns = {"/AdminUserServlet"})

public class AdminUserServlet extends HttpServlet {

	private final AdminUserService service = new AdminUserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		try {
			service.loadUserList(request);
			request.getRequestDispatcher("/admin/manage_user_admin.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException("Error loading users", e);
		}
	}
}
