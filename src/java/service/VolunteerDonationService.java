package service;

import dao.PaymentDonationDAO;
import dao.ViewEventsDAO;
import dao.VolunteerDonationDAO;
import model.Event;

import java.sql.SQLException;

/**
 * Service layer for Volunteer Donation functionality
 * Handles business logic for volunteer donations
 */
public class VolunteerDonationService {

    private VolunteerDonationDAO donationDAO;
    private ViewEventsDAO viewEventsDAO;

    public VolunteerDonationService() {
        this.donationDAO = new VolunteerDonationDAO();
        this.viewEventsDAO = new ViewEventsDAO();
    }

    /**
     * Get event information by ID
     */
    public Event getEventById(int eventId) {
        return viewEventsDAO.getEventById(eventId);
    }

    /**
     * Check if volunteer has already donated to this event
     */
    public boolean hasVolunteerDonated(int volunteerId, int eventId) {
        return viewEventsDAO.hasVolunteerDonated(volunteerId, eventId);
    }

    /**
     * Validate donation amount
     * @param amount Amount to validate
     * @return Error message if invalid, null if valid
     */
    public String validateDonationAmount(long amount) {
        if (amount < 10000) {
            return "Số tiền ủng hộ phải ít nhất 10,000 VND";
        }
        return null;
    }

    /**
     * Create or get donor record for volunteer
     * @param accountId Volunteer account ID
     * @param fullName Volunteer full name
     * @param phone Volunteer phone
     * @param email Volunteer email
     * @return Donor ID
     * @throws SQLException If database error occurs
     */
    public int createOrGetDonor(int accountId, String fullName, String phone, String email) throws SQLException {
        PaymentDonationDAO dao = new PaymentDonationDAO();
        try {
            return dao.createOrGetDonor("volunteer", accountId, fullName, phone, email, false);
        } finally {
            dao.close();
        }
    }

    /**
     * Create payment donation record
     * @param donorId Donor ID
     * @param eventId Event ID
     * @param txnRef Transaction reference
     * @param amount Amount in VND cents
     * @param orderInfo Order information
     * @param paymentMethod Payment method
     * @throws SQLException If database error occurs
     */
    public void createPaymentDonation(int donorId, int eventId, String txnRef, long amount,
                                     String orderInfo, String paymentMethod) throws SQLException {
        PaymentDonationDAO dao = new PaymentDonationDAO();
        try {
            dao.createPaymentDonation(donorId, eventId, txnRef, amount, orderInfo, paymentMethod);
        } finally {
            dao.close();
        }
    }

    /**
     * Create donation record (legacy method for backward compatibility)
     */
    public boolean createDonation(int eventId, int volunteerId, double amount,
            String paymentMethod, String note) {
        return donationDAO.createDonation(eventId, volunteerId, amount,
                paymentMethod, note);
    }

    /**
     * Close resources
     */
    public void close() {
        if (donationDAO != null) {
            donationDAO.close();
        }
        if (viewEventsDAO != null) {
            viewEventsDAO.close();
        }
    }
}
