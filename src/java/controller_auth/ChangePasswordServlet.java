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

//    @Override
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//
//        // 1. Lấy dữ liệu từ form
//        String currentPassword = request.getParameter("currentPassword");
//        String newPassword = request.getParameter("newPassword");
//        String confirmPassword = request.getParameter("confirmPassword");
//
//        // 2. Lấy accountId và role từ session
//        Integer accountId = (Integer) request.getSession().getAttribute("accountId");
//        String role = (String) request.getSession().getAttribute("role"); // "volunteer", "admin", "organization"
//
//        if (accountId == null || role == null) {
//            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
//            return;
//        }
//        System.out.println("🔹 Bắt đầu đổi mật khẩu cho ID: " + accountId);
//
//        // 3. Gọi service đổi mật khẩu
//        String errorMsg = service.changePassword(accountId, currentPassword, newPassword, confirmPassword);
//        
//        System.out.println("🔹 Kết quả service: " + result);
//
//        // 4. Nếu có lỗi → forward về trang change password theo role
//        if (errorMsg != null) {
//            request.setAttribute("error", errorMsg);
//            switch (role) {
//                case "volunteer":
//                    request.getRequestDispatcher("/volunteer/change_password_volunteer.jsp").forward(request, response);
//                    break;
//                case "admin":
//                    request.getRequestDispatcher("/admin/change_password_admin.jsp").forward(request, response);
//                    break;
//                case "organization":
//                    request.getRequestDispatcher("/organization/change_password_organization.jsp").forward(request, response);
//                    break;
//                default:
//                    request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
//                    break;
//            }
//            return;
//        }
//
//        // 5. Thành công → redirect về login.jsp tất cả vai trò
//        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
//
//    }
//    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        Integer accountId = (Integer) request.getSession().getAttribute("accountId");
        String role = (String) request.getSession().getAttribute("role");

        if (accountId == null || role == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // 🟢 Thêm log để xem servlet có nhận dữ liệu đúng không
        System.out.println("==== [DEBUG] ChangePasswordServlet ====");
        System.out.println("accountId = " + accountId);
        System.out.println("currentPassword = " + currentPassword);
        System.out.println("newPassword = " + newPassword);
        System.out.println("confirmPassword = " + confirmPassword);

        // Gọi service
        String errorMsg = service.changePassword(accountId, currentPassword, newPassword, confirmPassword);

        // 🟢 Log kết quả service trả về
        System.out.println("Kết quả service (errorMsg) = " + errorMsg);

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

        // 🟢 Nếu đến đây nghĩa là service thành công
        System.out.println("Đổi mật khẩu thành công! Redirect về login.jsp");
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    }

}
