package service;

import dao.AttendanceDAO;
import java.util.List;
import model.Attendance;

/**
 *
 * @author Admin
 */
public class AttendanceService {

    private AttendanceDAO attendanceDAO;

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    // Lấy danh sách volunteer để điểm danh
    public List<Attendance> getVolunteersForAttendance(int eventId, String statusFilter) {
        // Tự động mark absent cho pending khi sự kiện kết thúc (Organization)
        attendanceDAO.autoMarkAbsentForEndedEvents(eventId, null);
        
        return attendanceDAO.getVolunteersForAttendance(eventId, statusFilter);
    }

    // Cập nhật trạng thái điểm danh với validate
    public String updateAttendanceStatus(int eventId, int volunteerId, String status) {
        // Validate status
        if (!status.equals("pending") && !status.equals("present") && !status.equals("absent")) {
            return "Trạng thái không hợp lệ!";
        }
        
        // Validate 1: Sự kiện phải đã bắt đầu (start_date <= ngày hiện tại)
        if (!attendanceDAO.isEventStarted(eventId)) {
            return "Chưa thể cập nhật điểm danh! Sự kiện chưa bắt đầu.";
        }
        
        // Validate 2: Sự kiện không được kết thúc quá 24h
        if (attendanceDAO.isEventEndedOver24Hours(eventId)) {
            return "Không thể cập nhật điểm danh! Sự kiện đã kết thúc hơn 24 giờ.";
        }
        
        // Thực hiện cập nhật
        boolean result = attendanceDAO.updateAttendanceStatus(eventId, volunteerId, status);
        
        if (result) {
            return "success"; // Dùng để check trong servlet
        } else {
            return "Cập nhật điểm danh thất bại!";
        }
    }

    public List<Attendance> getAttendanceHistory(int volunteerId) {
        return attendanceDAO.getAttendanceHistoryByVolunteerId(volunteerId);
    }
    
    // Lấy danh sách attendance với phân trang và filter
    public List<Attendance> getAttendanceHistoryPaginated(int volunteerId, int page, int pageSize, String statusFilter) {
        // Tự động mark absent cho tất cả sự kiện đã kết thúc của volunteer này (Volunteer)
        attendanceDAO.autoMarkAbsentForEndedEvents(null, volunteerId);
        
        return attendanceDAO.getAttendanceHistoryPaginated(volunteerId, page, pageSize, statusFilter);
    }
    
    // Đếm tổng số attendance
    public int getTotalAttendance(int volunteerId, String statusFilter) {
        return attendanceDAO.getTotalAttendanceByVolunteer(volunteerId, statusFilter);
    }
    
    // Validate: Kiểm tra có thể cập nhật điểm danh không
    public boolean canUpdateAttendance(int eventId) {
        return attendanceDAO.isEventStarted(eventId) 
                && !attendanceDAO.isEventEndedOver24Hours(eventId);
    }
}
