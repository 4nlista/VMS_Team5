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
        // Truy cập GET trực tiếp → chuyển về trang login
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
      
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        // Lấy thông tin user từ session
        Integer accountId = (Integer) request.getSession().getAttribute("accountId");
        String role = (String) request.getSession().getAttribute("role");

        if (accountId == null || role == null) {
            // Chưa login → về trang login
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Gọi service xử lý logic đổi mật khẩu
        String errorMsg = service.changePassword(accountId, currentPassword, newPassword, confirmPassword);

        if (errorMsg != null) {
            // Có lỗi → forward về trang đổi mật khẩu với thông báo lỗi
            request.setAttribute("error", errorMsg);
            switch (role) {
                case "volunteer":
                    request.getRequestDispatcher("/volunteer/change_password_volunteer.jsp").forward(request, response);
                    break;
                case "admin":
                    request.getRequestDispatcher("/admin/change_password_admin.jsp").forward(request, response);
                    break;
                case "organization":
                    request.getRequestDispatcher("/organization/change_password_org.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                    break;
            }
            return;
        }

        // Đổi mật khẩu thành công → forward về trang đổi mật khẩu với thông báo thành công
        // JSP sẽ hiển thị thông báo và tự động redirect về login sau vài giây
        request.setAttribute("success", "Đổi mật khẩu thành công! Bạn sẽ được chuyển hướng trong giây lát.");
        switch (role) {
            case "volunteer":
                request.getRequestDispatcher("/volunteer/change_password_volunteer.jsp").forward(request, response);
                break;
            case "admin":
                request.getRequestDispatcher("/admin/change_password_admin.jsp").forward(request, response);
                break;
            case "organization":
                request.getRequestDispatcher("/organization/change_password_org.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                break;
        }

    }

}
