package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.Date;
import model.Account;
import model.User;
import service.RegisterService;
import utils.PasswordUtil;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})

public class RegisterServlet extends HttpServlet {

    private RegisterService registerService;

    @Override
    public void init() throws ServletException {
        registerService = new RegisterService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("auth/register.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String role = request.getParameter("role");
        String dobStr = request.getParameter("dob");

        //  Kiểm tra mật khẩu khớp
        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("auth/register.jsp").forward(request, response);
            return;
        }

        //  Kiểm tra độ dài mật khẩu (tối thiểu 6 ký tự)
        if (password.length() < 6) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự!");
            request.getRequestDispatcher("auth/register.jsp").forward(request, response);
            return;
        }

        // Chuyển đổi DOB sang java.sql.Date
        Date dob = null;
        if (dobStr != null && !dobStr.isEmpty()) {
            try {
                dob = Date.valueOf(dobStr);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Định dạng ngày sinh không hợp lệ!");
                request.getRequestDispatcher("auth/register.jsp").forward(request, response);
                return;
            }
        }

        // Tạo đối tượng Account
        Account acc = new Account();
        acc.setUsername(username);
        acc.setPassword(password);
        acc.setRole(role);
        acc.setStatus(true);

        // Tạo đối tượng User
        User user = new User();
        user.setFull_name(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setGender(gender);
        user.setDob(dob);

        // Gọi service để xử lý đăng ký
        String result = registerService.register(acc, user);

        // Xử lý kết quả
        if ("success".equals(result)) {
            request.getSession().setAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            response.sendRedirect("auth/login.jsp");
        } else {
            request.setAttribute("error", result);
            request.setAttribute("acc", acc);
            request.setAttribute("user", user);
            request.getRequestDispatcher("auth/register.jsp").forward(request, response);
        }
    }
}
