package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import service.AccountService;
import service.AdminHomeService;

@WebServlet(name = "AdminHomeServlet", urlPatterns = {"/AdminHomeServlet"})

public class AdminHomeServlet extends HttpServlet {

    private AccountService accountService;
    private AdminHomeService adminHomeService;

    @Override
    public void init() {
        accountService = new AccountService();
        adminHomeService = new AdminHomeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        Account sessionAccount = (Account) session.getAttribute("account");
        Account acc = accountService.getAccountById(sessionAccount.getId());
        if (acc == null || !acc.getRole().equals("admin")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }
        int totalAccounts = adminHomeService.getTotalAccount();
        double totalMoneyDonate = adminHomeService.getTotalMoneyDonate();
        request.setAttribute("totalAccounts", totalAccounts);
        request.setAttribute("totalMoneyDonate", totalMoneyDonate);
        request.setAttribute("username", acc.getUsername());
        request.getRequestDispatcher("/admin/home_admin.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
