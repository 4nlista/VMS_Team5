package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import model.Account;
import service.AccountService;

@WebServlet(name = "AdminAccountServlet", urlPatterns = {"/AdminAccountServlet"})

public class AdminAccountServlet extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() {
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách account từ DB
        List<Account> accounts = accountService.getAllAccounts();

        // Gửi qua JSP
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/admin/accounts_admin.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Tạm thời chưa xử lý, sau thêm các chức năng thêm, sửa, xóa
        doGet(request, response);
    }
}
