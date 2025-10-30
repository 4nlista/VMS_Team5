/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.*;
import model.Donation;
import utils.DBContext; // file DB connection của bạn

public class DonationDAO {

    public boolean insertDonation(Donation donation) {
        String sql = "INSERT INTO Donations (event_id, volunteer_id, amount, status, payment_method, qr_code, note) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, donation.getEventId());
            ps.setInt(2, donation.getVolunteerId());
            ps.setDouble(3, donation.getAmount());
            ps.setString(4, donation.getStatus());
            ps.setString(5, donation.getPaymentMethod());
            ps.setString(6, donation.getQrCode());
            ps.setString(7, donation.getNote());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Donation> getDonationsByVolunteer(int volunteerId) {
        List<Donation> list = new ArrayList<>();
        String sql = "SELECT * FROM Donations WHERE volunteer_id = ? ORDER BY donate_date DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, volunteerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Donation d = new Donation();
                d.setId(rs.getInt("id"));
                d.setEventId(rs.getInt("event_id"));
                d.setVolunteerId(rs.getInt("volunteer_id"));
                d.setAmount(rs.getDouble("amount"));
                d.setDonateDate(rs.getTimestamp("donate_date"));
                d.setStatus(rs.getString("status"));
                d.setPaymentMethod(rs.getString("payment_method"));
                d.setQrCode(rs.getString("qr_code"));
                d.setNote(rs.getString("note"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
