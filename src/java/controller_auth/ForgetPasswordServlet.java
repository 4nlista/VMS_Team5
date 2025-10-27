package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import service.ForgetPasswordService;

@WebServlet(name = "ForgetPasswordServlet", urlPatterns = {"/ForgetPasswordServlet"})

public class ForgetPasswordServlet extends HttpServlet {

    private ForgetPasswordService service = new ForgetPasswordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        boolean success = service.processForgetPassword(username, email);
        if (success) {
            request.setAttribute("msg", "Mật khẩu mới đã được gửi đến email của bạn.");
        } else {
            request.setAttribute("error", "Tên đăng nhập hoặc email không hợp lệ.");
        }
        request.getRequestDispatcher("auth/reset_password.jsp").forward(request, response);

    }
}
