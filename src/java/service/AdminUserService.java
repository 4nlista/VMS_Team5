/*
 * A friendly reminder to drink enough water
 */
package service;

import dao.AdminUserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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
 *
 * @author Mirinae
 */
public class AdminUserService {

	private final AdminUserDAO userDAO = new AdminUserDAO();
	private final int pageSize = 6; // configurable if needed
	private static final long MAX_AVATAR_BYTES = 5L * 1024 * 1024; // 5MB

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

		// Full name validation
		if (fullName == null || fullName.isEmpty()) {
			errors.put("full_name", "Họ tên không được trống.");
		} else if (fullName.length() > 100) {
			errors.put("full_name", "Họ tên phải ít hơn 100 ký tự.");
		} else if (!fullName.matches("^[\\p{L}\\s-]+$")) {
			errors.put("full_name", "Họ tên chỉ được chứa chữ cái.");
		}

		// Phone
		if (phone == null || phone.isEmpty()) {
			errors.put("phone", "Số điện thoại không được để trống.");
		} else if (!phone.matches("^[0-9]+$")) {
			errors.put("phone", "Số điện thoại phải là số.");
		} else if (phone.length() < 10 || phone.length() > 11) {
			errors.put("phone", "Số điện thoại được chứa 10-11 ký tự.");
		} else if (!userDAO.isPhoneUnique(phone, id)) {
			errors.put("phone", "Số điện thoại đã tồn tại trong hệ thống.");
		}

		// Email
		if (email == null || email.isEmpty()) {
			errors.put("email", "Email không được để trống.");
		} else if (!email.matches("^[A-Za-z0-9](?:[A-Za-z0-9._%+-]{0,62}[A-Za-z0-9])?@[A-Za-z0-9](?:[A-Za-z0-9.-]{0,61}[A-Za-z0-9])?\\.[A-Za-z]{2,}$") || email.contains("..") || email.contains(".@") || email.contains("@.")) {
			errors.put("email", "Định dạng email không hợp lệ.");
		} else if (email.length() > 100) {
			errors.put("email", "Email không được vượt quá 100 ký tự.");
		} else {
			if (!userDAO.isEmailUnique(email, id)) {
				errors.put("email", "Email đã tồn tại trong hệ thống.");
			}
		}

		// Address
		if (address != null && !address.isEmpty()) {
			if (address.length() > 255) {
				errors.put("address", "Địa chỉ tối đa 255 ký tự.");
			} else if (address.length() <= 2) {
				errors.put("address", "Địa chỉ cần dài hơn 2 ký tự.");
			}
		} else if (!address.matches("^[\\p{L}\\s-]+$")) {
			errors.put("address", "Địa chỉ không được chứa ký tự đặc biệt.");
		}

		// Job title
		if (jobTitle.length() > 100) {
			errors.put("job_title", "Nghề nghiệp tối đa 100 ký tự.");
		} else if (jobTitle.length() <= 2) {
			errors.put("job_title", "Nghề nghiệp cần dài hơn 2 ký tự.");
		} else if (!jobTitle.matches("^[\\p{L}\\s-]+$")) {
			errors.put("job_title", "Nghề nghiệp chỉ được chứa chữ cái.");
		}

		// Bio
		if (bio != null && !bio.isEmpty() && bio.length() > 1000) {
			errors.put("bio", "Mục này cần nhỏ hơn 1000 ký tự.");
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
				errors.put("dob", "Ngày sinh không hợp lệ.");
			} else if (localDate.isAfter(LocalDate.now())) {
				errors.put("dob", "Ngày sinh không thể ở tương lai.");
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
			boolean ok = userDAO.updateUserTextOnly(id, username, fullName, gender, phone, email, address, jobTitle, bio, dob);
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

	public User getUserByAccountId(int id) {
		return userDAO.getUserByAccountId(id);
	}

	private String safeTrim(String s) {
		return (s == null) ? null : s.trim();
	}

	public List<User> getUsersByPage(int page) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return userDAO.getAllUsersWithPagination(page, pageSize);
	}

	public List<User> getUsersByPage(int page, String role, String search, String sort, String gender) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return userDAO.getUsersWithFiltersAndPagination(page, pageSize, role, search, sort, gender);
	}

	public int getTotalPages() throws SQLException {
		int totalUsers = userDAO.getTotalUserCount();
		return (int) Math.ceil((double) totalUsers / pageSize);
	}

	public int getTotalPages(String role, String search, String gender) throws SQLException {
		int totalUsers = userDAO.getFilteredUserCount(role, search, gender);
		return (int) Math.ceil((double) totalUsers / pageSize);
	}

	public void loadUserList(HttpServletRequest request) throws SQLException {
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
		String gender = request.getParameter("gender");

		List<User> users = getUsersByPage(page, role, search, sort, gender);
		int totalPages = getTotalPages(role, search, gender);

		request.setAttribute("users", users);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentRole", role == null ? "" : role);
		request.setAttribute("currentSearch", search == null ? "" : search);
		request.setAttribute("currentSort", sort == null ? "" : sort);
		request.setAttribute("currentGender", gender == null ? "" : gender);
	}

	public User loadUserDetail(HttpServletRequest request) throws Exception {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			return userDAO.getUserDetailById(id);
		} catch (Exception e) {
			return null;
		}
	}
	public boolean handleAvatarUpload(HttpServletRequest request, int userId) {
    return AvatarUploadService.handleAvatarUpload(request, userId, this.userDAO);
}
}
