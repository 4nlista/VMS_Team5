package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import service.LoginService;

// Servlet điều hướng các hành động yêu cầu đăng nhập (donate, volunteer)
// Nếu chưa login → lưu action vào session và chuyển đến trang login
// Nếu đã login → gọi LoginService để quyết định redirect đến đâu
@WebServlet(name = "ActionRedirectServlet", urlPatterns = {"/ActionRedirectServlet"})

public class ActionRedirectServlet extends HttpServlet {

    private LoginService loginService;

    @Override
    public void init() {
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        HttpSession session = request.getSession();
         // Lấy account từ session để kiểm tra đã login 
        Account acc = (Account) session.getAttribute("account");
        
         // Lấy action từ request parameter (donate, be_a_volunteer...)
        String action = request.getParameter("action");

        if (acc == null) {
            // guest → Chưa login: lưu action để redirect sau khi login thành công
            session.setAttribute("redirectAfterLogin", action);
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        } else {
            // Đã login: gọi LoginService để xử lý logic phân quyền và redirect
            String redirectUrl = loginService.resolveRedirect(acc, session, action);
            response.sendRedirect(request.getContextPath() + redirectUrl);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
