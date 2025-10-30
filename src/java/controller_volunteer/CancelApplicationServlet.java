package controller_volunteer;

import dao.EventVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "CancelEventServlet", urlPatterns = {"/CancelEventServlet"})
public class CancelApplicationServlet extends HttpServlet {

    private EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            // Xóa mọi session message cũ
            session.removeAttribute("applyMessage");
            session.removeAttribute("justApplied");

            String status = eventVolunteerDAO.getStatus(eventId, accountId);

            if ("pending".equalsIgnoreCase(status)) {

                boolean success = eventVolunteerDAO.cancelParticipation(eventId, accountId);

                if (success) {
                    session.setAttribute("cancelMessage", "Hủy đơn đăng ký thành công!");

                    // Xóa dữ liệu người dùng đã nhập (giờ và ghi chú)
                    session.removeAttribute("hoursValue");
                    session.removeAttribute("noteValue");

                } else {
                    session.setAttribute("cancelMessage", "Không thể hủy đơn. Vui lòng thử lại!");
                }

            } else if (status == null) {
                session.setAttribute("cancelMessage", "Đơn đăng ký không tồn tại!");
            } else {
                session.setAttribute("cancelMessage", "Không thể hủy vì đơn đã được duyệt hoặc từ chối!");
            }

            // ✅ Quay lại trang đăng ký sự kiện
            response.sendRedirect(request.getContextPath()
                    + "/volunteer/apply_event_volunteer.jsp?eventId=" + eventId);

        } catch (Exception e) {
            e.printStackTrace();
            // Xóa flag cũ để tránh hiển thị sai modal
            session.removeAttribute("applyMessage");
            session.removeAttribute("justApplied");
            session.setAttribute("cancelMessage", "Lỗi hệ thống khi hủy đơn!");
            response.sendRedirect(request.getContextPath() + "/volunteer/apply_event_volunteer.jsp");
        }
    }
}
