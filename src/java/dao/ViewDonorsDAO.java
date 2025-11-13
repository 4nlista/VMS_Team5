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
import model.Event;
import utils.DBContext;

/**
 *
 * @author ADDMIN
 */
public class ViewDonorsDAO {

    private Connection conn;

    public ViewDonorsDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // lấy danh sách top 3 người donate nhiều nhất
    public List<Donation> getTop3UserDonation() {
        List<Donation> list = new ArrayList<>();

        String sql = """
                WITH TotalDonations AS (
                    SELECT 
                        d.volunteer_id,
                        SUM(d.amount) AS total_amount,
                        COUNT(DISTINCT d.event_id) AS events_count
                    FROM Donations d where d.status = 'success'
                    GROUP BY d.volunteer_id
                ),
                LatestDonation AS (
                    SELECT
                        d.id,
                        d.volunteer_id,
                        d.event_id,
                        d.amount AS donate_amount,
                        d.donate_date,
                        d.status AS donation_status,
                        d.payment_method,
                        d.note,
                        d.qr_code,
                        ROW_NUMBER() OVER (PARTITION BY d.volunteer_id ORDER BY d.donate_date DESC) AS rn
                    FROM Donations d
                )
                SELECT TOP 3
                    ld.id AS donation_id,
                    ld.volunteer_id,
                    ld.event_id,
                    u.full_name AS volunteer_name,
                    u.avatar AS volunteer_avatar,
                    a.username AS volunteer_username,
                    td.total_amount,
                    td.events_count,
                    e.title AS event_title,       
                    ld.donate_amount,
                    ld.donate_date,
                    ld.donation_status,
                    ld.payment_method,
                    ld.note,
                    ld.qr_code
                FROM TotalDonations td
                JOIN LatestDonation ld 
                    ON td.volunteer_id = ld.volunteer_id AND ld.rn = 1  
                JOIN Events e ON ld.event_id = e.id
                JOIN Accounts a ON td.volunteer_id = a.id
                JOIN Users u ON a.id = u.account_id
                ORDER BY td.total_amount DESC;
             """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Donation d = new Donation(
                        rs.getInt("donation_id"), // donation_id
                        rs.getInt("event_id"), // eventId
                        rs.getInt("volunteer_id"), // volunteerId
                        rs.getDouble("donate_amount"), // amount
                        rs.getTimestamp("donate_date"),
                        rs.getString("donation_status"),
                        rs.getString("payment_method"),
                        rs.getString("qr_code"), // nếu có
                        rs.getString("note"),
                        rs.getString("volunteer_username"),
                        rs.getString("volunteer_name"),
                        rs.getString("volunteer_avatar"),
                        rs.getString("event_title"),
                        rs.getDouble("total_amount"),
                        rs.getInt("events_count")
                );

                list.add(d);
            }
            System.out.println("==> getTop3UserDonation() trả về: " + list.size() + " bản ghi");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    // lấy danh sách cá nhân volunteer donate để xem lịch sử 
    // Thay thế phương thức getUserDonationsPaged() trong ViewDonorsDAO.java

