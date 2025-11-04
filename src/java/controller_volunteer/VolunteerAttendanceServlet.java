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

        List<Attendance> attendanceList = attendanceService.getAttendanceHistory(volunteerId);

        request.setAttribute("attendanceList", attendanceList);

        request.getRequestDispatcher("/views/volunteer/history_attendance.jsp")
                .forward(request, response);
    }
}
