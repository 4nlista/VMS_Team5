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

// Mục đích: chặn các hành động yêu cầu login trước (như donate, volunteer).
// Nếu chưa login → lưu redirectAfterLogin vào session và chuyển sang trang login.jsp.
// Nếu đã login → redirect trực tiếp đến action tương ứng (donate.jsp, VolunteerHomeServlet).
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
        Account acc = (Account) session.getAttribute("account");
        String action = request.getParameter("action");

        if (acc == null) {
            // guest → nhớ action, bắt login
            session.setAttribute("redirectAfterLogin", action);
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        } else {
            // đã login → delegate quyết định redirect cho LoginService
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
