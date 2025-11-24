package service;

import dao.PaymentDonationDAO;
import dao.ViewEventsDAO;
import model.Event;

import java.sql.SQLException;

/**
 * Service layer for Guest Donation functionality
 * Handles business logic for guest donations
 */
public class GuestDonationService {

    private ViewEventsDAO viewEventsDAO;

    public GuestDonationService() {
        this.viewEventsDAO = new ViewEventsDAO();
    }

    /**
     * Get event information by ID
     */
    public Event getEventById(int eventId) {
        return viewEventsDAO.getEventById(eventId);
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
     * Validate guest information for non-anonymous donation
     * @param isAnonymous Whether donation is anonymous
     * @param guestName Guest name
     * @param guestPhone Guest phone
     * @param guestEmail Guest email
     * @return Error message if invalid, null if valid
     */
    public String validateGuestInfo(boolean isAnonymous, String guestName, String guestPhone, String guestEmail) {
        if (!isAnonymous) {
            // At least one field must be provided
            if ((guestName == null || guestName.trim().isEmpty()) &&
                (guestPhone == null || guestPhone.trim().isEmpty()) &&
                (guestEmail == null || guestEmail.trim().isEmpty())) {
                return "Vui lòng cung cấp ít nhất tên, số điện thoại hoặc email";
            }

            // Validate phone format if provided
            if (guestPhone != null && !guestPhone.trim().isEmpty()) {
                if (!guestPhone.matches("^0\\d{9,10}$")) {
                    return "Số điện thoại phải có 10-11 chữ số và bắt đầu bằng 0";
                }
            }

            // Validate email format if provided
            if (guestEmail != null && !guestEmail.trim().isEmpty()) {
                if (!guestEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    return "Định dạng email không hợp lệ";
                }
            }
        }
        return null;
    }

    /**
     * Create or get donor record
     * @param fullName Guest full name
     * @param phone Guest phone
     * @param email Guest email
     * @param isAnonymous Whether donation is anonymous
     * @return Donor ID
     * @throws SQLException If database error occurs
     */
    public int createOrGetDonor(String fullName, String phone, String email, boolean isAnonymous) throws SQLException {
        PaymentDonationDAO dao = new PaymentDonationDAO();
        try {
            return dao.createOrGetDonor("guest", null, fullName, phone, email, isAnonymous);
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
     * Close resources
     */
    public void close() {
        if (viewEventsDAO != null) {
            viewEventsDAO.close();
        }
    }
}
