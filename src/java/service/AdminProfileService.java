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
import java.nio.file.Paths;
import model.User;

/**
 *
 * @author Mirinae
 */
public class AdminProfileService {

	private final AdminUserDAO userDAO = new AdminUserDAO();

	/**
	 * Loads profile by id.
	 * @param request
	 * @return The user profile and details.
	 * @throws IOException
	 */
	public User loadProfileById(HttpServletRequest request) throws IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			return userDAO.getUserDetailById(id);
		} catch (Exception e) {
			throw new IOException("Invalid user ID", e);
		}
	}

	/**
	 * Loads the admin profile.
	 * @return User admin profile details.
	 */
	public User loadDefaultProfile() {
		return userDAO.getUserDetailById(1);
	}

	/**
	 * Update profile text fields.
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public boolean updateProfileText(HttpServletRequest request) throws IOException {
		AdminUserService userService = new AdminUserService();
		return userService.adminUserEdit(request);
	}

	/**
	 * Handles avatar upload logic.
	 * @param request
	 * @param userId
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public boolean handleAvatarUpload(HttpServletRequest request, int userId)
		    throws IOException, ServletException {
		Part avatarPart = request.getPart("avatar");
		if (avatarPart == null || avatarPart.getSize() == 0) {
			return true;
		}

		String contentType = avatarPart.getContentType();
		if (!contentType.startsWith("image/")) {
			throw new IOException("Uploaded file is not an image.");
		}
		if (avatarPart.getSize() > 5 * 1024 * 1024) {
			throw new IOException("Image size exceeds 5MB.");
		}

		String realPath = request.getServletContext().getRealPath("");
		String uploadPath = realPath + File.separator + "uploads" + File.separator + "avatars";
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String submittedFileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
		String ext = submittedFileName.substring(submittedFileName.lastIndexOf("."));
		String fileName = "user_" + userId + "_" + System.currentTimeMillis() + ext;

		String fullPath = uploadPath + File.separator + fileName;
		avatarPart.write(fullPath);

		String avatarUrl = request.getContextPath() + "/uploads/avatars/" + fileName;
		boolean ok = userDAO.updateAvatar(userId, avatarUrl);
		if (!ok) {
			throw new IOException("Failed to update avatar in database.");
		}

		return true;
	}
}
