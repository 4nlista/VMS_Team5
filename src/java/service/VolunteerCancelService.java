package service;

import dao.EventVolunteerDAO;

public class VolunteerCancelService {

    private final EventVolunteerDAO eventVolunteerDAO = new EventVolunteerDAO();

    /**
     * Hủy đơn đăng ký sự kiện
     * Chỉ cho phép hủy nếu status = Pending
     * Xóa bản ghi và ghi log hành động
     */
    public String cancelApplication(int eventId, int volunteerId) {
        String status = eventVolunteerDAO.getApplicationStatus(eventId, volunteerId);

        if (status == null) {
            return "Không tìm thấy đơn đăng ký!";
        }

        switch (status.toLowerCase()) {
            case "pending":
                boolean success = eventVolunteerDAO.deleteApplicationWithLog(eventId, volunteerId);
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
