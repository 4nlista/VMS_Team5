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
import model.Notification;
import service.AccountService;
import service.AdminNotificationService;

@WebServlet(name = "AdminNotificationServlet", urlPatterns = {"/AdminNotificationServlet"})

public class AdminNotificationServlet extends HttpServlet {

    private AdminNotificationService service;
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        service = new AdminNotificationService();
        accountService = new AccountService();
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
        if (acc == null || !"admin".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        int adminId = acc.getId();

        // Lấy params phân trang và lọc
        String pageStr = request.getParameter("page");
        String sortOrder = request.getParameter("sort");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        int page = 1;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        if (sortOrder == null || (!sortOrder.equals("oldest") && !sortOrder.equals("newest"))) {
            sortOrder = "newest";
        }

        int pageSize = 3;

        // Kiểm tra xem có filter theo ngày không
        boolean hasDateFilter = (startDate != null && !startDate.trim().isEmpty()) 
                             || (endDate != null && !endDate.trim().isEmpty());

        List<Notification> notifications;
        int totalNotifications;
        
        if (hasDateFilter) {
            // Lấy danh sách với filter ngày
            notifications = service.getNotificationsPaginatedWithDateFilter(adminId, page, pageSize, sortOrder, startDate, endDate);
            totalNotifications = service.getTotalNotificationsWithDateFilter(adminId, startDate, endDate);
        } else {
            // Lấy danh sách bình thường
            notifications = service.getNotificationsPaginated(adminId, page, pageSize, sortOrder);
            totalNotifications = service.getTotalNotifications(adminId);
        }

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalNotifications / pageSize);

        // Set attributes
        request.setAttribute("notifications", notifications);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("totalNotifications", totalNotifications);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("account", acc);

        request.getRequestDispatcher("/admin/notifications_admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account sessionAccount = (Account) session.getAttribute("account");
        Account acc = accountService.getAccountById(sessionAccount.getId());
        if (acc == null || !"admin".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        System.out.println("DEBUG - Account ID: " + acc.getId());
        System.out.println("DEBUG - Account Role: " + acc.getRole());

        int adminId = acc.getId();
        String action = request.getParameter("action");

        // Action: Đánh dấu 1 thông báo đã đọc
        if ("markRead".equals(action)) {
            String notificationIdStr = request.getParameter("notificationId");
            try {
                int notificationId = Integer.parseInt(notificationIdStr);
                service.markAsRead(notificationId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } // Action: Đánh dấu tất cả đã đọc
        else if ("markAllRead".equals(action)) {
            service.markAllAsRead(adminId);
            session.setAttribute("message", "Đã đánh dấu tất cả thông báo là đã đọc!");
        }

        // Redirect về trang thông báo
        response.sendRedirect(request.getContextPath() + "/AdminNotificationServlet");
    }
}
