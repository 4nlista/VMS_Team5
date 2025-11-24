package service;

import dao.EventVolunteerDAO;
import dao.ViewEventsDAO;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import model.Event;

public class VolunteerCancelService {

    private final EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();
    private final ViewEventsDAO viewsEventDAO = new ViewEventsDAO();

    /**
     * Hủy đơn đăng ký sự kiện Chỉ cho phép hủy nếu status = Pending và còn >
     * 24h trước sự kiện
     */
    public String cancelApplication(int eventId, int volunteerId) {
        String status = eventVolunteerDAO.getApplicationStatus(eventId, volunteerId);

        if (status == null) {
            return "Không tìm thấy đơn đăng ký!";
        }

        Event event = viewsEventDAO.getEventById(eventId);
        if (event != null && event.getStartDate() != null) {

            long diffMs = event.getStartDate().getTime() - System.currentTimeMillis();

            // Không cho hủy nếu còn dưới hoặc đúng 24h
            if (diffMs <= 24L * 60 * 60 * 1000) {
                return "Không thể hủy đơn vì sự kiện sắp diễn ra trong vòng 24 giờ!";
            }
        }

        switch (status.toLowerCase()) {
            case "pending":
                boolean success = eventVolunteerDAO.deletePendingApplication(eventId, volunteerId);
                return success ? "Hủy đơn thành công!" : "Không thể hủy đơn. Vui lòng thử lại!";
            case "approved":
                return "Không thể hủy vì đơn đã được tổ chức duyệt!";
            case "rejected":
                return "Đơn của bạn đã bị từ chối, không thể hủy!";
            default:
                return "Trạng thái không hợp lệ!";
        }
    }

}
