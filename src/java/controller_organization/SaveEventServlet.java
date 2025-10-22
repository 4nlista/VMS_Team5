package controller_organization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.EventService;
import model.Event;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/organization/saveEvent")
public class SaveEventServlet extends HttpServlet {  // Dùng chung cho create/update
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer organizationId = (Integer) session.getAttribute("accountId");
        if (organizationId == null) {
            response.sendRedirect("../auth/login.jsp");
            return;
        }

        Event event = new Event();
        event.setId(Integer.parseInt(request.getParameter("id")));  // 0 nếu create mới
        event.setTitle(request.getParameter("title"));
        event.setDescription(request.getParameter("description"));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startDate = sdf.parse(request.getParameter("start_date"));
            Date endDate = sdf.parse(request.getParameter("end_date"));
            event.setStartDate((java.sql.Date) startDate);
            event.setEndDate((java.sql.Date) endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.setLocation(request.getParameter("location"));
        event.setNeededVolunteers(Integer.parseInt(request.getParameter("needed_volunteers")));
        event.setStatus(request.getParameter("status"));
        event.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        event.setOrganizationId(organizationId);

        EventService service = new EventService();
        boolean success = service.saveEvent(event);
        if (success) {
            request.setAttribute("message", "Lưu sự kiện thành công!");
        } else {
            request.setAttribute("error", "Lỗi khi lưu sự kiện!");
        }
        response.sendRedirect("events");  // Quay về danh sách
    }
}