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
import model.Event;
import utils.DBContext;

/**
 *
 * @author ADDMIN
 */
public class ViewEventsDAO {

    private Connection conn;

    public ViewEventsDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách sự kiện đang active và public để hiển thị lên jsp
    public List<Event> getActiveEvents() {
        List<Event> list = new ArrayList<>();
        String sql = """
    SELECT e.*, 
           u.full_name AS organization_name, 
           c.name AS category_name
    FROM Events e
    JOIN Accounts a ON e.organization_id = a.id
    JOIN Users u ON a.id = u.account_id
    JOIN Categories c ON e.category_id = c.category_id
    WHERE e.status = 'active' and e.visibility = 'public'
""";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event e = new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("images"),
                        rs.getString("description"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("end_date"),
                        rs.getString("location"),
                        rs.getInt("needed_volunteers"),
                        rs.getString("status"),
                        rs.getString("visibility"),
                        rs.getInt("organization_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("total_donation"),
                        rs.getString("organization_name"),
                        rs.getString("category_name")
                );
                list.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Lấy 3 sự kiện mới nhất để update lên màn hình giao diện quảng bá
    public List<Event> getLatestActivePublicEvents() {
        List<Event> list = new ArrayList<>();
        String sql = """
        SELECT TOP 3 e.*, 
               u.full_name AS organization_name, 
               c.name AS category_name
        FROM Events e
        JOIN Accounts a ON e.organization_id = a.id
        JOIN Users u ON a.id = u.account_id
        JOIN Categories c ON e.category_id = c.category_id
        WHERE e.status = 'active' 
          AND e.visibility = 'public'
        ORDER BY e.start_date DESC
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event e = new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("images"),
                        rs.getString("description"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("end_date"),
                        rs.getString("location"),
                        rs.getInt("needed_volunteers"),
                        rs.getString("status"),
                        rs.getString("visibility"),
                        rs.getInt("organization_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("total_donation"),
                        rs.getString("organization_name"),
                        rs.getString("category_name")
                );
                list.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //cú pháp phân trang sự kiện
    public List<Event> getActiveEventsPaged(int offset, int limit) {
        List<Event> list = new ArrayList<>();
        String sql = """
        SELECT e.*, 
               u.full_name AS organization_name, 
               c.name AS category_name
        FROM Events e
        JOIN Accounts a ON e.organization_id = a.id
        JOIN Users u ON a.id = u.account_id
        JOIN Categories c ON e.category_id = c.category_id
        WHERE e.status = 'active' and e.visibility = 'public'
        ORDER BY e.start_date DESC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("images"),
                            rs.getString("description"),
                            rs.getTimestamp("start_date"),
                            rs.getTimestamp("end_date"),
                            rs.getString("location"),
                            rs.getInt("needed_volunteers"),
                            rs.getString("status"),
                            rs.getString("visibility"),
                            rs.getInt("organization_id"),
                            rs.getInt("category_id"),
                            rs.getDouble("total_donation"),
                            rs.getString("organization_name"),
                            rs.getString("category_name")
                    );
                    list.add(e);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //tính tổng event đang hoạt động + công khai nhằm chia trang
    public int getTotalActiveEvents() {
        String sql = "SELECT COUNT(*) FROM Events WHERE status = 'active' and visibility = 'public'";
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
