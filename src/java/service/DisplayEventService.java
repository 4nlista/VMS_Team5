/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ViewEventsDAO;
import java.util.List;
import model.Event;

/**
 *
 * @author Admin
 */
// hiển thị danh sách các sự kiện
public class DisplayEventService {

    private ViewEventsDAO viewEventsDAO;

    public DisplayEventService() {
        viewEventsDAO = new ViewEventsDAO();
    }

    // trả về danh sách các sự kiện public + active
    public List<Event> getActiveEvents() {
        return viewEventsDAO.getActiveEvents();
    }

    public Event getEventById(int eventId) {
        return viewEventsDAO.getEventById(eventId);
    }

    // trả về danh sách 3 sự kiện mới nhất
    public List<Event> getLatestActivePublicEvents() {
        return viewEventsDAO.getLatestActivePublicEvents();
    }

    // trả về danh sách phân trang
    public List<Event> getActiveEventsPaged(int offset, int limit) {
        return viewEventsDAO.getActiveEventsPaged(offset, limit);
    }

    // tính tổng số trang
    public int getTotalActiveEvents() {
        return viewEventsDAO.getTotalActiveEvents();
    }
}
