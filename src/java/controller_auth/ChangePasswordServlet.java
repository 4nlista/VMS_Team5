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

        // 🟢 Thêm log kiểm tra session
        System.out.println("==== [DEBUG] SESSION CHECK ====");
        System.out.println("Session ID: " + request.getSession().getId());
        System.out.println("accountId in session: " + request.getSession().getAttribute("accountId"));
        System.out.println("role in session: " + request.getSession().getAttribute("role"));
        System.out.println("================================");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        Integer accountId = (Integer) request.getSession().getAttribute("accountId");
        String role = (String) request.getSession().getAttribute("role");

        if (accountId == null || role == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Gọi service
        String errorMsg = service.changePassword(accountId, currentPassword, newPassword, confirmPassword);

        if (errorMsg != null) {
            // Có lỗi → forward lại trang đổi mật khẩu
            request.setAttribute("error", errorMsg);
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

        System.out.println("Đổi mật khẩu thành công! Redirect về login.jsp");
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    }

}
