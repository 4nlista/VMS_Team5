package controller_organization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.EventVolunteer;
import service.AccountService;
import service.OrganizationApplyService;

@WebServlet(name = "OrganizationApplyServlet", urlPatterns = {"/OrganizationApplyServlet"})

public class OrganizationApplyServlet extends HttpServlet {

    private AccountService accountService;
    private OrganizationApplyService organizationApplyService;

    @Override
    public void init() {
        accountService = new AccountService();
        organizationApplyService = new OrganizationApplyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId()); // cập nhật từ DB
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        String eventIdParam = request.getParameter("id");
        if (eventIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
            return;
        }
        int organizationId = acc.getId();
        int eventId = Integer.parseInt(eventIdParam);

        // nhận trạng thái lọc, mặc định là all
        String statusFilter = request.getParameter(("statusFilter"));
        if (statusFilter == null) {
            statusFilter = "all";
        }

        // Tự động reject và lấy số lượng bị ảnh hưởng
        int autoRejectedCount = organizationApplyService.autoRejectPendingApplications(eventId);
        if (autoRejectedCount > 0) {
            session.setAttribute("warningMessage", 
                "Đã tự động từ chối " + autoRejectedCount + " đơn đăng ký do không được xử lý trong 24h trước sự kiện.");
        }
        
        List<EventVolunteer> volunteers = organizationApplyService.getFilterVolunteersByEvent(organizationId, eventId, statusFilter);
        request.setAttribute("volunteers", volunteers);
        request.setAttribute("eventId", eventId);
        request.setAttribute("statusFilter", statusFilter);
        // Gửi sang JSP
        request.setAttribute("account", acc);
        request.getRequestDispatcher("/organization/apply_org.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        String action = request.getParameter("action");
        int applyEventId = Integer.parseInt(request.getParameter("id"));

        if ("approve".equals(action)) {
            organizationApplyService.updateVolunteerStatus(applyEventId, "approved");
            session.setAttribute("successMessage", "Đã duyệt đơn đăng ký thành công!");
        } else if ("reject".equals(action)) {
            organizationApplyService.updateVolunteerStatus(applyEventId, "rejected");
            session.setAttribute("successMessage", "Đã từ chối đơn đăng ký!");
        }

        // Quay lại trang danh sách apply của event
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        response.sendRedirect(request.getContextPath() + "/OrganizationApplyServlet?id=" + eventId);
    }

}
