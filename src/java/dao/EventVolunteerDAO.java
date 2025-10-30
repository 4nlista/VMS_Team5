package dao;

import java.sql.*;
import java.util.*;
import model.EventVolunteer;
import model.EventVolunteerInfo;
import utils.DBContext;

public class EventVolunteerDAO {

    // Volunteer Apply
    public boolean applyForEvent(EventVolunteer ev) {
        String checkSQL = "SELECT COUNT(*) FROM Event_Volunteers WHERE event_id = ? AND volunteer_id = ?";
        String insertSQL = "INSERT INTO Event_Volunteers (event_id, volunteer_id, apply_date, hours, note, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSQL); PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            checkStmt.setInt(1, ev.getEventId());
            checkStmt.setInt(2, ev.getVolunteerId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // đã apply rồi
            }

            insertStmt.setInt(1, ev.getEventId());
            insertStmt.setInt(2, ev.getVolunteerId());
            //  dùng thời gian hiện tại
            insertStmt.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
            insertStmt.setInt(4, ev.getHours());
            insertStmt.setString(5, ev.getNote());
            insertStmt.setString(6, ev.getStatus());

            return insertStmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Volunteer xem danh sách apply
    public List<EventVolunteer> getAppliedEvents(int volunteerId) {
        List<EventVolunteer> list = new ArrayList<>();
        String sql = "SELECT * FROM Event_Volunteers WHERE volunteer_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, volunteerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EventVolunteer ev = new EventVolunteer();
                ev.setId(rs.getInt("id"));
                ev.setEventId(rs.getInt("event_id"));
                ev.setVolunteerId(rs.getInt("volunteer_id"));
                ev.setApplyDate(rs.getTimestamp("apply_date"));
                ev.setStatus(rs.getString("status"));
                ev.setHours(rs.getInt("hours"));
                ev.setNote(rs.getString("note"));
                list.add(ev);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Volunteer hủy đơn đăng ký (chỉ khi chưa được duyệt)
    public boolean cancelParticipation(int eventId, int volunteerId) {
        String sql = "DELETE FROM Event_Volunteers WHERE event_id = ? AND volunteer_id = ? AND status = 'pending'";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.setInt(2, volunteerId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật trạng thái (duyệt / từ chối)
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE Event_Volunteers SET status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách volunteer đang chờ duyệt theo tổ chức
    public List<EventVolunteerInfo> getPendingVolunteersByOrganization(int orgId) {
        List<EventVolunteerInfo> list = new ArrayList<>();
        String sql = """
            SELECT ev.id AS apply_id, e.title AS event_title, u.full_name, u.email, ev.status, ev.apply_date
            FROM Event_Volunteers ev
            JOIN Events e ON ev.event_id = e.id
            JOIN Users u ON ev.volunteer_id = u.id
            WHERE e.organization_id = ? AND ev.status = 'pending'
            ORDER BY ev.apply_date DESC
        """;
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orgId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EventVolunteerInfo info = new EventVolunteerInfo();
                info.setApplyId(rs.getInt("apply_id"));
                info.setEventTitle(rs.getString("event_title"));
                info.setVolunteerName(rs.getString("full_name"));
                info.setVolunteerEmail(rs.getString("email"));
                info.setStatus(rs.getString("status"));
                info.setApplyDate(rs.getTimestamp("apply_date"));
                list.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy volunteer_id & event_id theo applyId
    public int[] getVolunteerAndEventByApplyId(int applyId) {
        String sql = "SELECT volunteer_id, event_id FROM Event_Volunteers WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, applyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new int[]{rs.getInt("volunteer_id"), rs.getInt("event_id")};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // (Tùy chọn) - Lấy trạng thái đơn apply
    public String getStatus(int eventId, int volunteerId) {
        String sql = "SELECT status FROM Event_Volunteers WHERE event_id = ? AND volunteer_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.setInt(2, volunteerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Lấy toàn bộ lịch sử đăng ký của volunteer kèm thông tin sự kiện

    public List<EventVolunteerInfo> getAllApplicationsByVolunteer(int accountId) {
        List<EventVolunteerInfo> list = new ArrayList<>();
        String sql = """
        SELECT ev.id AS apply_id, e.title AS event_title, u.full_name, u.email, ev.status, ev.apply_date
        FROM Event_Volunteers ev
        JOIN Events e ON ev.event_id = e.id
        JOIN Users u ON ev.volunteer_id = u.id
        WHERE ev.volunteer_id = ?
        ORDER BY ev.apply_date DESC
    """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EventVolunteerInfo info = new EventVolunteerInfo();
                info.setApplyId(rs.getInt("apply_id"));
                info.setEventTitle(rs.getString("event_title"));
                info.setVolunteerName(rs.getString("full_name"));
                info.setVolunteerEmail(rs.getString("email"));
                info.setStatus(rs.getString("status"));
                info.setApplyDate(rs.getTimestamp("apply_date"));
                list.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
