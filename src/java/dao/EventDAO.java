/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Admin
 */
import model.Event;
import utils.DBContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // Lấy danh sách sự kiện theo organization_id
    public List<Event> getEventsByOrganization(int organizationId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, c.name AS category_name "
                + "FROM Events e "
                + "LEFT JOIN Categories c ON e.category_id = c.category_id "
                + "WHERE e.organization_id = ? ORDER BY e.start_date DESC";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, organizationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartDate((Date) new java.util.Date(rs.getTimestamp("start_date").getTime()));
                event.setEndDate((Date) new java.util.Date(rs.getTimestamp("end_date").getTime()));
                event.setLocation(rs.getString("location"));
                event.setNeededVolunteers(rs.getInt("needed_volunteers"));
                event.setStatus(rs.getString("status"));
                event.setOrganizationId(rs.getInt("organization_id"));
                event.setCategoryId(rs.getInt("category_id"));
                event.setTotalDonation(rs.getDouble("total_donation"));
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    //Xóa sự kiện
    public static boolean deleteEventById(int eventId) {
        String sql = "DELETE FROM Events WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Event getEventById(int eventId) {
        String sql = "SELECT e.*, c.name AS category_name "
                + "FROM Events e "
                + "LEFT JOIN Categories c ON e.category_id = c.category_id "
                + "WHERE e.id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("id"));
                    event.setTitle(rs.getString("title"));
                    event.setDescription(rs.getString("description"));

                    // start_date
                    java.sql.Timestamp tsStart = rs.getTimestamp("start_date");
                    if (tsStart != null) {
                        // model dùng java.util.Date
                        event.setStartDate(new java.util.Date(tsStart.getTime()));
                    } else {
                        event.setStartDate(null);
                    }

                    // end_date
                    java.sql.Timestamp tsEnd = rs.getTimestamp("end_date");
                    if (tsEnd != null) {
                        event.setEndDate(new java.util.Date(tsEnd.getTime()));
                    } else {
                        event.setEndDate(null);
                    }

                    event.setLocation(rs.getString("location"));

                    // needed_volunteers (kiểm tra nếu DB cho phép NULL)
                    int needed = rs.getInt("needed_volunteers");
                    if (rs.wasNull()) {
                        // nếu bạn muốn biểu diễn NULL, đổi kiểu trong model sang Integer
                        event.setNeededVolunteers(0); // hoặc set một giá trị mặc định / or leave as 0
                    } else {
                        event.setNeededVolunteers(needed);
                    }

                    event.setStatus(rs.getString("status"));

                    int orgId = rs.getInt("organization_id");
                    if (rs.wasNull()) {
                        event.setOrganizationId(0); // hoặc dùng Integer trong model
                    } else {
                        event.setOrganizationId(orgId);
                    }

                    // category_id có thể NULL
                    Object catObj = rs.getObject("category_id");
                    if (catObj == null) {
                        event.setCategoryId(0); // hoặc chuyển model sang Integer và set null
                    } else {
                        event.setCategoryId(rs.getInt("category_id"));
                    }

                    // total_donation (double)
                    double total = rs.getDouble("total_donation");
                    if (rs.wasNull()) {
                        event.setTotalDonation(0.0); // hoặc dùng Double trong model để cho phép null
                    } else {
                        event.setTotalDonation(total);
                    }

                    // category name (từ join)
                    String catName = rs.getString("category_name");
                    event.setCategoryName(catName); // nếu null thì setter nhận null

                    return event;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // tốt hơn dùng logger
        }
        return null;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, c.name AS category_name "
                + "FROM Events e "
                + "LEFT JOIN Categories c ON e.category_id = c.category_id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartDate(new java.sql.Date(rs.getTimestamp("start_date").getTime()));
                event.setEndDate(new java.sql.Date(rs.getTimestamp("end_date").getTime()));
                event.setLocation(rs.getString("location"));
                event.setNeededVolunteers(rs.getInt("needed_volunteers"));
                event.setStatus(rs.getString("status"));
                event.setOrganizationId(rs.getInt("organization_id"));
                event.setCategoryId(rs.getInt("category_id"));
                event.setTotalDonation(rs.getDouble("total_donation"));
                event.setCategoryId(rs.getInt("category_id"));

                events.add(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    // Thêm mới sự kiện
    public boolean createEvent(Event event) {
        String sql = "INSERT INTO Events (title, description, start_date, end_date, location, needed_volunteers, status, organization_id, category_id, total_donation) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new Timestamp(event.getStartDate().getTime()));
            stmt.setTimestamp(4, new Timestamp(event.getEndDate().getTime()));
            stmt.setString(5, event.getLocation());
            stmt.setInt(6, event.getNeededVolunteers());
            stmt.setString(7, event.getStatus());
            stmt.setInt(8, event.getOrganizationId());
            stmt.setInt(9, event.getCategoryId() == 0 ? null : event.getCategoryId()); // Xử lý category_id NULL
            stmt.setDouble(10, event.getTotalDonation());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEvent(Event event) {
    String sql = "UPDATE Events SET title=?, description=?, start_date=?, end_date=?, "
               + "location=?, needed_volunteers=?, status=?, category_id=?, total_donation=?, organization_id=? "
               + "WHERE id=?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, event.getTitle());
        ps.setString(2, event.getDescription());

        // start_date
        if (event.getStartDate() != null) {
            ps.setTimestamp(3, new java.sql.Timestamp(event.getStartDate().getTime()));
        } else {
            ps.setNull(3, java.sql.Types.TIMESTAMP);
        }

        // end_date
        if (event.getEndDate() != null) {
            ps.setTimestamp(4, new java.sql.Timestamp(event.getEndDate().getTime()));
        } else {
            ps.setNull(4, java.sql.Types.TIMESTAMP);
        }

        ps.setString(5, event.getLocation());
        ps.setInt(6, event.getNeededVolunteers());
        ps.setString(7, event.getStatus());

        // category_id
        if (event.getCategoryId() != 0) {
            ps.setInt(8, event.getCategoryId());
        } else {
            ps.setNull(8, java.sql.Types.INTEGER);
        }

        // total_donation
        ps.setDouble(9, event.getTotalDonation());

        // organization_id mặc định là 2
        ps.setInt(10, 2);

        // WHERE id
        ps.setInt(11, event.getId());

        int rows = ps.executeUpdate();
        return rows > 0;

    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}


    // Xóa sự kiện (tùy chọn, để hỗ trợ nếu cần)
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
