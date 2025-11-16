/*
 * A friendly reminder to drink enough water
 */
package service;

import dao.OrganizationProfileDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import model.User;

/**
 *
 * @author Mirinae
 */
public class OrganizationProfileService {

    private OrganizationProfileDAO dao = new OrganizationProfileDAO();
    private FileStorageService storage = new FileStorageService();
    private static final long MAX_AVATAR_BYTES = 5L * 1024 * 1024; // 5MB

    public Integer getLoggedInAccountId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        Object accObj = session.getAttribute("account");
        if (accObj == null) {
            return null;
        }

        try {
            return (Integer) accObj.getClass()
                    .getMethod("getId")
                    .invoke(accObj);
        } catch (Exception e) {
            return null;
        }
    }

    public User loadProfile(HttpServletRequest request) throws Exception {
        Integer accountId = getLoggedInAccountId(request);
        if (accountId == null) {
            throw new Exception("User not logged in.");
        }

        User profile = dao.getOrganizationProfile(accountId);

        if (profile == null) {
            throw new Exception("Không tìm thấy hồ sơ của tổ chức.");
        }
        return profile;
    }

    // Update
    public User loadForEdit(HttpServletRequest request) throws Exception {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            throw new Exception("Missing id");
        }
        int id = Integer.parseInt(idParam);
        User u = dao.getByUserId(id);
        if (u == null) {
            throw new Exception("User not found");
        }
        return u;
    }

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
        String phone = safeTrim(request.getParameter("phone"));
        String email = safeTrim(request.getParameter("email"));
        String address = safeTrim(request.getParameter("address"));
        String jobTitle = safeTrim(request.getParameter("job_title"));
        String bio = request.getParameter("bio");
        String dobStr = safeTrim(request.getParameter("dob"));
        // Full name validation
        if (fullName == null || fullName.isEmpty()) {
            errors.put("full_name", "Họ tên không được để trống.");
        } else if (fullName.length() > 100) {
            errors.put("full_name", "Họ tên phải dưới 100 ký tự.");
        } else if (!fullName.matches("^[\\p{L}\\s-]+$")) {
            errors.put("full_name", "Họ tên không được chứa ký tự.");
        }
        // Phone
        if (phone != null && !phone.isEmpty()) {
            if (!phone.matches("^[0-9]+$")) {
                errors.put("phone", "Số điện thoại phải là số.");
            } else if (phone.length() < 10 || phone.length() > 11) {
                errors.put("phone", "Số điện thoại được chứa 10-11 ký tự.");
            } else {
                if (!dao.isPhoneUnique(phone, id)) {
                    errors.put("phone", "Số điện thoại đã tồn tại trong hệ thống.");
                }
            }
        }
        // Email
        if (email == null || email.isEmpty()) {
            errors.put("email", "Email không được để trống.");
        } else if (!email.matches("^[A-Za-z0-9](?:[A-Za-z0-9._%+-]{0,62}[A-Za-z0-9])?@[A-Za-z0-9](?:[A-Za-z0-9.-]{0,61}[A-Za-z0-9])?\\.[A-Za-z]{2,}$") || email.contains("..") || email.contains(".@") || email.contains("@.")) {
            errors.put("email", "Định dạng email không hợp lệ.");
        } else if (email.length() > 100) {
            errors.put("email", "Email không được vượt quá 100 ký tự.");
        } else {
            if (!dao.isEmailUnique(email, id)) {
                errors.put("email", "Email đã tồn tại trong hệ thống.");
            }
        }
        // Address
        if (address != null && !address.isEmpty()) {
            if (address.length() > 255) {
                errors.put("address", "Địa chỉ tối đa 255 ký tự.");
            } else if (address.length() <= 2) {
                errors.put("address", "Địa chỉ cần dài hơn 2 ký tự.");
            } else if (!address.matches("^[\\p{L}\\s-]+$")) {
                errors.put("address", "Địa chỉ không được chứa ký tự đặc biệt.");
            }
        }
        // Job title
        if (jobTitle != null && !jobTitle.isEmpty()) {
            if (jobTitle.length() > 100) {
                errors.put("job_title", "Nghề nghiệp tối đa 100 ký tự.");
            } else if (jobTitle.length() <= 2) {
                errors.put("job_title", "Nghề nghiệp cần dài hơn 2 ký tự.");
            } else if (!jobTitle.matches("^[\\p{L}\\s-]+$")) {
                errors.put("job_title", "Nghề nghiệp chỉ được chứa chữ cái.");
            }
        }
        // Bio
        if (bio != null && !bio.isEmpty() && bio.length() > 1000) {
            errors.put("bio", "Mục này cần nhỏ hơn 1000 ký tự.");
        }
        // DOB parsing
        java.sql.Date dob = null;
        if (dobStr != null && !dobStr.isEmpty()) {
            String s = dobStr.trim();
            LocalDate localDate = null;
            try {
                localDate = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ignored) {
            }
            if (localDate == null) {
                errors.put("dob", "Ngày sinh không hợp lệ.");
            } else if (localDate.isAfter(LocalDate.now())) {
                errors.put("dob", "Ngày sinh không thể ở tương lai.");
            } else {
                dob = java.sql.Date.valueOf(localDate);
            }
        }
        // if any validation error -> attach and return false
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            // keep submitted values
            request.setAttribute("username", username);
            request.setAttribute("full_name", fullName);
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            request.setAttribute("address", address);
            request.setAttribute("job_title", jobTitle);
            request.setAttribute("bio", bio);
            request.setAttribute("dob", dobStr);
            return false;
        }
        // Attempt DB update (text-only)
        try {
            boolean ok = dao.updateUserTextOnly(id, fullName, phone, email, address, jobTitle, bio, dob);
            if (!ok) {
                errors.put("general", "Cập nhật thất bại.");
                request.setAttribute("errors", errors);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("general", "Lỗi bất ngờ khi cập nhật.");
            request.setAttribute("errors", errors);
            return false;
        }
    }

    private String safeTrim(String s) {
        return (s == null) ? null : s.trim();
    }
}
