package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import service.ChangePasswordService;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePasswordServlet"})

public class ChangePasswordServlet extends HttpServlet {

    private ChangePasswordService service = new ChangePasswordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chỉ redirect về trang login nếu truy cập GET trực tiếp
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 1. Lấy dữ liệu từ form
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // 2. Lấy accountId và role từ session
        Integer accountId = (Integer) request.getSession().getAttribute("accountId");
        String role = (String) request.getSession().getAttribute("role"); // "volunteer", "admin", "organization"

        if (accountId == null || role == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // 3. Gọi service đổi mật khẩu
        String errorMsg = service.changePassword(accountId, currentPassword, newPassword, confirmPassword);

        // 4. Nếu có lỗi → forward về trang change password theo role
        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            switch (role) {
                case "volunteer":
                    request.getRequestDispatcher("/volunteer/change_password_volunteer.jsp").forward(request, response);
                    break;
                case "admin":
                    request.getRequestDispatcher("/admin/change_password_admin.jsp").forward(request, response);
                    break;
                case "organization":
                    request.getRequestDispatcher("/organization/change_password_organization.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                    break;
            }
            return;
        }

        // 5. Thành công → redirect về login.jsp tất cả vai trò
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");

    }
}

