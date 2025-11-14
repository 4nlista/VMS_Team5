package controller_organization ;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Notification;
import service.OrganizationNotificationService;

@WebServlet(name = "OrganizationNotificationServlet", urlPatterns = {"/OrganizationNotificationServlet"})

public class OrganizationNotificationServlet extends HttpServlet {

    private OrganizationNotificationService service;

    @Override
    public void init() throws ServletException {
        service = new OrganizationNotificationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer organizationId = (Integer) session.getAttribute("accountId");

        if (organizationId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

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
            sortOrder = "newest"; // Mặc định mới nhất
        }

        int pageSize = 3; // 3 thông báo/trang

        // Kiểm tra xem có filter theo ngày không
        boolean hasDateFilter = (startDate != null && !startDate.trim().isEmpty()) 
                             || (endDate != null && !endDate.trim().isEmpty());

        List<Notification> notifications;
        int totalNotifications;
        
        if (hasDateFilter) {
            // Lấy danh sách với filter ngày
            notifications = service.getNotificationsPaginatedWithDateFilter(organizationId, page, pageSize, sortOrder, startDate, endDate);
            totalNotifications = service.getTotalNotificationsWithDateFilter(organizationId, startDate, endDate);
        } else {
            // Lấy danh sách bình thường
            notifications = service.getNotificationsPaginated(organizationId, page, pageSize, sortOrder);
            totalNotifications = service.getTotalNotifications(organizationId);
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

        request.getRequestDispatcher("/organization/notification_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer organizationId = (Integer) session.getAttribute("accountId");

        if (organizationId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

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
            service.markAllAsRead(organizationId);
            session.setAttribute("message", "Đã đánh dấu tất cả thông báo là đã đọc!");
        }

        // Redirect về trang thông báo
        response.sendRedirect(request.getContextPath() + "/OrganizationNotificationServlet");
    }
}
