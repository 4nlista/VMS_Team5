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

    public List<Event> getActiveEvents() {
        return viewEventsDAO.getActiveEvents();
    }

    public List<Event> getActiveEventsPaged(int offset, int limit) {
        return viewEventsDAO.getActiveEventsPaged(offset, limit);
    }
    
    public int getTotalActiveEvents() {
        return viewEventsDAO.getTotalActiveEvents();
    }
}
