/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Donation;
import utils.DBContext;

/**
 *
 * @author ADMIN
 */
public class DonationDAO {

    private Connection conn;

    public DonationDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // truy vấn tổng tiền donation trong database
    public double getTotalDonationAmount() {
        double total = 0;
        String sql = "select SUM(amount) as total_success from Donations where status = 'success'";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    
     public List<Donation> getDonationHistoryByVolunteerId(int volunteerId) {
        List<Donation> list = new ArrayList<>();

        String sql = """
            SELECT 
                d.id,
                d.event_id,
                d.volunteer_id,
                d.amount,
                d.donate_date,
                d.status,
                d.payment_method,
                d.qr_code,
                d.note,
                a.username AS volunteerUsername,
                u.full_name AS volunteerFullName,
                e.title AS eventTitle
            FROM Donations d
            JOIN Accounts a ON d.volunteer_id = a.id
            JOIN Users u ON a.id = u.account_id
            JOIN Events e ON d.event_id = e.id
            WHERE d.volunteer_id = ?
            ORDER BY d.donate_date DESC
        """;

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, volunteerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Donation donation = new Donation(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("volunteer_id"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("donate_date") != null ? new java.util.Date(rs.getTimestamp("donate_date").getTime()) : null,
                        rs.getString("status"),
                        rs.getString("payment_method"),
                        rs.getString("qr_code"),
                        rs.getString("note"),
                        rs.getString("volunteerUsername"),
                        rs.getString("volunteerFullName"),
                        rs.getString("eventTitle"),
                        0, // totalAmountDonated, có thể tính thêm nếu muốn
                        0  // numberOfEventsDonated, có thể tính thêm nếu muốn
                );
                list.add(donation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
