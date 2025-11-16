package service;

import dao.ProfileVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import model.ProfileVolunteer;

public class VolunteerProfileService {

    private static final long MAX_AVATAR_SIZE = 5L * 1024L * 1024L;
    private static final DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final ProfileVolunteerDAO profileVolunteerDAO;
    private final FileStorageService fileStorageService;

    public VolunteerProfileService() {
        profileVolunteerDAO = new ProfileVolunteerDAO();
        fileStorageService = new FileStorageService();
    }

    public ProfileVolunteer loadProfile(int accountId) {
        return profileVolunteerDAO.getDetailedProfileByAccountId(accountId);
    }

    public UpdateResult processUpdate(HttpServletRequest request, ProfileVolunteer existingProfile) {
        Map<String, String> errors = new HashMap<>();

        String fullName = safeTrim(request.getParameter("fullName"));
        String email = safeTrim(request.getParameter("email"));
        String phone = safeTrim(request.getParameter("phone"));
        String gender = safeTrim(request.getParameter("gender"));
        String address = safeTrim(request.getParameter("address"));
        String jobTitle = safeTrim(request.getParameter("jobTitle"));
        String bio = safeTrim(request.getParameter("bio"));
        String dobStr = safeTrim(request.getParameter("dob"));

        if (fullName == null || fullName.isEmpty()) {
            errors.put("fullName", "Họ và tên không được để trống.");
        } else if (fullName.length() > 100) {
            errors.put("fullName", "Họ và tên không được vượt quá 100 ký tự.");
        }

        if (email == null || email.isEmpty()) {
            errors.put("email", "Email không được để trống.");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", "Định dạng email không hợp lệ.");
        } else if (profileVolunteerDAO.emailExistsForOther(email, existingProfile.getId())) {
            errors.put("email", "Email đã tồn tại trong hệ thống.");
        }

        if (phone == null || phone.isEmpty()) {
            errors.put("phone", "Số điện thoại không được để trống.");
        } else if (!phone.matches("^0\\d{9,10}$")) {
            errors.put("phone", "Số điện thoại phải bắt đầu bằng 0 và gồm 10-11 chữ số.");
        } else if (profileVolunteerDAO.phoneExistsForOther(phone, existingProfile.getId())) {
            errors.put("phone", "Số điện thoại đã tồn tại trong hệ thống.");
        }

        if (gender == null || gender.isEmpty()) {
            errors.put("gender", "Vui lòng chọn giới tính.");
        } else {
            String genderLower = gender.toLowerCase();
            if (!genderLower.equals("male") && !genderLower.equals("female") && !genderLower.equals("other")) {
                errors.put("gender", "Giá trị giới tính không hợp lệ.");
            } else {
                gender = genderLower;
            }
        }

        if (address == null || address.isEmpty()) {
            errors.put("address", "Địa chỉ không được để trống.");
        } else if (address.length() > 255) {
            errors.put("address", "Địa chỉ không được vượt quá 255 ký tự.");
        }

        if (jobTitle == null || jobTitle.isEmpty()) {
            errors.put("jobTitle", "Nghề nghiệp không được để trống.");
        } else if (jobTitle.length() > 100) {
            errors.put("jobTitle", "Nghề nghiệp không được vượt quá 100 ký tự.");
        }

        if (bio == null || bio.isEmpty()) {
            errors.put("bio", "Giới thiệu bản thân không được để trống.");
        } else if (bio.length() > 1000) {
            errors.put("bio", "Giới thiệu bản thân không được vượt quá 1000 ký tự.");
        }

        LocalDate dob = null;
        if (dobStr == null || dobStr.isEmpty()) {
            errors.put("dob", "Ngày sinh không được để trống.");
        } else {
            try {
                dob = LocalDate.parse(dobStr, DOB_FORMATTER);
                if (dob.isAfter(LocalDate.now())) {
                    errors.put("dob", "Ngày sinh không được ở tương lai.");
                }
            } catch (DateTimeParseException ex) {
                errors.put("dob", "Ngày sinh không hợp lệ. Vui lòng dùng định dạng YYYY-MM-DD.");
            }
        }

        // Upload avatar using UnifiedImageUploadService
        String avatarPath = existingProfile.getImages();
        UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
        Map<String, Object> uploadResult = uploadService.uploadAvatar(request, existingProfile.getAccountId(), "avatar");
        
        if ((boolean) uploadResult.get("success")) {
            avatarPath = (String) uploadResult.get("fileName");
        } else {
            // Check if there was an upload attempt (file selected)
            try {
                Part avatarPart = request.getPart("avatar");
                if (avatarPart != null && avatarPart.getSize() > 0) {
                    // Upload was attempted but failed
                    errors.put("avatar", (String) uploadResult.get("error"));
                }
                // If no file selected, keep existing avatar
            } catch (Exception ex) {
                // Ignore - no file part
            }
        }

        if (avatarPath == null || avatarPath.trim().isEmpty()) {
            errors.put("avatar", "Ảnh đại diện không được để trống.");
        }

        java.util.Date utilDob = null;
        Date sqlDob = null;
        if (dob != null) {
            sqlDob = Date.valueOf(dob);
            utilDob = new java.util.Date(sqlDob.getTime());
        }

        ProfileVolunteer pendingProfile = new ProfileVolunteer(
                existingProfile.getId(),
                avatarPath,
                fullName,
                utilDob,
                gender,
                phone,
                email,
                address,
                jobTitle,
                bio,
                existingProfile.getTotalEvents(),
                existingProfile.getTotalDonated()
        );
        pendingProfile.setEventName(existingProfile.getEventName());
        pendingProfile.setOrganizationName(existingProfile.getOrganizationName());

        if (!errors.isEmpty()) {
            return new UpdateResult(false, pendingProfile, errors, "Vui lòng kiểm tra lại thông tin đã nhập.");
        }

        boolean updated = profileVolunteerDAO.updateVolunteerProfile(
                existingProfile.getId(),
                avatarPath,
                fullName,
                sqlDob,
                gender,
                phone,
                email,
                address,
                jobTitle,
                bio
        );

        if (!updated) {
            errors.put("general", "Cập nhật hồ sơ thất bại. Vui lòng thử lại sau.");
            return new UpdateResult(false, pendingProfile, errors, "Cập nhật hồ sơ thất bại.");
        }

        ProfileVolunteer refreshedProfile = profileVolunteerDAO.getDetailedProfileByAccountId(existingProfile.getId());
        return new UpdateResult(true, refreshedProfile, errors, "Cập nhật hồ sơ thành công.");
    }

    private String safeTrim(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static class UpdateResult {

        private final boolean success;
        private final ProfileVolunteer profile;
        private final Map<String, String> errors;
        private final String message;

        public UpdateResult(boolean success, ProfileVolunteer profile, Map<String, String> errors, String message) {
            this.success = success;
            this.profile = profile;
            this.errors = errors == null ? Map.of() : errors;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public ProfileVolunteer getProfile() {
            return profile;
        }

        public Map<String, String> getErrors() {
            return errors;
        }

        public String getMessage() {
            return message;
        }
    }
}
