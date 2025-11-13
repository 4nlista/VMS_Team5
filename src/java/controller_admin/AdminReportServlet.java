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
import model.Notification;
import model.Report;
import service.AccountService;
import service.AdminReportService;

@WebServlet(name = "AdminReportServlet", urlPatterns = {"/AdminReportServlet"})
public class AdminReportServlet extends HttpServlet {

    private AccountService accountService;
    private AdminReportService reportService;

    @Override
    public void init() {
        accountService = new AccountService();
        reportService = new AdminReportService();
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
            // Hiển thị chi tiết 1 báo cáo
            showReportDetail(request, response, acc);
        } else {
            // Hiển thị danh sách
            showReportList(request, response, acc);
        }
    }

    //Hiển thị danh sách Reports
    private void showReportList(HttpServletRequest request, HttpServletResponse response, Account acc)
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

        int pageSize = 10; // 10 báo cáo/trang

        // Lấy danh sách Reports
        List<Report> reportList = reportService.getAllReports(statusFilter, sortOrder, page, pageSize);

        // Tính tổng số trang
        int totalRecords = reportService.getTotalReports(statusFilter);
        int totalPages = reportService.getTotalPages(totalRecords, pageSize);

        // Tính số báo cáo hiển thị
        int startRecord = (page - 1) * pageSize + 1;
        int endRecord = Math.min(page * pageSize, totalRecords);

        // Set attributes
        request.setAttribute("reportList", reportList);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("startRecord", startRecord);
        request.setAttribute("endRecord", endRecord);
        request.setAttribute("account", acc);

        // Forward to JSP
        request.getRequestDispatcher("/admin/manage_report_admin.jsp").forward(request, response);
    }

    // Hiển thị chi tiết 1 Report
    private void showReportDetail(HttpServletRequest request, HttpServletResponse response, Account acc)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/AdminReportServlet");
            return;
        }

        int reportId = Integer.parseInt(idParam);

        // Lấy chi tiết Report
        Report report = reportService.getReportById(reportId);

        if (report == null) {
            response.sendRedirect(request.getContextPath() + "/AdminReportServlet");
            return;
        }

        // Set attributes
        request.setAttribute("report", report);
        request.setAttribute("account", acc);

        // Forward to JSP
        request.getRequestDispatcher("/admin/detail_report_admin.jsp").forward(request, response);
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
        String reportIdParam = request.getParameter("reportId");
        String feedbackIdParam = request.getParameter("feedbackId");

        if (reportIdParam == null || reportIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/AdminReportServlet");
            return;
        }

        int reportId = Integer.parseInt(reportIdParam);
        int feedbackId = 0;

        if (feedbackIdParam != null && !feedbackIdParam.isEmpty()) {
            feedbackId = Integer.parseInt(feedbackIdParam);
        }

        // Lấy thông tin report trước khi xử lý để biết organizationId
        Report report = reportService.getReportById(reportId);

        boolean success = false;

        switch (action) {
            case "approve":
                success = reportService.approveReport(reportId);

                // GỬI THÔNG BÁO CHO ORG
                if (success && report != null) {
                    try {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId()); // Admin gửi
                        noti.setReceiverId(report.getOrganizationId());
                        noti.setMessage("Admin đã chấp nhận báo cáo vi phạm của bạn");
                        noti.setType("report");
                        noti.setEventId(0);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "approve_and_lock":
                success = reportService.approveReportAndLockAccount(reportId, feedbackId);

                // GỬI THÔNG BÁO CHO ORG
                if (success && report != null) {
                    try {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId());
                        noti.setReceiverId(report.getOrganizationId());
                        noti.setMessage("Admin đã chấp nhận báo cáo vi phạm và khóa tài khoản volunteer");
                        noti.setType("report");
                        noti.setEventId(0);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "reject":
                success = reportService.rejectReport(reportId);

                // GỬI THÔNG BÁO CHO ORG
                if (success && report != null) {
                    try {
                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId());
                        noti.setReceiverId(report.getOrganizationId());
                        noti.setMessage("Admin đã từ chối báo cáo vi phạm của bạn");
                        noti.setType("report");
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
            response.sendRedirect(request.getContextPath() + "/AdminReportServlet?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/AdminReportServlet?error=true");
        }
    }
}