    public List<Donation> getUserDonationsPaged(int volunteerId, int pageIndex, int pageSize) {
        List<Donation> list = new ArrayList<>();

        String sql = """
    SELECT 
        d.id AS donation_id,
        d.volunteer_id,
        d.event_id,
        d.amount AS donate_amount,
        d.donate_date,
        d.status AS donation_status,
        d.payment_method,
        d.note,
        d.qr_code,
        u.full_name AS volunteer_name,
        u.avatar AS volunteer_avatar,
        a.username AS volunteer_username,
        e.title AS event_title,
        (SELECT SUM(amount) FROM Donations WHERE volunteer_id = ? AND status = 'success') AS total_amount,
        (SELECT COUNT(DISTINCT event_id) FROM Donations WHERE volunteer_id = ?) AS events_count
    FROM Donations d
    JOIN Events e ON d.event_id = e.id
    JOIN Accounts a ON d.volunteer_id = a.id
    JOIN Users u ON a.id = u.account_id
    WHERE d.volunteer_id = ?
    ORDER BY d.donate_date DESC
    OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);  // cho subquery total_amount
            ps.setInt(2, volunteerId);  // cho subquery events_count
            ps.setInt(3, volunteerId);  // cho WHERE chính
            ps.setInt(4, (pageIndex - 1) * pageSize);  // OFFSET
            ps.setInt(5, pageSize);  // FETCH NEXT

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Donation d = new Donation(
                            rs.getInt("donation_id"),
                            rs.getInt("event_id"),
                            rs.getInt("volunteer_id"),
                            rs.getDouble("donate_amount"),
                            rs.getTimestamp("donate_date"),
                            rs.getString("donation_status"),
                            rs.getString("payment_method"),
                            rs.getString("qr_code"),
                            rs.getString("note"),
                            rs.getString("volunteer_username"),
                            rs.getString("volunteer_name"),
                            rs.getString("volunteer_avatar"),
                            rs.getString("event_title"),
                            rs.getDouble("total_amount"),
                            rs.getInt("events_count")
                    );
                    list.add(d);
                }
            }

            System.out.println("==> getUserDonationsPaged() trả về: " + list.size() + " bản ghi (Page: " + pageIndex + ")");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    // lấy danh sách những người donate + số sự kiện họ donate
    public List<Donation> getAllUserDonation() {
        List<Donation> list = new ArrayList<>();
        String sql = """
                      WITH TotalDonations AS (
                                         SELECT 
                                             d.volunteer_id,
                                             SUM(d.amount) AS total_amount,
                                             COUNT(DISTINCT d.event_id) AS events_count
                                         FROM Donations d where d.status = 'success'
                                         GROUP BY d.volunteer_id
                                     ),
                                     LatestDonation AS (
                                         SELECT
                                             d.id,
                                             d.volunteer_id,
                                             d.event_id,
                                             d.amount AS donate_amount,
                                             d.donate_date,
                                             d.status AS donation_status,
                                             d.payment_method,
                                             d.note,
                                             d.qr_code,
                                             ROW_NUMBER() OVER (PARTITION BY d.volunteer_id ORDER BY d.donate_date DESC) AS rn
                                         FROM Donations d
                                     )
                                     SELECT 
                                         ld.id AS donation_id,
                                         ld.volunteer_id,
                                         ld.event_id,
                                         u.full_name AS volunteer_name,
                                         u.avatar AS volunteer_avatar,
                                         a.username AS volunteer_username,
                                         td.total_amount,
                                         td.events_count,
                                         e.title AS event_title,       
                                         ld.donate_amount,
                                         ld.donate_date,
                                         ld.donation_status,
                                         ld.payment_method,
                                         ld.note,
                                         ld.qr_code
                                     FROM TotalDonations td
                                     JOIN LatestDonation ld 
                                         ON td.volunteer_id = ld.volunteer_id AND ld.rn = 1  
                                     JOIN Events e ON ld.event_id = e.id
                                     JOIN Accounts a ON td.volunteer_id = a.id
                                     JOIN Users u ON a.id = u.account_id
                                     ORDER BY td.total_amount DESC;
                     """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Donation d = new Donation(
                        rs.getInt("donation_id"), // donation_id
                        rs.getInt("event_id"), // eventId
                        rs.getInt("volunteer_id"), // volunteerId
                        rs.getDouble("donate_amount"), // amount
                        rs.getTimestamp("donate_date"),
                        rs.getString("donation_status"),
                        rs.getString("payment_method"),
                        rs.getString("qr_code"), // nếu có
                        rs.getString("note"),
                        rs.getString("volunteer_username"),
                        rs.getString("volunteer_name"),
                        rs.getString("volunteer_avatar"),
                        rs.getString("event_title"),
                        rs.getDouble("total_amount"),
                        rs.getInt("events_count")
                );

                list.add(d);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Donation> getDonorsPaged(int offset, int limit) {
        List<Donation> list = new ArrayList<>();
        String sql = """
                      WITH TotalDonations AS (
                                         SELECT 
                                             d.volunteer_id,
                                             SUM(d.amount) AS total_amount,
                                             COUNT(DISTINCT d.event_id) AS events_count
                                         FROM Donations d where d.status = 'success'
                                         GROUP BY d.volunteer_id
                                     ),
                                     LatestDonation AS (
                                         SELECT
                                             d.id,
                                             d.volunteer_id,
                                             d.event_id,
                                             d.amount AS donate_amount,
                                             d.donate_date,
                                             d.status AS donation_status,
                                             d.payment_method,
                                             d.note,
                                             d.qr_code,
                                             ROW_NUMBER() OVER (PARTITION BY d.volunteer_id ORDER BY d.donate_date DESC) AS rn
                                         FROM Donations d
                                     )
                                     SELECT 
                                         ld.id AS donation_id,
                                         ld.volunteer_id,
                                         ld.event_id,
                                         u.full_name AS volunteer_name,
                                         u.avatar AS volunteer_avatar,
                                         a.username AS volunteer_username,
                                         td.total_amount,
                                         td.events_count,
                                         e.title AS event_title,       
                                         ld.donate_amount,
                                         ld.donate_date,
                                         ld.donation_status,
                                         ld.payment_method,
                                         ld.note,
                                         ld.qr_code
                                     FROM TotalDonations td
                                     JOIN LatestDonation ld 
                                         ON td.volunteer_id = ld.volunteer_id AND ld.rn = 1  
                                     JOIN Events e ON ld.event_id = e.id
                                     JOIN Accounts a ON td.volunteer_id = a.id
                                     JOIN Users u ON a.id = u.account_id
                                     ORDER BY td.total_amount DESC
                                     OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                     """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Donation d = new Donation(
                            rs.getInt("donation_id"),
                            rs.getInt("event_id"),
                            rs.getInt("volunteer_id"),
                            rs.getDouble("donate_amount"),
                            rs.getTimestamp("donate_date"),
                            rs.getString("donation_status"),
                            rs.getString("payment_method"),
                            rs.getString("qr_code"),
                            rs.getString("note"),
                            rs.getString("volunteer_username"),
                            rs.getString("volunteer_name"),
                            rs.getString("volunteer_avatar"),
                            rs.getString("event_title"),
                            rs.getDouble("total_amount"),
                            rs.getInt("events_count")
                    );
                    list.add(d);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // tổng số đơn donate cho mỗi cá nhân volunteer để phân trang
    public int getTotalDonationsByVolunteer(int volunteerId) {
        String sql = "SELECT COUNT(*) FROM Donations WHERE volunteer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    //tính tổng số donors đã đăng để chia trang
    public int getTotalDonors() {
        String sql = "SELECT COUNT(*) FROM ( "
                + "  SELECT volunteer_id "
                + "  FROM Donations "
                + "  WHERE status = 'success' "
                + "  GROUP BY volunteer_id "
                + ") AS t";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
