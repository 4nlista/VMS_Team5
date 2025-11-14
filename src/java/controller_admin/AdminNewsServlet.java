package controller_admin;

import dao.NotificationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.New;
import model.Notification;
import service.AccountService;
import service.AdminNewsService;

@WebServlet(name = "AdminNewsServlet", urlPatterns = {"/AdminNewsServlet"})

public class AdminNewsServlet extends HttpServlet {

    private AccountService accountService;
    private AdminNewsService newsService;

    @Override
    public void init() {
        accountService = new AccountService();
        newsService = new AdminNewsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());

        // Kiểm tra role admin
        if (acc == null || !"admin".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        // Kiểm tra action (mặc định: danh sách)
        String action = request.getParameter("action");

        if ("detail".equals(action)) {
            // Hiển thị chi tiết 1 bài
            showNewsDetail(request, response, acc);
        } else {
            // Hiển thị danh sách
            showNewsList(request, response, acc);
        }
    }

    // Hiển thị danh sách News
    private void showNewsList(HttpServletRequest request, HttpServletResponse response, Account acc)
            throws ServletException, IOException {

        // Lấy tham số filter + sort + page
        String statusFilter = request.getParameter("status");
        String sortOrder = request.getParameter("sort");
        String pageParam = request.getParameter("page");

        // Set giá trị mặc định
        if (statusFilter == null || statusFilter.isEmpty()) {
            statusFilter = "all";
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "newest";
        }

        int page = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int pageSize = 5; // 10 bài/trang

        // Lấy danh sách News
        List<New> newsList = newsService.getAllNews(statusFilter, sortOrder, page, pageSize);

        // Tính tổng số trang
        int totalRecords = newsService.getTotalNews(statusFilter);
        int totalPages = newsService.getTotalPages(totalRecords, pageSize);

        // Tính số bài hiển thị
        int startRecord = (page - 1) * pageSize + 1;
        int endRecord = Math.min(page * pageSize, totalRecords);

        // Set attributes
        request.setAttribute("newsList", newsList);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("startRecord", startRecord);
        request.setAttribute("endRecord", endRecord);
        request.setAttribute("account", acc);

        // Forward to JSP
        request.getRequestDispatcher("/admin/manage_news_admin.jsp").forward(request, response);
    }

    // Hiển thị chi tiết 1 bài News
    private void showNewsDetail(HttpServletRequest request, HttpServletResponse response, Account acc)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/AdminNewsServlet");
            return;
        }

        int newsId = Integer.parseInt(idParam);

        // Lấy chi tiết bài News
        New news = newsService.getNewsById(newsId);

        if (news == null) {
            response.sendRedirect(request.getContextPath() + "/AdminNewsServlet");
            return;
        }

        // Set attributes
        request.setAttribute("news", news);
        request.setAttribute("account", acc);

        // Forward to JSP
        request.getRequestDispatcher("/admin/detail_new_admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());

        // Kiểm tra role admin
        if (acc == null || !"admin".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        String action = request.getParameter("action");
        String newsIdParam = request.getParameter("newsId");

        if (newsIdParam == null || newsIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/AdminNewsServlet");
            return;
        }

        int newsId = Integer.parseInt(newsIdParam);

        // Lấy thông tin news trước khi xử lý để biết organizationId
        New news = newsService.getNewsById(newsId);

        boolean success = false;

        switch (action) {
            case "approve":
                success = newsService.approveNews(newsId);

                // GỬI THÔNG BÁO CHO ORG
                if (success && news != null) {
                    try {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId()); // Admin gửi
                        noti.setReceiverId(news.getOrganizationId());
                        noti.setMessage("Admin đã duyệt tin tức \"" + news.getTitle() + "\" của bạn");
                        noti.setType("system");
                        noti.setEventId(0);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "reject":
                success = newsService.rejectNews(newsId);

                // GỬI THÔNG BÁO CHO ORG
                if (success && news != null) {
                    try {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId());
                        noti.setReceiverId(news.getOrganizationId());
                        noti.setMessage("Admin đã từ chối tin tức \"" + news.getTitle() + "\" của bạn");
                        noti.setType("system");
                        noti.setEventId(0);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "hide":
                success = newsService.hideNews(newsId);

                // GỬI THÔNG BÁO CHO ORG
                if (success && news != null) {
                    try {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId());
                        noti.setReceiverId(news.getOrganizationId());
                        noti.setMessage("Admin đã ẩn tin tức \"" + news.getTitle() + "\" của bạn");
                        noti.setType("system");
                        noti.setEventId(0);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "publish":
                success = newsService.showNews(newsId);

                // GỬI THÔNG BÁO CHO ORG
                if (success && news != null) {
                    try {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId());
                        noti.setReceiverId(news.getOrganizationId());
                        noti.setMessage("Admin đã hiển thị lại tin tức \"" + news.getTitle() + "\" của bạn");
                        noti.setType("system");
                        noti.setEventId(0);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
        // Redirect về danh sách
        if (success) {
            response.sendRedirect(request.getContextPath() + "/AdminNewsServlet?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/AdminNewsServlet?error=true");
        }
    }
}
