package dao;

import java.sql.*;
import java.util.*;
import model.Event;
import utils.DBContext;

public class EventDAO {

    // Lấy tất cả sự kiện đang active (cho volunteer xem)
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE status = 'active'";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapEvent(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sự kiện theo ID
    public Event getEventById(int id) {
        Event event = null;
        String sql = "SELECT * FROM Events WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    event = mapEvent(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    // Lấy danh sách sự kiện theo tổ chức
    public List<Event> getEventsByOrganization(int orgId) {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE organization_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orgId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapEvent(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới sự kiện
    public void addEvent(Event e) {
        String sql = "INSERT INTO Events (title, description, start_date, end_date, location, needed_volunteers, status, organization_id, category_id, total_donation) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getTitle());
            ps.setString(2, e.getDescription());
            ps.setTimestamp(3, (Timestamp) e.getStartDate());
            ps.setTimestamp(4, (Timestamp) e.getEndDate());
            ps.setString(5, e.getLocation());
            ps.setInt(6, e.getNeededVolunteers());
            ps.setString(7, e.getStatus() != null ? e.getStatus() : "active");
            ps.setInt(8, e.getOrganizationId());
            ps.setInt(9, e.getCategoryId());
            ps.setDouble(10, e.getTotalDonation());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //  Cập nhật sự kiện
    public void updateEvent(Event e) {
        String sql = "UPDATE Events SET title=?, description=?, start_date=?, end_date=?, location=?, needed_volunteers=?, status=?, category_id=?, total_donation=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getTitle());
            ps.setString(2, e.getDescription());
            ps.setTimestamp(3, (Timestamp) e.getStartDate());
            ps.setTimestamp(4, (Timestamp) e.getEndDate());
            ps.setString(5, e.getLocation());
            ps.setInt(6, e.getNeededVolunteers());
            ps.setString(7, e.getStatus());
            ps.setInt(8, e.getCategoryId());
            ps.setDouble(9, e.getTotalDonation());
            ps.setInt(10, e.getId());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //  Xóa sự kiện
    public void deleteEvent(int id) {
        String sql = "DELETE FROM Events WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Hàm phụ trợ chung (tránh lặp lại code)
    private Event mapEvent(ResultSet rs) throws SQLException {
        return new Event(
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
