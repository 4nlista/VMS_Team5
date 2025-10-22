/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author locng
 */
import dao.EventDAO;
import dao.EventVolunteerDAO;
import model.Event;
import model.EventVolunteer;
import java.util.List;
import model.EventVolunteer;

public class EventService {
    private EventDAO eventDAO = new EventDAO();
    private EventVolunteerDAO evDAO = new EventVolunteerDAO();

    // Lấy danh sách sự kiện
    public List<Event> getEventsByOrganization(int organizationId) {
        return eventDAO.getEventsByOrganization(organizationId);
    }
    public List<Event> getAllEvent() {
        return eventDAO.getAllEvents();
    }
    // Thêm/chỉnh sửa sự kiện
    public boolean saveEvent(Event event) {
        if (event.getId() == 0) {
            return eventDAO.createEvent(event);
        } else {
            return eventDAO.updateEvent(event);
        }
    }

    // Lấy sự kiện chi tiết
    public Event getEventById(int eventId) {
        return eventDAO.getEventById(eventId);
    }

    // Lấy danh sách volunteer apply
    public List<EventVolunteer> getVolunteersByEvent(int eventId) {
        return evDAO.getVolunteersByEvent(eventId);
    }

    // Duyệt/từ chối volunteer
    public boolean updateVolunteerStatus(int evId, String status) {
        return evDAO.updateVolunteerStatus(evId, status);
    }

    public boolean createEvent(Event event) {
        return eventDAO.createEvent(event);
    }
    public boolean updateEvent(Event event) {
    event.setOrganizationId(2);
    return eventDAO.updateEvent (event);
}

}
