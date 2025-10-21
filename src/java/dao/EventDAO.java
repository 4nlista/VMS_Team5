package dao;

import java.sql.*;
import java.util.*;
import model.Event;
import utils.DBContext;

public class EventDAO {
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE status = 'active'";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Event(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getTimestamp("start_date"),
                    rs.getTimestamp("end_date"),
                    rs.getString("location"),
                    rs.getInt("needed_volunteers"),
                    rs.getString("status"),
                    rs.getInt("organization_id"),
                    rs.getInt("category_id"),
                    rs.getDouble("total_donation")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Event getEventById(int id) {
        Event event = null;
        String sql = "SELECT * FROM Events WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    event = new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("end_date"),
                        rs.getString("location"),
                        rs.getInt("needed_volunteers"),
                        rs.getString("status"),
                        rs.getInt("organization_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("total_donation")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }
}
