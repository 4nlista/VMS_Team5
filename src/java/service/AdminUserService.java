/*
 * A friendly reminder to drink enough water
 */
package service;

import dao.AdminUserDAO;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import model.User;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

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
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("invalid_username_empty");
		}
		if (username.length() < 3 || username.length() > 16) {
			throw new IllegalArgumentException("invalid_username_length_should_be_between_3_and_16");
		}
		// allow letters, numbers, dot, underscore, dash
		if (!username.matches("^[A-Za-z0-9_.-]+$")) {
			throw new IllegalArgumentException("invalid_username_format");
		}

		String fullName = request.getParameter("full_name");
		if (fullName == null || fullName.isEmpty()) {
			throw new IllegalArgumentException("invalid_fullname_empty");
		}
		if (fullName.length() > 26) {
			throw new IllegalArgumentException("invalid_fullname_length_longer_than_26");
		}

		String gender = request.getParameter("gender");

		String phone = request.getParameter("phone");
		if (phone == null || phone.isEmpty()) {
			throw new IllegalArgumentException("invalid_phonenumber_empty");
		}

		String email = request.getParameter("email");
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("invalid_email_empty");
		}
		if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
			throw new IllegalArgumentException("invalid_email_format");
		}

		String address = request.getParameter("address");
		if (address == null || address.isEmpty()) {
			throw new IllegalArgumentException("invalid_address_empty");
		}

		String jobTitle = request.getParameter("job_title");
		if (jobTitle == null || jobTitle.isEmpty()) {
			throw new IllegalArgumentException("invalid_jobtitle_empty");
		}

		//Bio can be empty cuz why not
		String bio = request.getParameter("bio");

		String dobStr = request.getParameter("dob");
		java.sql.Date dob = null;
		if (dobStr != null && !dobStr.trim().isEmpty()) {
			String s = dobStr.trim();
			LocalDate localDate = null;

			// Try multiple possible formats
			List<DateTimeFormatter> fmts = Arrays.asList(
				    DateTimeFormatter.ISO_LOCAL_DATE, // yyyy-MM-dd
				    DateTimeFormatter.ofPattern("d/M/yyyy"), // 1/2/2020 or 01/2/2020
				    DateTimeFormatter.ofPattern("dd/MM/yyyy") // 01/02/2020
			);

			for (DateTimeFormatter fmt : fmts) {
				try {
					localDate = LocalDate.parse(s, fmt);
					break;
				} catch (DateTimeParseException e) {
					// try next
				}
			}

			if (localDate == null) {
				throw new IllegalArgumentException("invalid_dob");
			}
			dob = java.sql.Date.valueOf(localDate);
		}

		try {
			AdminUserDAO dao = new AdminUserDAO();
			return dao.updateUser(id, username, fullName, gender, phone, email, address, jobTitle, bio, dob);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
