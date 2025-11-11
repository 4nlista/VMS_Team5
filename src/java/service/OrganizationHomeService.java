/*
 * A friendly reminder to drink enough water
 */

package service;

import dao.OrganizationHomeDAO;
import java.util.List;
import model.Event;
import model.ProfileVolunteer;

/**
 *
 * @author Mirinae
 */
public class OrganizationHomeService {
    private OrganizationHomeDAO orgHomeDAO;
    
    public OrganizationHomeService() {
        this.orgHomeDAO = new OrganizationHomeDAO();
    }
    
    public int getTotalEvents(int organizationId) {
        return orgHomeDAO.getTotalEventsByOrganization(organizationId);
    }
    
    
    public int getTotalVolunteers(int organizationId) {
        return orgHomeDAO.getTotalVolunteersByOrganization(organizationId);
    }
    
    public double getTotalDonations(int organizationId) {
        return orgHomeDAO.getTotalDonationsByOrganization(organizationId);
    }
    
    public List<Event> getTop3EventsByDonation(int organizationId) {
        return orgHomeDAO.getTop3EventsByDonation(organizationId);
    }
    
    public List<ProfileVolunteer> getTop3DonorVolunteers(int organizationId) {
        return orgHomeDAO.getTop3DonorVolunteers(organizationId);
    }
    
    public List<Event> getUpcomingEvents(int organizationId) {
        return orgHomeDAO.getUpcomingEvents(organizationId);
    }
    
    public List<Double> getMonthlyDonations(int organizationId) {
        return orgHomeDAO.getMonthlyDonations(organizationId);
    }
}
