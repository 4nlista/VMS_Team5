package controller_admin;

import service.AdminUserService;
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

		int page = 1;
		String pageParam = request.getParameter("page");
		if (pageParam != null) {
			try {
				page = Integer.parseInt(pageParam);
			} catch (NumberFormatException ignored) {
			}
		}
		
		String role = request.getParameter("role");
		String search = request.getParameter("search");
		String sort = request.getParameter("sort");

		try {
			AdminUserService service = new AdminUserService();

			List<User> users = service.getUsersByPage(page, role, search, sort);
			int totalPages = service.getTotalPages(role, search);

			request.setAttribute("users", users);
			request.setAttribute("currentPage", page);
			request.setAttribute("totalPages", totalPages);
			
			request.setAttribute("currentRole", role == null ? "" : role);
			request.setAttribute("currentSearch", search == null ? "" : search);
			request.setAttribute("currentSort", sort == null ? "" : sort);

			request.getRequestDispatcher("/admin/manage_user_admin.jsp").forward(request, response);

		} catch (Exception e) {
			throw new ServletException("Error loading users", e);
		}
	}

	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

	}
}
