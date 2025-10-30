package controller_volunteer;

import dao.EventVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "CancelApplicationServlet", urlPatterns = {"/CancelEventServlet"})
public class CancelApplicationServlet extends HttpServlet {

    private EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId"); // account id của volunteer

        if (accountId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            // Kiểm tra trạng thái hiện tại của đơn
            String status = eventVolunteerDAO.getStatus(eventId, accountId); // DAO cần có method này

            if ("pending".equalsIgnoreCase(status)) {
                boolean success = eventVolunteerDAO.cancelParticipation(eventId, accountId);

                if (success) {
                    // Set session để JSP hiện pop-up
                    session.setAttribute("cancelMessage", "Hủy đơn đăng ký thành công!");
                } else {
                    session.setAttribute("cancelMessage", "Không thể hủy đơn. Vui lòng thử lại!");
                }
            } else if (status == null) {
                session.setAttribute("cancelMessage", "Đơn đăng ký không tồn tại!");
            } else {
                session.setAttribute("cancelMessage", "Không thể hủy vì đơn đã được duyệt hoặc từ chối!");
            }

            // Quay lại trang đăng ký/đã đăng ký
            response.sendRedirect(request.getContextPath() + "/volunteer/apply_event_volunteer.jsp?eventId=" + eventId);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("cancelMessage", "Lỗi hệ thống khi hủy đơn!");
            response.sendRedirect(request.getContextPath() + "/volunteer/apply_event_volunteer.jsp");
        }
    }
}
