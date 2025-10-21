package dao;

import java.sql.*;
import java.util.*;
import model.Event;
import utils.DBContext;

public class EventDAO {
    public Event getEventById(int id) {
        Event event = null;
        String sql = "SELECT * FROM Event WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    event = new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("location"),
                        rs.getInt("organization_id"),
                        rs.getString("status")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Event WHERE status = 'active'";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("location"),
                    rs.getInt("organization_id"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
