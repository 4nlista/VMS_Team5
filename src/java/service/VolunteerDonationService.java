/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ViewEventsDAO;
import dao.VolunteerDonationDAO;
import model.Event;

/**
 *
 * @author Admin
 */
public class VolunteerDonationService {

    private VolunteerDonationDAO donationDAO;
    private ViewEventsDAO viewEventsDAO;

    public VolunteerDonationService() {
        this.donationDAO = new VolunteerDonationDAO();
        this.viewEventsDAO = new ViewEventsDAO();
    }

    // Lấy thông tin event theo ID
    public Event getEventById(int eventId) {
        return viewEventsDAO.getEventById(eventId);
    }

    // Kiểm tra volunteer đã donate cho event này chưa
    public boolean hasVolunteerDonated(int volunteerId, int eventId) {
        return viewEventsDAO.hasVolunteerDonated(volunteerId, eventId);
    }

    // Sinh mã QR code
    public String generateQRCode(int volunteerId, double amount) {
        return donationDAO.generateQRCode(volunteerId, amount);
    }

    // Tạo donation mới
    public boolean createDonation(int eventId, int volunteerId, double amount,
            String paymentMethod, String qrCode, String note) {
        return donationDAO.createDonation(eventId, volunteerId, amount,
                paymentMethod, qrCode, note);
    }

    // Đóng kết nối
    public void close() {
        if (donationDAO != null) {
            donationDAO.close();
        }
        if (viewEventsDAO != null) {
            viewEventsDAO.close();
        }
    }
}
