/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.OrganizationApplyDAO;
import java.util.List;
import model.EventVolunteer;

/**
 *
 * @author Admin
 */
public class OrganizationApplyService {

    private OrganizationApplyDAO organizationApplyDAO;

    public OrganizationApplyService() {
        organizationApplyDAO = new OrganizationApplyDAO();
    }

    public List<EventVolunteer> getVolunteersByEvent(int organizationId, int eventId) {
        return organizationApplyDAO.getVolunteersByEvent(organizationId, eventId);
    }

    public void updateVolunteerStatus(int volunteerId, String status) {
        organizationApplyDAO.updateVolunteerStatus(volunteerId, status);
    }
    
    // Đếm số volunteer đã được approved cho event (dùng để check slot)
    public int countApprovedVolunteers(int eventId) {
        return organizationApplyDAO.countApprovedVolunteers(eventId);
    }
    
    public List<EventVolunteer> getFilterVolunteersByEvent(int organizationId, int eventId, String statusFilter) {
        // Tự động reject các pending applications trước khi load danh sách
        organizationApplyDAO.autoRejectPendingApplications(eventId);
        
        return organizationApplyDAO.getFilterVolunteersByEvent(organizationId, eventId, statusFilter);
    }
    
    // Tự động reject pending applications của 1 event cụ thể
    public int autoRejectPendingApplications(int eventId) {
        return organizationApplyDAO.autoRejectPendingApplications(eventId);
    }
    
    // Tự động reject pending applications cho tất cả events
    public int autoRejectAllPendingApplications() {
        return organizationApplyDAO.autoRejectAllPendingApplications();
    }
    
}
