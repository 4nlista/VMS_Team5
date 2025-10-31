/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.VolunteerApplyDAO;
import java.util.List;
import model.EventVolunteer;

/**
 *
 * @author Admin
 */
public class VolunteerApplyService {

    private VolunteerApplyDAO volunteerApplyDAO;

    public VolunteerApplyService() {
        volunteerApplyDAO = new VolunteerApplyDAO();
    }

    public boolean hasApplied(int volunteerId, int eventId) {
        return volunteerApplyDAO.hasApplied(volunteerId, eventId);
    }

    public void applyToEvent(int volunteerId, int eventId, int hours, String note) {
        volunteerApplyDAO.applyToEvent(volunteerId, eventId, hours, note);
    }

    public List<EventVolunteer> getMyApplications(int volunteerId) {
        return volunteerApplyDAO.getMyApplications(volunteerId);
    }
}
