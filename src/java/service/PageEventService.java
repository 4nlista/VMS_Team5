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
// dùng để phân trang sự kiện ở giao diện Guest hay Volunteer
public class PageEventService {

    private int RECORDS_PER_PAGE = 3; // số event mỗi trang
    private ViewEventsDAO viewEventDAO = new ViewEventsDAO();

    // Lấy danh sách event theo trang
    public List<Event> getEventsByPage(int page) {
        int offset = (page - 1) * RECORDS_PER_PAGE; // vị trí bắt đầu
        return viewEventDAO.getActiveEventsPaged(offset, RECORDS_PER_PAGE);
    }

    // Tính tổng số trang
    public int getTotalPages() {
        int totalRecords = viewEventDAO.getTotalActiveEvents();
        return (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
    }
}
