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
            SELECT ev.id, ev.event_id, ev.volunteer_id, ev.apply_date, ev.status, ev.note,
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

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
        SELECT 
            ev.id,
            ev.event_id,
            e.title AS eventTitle,
            c.name AS categoryName,
            u_org.full_name AS organizationName,  -- đổi sang fullname từ bảng Users
            ev.volunteer_id,
            v.username AS volunteerName,
            ev.apply_date,
            ev.status,
            ev.note,
            ISNULL(d.totalDonate, 0) AS totalDonate,
            e.start_date AS startDateEvent,
            e.end_date AS endDateEvent,
            a.status AS attendanceReport
        FROM Event_Volunteers ev
        INNER JOIN Events e ON ev.event_id = e.id
        LEFT JOIN Categories c ON e.category_id = c.category_id
        
        -- join Accounts và Users để lấy fullname tổ chức
        INNER JOIN Accounts o ON e.organization_id = o.id
        INNER JOIN Users u_org ON u_org.account_id = o.id
        
        INNER JOIN Accounts v ON ev.volunteer_id = v.id
        
        LEFT JOIN (
            SELECT volunteer_id, event_id, SUM(amount) AS totalDonate
            FROM Donations
            GROUP BY volunteer_id, event_id
        ) d ON d.event_id = ev.event_id AND d.volunteer_id = ev.volunteer_id
        
        LEFT JOIN Attendance a ON a.event_id = ev.event_id AND a.volunteer_id = ev.volunteer_id
        
        WHERE ev.event_id = ? AND ev.volunteer_id = ?;
    """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
                    ev.setNote(rs.getString("note"));
                    ev.setEventTitle(rs.getString("eventTitle"));
                    ev.setCategoryName(rs.getString("categoryName"));
                    ev.setOrganizationName(rs.getString("organizationName"));
                    ev.setVolunteerName(rs.getString("volunteerName"));
                    ev.setTotalDonate(rs.getDouble("totalDonate"));
                    ev.setStartDateEvent(rs.getTimestamp("startDateEvent"));
                    ev.setEndDateEvent(rs.getTimestamp("endDateEvent"));
                    ev.setAttendanceReport(rs.getString("attendanceReport"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ev;
    }
}
