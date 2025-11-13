package service;

import dao.EventVolunteerDAO;
import java.util.List;
import model.EventVolunteer;

public class EventVolunteerService {

    private EventVolunteerDAO dao;

    public EventVolunteerService() {
        dao = new EventVolunteerDAO();
    }

    public List<EventVolunteer> getEventRegistrations(int volunteerId) {
        return dao.getEventRegistrationsByVolunteerId(volunteerId);
    }
    
    public List<EventVolunteer> getEventRegistrationsFiltered(int volunteerId, String statusFilter, String sortOrder, int page, int pageSize) {
        return dao.getEventRegistrationsFiltered(volunteerId, statusFilter, sortOrder, page, pageSize);
    }
    
    public int countEventRegistrations(int volunteerId, String statusFilter) {
        return dao.countEventRegistrations(volunteerId, statusFilter);
    }
}
