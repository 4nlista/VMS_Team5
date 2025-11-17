package dao;

import java.sql.*;
import java.math.BigDecimal;
import utils.DBContext;

/**
 * DAO for Payment Donation operations (VNPay and other payment gateways)
 */
public class PaymentDonationDAO {

    private Connection connection;

    public PaymentDonationDAO() throws SQLException {
        this.connection = new DBContext().getConnection();
    }

    /**
     * Create or get donor record
     * @return donor_id
     */
    public int createOrGetDonor(String donorType, Integer accountId, String fullName,
                                 String phone, String email, boolean isAnonymous) throws SQLException {
        // For volunteers, check if donor already exists
        if ("volunteer".equals(donorType) && accountId != null) {
            String checkSql = "SELECT id FROM Donors WHERE donor_type = 'volunteer' AND account_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(checkSql)) {
                ps.setInt(1, accountId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        
        // Create new donor
        String sql = "INSERT INTO Donors (donor_type, account_id, full_name, phone, email, is_anonymous) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, donorType);
            if (accountId != null) {
                ps.setInt(2, accountId);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, fullName);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setBoolean(6, isAnonymous);
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Failed to create donor record");
    }

    /**
     * Create payment donation record (VNPay or other gateway)
     */
    public void createPaymentDonation(int donorId, int eventId, String paymentTxnRef, 
                                       long amount, String orderInfo, String paymentGateway) throws SQLException {
        String sql = "INSERT INTO Payment_Donations (donor_id, event_id, payment_txn_ref, payment_amount, " +
                     "order_info, payment_gateway, payment_status) VALUES (?, ?, ?, ?, ?, ?, 'pending')";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, donorId);
            ps.setInt(2, eventId);
            ps.setString(3, paymentTxnRef);
            ps.setLong(4, amount);
            ps.setString(5, orderInfo);
            ps.setString(6, paymentGateway);
            ps.executeUpdate();
        }
    }

