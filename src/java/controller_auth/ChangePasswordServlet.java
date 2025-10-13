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

        // Lấy dữ liệu từ form
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Lấy accountId và role từ session
        Integer accountId = (Integer) request.getSession().getAttribute("accountId");
        String role = (String) request.getSession().getAttribute("role"); // "volunteer", "admin", "organization"

        if (accountId == null || role == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Gọi Service đổi mật khẩu
        String errorMsg = service.changePassword(accountId, currentPassword, newPassword, confirmPassword);

        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            // Trả về trang change password theo role
            switch (role) {
                case "volunteer":
                    request.getRequestDispatcher("/auth/change_password_volunteer.jsp").forward(request, response);
                    break;
                case "admin":
                    request.getRequestDispatcher("/auth/change_password_admin.jsp").forward(request, response);
                    break;
                case "organization":
                    request.getRequestDispatcher("/auth/change_password_organization.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                    break;
            }
            return;
        }

        // Thành công → redirect về trang chính role
        switch (role) {
            case "volunteer":
                response.sendRedirect(request.getContextPath() + "/volunteer/profile.jsp");
                break;
            case "admin":
                response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                break;
            case "organization":
                response.sendRedirect(request.getContextPath() + "/organization/profile.jsp");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
                break;
        }
    }
}
