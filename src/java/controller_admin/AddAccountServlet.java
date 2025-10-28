package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import service.AdminAccountService;
import dao.UserDAO;

@WebServlet(name = "AddAccountServlet", urlPatterns = {"/AddAccountServlet"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1 MB
    maxFileSize = 1024 * 1024 * 5,  // 5 MB
    maxRequestSize = 1024 * 1024 * 10  // 10 MB
)
public class AddAccountServlet extends HttpServlet {

    private AdminAccountService adminAccountService;
    private UserDAO userDAO;

    @Override
    public void init() {
        adminAccountService = new AdminAccountService();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/add_account_admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String statusParam = request.getParameter("status");
        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");
        String jobTitle = request.getParameter("job_title");
        String bio = request.getParameter("bio");

        // Validate input
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            role == null || role.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/add_account_admin.jsp?msg=error_validation");
            return;
        }

        // Chỉ cho phép tạo organization
        if (!"organization".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/admin/add_account_admin.jsp?msg=error_validation");
            return;
        }

        // Check username exists
        if (adminAccountService.usernameExists(username)) {
            response.sendRedirect(request.getContextPath() + "/admin/add_account_admin.jsp?msg=error_username_exists");
            return;
        }

        // Convert status
        boolean status = "active".equalsIgnoreCase(statusParam);

        // Tạo account và lấy ID
        int accountId = adminAccountService.createAccountAndGetId(username, password, role, status);
        
        if (accountId <= 0) {
            response.sendRedirect(request.getContextPath() + "/admin/add_account_admin.jsp?msg=error_username_exists");
            return;
        }

        // Xử lý upload avatar
        String avatarPath = null;
        try {
            Part avatarPart = request.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                String fileName = avatarPart.getSubmittedFileName();
                if (fileName != null && !fileName.isEmpty()) {
                    // Tạo tên file unique
                    String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                    String uniqueFileName = "avatar_" + accountId + "_" + UUID.randomUUID().toString() + fileExtension;
                    
                    // Tạo thư mục uploads nếu chưa có
                    String uploadDir = getServletContext().getRealPath("/uploads/avatars/");
                    Path uploadPath = Paths.get(uploadDir);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }
                    
                    // Lưu file
                    Path filePath = uploadPath.resolve(uniqueFileName);
                    Files.copy(avatarPart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    
                    // Lưu đường dẫn tương đối
                    avatarPath = "uploads/avatars/" + uniqueFileName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu upload thất bại, vẫn tiếp tục tạo user profile (không có avatar)
        }

        // Tạo user profile
        boolean userCreated = userDAO.insertUser(accountId, fullName, email, phone, gender, dob, address, avatarPath, jobTitle, bio);
        
        if (!userCreated) {
            // Nếu tạo user profile thất bại, cần xóa account đã tạo (optional cleanup)
            // adminAccountService.deleteAccount(accountId);
            response.sendRedirect(request.getContextPath() + "/admin/add_account_admin.jsp?msg=error_validation");
            return;
        }
        
        // Redirect về trang danh sách với thông báo thành công
        response.sendRedirect(request.getContextPath() + "/AdminAccountServlet?msg=created_successfully");
    }
}
