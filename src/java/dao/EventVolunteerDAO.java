package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.EventVolunteer;
import utils.DBContext;

public class EventVolunteerDAO {

    // --- Phần code cũ giữ nguyên ---
    public List<EventVolunteer> getEventRegistrationsByVolunteerId(int volunteerId) {
        List<EventVolunteer> list = new ArrayList<>();
        String sql = """
            SELECT ev.id, ev.event_id, ev.volunteer_id, ev.apply_date, ev.status, ev.hours, ev.note,
                   e.title AS eventTitle,
                   c.name AS categoryName,
                   u_org.full_name AS organizationName,
                   u_vol.full_name AS volunteerName
            FROM Event_Volunteers ev
            JOIN Events e ON ev.event_id = e.id
            LEFT JOIN Categories c ON e.category_id = c.category_id
            LEFT JOIN Accounts o ON e.organization_id = o.id
            LEFT JOIN Users u_org ON o.id = u_org.account_id
            LEFT JOIN Accounts v ON ev.volunteer_id = v.id
            LEFT JOIN Users u_vol ON v.id = u_vol.account_id
            WHERE ev.volunteer_id = ?
            ORDER BY ev.apply_date DESC
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, volunteerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EventVolunteer ev = new EventVolunteer();
                    ev.setId(rs.getInt("id"));
                    ev.setEventId(rs.getInt("event_id"));
                    ev.setVolunteerId(rs.getInt("volunteer_id"));
                    Timestamp ts = rs.getTimestamp("apply_date");
                    ev.setApplyDate(ts != null ? new java.util.Date(ts.getTime()) : null);
                    ev.setStatus(rs.getString("status"));
                    ev.setHours(rs.getInt("hours"));
                    ev.setNote(rs.getString("note"));
                    ev.setEventTitle(rs.getString("eventTitle"));
                    ev.setCategoryName(rs.getString("categoryName"));
                    ev.setOrganizationName(rs.getString("organizationName"));
                    ev.setVolunteerName(rs.getString("volunteerName"));

                    list.add(ev);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // --- Lấy trạng thái đơn ---
    public String getApplicationStatus(int eventId, int volunteerId) {
        String sql = "SELECT status FROM Event_Volunteers WHERE event_id = ? AND volunteer_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setInt(2, volunteerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- Xóa đơn + ghi log --
public boolean deletePendingApplication(int eventId, int volunteerId) {
    String sql = "DELETE FROM Event_Volunteers WHERE event_id = ? AND volunteer_id = ? AND status = 'Pending'";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, eventId);
        ps.setInt(2, volunteerId);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    public EventVolunteer getRegistrationByEventAndVolunteer(int eventId, int volunteerId) {
    EventVolunteer ev = null;
    String sql = """
        SELECT ev.id, ev.event_id, ev.volunteer_id, ev.apply_date, ev.status, ev.hours, ev.note,
               e.title AS eventTitle,
               c.name AS categoryName,
               u_org.full_name AS organizationName,
               u_vol.full_name AS volunteerName
        FROM Event_Volunteers ev
        JOIN Events e ON ev.event_id = e.id
        LEFT JOIN Categories c ON e.category_id = c.category_id
        LEFT JOIN Accounts o ON e.organization_id = o.id
        LEFT JOIN Users u_org ON o.id = u_org.account_id
        LEFT JOIN Accounts v ON ev.volunteer_id = v.id
        LEFT JOIN Users u_vol ON v.id = u_vol.account_id
        WHERE ev.event_id = ? AND ev.volunteer_id = ?
    """;

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, eventId);
        ps.setInt(2, volunteerId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                ev = new EventVolunteer();
                ev.setId(rs.getInt("id"));
                ev.setEventId(rs.getInt("event_id"));
                ev.setVolunteerId(rs.getInt("volunteer_id"));
                Timestamp ts = rs.getTimestamp("apply_date");
                ev.setApplyDate(ts != null ? new java.util.Date(ts.getTime()) : null);
                ev.setStatus(rs.getString("status"));
                ev.setHours(rs.getInt("hours"));
                ev.setNote(rs.getString("note"));
                ev.setEventTitle(rs.getString("eventTitle"));
                ev.setCategoryName(rs.getString("categoryName"));
                ev.setOrganizationName(rs.getString("organizationName"));
                ev.setVolunteerName(rs.getString("volunteerName"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return ev;
}
}
