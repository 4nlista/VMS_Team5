/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.DonationDAO;
import dao.ViewEventsDAO;
import model.Donation;
import model.Event;

/**
 *
 * @author Admin
 */
public class VolunteerDetailPaymentService {
    
    private DonationDAO donationDAO;
    private ViewEventsDAO viewEventsDAO;
    
    public VolunteerDetailPaymentService() {
        this.donationDAO = new DonationDAO();
        this.viewEventsDAO = new ViewEventsDAO();
    }
    
    // Lấy chi tiết donation theo ID
    public Donation getDonationDetailById(int donationId) {
        return donationDAO.getDonationDetailById(donationId);
    }
    
    // Lấy chi tiết event theo ID
    public Event getEventById(int eventId) {
        return viewEventsDAO.getEventById(eventId);
    }
}
