/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Event;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class AdminHomeDAO {

    private Connection conn;

    public AdminHomeDAO() {
        try {
            DBContext db = new DBContext();

            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // lấy tổng số tài khoản
    public int getTotalAccount() {
        String sql = "SELECT COUNT(*) AS total FROM Accounts";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    // lấy tổng số tiền Donate
    public double getTotalMoneyDonate() {
        String sql = " SELECT \n"
                + "    SUM(amount) AS total_success_amount\n"
                + "FROM Donations\n"
                + "WHERE status = 'success';";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total_success_amount");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    // lấy top 3 sự kiện nhiều có tổng tiền donate nhiều nhất
    public List<Event> getTop3EventsMoneyDonate() {
        List<Event> list = new ArrayList<>();
        String sql = """ 
                     SELECT TOP 3 e.id, e.title, e.total_donation, u.full_name
                     FROM events e
                     JOIN [Users] u ON e.organization_id = u.id
                     ORDER BY e.total_donation DESC;
                     """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setTotalDonation(rs.getDouble("total_donation"));
                event.setOrganizationName(rs.getString("full_name"));
                list.add(event);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Event> getTop3EventsComing() {
        List<Event> list = new ArrayList<>();
        String sql = """
        SELECT TOP 3 e.id, e.title, e.start_date, e.location, u.full_name AS organization_name
        FROM Events e
        JOIN Users u ON e.organization_id = u.id
        WHERE e.start_date > GETDATE()
        ORDER BY e.start_date ASC;
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setStartDate(rs.getDate("start_date"));
                event.setLocation(rs.getString("location"));
                event.setOrganizationName(rs.getString("organization_name"));
                list.add(event);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public Map<String, Integer> getAccountStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT role, COUNT(*) AS total FROM Accounts GROUP BY role";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                stats.put(rs.getString("role"), rs.getInt("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stats;
    }
}
