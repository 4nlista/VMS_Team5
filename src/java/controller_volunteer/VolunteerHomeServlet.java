package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import service.AccountService;

@WebServlet(name = "VolunteerHomeServlet", urlPatterns = {"/VolunteerHomeServlet"})

public class VolunteerHomeServlet extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() {
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            // Nếu session không hợp lệ, điều hướng về login
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());  // Lấy lại từ DB cho chắc chắn

        if (acc == null || !acc.getRole().equals("volunteer")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        // Lưu fullname vào session
        session.setAttribute("username", acc.getUsername());
        // Forward đến JSP, không redirect
        request.getRequestDispatcher("/volunteer/home_volunteer.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
