/*
 * A friendly reminder to drink enough water
 */
package controller_admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.User;
import service.AccountService;
import service.AdminHomeService;
import service.AdminProfileService;

/**
 *
 * @author Mirinesa
 */
@WebServlet(name = "AdminProfileServlet", urlPatterns = {"/AdminProfileServlet"})
public class AdminProfileServlet extends HttpServlet {

    private AdminProfileService profileService = new AdminProfileService();
    private AccountService accountService;
    private AdminHomeService adminHomeService;

    @Override
    public void init() {
        accountService = new AccountService();
        adminHomeService = new AdminHomeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        try {
            User user = profileService.loadProfileById(request);
            if (user == null) {
                response.sendRedirect("AdminProfileServlet?error=UserNotFound");
                return;
            }
            request.setAttribute("user", user);
            request.getRequestDispatcher("/admin/profile_admin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AdminProfileServlet?error=InvalidRequest");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Unused, kept for compatibility
        response.sendRedirect("AdminProfileServlet");
    }
}
