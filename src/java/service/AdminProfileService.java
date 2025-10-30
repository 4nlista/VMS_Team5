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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.User;

/**
 *
 * @author Mirinae
 */
public class AdminProfileService {

    private final AdminUserDAO userDAO = new AdminUserDAO();
    private static final long MAX_AVATAR_BYTES = 5L * 1024 * 1024; // 5MB

    /**
     * Load admin default profile (usually id = 1).
     */
    public User loadDefaultProfile() {
        return userDAO.getUserDetailById(1);
    }

    /**
     * Load profile by ID (used when forwarding back on errors).
     */
    public User loadProfileById(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            return userDAO.getUserDetailById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Validate and update text fields only (will NOT touch avatar column).
     * Follows the same error-collection pattern as your
     * AdminUserService.adminUserEdit.
     *
     * On validation failure this method will set request attribute "errors"
     * (Map<String,String>) and will also set back the submitted values as
     * attributes for the JSP to prefer (optional).
     *
     * @return true if update succeeded; false if validation failed or DB update
     * failed.
     */
    public boolean updateProfileText(HttpServletRequest request) {
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

        // collect params
        String username = safeTrim(request.getParameter("username"));
        String fullName = safeTrim(request.getParameter("full_name"));
        String gender = safeTrim(request.getParameter("gender"));
        String phone = safeTrim(request.getParameter("phone"));
        String email = safeTrim(request.getParameter("email"));
        String address = safeTrim(request.getParameter("address"));
        String jobTitle = safeTrim(request.getParameter("job_title"));
        String bio = request.getParameter("bio");
        String dobStr = safeTrim(request.getParameter("dob"));

        // Full name validation
        if (fullName == null || fullName.isEmpty()) {
            errors.put("full_name", "Full name cannot be empty.");
        } else if (fullName.length() > 26) {
            errors.put("full_name", "Full name must be 26 characters or fewer.");
        } else if (!fullName.matches("^[\\p{L}\\s-]+$")) {
            errors.put("full_name", "Full name must contain only letters.");
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
            errors.put("job_title", "Job title must contain only letters.");
        } else if (jobTitle.length() <= 2) {
            errors.put("job_title", "No such job is 2 letters long.");
        }

        // DOB parsing (optional)
        java.sql.Date dob = null;
        if (dobStr != null && !dobStr.isEmpty()) {
            String s = dobStr.trim();
            LocalDate localDate = null;
            List<DateTimeFormatter> fmts = Arrays.asList(
                    DateTimeFormatter.ISO_LOCAL_DATE,
                    DateTimeFormatter.ofPattern("d/M/yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            for (DateTimeFormatter fmt : fmts) {
                try {
                    localDate = LocalDate.parse(s, fmt);
                    break;
                } catch (DateTimeParseException ignored) {
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

        // if any validation error -> attach and return false
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            // keep submitted values (JSP can use param.* but we set some as attributes too)
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

        // Attempt DB update (text-only) using DAO.updateUserTextOnly
        try {
            boolean ok = userDAO.updateUserTextOnly(id, username, fullName, gender, phone, email, address, jobTitle, bio, dob);
            if (!ok) {
                errors.put("general", "Failed to update user in database.");
                request.setAttribute("errors", errors);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("general", "An unexpected error occurred while updating the user.");
            request.setAttribute("errors", errors);
            return false;
        }
    }

    /**
     * Handle avatar upload or deletion.- If delete_avatar param is truthy ->
     * sets avatar to NULL.- If file uploaded -> validate (MIME & size), save
     * and update DB.On validation failure: sets request attribute "errors" and
     * returns false (no exception thrown).
     *
     * @param request
     * @param userId
     * @return
     */
    public boolean handleAvatarUpload(HttpServletRequest request, int userId) {
        Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
        if (errors == null) {
            errors = new HashMap<>();
        }

        Part avatarPart = null;
        try {
            avatarPart = request.getPart("avatar");
        } catch (IllegalStateException | IOException | ServletException e) {
            // e.g. file too large, not multipart or IO issue
            e.printStackTrace();
            errors.put("avatar", "Uploaded file too large or invalid multipart request.");
            request.setAttribute("errors", errors);
            return false;
        }

        // Nothing uploaded -> preserve existing avatar
        if (avatarPart == null || avatarPart.getSize() == 0) {
            return true;
        }

        // Validate content type
        String contentType = avatarPart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            errors.put("avatar", "Uploaded file must be an image.");
            request.setAttribute("errors", errors);
            return false;
        }

        // Validate size
        if (avatarPart.getSize() > MAX_AVATAR_BYTES) {
            errors.put("avatar", "Avatar file must be <= 5MB.");
            request.setAttribute("errors", errors);
            return false;
        }

        // Save file to uploads/avatars (fallback to tmp dir)
        try {
            String submitted = avatarPart.getSubmittedFileName();
            String filename = Paths.get(submitted).getFileName().toString();
            String ext = "";
            int dot = filename.lastIndexOf('.');
            if (dot >= 0) {
                ext = filename.substring(dot);
            }

            String uploadsRelative = "/uploads/user_avatars";
            String uploadsAbsolute = "C:\\Users\\Admin\\Downloads\\uploads\\user_avatars";  // FIXED LOCAL PATH OUTSIDE PROJECT
            // Fallback
            if (uploadsAbsolute == null) {
                uploadsAbsolute = System.getProperty("java.io.tmpdir") + File.separator + "uploads" + File.separator + "user_avatars";
            }
            File uploadsDir = new File(uploadsAbsolute);
            if (!uploadsDir.exists() && !uploadsDir.mkdirs()) {
                errors.put("avatar", "Lỗi up ảnh.");
                request.setAttribute("errors", errors);
                return false;
            }

            String newFilename = "user_" + userId + "_" + System.currentTimeMillis() + ext;
            File saved = new File(uploadsDir, newFilename);

            try (InputStream in = avatarPart.getInputStream()) {
                java.nio.file.Files.copy(in, saved.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            String avatarUrl = request.getContextPath() + uploadsRelative + "/" + newFilename;
            boolean ok = userDAO.updateAvatar(userId, avatarUrl);
            if (!ok) {
                errors.put("avatar", "Failed to update avatar in database.");
                request.setAttribute("errors", errors);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("avatar", "Failed to save uploaded avatar file.");
            request.setAttribute("errors", errors);
            return false;
        }
    }

    private String safeTrim(String s) {
        return (s == null) ? null : s.trim();
    }
}
