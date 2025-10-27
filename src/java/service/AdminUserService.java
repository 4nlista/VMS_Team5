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
import java.util.HashMap;
import java.util.Map;

/**
 * Handling servlet logic for Admin user management.
 * @author Mirinae
 */
public class AdminUserService {

	private final AdminUserDAO userDAO = new AdminUserDAO();
	private final int pageSize = 6; // configurable if needed
	// Returns true if update succeeded; if false, request will have "errors" Map<String,String>

	/**
	 * Handles the validation and update process for editing an admin user's information.
	 * It will retrieve form parameters using {@link HttpServletRequest} and validate the user inputs.
	 * If validation fails, it will send back the original inputs.
	 * If validation passes, it will send the new validated inputs.
	 * @param request the {@link HttpServletRequest} containing the form data and used to attach validation errors
	 * @return {@code true} if the user information was successfully validated and updated;
	 *		{@code false} if validation failed or the update could not be completed
	 */
	public boolean adminUserEdit(HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();

		// id parsing
		String idStr = request.getParameter("id");
		int id = -1;
		if (idStr == null || idStr.trim().isEmpty()) {
			errors.put("id", "invalid_id_missing");
		} else {
			try {
				id = Integer.parseInt(idStr.trim());
				if (id < 1) {
					errors.put("id", "invalid_id");
				}
			} catch (NumberFormatException e) {
				errors.put("id", "invalid_id_format");
			}
		}

		// collect raw parameters (trim where appropriate) so we can re-populate form easily
		String username = safeTrim(request.getParameter("username"));
		String fullName = safeTrim(request.getParameter("full_name"));
		String gender = safeTrim(request.getParameter("gender"));
		String phone = safeTrim(request.getParameter("phone"));
		String email = safeTrim(request.getParameter("email"));
		String address = safeTrim(request.getParameter("address"));
		String jobTitle = safeTrim(request.getParameter("job_title"));
		String bio = request.getParameter("bio"); // allow null/empty
		String dobStr = safeTrim(request.getParameter("dob"));

		// Validate username (Currently unused)
		/*
        if (username == null || username.isEmpty()) {
            errors.put("username", "Username cannot be empty.");
        } else if (username.length() < 3 || username.length() > 16) {
            errors.put("username", "Username length must be between 3 and 16 characters.");
        } else if (!username.matches("^[A-Za-z0-9_.-]+$")) {
            errors.put("username", "Username may contain only letters, numbers, dot, underscore or dash.");
        }
		 */
		
		// Full name
		if (fullName == null || fullName.isEmpty()) {
			errors.put("full_name", "Full name cannot be empty.");
		} else if (fullName.length() > 26) {
			errors.put("full_name", "Full name must be 26 characters or fewer.");
		} else if (!fullName.matches("^[\\p{L}\\s-]+$")) {
			errors.put("full_name","Full name must contain only letters.");
		}

		// Phone
		if (phone == null || phone.isEmpty()) {
			errors.put("phone", "Phone cannot be empty.");
		} else if (!phone.matches("^[0-9]+$")) {
			errors.put("phone", "Phone must contain digits only.");
		} else if (phone.length() < 10 || phone.length() > 11) {
			errors.put("phone", "Phone must be between 10 and 11 digits.");
		}

		// Email
		if (email == null || email.isEmpty()) {
			errors.put("email", "Email cannot be empty.");
		} else if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
			errors.put("email", "Invalid email format.");
		} else if (email.length() > 100) {
			errors.put("email", "Email must not exceed 100 characters.");
		}

		// Address
		if (address == null || address.isEmpty()) {
			errors.put("address", "Address cannot be empty.");
		} else if (address.length() <= 2) {
			errors.put("address", "No such place is 2 letters long.");
		} else if (!address.matches("^[\\p{L}\\s-]+$")) {
			errors.put("address", "Invalid address format");
		}

		// Job title
		if (jobTitle == null || jobTitle.isEmpty()) {
			errors.put("job_title", "Job title cannot be empty.");
		} else if (!jobTitle.matches("^[\\p{L}\\s-]+$")) {
			errors.put("job_title","Job title must contain only letters.");
		} else if (jobTitle.length() <= 2) {
			errors.put("job_title", "No such job is 2 letters long.");
		}

		// DOB parsing (optional)
		java.sql.Date dob = null;
		if (dobStr != null && !dobStr.isEmpty()) {
			String s = dobStr.trim();
			LocalDate localDate = null;

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
				errors.put("dob", "Invalid date of birth.");
			} else if (localDate.isAfter(LocalDate.now())) {
				errors.put("dob", "Date of birth cannot be in the future.");
			} else {
				dob = java.sql.Date.valueOf(localDate);
			}
		}

		// If there are validation errors, attach them to request and return false (no DAO call)
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			// Also set back the submitted values so JSP can prefer them
			request.setAttribute("username", username);
			request.setAttribute("full_name", fullName);
			request.setAttribute("gender", gender);
			request.setAttribute("phone", phone);
			request.setAttribute("email", email);
			request.setAttribute("address", address);
			request.setAttribute("job_title", jobTitle);
			request.setAttribute("bio", bio);
			request.setAttribute("dob", dobStr);
			return false;
		}

		// No validation errors: attempt DAO update
		try {
			boolean ok = userDAO.updateUser(id, username, fullName, gender, phone, email, address, jobTitle, bio, dob);
			if (!ok) {
				errors.put("general", "Failed to update user in database.");
				request.setAttribute("errors", errors);
				return false;
			}
			return true;
		} catch (Exception e) {
			// Log the exception (stack trace) and surface a general message
			e.printStackTrace();
			errors.put("general", "An unexpected error occurred while updating the user.");
			request.setAttribute("errors", errors);
			return false;
		}
	}

	/**
	 * Safely trims a string by removing leading and trailing whitespace.
	 * @param s The inputted string. The string itself can be null.
	 * @return The trimmed string, or null (instead of throwing a NullPointerException) if the inputted string were null.
	 */
	private String safeTrim(String s) {
		return (s == null) ? null : s.trim();
	}

	/**
	 * Get list of all users based on the page number.
	 * @param page The page number. If it's somehow less than 1, it will be set to 1.
	 * @return List of all users in a page based on the page number.
	 * @throws SQLException
	 */
	public List<User> getUsersByPage(int page) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return userDAO.getAllUsersWithPagination(page, pageSize);
	}

	/**
	 * Get list of all users based on the page number, now with filter support.
	 * @param page The specified page number, if it's less than 1, set to 1.
	 * @param role The role for filtering purpose.
	 * @param search Who to search for.
	 * @param sort What kind of sort, asc or desc?
	 * @return The list of user per page, with filter.
	 * @throws SQLException
	 */
	public List<User> getUsersByPage(int page, String role, String search, String sort) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return userDAO.getUsersWithFiltersAndPagination(page, pageSize, role, search, sort);
	}

	/**
	 * Method to get the current total number of pages.
	 * @return The number of total pages.
	 * @throws SQLException
	 */
	public int getTotalPages() throws SQLException {
		int totalUsers = userDAO.getTotalUserCount();
		return (int) Math.ceil((double) totalUsers / pageSize);
	}

	/**
	 * Get the total number of page, based on filter.
	 * @param role The role to filter.
	 * @param search The name to filter.
	 * @return The number of page, with filter.
	 * @throws SQLException
	 */
	public int getTotalPages(String role, String search) throws SQLException {
		int totalUsers = userDAO.getFilteredUserCount(role, search);
		return (int) Math.ceil((double) totalUsers / pageSize);
	}
}
