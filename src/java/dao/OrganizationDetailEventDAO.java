/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Donation;
import model.Event;
import utils.DBContext;

public class OrganizationDetailEventDAO {

    private Connection conn;

    public OrganizationDetailEventDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Lấy thông tin chi tiết 1 sự kiện theo ID

    public Event getEventById(int eventId) {
        Event event = null;

        // Viết rõ từng cột thay vì e.*
        String sql = "SELECT e.id, e.title, e.images, e.description, "
                + "e.start_date, e.end_date, e.location, e.needed_volunteers, "
                + "e.status, e.visibility, e.organization_id, e.category_id, "
                + "e.total_donation, "
                + "c.name as category_name, "
                + "a.full_name as organization_name "
                + "FROM Events e "
                + "LEFT JOIN Categories c ON e.category_id = c.category_id "
                + "LEFT JOIN Users a ON e.organization_id = a.id "
                + "WHERE e.id = ?";

        System.out.println("===== DAO DEBUG =====");
        System.out.println("Query event với id = " + eventId);

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Tìm thấy event: " + rs.getString("title"));
                event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setImages(rs.getString("images"));
                event.setDescription(rs.getString("description"));
                event.setStartDate(rs.getDate("start_date"));
                event.setEndDate(rs.getDate("end_date"));
                event.setLocation(rs.getString("location"));
                event.setNeededVolunteers(rs.getInt("needed_volunteers"));
                event.setStatus(rs.getString("status"));
                event.setVisibility(rs.getString("visibility"));
                event.setOrganizationId(rs.getInt("organization_id"));
                event.setCategoryId(rs.getInt("category_id"));
                event.setTotalDonation(rs.getDouble("total_donation"));
                event.setOrganizationName(rs.getString("organization_name"));
                event.setCategoryName(rs.getString("category_name"));
            } else {
                System.out.println("KHÔNG tìm thấy event với id = " + eventId);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Lỗi query: " + e.getMessage());
            e.printStackTrace();
        }

        return event;
    }

    // Lấy danh sách donations của 1 sự kiện
    public List<Donation> getDonationsByEventId(int eventId) {
        List<Donation> donations = new ArrayList<>();
        String sql = """ 
                     SELECT 
                         d.*,
                         a.username AS volunteer_username,
                         u.full_name AS volunteer_full_name,
                         e.title AS event_title
                     FROM Donations d
                     JOIN Accounts a ON d.volunteer_id = a.id
                     JOIN Users u ON a.id = u.account_id
                     JOIN Events e ON d.event_id = e.id
                     WHERE d.event_id = ?
                     ORDER BY d.donate_date DESC;
                     """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Donation donation = new Donation();
                donation.setId(rs.getInt("id"));
                donation.setEventId(rs.getInt("event_id"));
                donation.setVolunteerId(rs.getInt("volunteer_id"));
                donation.setAmount(rs.getDouble("amount"));
                donation.setDonateDate(rs.getDate("donate_date"));
                donation.setStatus(rs.getString("status"));
                donation.setPaymentMethod(rs.getString("payment_method"));
                donation.setQrCode(rs.getString("qr_code"));
                donation.setNote(rs.getString("note"));
                donation.setVolunteerUsername(rs.getString("volunteer_username"));
                donation.setVolunteerFullName(rs.getString("volunteer_full_name"));
                donation.setEventTitle(rs.getString("event_title"));

                donations.add(donation);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return donations;
    }

    // Cập nhật thông tin sự kiện
    public boolean updateEvent(int eventId, String title, String description, String location,
            java.sql.Date startDate, java.sql.Date endDate, int neededVolunteers,
            String status, String visibility) {
        String sql = "UPDATE Events SET title = ?, description = ?, location = ?, "
                + "start_date = ?, end_date = ?, needed_volunteers = ?, "
                + "status = ?, visibility = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setDate(4, startDate);
            ps.setDate(5, endDate);
            ps.setInt(6, neededVolunteers);
            ps.setString(7, status);
            ps.setString(8, visibility);
            ps.setInt(9, eventId);

            int rowsAffected = ps.executeUpdate();
            ps.close();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Xóa sự kiện
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);

            int rowsAffected = ps.executeUpdate();
            ps.close();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Duyệt đơn donate (cập nhật status = 'success' và cộng vào total_donation)
    public boolean approveDonation(int donationId, int eventId) {
        try {
            conn.setAutoCommit(false);

            // 1. Lấy số tiền của donation
            String getAmountSql = "SELECT amount FROM Donations WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(getAmountSql);
            ps1.setInt(1, donationId);
            ResultSet rs = ps1.executeQuery();

            double amount = 0;
            if (rs.next()) {
                amount = rs.getDouble("amount");
            }
            rs.close();
            ps1.close();

            // 2. Update status donation
            String updateDonationSql = "UPDATE Donations SET status = 'success' WHERE id = ?";
            PreparedStatement ps2 = conn.prepareStatement(updateDonationSql);
            ps2.setInt(1, donationId);
            ps2.executeUpdate();
            ps2.close();

            // 3. Cộng vào total_donation của event
            String updateEventSql = "UPDATE Events SET total_donation = total_donation + ? WHERE id = ?";
            PreparedStatement ps3 = conn.prepareStatement(updateEventSql);
            ps3.setDouble(1, amount);
            ps3.setInt(2, eventId);
            ps3.executeUpdate();
            ps3.close();

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Từ chối đơn donate
    public boolean rejectDonation(int donationId) {
        String sql = "UPDATE Donations SET status = 'cancelled' WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, donationId);

            int rowsAffected = ps.executeUpdate();
            ps.close();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
}
