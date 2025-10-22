/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author locng
 */
import model.EventVolunteer;
import utils.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventVolunteerDAO {
    // Lấy danh sách volunteer apply cho event_id
    public List<EventVolunteer> getVolunteersByEvent(int eventId) {
        List<EventVolunteer> volunteers = new ArrayList<>();
        String sql = "SELECT ev.*, u.full_name, u.email " +
                     "FROM Event_Volunteers ev " +
                     "JOIN Accounts a ON ev.volunteer_id = a.id " +
                     "JOIN Users u ON a.id = u.account_id " +
                     "WHERE ev.event_id = ? ORDER BY ev.apply_date DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
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
                ev.setVolunteerName(rs.getString("full_name"));
                ev.setVolunteerEmail(rs.getString("email"));
                volunteers.add(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return volunteers;
    }
    
    
    

    // Cập nhật trạng thái volunteer (duyệt/từ chối)
    public boolean updateVolunteerStatus(int evId, String newStatus) {
        String sql = "UPDATE Event_Volunteers SET status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, evId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
