package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import service.AdminAddAccountService;

/**
 * Servlet for Admin to add new organization accounts
 * This servlet only handles request/response, all business logic is in AdminAddAccountService
 */
@WebServlet(name = "AdminAddAccountServlet", urlPatterns = {"/AdminAddAccountServlet"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1 MB
    maxFileSize = 1024 * 1024 * 5,  // 5 MB
    maxRequestSize = 1024 * 1024 * 10  // 10 MB
)
public class AdminAddAccountServlet extends HttpServlet {

    private AdminAddAccountService adminAddAccountService;

    @Override
    public void init() {
        adminAddAccountService = new AdminAddAccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/add_account_admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy dữ liệu từ request (chỉ extract data, không xử lý logic)
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

        // Lấy avatar Part từ request (không xử lý logic kiểm tra)
        Part avatarPart = null;
        try {
            avatarPart = request.getPart("avatar");
        } catch (Exception e) {
            // Ignore - service sẽ xử lý
        }

        // Gọi service để xử lý toàn bộ logic nghiệp vụ
        // Service sẽ tự xử lý upload vào thư mục cố định
        AdminAddAccountService.CreateAccountResult result = adminAddAccountService.createOrganizationAccount(
            username, password, role, statusParam,
            fullName, email, phone, gender, dob, address, jobTitle, bio,
            avatarPart
        );

        // Xử lý response dựa trên kết quả từ service
        if (result.isSuccess()) {
            response.sendRedirect(request.getContextPath() + "/AdminAccountServlet?msg=created_successfully");
        } else {
            String errorMsg = result.getErrorMessage() != null ? result.getErrorMessage() : "error_validation";
            response.sendRedirect(request.getContextPath() + "/admin/add_account_admin.jsp?msg=" + errorMsg);
        }
    }
}

