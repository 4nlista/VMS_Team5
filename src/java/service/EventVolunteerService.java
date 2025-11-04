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
}
