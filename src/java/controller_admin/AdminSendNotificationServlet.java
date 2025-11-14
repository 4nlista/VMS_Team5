package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AdminSendNotificationService;
import dao.AccountDAO;
import dao.AdminUserDAO;
import dao.UserDAO;
import model.Account;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AdminSendNotificationServlet", urlPatterns = {"/admin/AdminSendNotificationServlet"}) 
public class AdminSendNotificationServlet extends HttpServlet {
   
    private AdminSendNotificationService notificationService;
    private AccountDAO accountDAO;
    private AdminUserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        notificationService = new AdminSendNotificationService();
        accountDAO = new AccountDAO();
        userDAO = new AdminUserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        // Kiểm tra session admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
        
        Account admin = (Account) session.getAttribute("account");
        if (!"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ Admin mới có quyền truy cập!");
            return;
        }
        
        if ("individual".equals(action)) {
            // Hiển thị form gửi thông báo cá nhân
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            Account account = accountDAO.getAccountById(accountId);
            User user = userDAO.getUserByAccountId(accountId);
            
            if (account == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy tài khoản!");
                return;
            }
            
            request.setAttribute("account", account);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/admin/send_notification_admin.jsp").forward(request, response);
            
        } else if ("all".equals(action)) {
            // Hiển thị form gửi thông báo chung
            request.getRequestDispatcher("/admin/send_all_notification_admin.jsp").forward(request, response);
            
        } else if ("countRecipients".equals(action)) {
            // AJAX: Đếm số lượng recipients
            String rolesParam = request.getParameter("roles");
            String statusFilter = request.getParameter("status");
            
            List<String> roles = Arrays.asList(rolesParam.split(","));
            int count = notificationService.countRecipients(roles, statusFilter);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("{\"count\": " + count + "}");
            out.flush();
        }
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        // Kiểm tra session admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
        
        Account admin = (Account) session.getAttribute("account");
        if (!"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ Admin mới có quyền truy cập!");
            return;
        }
        
        if ("sendIndividual".equals(action)) {
            // Gửi thông báo cá nhân
            int receiverId = Integer.parseInt(request.getParameter("receiverId"));
            String message = request.getParameter("message");
            
            boolean success = notificationService.sendIndividualNotification(admin.getId(), receiverId, message);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/AdminSendNotificationServlet?action=individual&accountId=" + receiverId + "&msg=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/AdminSendNotificationServlet?action=individual&accountId=" + receiverId + "&msg=error");
            }
            
        } else if ("sendAll".equals(action)) {
            // Gửi thông báo chung
            String message = request.getParameter("message");
            String rolesParam = request.getParameter("roles");
            String statusFilter = request.getParameter("status");
            
            List<String> roles = Arrays.asList(rolesParam.split(","));
            int successCount = notificationService.sendBulkNotification(admin.getId(), message, roles, statusFilter);
            
            if (successCount > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/AdminSendNotificationServlet?action=all&msg=success&count=" + successCount);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/AdminSendNotificationServlet?action=all&msg=error");
            }
        }
    }
}
