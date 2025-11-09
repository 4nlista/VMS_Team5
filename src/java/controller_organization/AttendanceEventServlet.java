package controller_organization ;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.Attendance;
import service.AccountService;
import service.AttendanceService;

@WebServlet(name = "AttendanceEventServlet", urlPatterns = {"/AttendanceEventServlet"}) 

public class AttendanceEventServlet extends HttpServlet {
   
   private AccountService accountService;
    private AttendanceService attendanceService;
    
    @Override
    public void init() {
        accountService = new AccountService();
        attendanceService = new AttendanceService();
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
        
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }
        
        // Lấy eventId
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
            return;
        }
        
        int eventId = Integer.parseInt(eventIdParam);
        
        // Lấy filter status (nếu có)
        String statusFilter = request.getParameter("status");
        if (statusFilter == null || statusFilter.isEmpty()) {
            statusFilter = "all";
        }
        
        // Lấy danh sách volunteer để điểm danh
        List<Attendance> volunteers = attendanceService.getVolunteersForAttendance(eventId, statusFilter);
        
        // Set attributes
        request.setAttribute("volunteers", volunteers);
        request.setAttribute("eventId", eventId);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("account", acc);
        
        // Forward to JSP
        request.getRequestDispatcher("/organization/attendance_org.jsp").forward(request, response);
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
        
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }
        
        String action = request.getParameter("action");
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        
        if ("update".equals(action)) {
            // Lấy tất cả volunteerIds và status tương ứng
            String[] volunteerIds = request.getParameterValues("volunteerId");
            
            if (volunteerIds != null) {
                for (String volId : volunteerIds) {
                    String newStatus = request.getParameter("status_" + volId);
                    if (newStatus != null && !newStatus.isEmpty()) {
                        attendanceService.updateAttendanceStatus(eventId, Integer.parseInt(volId), newStatus);
                    }
                }
            }
            
            // Redirect về danh sách events
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
        } else {
            // Nút Hủy - quay về danh sách
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
        }
    }
}
