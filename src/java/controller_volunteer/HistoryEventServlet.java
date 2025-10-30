package controller_volunteer;

import dao.EventVolunteerDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.EventVolunteerInfo;

@WebServlet("/HistoryEventServlet")
public class HistoryEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId"); // Lấy từ session
        if (accountId== null) {
            response.sendRedirect("/auth/login.jsp"); // Nếu chưa đăng nhập
            return;
        }

        EventVolunteerDAO dao = new EventVolunteerDAO();
        List<EventVolunteerInfo> history = dao.getAllApplicationsByVolunteer(accountId);

        request.setAttribute("historyList", history);
        request.getRequestDispatcher("/volunteer/history_event_volunteer.jsp").forward(request, response);
    }
}
