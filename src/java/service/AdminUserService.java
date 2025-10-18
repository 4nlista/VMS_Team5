/*
 * A friendly reminder to drink enough water
 */
package service;

import dao.AdminUserDAO;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import model.User;
import java.sql.SQLException;

/**
 *
 * @author Mirinae
 */
public class AdminUserService {

	private final AdminUserDAO userDAO = new AdminUserDAO();
	private final int pageSize = 6; // configurable if needed
	//AdminUserEdit (Update user details as admin)

	public boolean adminUserEdit(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		String fullName = request.getParameter("full_name");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String jobTitle = request.getParameter("job_title");
		String bio = request.getParameter("bio");

		AdminUserDAO dao = new AdminUserDAO();
		return dao.updateUser(id, username, fullName, gender, phone, email, address, jobTitle, bio);
	}

	//Pagination logic
	public List<User> getUsersByPage(int page) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return userDAO.getAllUsersWithPagination(page, pageSize);
	}

	//For filter
	public List<User> getUsersByPage(int page, String role, String search, String sort) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return userDAO.getUsersWithFiltersAndPagination(page, pageSize, role, search, sort);
	}

	public int getTotalPages() throws SQLException {
		int totalUsers = userDAO.getTotalUserCount();
		return (int) Math.ceil((double) totalUsers / pageSize);
	}

	//For filter
	public int getTotalPages(String role, String search) throws SQLException {
		int totalUsers = userDAO.getFilteredUserCount(role, search);
		return (int) Math.ceil((double) totalUsers / pageSize);
	}
}
