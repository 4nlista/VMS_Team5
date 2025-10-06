package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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

    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form JSP
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        // Mã hóa mật khẩu (bắt buộc)
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Tạo đối tượng Account
        Account acc = new Account();
        acc.setUsername(username);
        acc.setPassword(hashedPassword);
        acc.setRole("volunteer"); // mặc định
        acc.setStatus(true);

        // Tạo đối tượng User
        User user = new User();
        user.setFull_name(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setGender(gender);
        user.setAddress(address);

        // Gọi service để xử lý logic
        String result = registerService.register(acc, user);

        if ("success".equals(result)) {
            // Nếu đăng ký thành công → chuyển đến trang login
            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // Nếu thất bại → trả lỗi về form
            request.setAttribute("error", result);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

    }
}
