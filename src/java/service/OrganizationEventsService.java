/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.OrganizationEventsDAO;
import java.util.List;
import model.Event;

/**
 *
 * @author Admin
 */
public class OrganizationEventsService {

    private OrganizationEventsDAO organizationEventsDAO;

    public OrganizationEventsService() {
        organizationEventsDAO = new OrganizationEventsDAO();
    }

    public List<Event> getEventsByOrganization(int organizationId) {
        return organizationEventsDAO.getEventsByOrganization(organizationId);
    }

    public List<Event> getEventsByOrganizationFiltered(int organizationId, String categoryName, String status, String visibility) {
        return organizationEventsDAO.getEventsByOrganizationFiltered(organizationId, categoryName, status, visibility);
    }
}
