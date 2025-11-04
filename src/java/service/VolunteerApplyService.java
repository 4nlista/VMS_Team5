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

    public boolean hasApplied(int volunteerId, int eventId) {
        return volunteerApplyDAO.hasApplied(volunteerId, eventId);
    }

    public int countRejected(int eventId, int volunteerId) {
        return volunteerApplyDAO.countRejected(eventId, volunteerId);
    }

    public boolean applyToEvent(int volunteerId, int eventId, int hours, String note) {
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

        // 4. Nếu chưa thì thêm vào DB
        volunteerApplyDAO.applyToEvent(volunteerId, eventId, hours, note);
        return true;
    }

    public List<EventVolunteer> getMyApplications(int volunteerId) {
        return volunteerApplyDAO.getMyApplications(volunteerId);
    }
}
