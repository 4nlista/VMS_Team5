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
        
        // Kiểm tra xem có thể cập nhật điểm danh không
        boolean canUpdate = attendanceService.canUpdateAttendance(eventId);
        
        // Set attributes
        request.setAttribute("volunteers", volunteers);
        request.setAttribute("eventId", eventId);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("canUpdate", canUpdate);
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
            // Kiểm tra xem có thể cập nhật điểm danh không
            if (!attendanceService.canUpdateAttendance(eventId)) {
                session.setAttribute("errorMessage", "Không thể cập nhật điểm danh! Sự kiện chưa bắt đầu hoặc đã kết thúc hơn 24 giờ.");
                response.sendRedirect(request.getContextPath() + "/AttendanceEventServlet?eventId=" + eventId);
                return;
            }
            
            // Lấy tất cả volunteerIds và status tương ứng
            String[] volunteerIds = request.getParameterValues("volunteerId");
            
            int successCount = 0;
            int failCount = 0;
            String firstError = null;
            
            if (volunteerIds != null) {
                for (String volId : volunteerIds) {
                    String newStatus = request.getParameter("status_" + volId);
                    if (newStatus != null && !newStatus.isEmpty()) {
                        String result = attendanceService.updateAttendanceStatus(eventId, Integer.parseInt(volId), newStatus);
                        
                        if ("success".equals(result)) {
                            successCount++;
                        } else {
                            failCount++;
                            if (firstError == null) {
                                firstError = result;
                            }
                        }
                    }
                }
            }
            
            // Set message
            if (successCount > 0 && failCount == 0) {
                session.setAttribute("successMessage", "Cập nhật điểm danh thành công cho " + successCount + " tình nguyện viên!");
            } else if (successCount > 0 && failCount > 0) {
                session.setAttribute("warningMessage", "Cập nhật thành công " + successCount + ", thất bại " + failCount + ". Lỗi: " + firstError);
            } else if (failCount > 0) {
                session.setAttribute("errorMessage", "Cập nhật điểm danh thất bại! " + firstError);
            }
            
            // Redirect về danh sách events
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
        } else {
            // Nút Hủy - quay về danh sách
            response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
        }
    }
}
