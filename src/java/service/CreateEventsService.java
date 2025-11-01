/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CreateEventsDAO;
import model.Event;

/**
 *
 * @author Admin
 */
public class CreateEventsService {

    private CreateEventsDAO createEventsDAO;

    public CreateEventsService() {
        createEventsDAO = new CreateEventsDAO();
    }

    public boolean createEvent(Event e) {
        if (e == null) {
            return false;
        }

        // Kiểm tra các trường bắt buộc
        if (e.getTitle() == null || e.getTitle().isEmpty()) {
            return false;
        }
        if (e.getStartDate() == null || e.getEndDate() == null) {
            return false;
        }
        if (e.getStartDate().after(e.getEndDate())) {
            return false;
        }
        if (e.getNeededVolunteers() <= 0) {
            return false;
        }
        if (e.getCategoryId() <= 0) {
            return false;
        }

        // Set mặc định
        if (e.getStatus() == null) {
            e.setStatus("pending");
        }
        if (e.getVisibility() == null) {
            e.setVisibility("public");
        }

        return createEventsDAO.createEvent(e);
    }

}
