/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.EventVolunteer;
import utils.DBContext;

public class EventVolunteerDAO {


    public boolean applyForEvent(EventVolunteer ev) {
        String checkSQL = "SELECT COUNT(*) FROM Event_Volunteers WHERE event_id = ? AND volunteer_id = ?";
        String insertSQL = "INSERT INTO Event_Volunteers (event_id, volunteer_id, status) VALUES (?, ?, 'pending')";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            // Kiểm tra xem đã apply chưa
            checkStmt.setInt(1, ev.getEventId());
            checkStmt.setInt(2, ev.getVolunteerId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // đã tồn tại rồi
            }

            // Insert
            insertStmt.setInt(1, ev.getEventId());
            insertStmt.setInt(2, ev.getVolunteerId());
            return insertStmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<EventVolunteer> getAppliedEvents(int volunteerId) {
        List<EventVolunteer> list = new ArrayList<>();
        String sql = "SELECT * FROM Event_Volunteers WHERE volunteer_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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


    public boolean cancelParticipation(int eventId, int volunteerId) {
        String sql = "DELETE FROM Event_Volunteers WHERE event_id = ? AND volunteer_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.setInt(2, volunteerId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE Event_Volunteers SET status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
