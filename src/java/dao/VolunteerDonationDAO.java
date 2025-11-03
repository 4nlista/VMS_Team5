/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class VolunteerDonationDAO {

    private Connection conn;

    public VolunteerDonationDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tạo donation mới
    public boolean createDonation(int eventId, int volunteerId, double amount,
            String paymentMethod, String qrCode, String note) {
        String sql = "INSERT INTO Donations (event_id, volunteer_id, amount, donate_date, "
                + "status, payment_method, qr_code, note) "
                + "VALUES (?, ?, ?, GETDATE(), 'pending', ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ps.setInt(2, volunteerId);
            ps.setDouble(3, amount);
            ps.setString(4, paymentMethod);
            ps.setString(5, qrCode);
            ps.setString(6, note);

            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sinh mã QR code theo format: QR-250901-V5-500K
    public String generateQRCode(int volunteerId, double amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dateStr = sdf.format(new java.util.Date());

        String amountStr = String.format("%.0fK", amount / 1000);

        return "QR-" + dateStr + "-V" + volunteerId + "-" + amountStr;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("========== hàm test ==========");

        VolunteerDonationDAO dao = new VolunteerDonationDAO();

        // Test data
        int eventId = 1;
        int volunteerId = 5;
        double amount = -100000; // Số âm để test validate
        String note = "Test donate";

        if (amount <= 0) {
            System.out.println("Số tiền phải > 0 - amount = " + amount + ")");
        } else {
            System.out.println("số tiền hợp lệ - amount = " + amount + ")");
        }

        String qrCode = dao.generateQRCode(volunteerId, Math.abs(amount));
        System.out.println("Mã QR: " + qrCode);
        System.out.println("tạo mã QR thành công!");

        System.out.println("\n3. TEST TẠO DONATION (với số tiền dương):");
        double validAmount = 500000; // Số dương
        String validQR = dao.generateQRCode(volunteerId, validAmount);
        boolean success = dao.createDonation(eventId, volunteerId, validAmount,
                "QR Code", validQR, note);

        if (success) {
            System.out.println(" pass: Tạo donation thành công!");
            System.out.println("   - Event ID: " + eventId);
            System.out.println("   - Volunteer ID: " + volunteerId);
            System.out.println("   - Amount: " + validAmount);
            System.out.println("   - QR Code: " + validQR);
            System.out.println("   - Status: pending");
        } else {
            System.out.println(" fail: Tạo donation thất bại!");
        }

        dao.close();
    }
}
