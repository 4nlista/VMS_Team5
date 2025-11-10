/*
 * A friendly reminder to drink enough water
 */

package service;

import dao.AdminUserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mirinae
 */
public class AvatarUploadService {
	private static final long MAX_AVATAR_BYTES = 5L * 1024 * 1024; // 5MB
	public static boolean handleAvatarUpload(HttpServletRequest request, int userId, AdminUserDAO dao) {
        Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
        if (errors == null) errors = new HashMap<>();

        Part avatarPart;
        try {
            avatarPart = request.getPart("avatar");
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("avatar", "File quá lớn hoặc multipart request lỗi.");
            request.setAttribute("errors", errors);
            return false;
        }

        // Nothing uploaded -> nothing to do
        if (avatarPart == null || avatarPart.getSize() == 0) {
            return true;
        }

        // Validate content type
        String contentType = avatarPart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            errors.put("avatar", "Định dạng tệp cần là ảnh.");
            request.setAttribute("errors", errors);
            return false;
        }

        // Validate size
        if (avatarPart.getSize() > MAX_AVATAR_BYTES) {
            errors.put("avatar", "Ảnh đại diện cần nhỏ hơn 5MB.");
            request.setAttribute("errors", errors);
            return false;
        }

        // Save file using FileStorageService
        FileStorageService storage = new FileStorageService();
        String savedFilename = null;
        try (InputStream is = avatarPart.getInputStream()) {
            String original = avatarPart.getSubmittedFileName();
            savedFilename = storage.saveAvatar(is, original, userId); // returns filename like "avatar_12_uuid.jpg"
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("avatar", "Lỗi khi đọc tệp ảnh.");
            request.setAttribute("errors", errors);
            return false;
        }

        if (savedFilename == null) {
            errors.put("avatar", "Lưu ảnh thất bại.");
            request.setAttribute("errors", errors);
            return false;
        }

        // Persist filename in DB (AdminUserDAO.updateAvatar accepts avatarPath; now we pass filename)
        boolean ok = dao.updateAvatar(userId, savedFilename);
        if (!ok) {
            errors.put("avatar", "Cập nhật avatar vào CSDL thất bại.");
            request.setAttribute("errors", errors);
            return false;
        }

        return true;
    }
}