    /**
     * Update payment donation record with transaction details
     */
    public void updatePaymentDonation(String paymentTxnRef, String bankCode, String cardType,
                                       String payDate, String responseCode, String transactionNo,
                                       String transactionStatus, String secureHash, 
                                       String paymentStatus) throws SQLException {
        String sql = "UPDATE Payment_Donations SET bank_code = ?, card_type = ?, " +
                     "pay_date = ?, response_code = ?, transaction_no = ?, " +
                     "transaction_status = ?, secure_hash = ?, payment_status = ?, " +
                     "updated_at = GETDATE() WHERE payment_txn_ref = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bankCode);
            ps.setString(2, cardType);
            ps.setString(3, payDate);
            ps.setString(4, responseCode);
            ps.setString(5, transactionNo);
            ps.setString(6, transactionStatus);
            ps.setString(7, secureHash);
            ps.setString(8, paymentStatus);
            ps.setString(9, paymentTxnRef);
            ps.executeUpdate();
        }
    }

    /**
     * Create donation record after successful payment
     */
    public int createDonation(int eventId, Integer volunteerId, int donorId, BigDecimal amount, String status,
                               String paymentMethod, String paymentTxnRef, String note) throws SQLException {
        
        // DEBUG: Log before creating donation
        System.out.println("=== DEBUG: createDonation START ===");
        System.out.println("Event ID: " + eventId);
        System.out.println("Volunteer ID: " + volunteerId);
        System.out.println("Donor ID: " + donorId);
        System.out.println("Amount: " + amount);
        System.out.println("Status: " + status);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Txn Ref: " + paymentTxnRef);
        
        // Get current total_donation before insert
        BigDecimal totalBefore = getEventTotalDonation(eventId);
        System.out.println("Total donation BEFORE insert: " + totalBefore);
        
        String sql = "INSERT INTO Donations (event_id, volunteer_id, donor_id, amount, status, " +
                     "payment_method, payment_txn_ref, note) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, eventId);
            if (volunteerId != null) {
                ps.setInt(2, volunteerId);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, donorId);
            ps.setBigDecimal(4, amount);
            ps.setString(5, status);
            ps.setString(6, paymentMethod);
            ps.setString(7, paymentTxnRef);
            ps.setString(8, note);

            System.out.println("Executing INSERT into Donations...");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int donationId = rs.getInt(1);
                System.out.println("Donation created with ID: " + donationId);

                // Update Payment_Donations with donation_id
                updatePaymentDonationId(paymentTxnRef, donationId);

                // Note: total_donation is automatically updated by trigger trg_UpdateDonationTotals
                // when a donation is inserted/updated/deleted, so no manual update is needed here
                
                // Get total_donation after trigger execution (wait a bit for trigger)
                try {
                    Thread.sleep(100); // Small delay to ensure trigger completes
                } catch (InterruptedException e) {
                    // Ignore
                }
                BigDecimal totalAfter = getEventTotalDonation(eventId);
                System.out.println("Total donation AFTER insert (trigger): " + totalAfter);
                System.out.println("Expected total: " + totalBefore.add(amount));
                System.out.println("Difference: " + totalAfter.subtract(totalBefore));
                
                // Check all donations for this event
                checkAllDonationsForEvent(eventId);
                
                System.out.println("=== DEBUG: createDonation END ===");

                return donationId;
            }
        }
        throw new SQLException("Failed to create donation record");
    }
    
    /**
     * Get current total_donation for an event
     */
    private BigDecimal getEventTotalDonation(int eventId) throws SQLException {
        String sql = "SELECT total_donation FROM Events WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("total_donation");
            }
        }
        return BigDecimal.ZERO;
    }
    
    /**
     * Check all donations for an event (for debugging)
     */
    private void checkAllDonationsForEvent(int eventId) throws SQLException {
        String sql = "SELECT id, amount, status, payment_txn_ref, donate_date " +
                     "FROM Donations WHERE event_id = ? ORDER BY donate_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            System.out.println("--- All donations for event " + eventId + " ---");
            BigDecimal sumSuccess = BigDecimal.ZERO;
            int count = 0;
            while (rs.next()) {
                count++;
                BigDecimal amount = rs.getBigDecimal("amount");
                String status = rs.getString("status");
                String txnRef = rs.getString("payment_txn_ref");
                System.out.println("Donation #" + count + ": ID=" + rs.getInt("id") + 
                                 ", Amount=" + amount + ", Status=" + status + 
                                 ", TxnRef=" + txnRef);
                if ("success".equals(status)) {
                    sumSuccess = sumSuccess.add(amount);
                }
            }
            System.out.println("Sum of all SUCCESS donations: " + sumSuccess);
            System.out.println("--- End of donations list ---");
        }
    }

    /**
     * Link payment to donation
     */
    private void updatePaymentDonationId(String paymentTxnRef, int donationId) throws SQLException {
        String sql = "UPDATE Payment_Donations SET donation_id = ? WHERE payment_txn_ref = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, donationId);
            ps.setString(2, paymentTxnRef);
            ps.executeUpdate();
        }
    }

    /**
     * Get payment donation details by transaction reference
     */
    public PaymentDonationDetail getPaymentDonationByTxnRef(String paymentTxnRef) throws SQLException {
        String sql = "SELECT pd.*, d.id as donor_id, d.donor_type, d.account_id, d.full_name, " +
                     "d.phone, d.email, d.is_anonymous, e.title as event_title " +
                     "FROM Payment_Donations pd " +
                     "INNER JOIN Donors d ON pd.donor_id = d.id " +
                     "INNER JOIN Events e ON pd.event_id = e.id " +
                     "WHERE pd.payment_txn_ref = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, paymentTxnRef);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PaymentDonationDetail detail = new PaymentDonationDetail();
                detail.paymentId = rs.getInt("payment_id");
                detail.donationId = rs.getInt("donation_id");
                detail.donorId = rs.getInt("donor_id");
                detail.eventId = rs.getInt("event_id");
                detail.eventTitle = rs.getString("event_title");
                detail.paymentTxnRef = rs.getString("payment_txn_ref");
                detail.paymentAmount = rs.getLong("payment_amount");
                detail.paymentStatus = rs.getString("payment_status");
                detail.paymentGateway = rs.getString("payment_gateway");
                detail.donorType = rs.getString("donor_type");
                detail.accountId = rs.getInt("account_id");
                detail.donorFullName = rs.getString("full_name");
                detail.donorPhone = rs.getString("phone");
                detail.donorEmail = rs.getString("email");
                detail.isAnonymous = rs.getBoolean("is_anonymous");
                return detail;
            }
        }
        return null;
    }

    /**
     * Get donor email for notification
     */
    public String getDonorEmail(int donorId) throws SQLException {
        String sql = "SELECT d.email, d.donor_type, d.account_id, u.email as volunteer_email, d.is_anonymous " +
                     "FROM Donors d " +
                     "LEFT JOIN Users u ON d.account_id = u.account_id " +
                     "WHERE d.id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, donorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean isAnonymous = rs.getBoolean("is_anonymous");
                if (isAnonymous) {
                    System.out.println("DEBUG: Donor is anonymous, no email will be sent");
                    return null;
                }
                
                String donorType = rs.getString("donor_type");
                String email = null;
                if ("volunteer".equals(donorType)) {
                    email = rs.getString("volunteer_email");
                    System.out.println("DEBUG: Volunteer donor, email from Users table: " + email);
                } else {
                    email = rs.getString("email");
                    System.out.println("DEBUG: Guest donor, email from Donors table: " + email);
                }
                return email;
            } else {
                System.out.println("DEBUG: No donor found with ID: " + donorId);
            }
        }
        return null;
    }

    /**
     * Inner class for payment donation details
     */
    public static class PaymentDonationDetail {
        public int paymentId;
        public int donationId;
        public int donorId;
        public int eventId;
        public String eventTitle;
        public String paymentTxnRef;
        public long paymentAmount;
        public String paymentStatus;
        public String paymentGateway;
        public String donorType;
        public int accountId;
        public String donorFullName;
        public String donorPhone;
        public String donorEmail;
        public boolean isAnonymous;
    }

    /**
     * Close database connection
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

