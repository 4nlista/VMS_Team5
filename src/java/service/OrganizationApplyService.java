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
    
    public List<EventVolunteer> getFilterVolunteersByEvent(int organizationId, int eventId, String statusFilter) {
        return organizationApplyDAO.getFilterVolunteersByEvent(organizationId, eventId, statusFilter);
    }
    
}
