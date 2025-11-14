package controller_volunteer;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Attendance;
import service.AttendanceService;

@WebServlet("/VolunteerAttendanceServlet")
public class VolunteerAttendanceServlet extends HttpServlet {

    private AttendanceService attendanceService = new AttendanceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("accountId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int volunteerId = (int) session.getAttribute("accountId");

        // Lấy params phân trang và filter
        String pageParam = request.getParameter("page");
        String statusFilter = request.getParameter("status");
        
        int page = 1;
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        if (statusFilter == null || statusFilter.trim().isEmpty()) {
            statusFilter = "all";
        }
        
        int pageSize = 5; // 5 dòng/trang
        
        // Lấy danh sách với phân trang và filter
        List<Attendance> attendanceList = attendanceService.getAttendanceHistoryPaginated(volunteerId, page, pageSize, statusFilter);
        
        // Tính tổng số trang
        int totalAttendance = attendanceService.getTotalAttendance(volunteerId, statusFilter);
        int totalPages = (int) Math.ceil((double) totalAttendance / pageSize);

        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("statusFilter", statusFilter);

        request.getRequestDispatcher("/volunteer/history_attendance_volunteer.jsp")
                .forward(request, response);
    }
}
