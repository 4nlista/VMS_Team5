/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.VolunteerApplyDAO;
import java.util.List;
import model.Event;
import model.EventVolunteer;

/**
 *
 * @author Admin
 */
public class VolunteerApplyService {

    private VolunteerApplyDAO volunteerApplyDAO;
    private DisplayEventService displayEventService;

    public VolunteerApplyService() {
        volunteerApplyDAO = new VolunteerApplyDAO();
        displayEventService = new DisplayEventService();
    }

    // kiêm tra xem đã apply chưa
    public boolean hasApplied(int volunteerId, int eventId) {
        return volunteerApplyDAO.hasApplied(volunteerId, eventId);
    }

    // đếm số lần bị từ chối
    public int countRejected(int eventId, int volunteerId) {
        return volunteerApplyDAO.countRejected(eventId, volunteerId);
    }

    // đếm số slot đã đc approved
    public int countApprovedVolunteers(int eventId) {
        return volunteerApplyDAO.countApprovedVolunteers(eventId);
    }

    // kiểm tra sự kiện đã full slot chưa
    public boolean isEventFull(int eventId) {
        Event event = displayEventService.getEventById(eventId);
        if (event == null) {
            return false;
        }

        int approvedCount = volunteerApplyDAO.countApprovedVolunteers(eventId);
        return approvedCount >= event.getNeededVolunteers();
    }

    // Kiểm tra xem volunteer có đang tham gia sự kiện nào trùng thời gian không
    public boolean hasConflictingEvent(int volunteerId, int eventId) {
        return volunteerApplyDAO.hasConflictingEvent(volunteerId, eventId);
    }

    public boolean applyToEvent(int volunteerId, int eventId, String note) {
        // 1️. Kiểm tra sự kiện có tồn tại
        Event event = displayEventService.getEventById(eventId);
        if (event == null) {
            throw new IllegalArgumentException("Sự kiện không tồn tại!");
        }

        // 2️. Kiểm tra volunteer đã apply chưa
        boolean hasApplied = volunteerApplyDAO.hasApplied(volunteerId, eventId);
        if (hasApplied) {
            // Có thể log hoặc trả về false để Servlet hiển thị thông báo
            System.out.println("Volunteer " + volunteerId + " đã apply event " + eventId);
            return false;
        }
        // 3. Kiểm tra số lần bị từ chối
        int rejectedCount = volunteerApplyDAO.countRejected(eventId, volunteerId);
        if (rejectedCount >= 3) {
            System.out.println("Volunteer " + volunteerId + " đã bị từ chối 3 lần cho event " + eventId);
            return false;
        }
        // 4. Kiểm tra trùng thời gian với sự kiện khác
        if (hasConflictingEvent(volunteerId, eventId)) {
            throw new IllegalArgumentException("Bạn đã đăng ký sự kiện khác trùng thời gian đó bạn nhỏ!");
        }
        
        // 5. Kiểm tra còn đủ 24h trước sự kiện không
        long diff = event.getStartDate().getTime() - new java.util.Date().getTime();
        long hoursRemaining = java.util.concurrent.TimeUnit.MILLISECONDS.toHours(diff);
        
        if (hoursRemaining < 24) {
            throw new IllegalArgumentException("Bạn cần gửi đơn trước 24h diễn ra sự kiện!");
        }

        // 6. Nếu pass hết → thêm vào DB
        volunteerApplyDAO.applyToEvent(volunteerId, eventId, note);
        return true;
    }

    public List<EventVolunteer> getMyApplications(int volunteerId) {
        return volunteerApplyDAO.getMyApplications(volunteerId);
    }
}
