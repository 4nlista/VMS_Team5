/*
 * Service for Admin to add new organization accounts
 */
package service;

import dao.AdminUserDAO;
import dao.UserDAO;
import java.io.InputStream;
import utils.PasswordUtil;
import service.FileStorageService;

/**
 * Service class for handling logic of adding new organization accounts by Admin
 *
 * @author Admin
 */
public class AdminAddAccountService {

    private AdminAccountService adminAccountService;
    private UserDAO userDAO;
    private AdminUserDAO adminUserDAO;
    private FileStorageService fileStorageService;

    public AdminAddAccountService() {
        adminAccountService = new AdminAccountService();
        fileStorageService = new FileStorageService();
        adminUserDAO = new AdminUserDAO();
    }

 
    public static class CreateAccountResult {

        private boolean success;
        private String errorMessage;
        private int accountId;

        public CreateAccountResult(boolean success, String errorMessage, int accountId) {
            this.success = success;
            this.errorMessage = errorMessage;
            this.accountId = accountId;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public int getAccountId() {
            return accountId;
        }
    }

    /**
     * Validate input data for creating account
     */
    public String validateInput(String username, String password, String role,
            String fullName, String email) {
        if (username == null || username.trim().isEmpty()) {
            return "error_validation";
        }
        if (password == null || password.trim().isEmpty()) {
            return "error_validation";
        }
        if (role == null || role.trim().isEmpty()) {
            return "error_validation";
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            return "error_validation";
        }
        if (email == null || email.trim().isEmpty()) {
            return "error_validation";
        }
        return null;
    }

    /**
     * Validate that role is organization
     */
    public boolean isOrganizationRole(String role) {
        return "organization".equalsIgnoreCase(role);
    }

    /**
     * Check if username already exists
     */
    public boolean checkUsernameExists(String username) {
        return adminAccountService.usernameExists(username);
    }

    public String uploadAvatar(InputStream avatarInputStream, String fileName, int accountId) {
        return fileStorageService.saveAvatar(avatarInputStream, fileName, accountId);
    }

    private Object[] extractAvatarInfo(jakarta.servlet.http.Part avatarPart) {
        if (avatarPart == null) {
            return new Object[]{null, null};
        }

        try {
            long size = avatarPart.getSize();
            String fileName = avatarPart.getSubmittedFileName();

            if (size > 0 && fileName != null && !fileName.isEmpty()) {
                return new Object[]{avatarPart.getInputStream(), fileName};
            }
        } catch (Exception e) {
            // Avatar không bắt buộc, nên không cần xử lý lỗi
        }

        return new Object[]{null, null};
    }

    public CreateAccountResult createOrganizationAccount(
            String username, String password, String role, String statusParam,
            String fullName, String email, String phone, String gender,
            String dob, String address, String jobTitle, String bio,
            jakarta.servlet.http.Part avatarPart) {

        // Validate input
        String validationError = validateInput(username, password, role, fullName, email);
        if (validationError != null) {
            return new CreateAccountResult(false, validationError, -1);
        }

        // Validate role - chỉ cho phép organization
        if (!isOrganizationRole(role)) {
            return new CreateAccountResult(false, "error_validation", -1);
        }

        // Check username exists
        if (checkUsernameExists(username)) {
            return new CreateAccountResult(false, "error_username_exists", -1);
        }

        // Convert status
        boolean status = "active".equalsIgnoreCase(statusParam);

        // Mã hóa mật khẩu trước khi lưu vào database
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Tạo account và lấy ID
        int accountId = adminAccountService.createAccountAndGetId(username, hashedPassword, role, status);

        if (accountId <= 0) {
            return new CreateAccountResult(false, "error_username_exists", -1);
        }

        // Xử lý upload avatar - logic nghiệp vụ trong service
        // Lưu vào thư mục cố định và chỉ lưu tên file vào database
        String avatarFileName = null;
        Object[] avatarInfo = extractAvatarInfo(avatarPart);
        InputStream avatarInputStream = (InputStream) avatarInfo[0];
        String originalFileName = (String) avatarInfo[1];

        if (avatarInputStream != null && originalFileName != null && !originalFileName.isEmpty()) {
            avatarFileName = uploadAvatar(avatarInputStream, originalFileName, accountId);
        }

//         Tạo user profile (lưu tên file avatar, không phải đường dẫn đầy đủ)
        boolean userCreated = adminUserDAO.insertUser(accountId, fullName, email, phone,
                gender, dob, address, avatarFileName, jobTitle, bio);

        if (!userCreated) {
            // Nếu tạo user profile thất bại, có thể xóa account đã tạo
            // adminAccountService.deleteAccount(accountId);
            return new CreateAccountResult(false, "error_validation", accountId);
        }

        return new CreateAccountResult(true, null, accountId);
    }
}
