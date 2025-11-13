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

    // phân trang với các trạng thái
    public List<Event> getActiveEventsPagedWithStatus(int offset, int limit, Integer volunteerId) {
        // 1. Lấy danh sách events
        List<Event> events = viewEventsDAO.getActiveEventsPaged(offset, limit);

        // 2. Nếu volunteer đã login → kiểm tra từng event
        if (volunteerId != null) {
            for (Event event : events) {
                // Kiểm tra đã đăng ký chưa
                boolean hasApplied = viewEventsDAO.hasVolunteerApplied(volunteerId, event.getId());
                event.setHasApplied(hasApplied);

                // Kiểm tra đã donate chưa
                boolean hasDonated = viewEventsDAO.hasVolunteerDonated(volunteerId, event.getId());
                event.setHasDonated(hasDonated);
            }
        }

        return events;
    }
    
    // Lấy danh sách categories
    public List<model.Category> getAllCategories() {
        return viewEventsDAO.getAllCategories();
    }
    
    // Lọc events theo category, date range, sort + phân trang
    public List<Event> getFilteredEventsPaged(Integer categoryId, String startDateStr, String endDateStr, 
                                              String sortOrder, int offset, int limit) {
        return viewEventsDAO.getFilteredEventsPaged(categoryId, startDateStr, endDateStr, sortOrder, offset, limit);
    }
    
    // Đếm tổng events sau khi filter
    public int getTotalFilteredEvents(Integer categoryId, String startDateStr, String endDateStr) {
        return viewEventsDAO.getTotalFilteredEvents(categoryId, startDateStr, endDateStr);
    }
}
