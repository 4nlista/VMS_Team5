package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import service.AdminAccountService;

@WebServlet(name = "AdminHomeServlet", urlPatterns = {"/AdminHomeServlet"})

public class AdminHomeServlet extends HttpServlet {

    private AdminAccountService adminAccountService;

    @Override
    public void init() {
        adminAccountService = new AdminAccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        Account sessionAccount = (Account) session.getAttribute("account");
        Account acc = adminAccountService.getAccountById(sessionAccount.getId());
        if (acc == null || !acc.getRole().equals("admin")) {

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }
        request.setAttribute("username", acc.getUsername());
        request.getRequestDispatcher("/admin/home_admin.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
