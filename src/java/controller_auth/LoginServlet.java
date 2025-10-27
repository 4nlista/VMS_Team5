package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import service.LoginService;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})

public class LoginServlet extends HttpServlet {

    private LoginService loginService;

    @Override
    public void init() {
        loginService = new LoginService();
    }

    // GET: hiển thị trang login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hủy session để người dùng không thể back truy cập lại các trang bảo mật
        request.getSession().invalidate();
        // Sau khi hủy session, chuyển hướng người dùng về trang đăng nhập
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        //  request.getRequestDispatcher("auth/login.jsp").forward(request, response);
    }

    // POST: xử lý form login
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        // Gọi service xử lý login
        String redirectUrl = loginService.processLogin(username, password, session);

        if (redirectUrl != null) {
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } else {
            request.setAttribute("error", "Sai username hoặc password rồi em ơi!");
            request.getRequestDispatcher("auth/login.jsp").forward(request, response);
        }
    }

}
