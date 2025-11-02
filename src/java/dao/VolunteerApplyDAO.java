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
import model.EventVolunteer;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class VolunteerApplyDAO {

    private Connection conn;

    public VolunteerApplyDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra volunteer đã apply event chưa
    public boolean hasApplied(int volunteerId, int eventId) {
        String sql = "SELECT COUNT(*) FROM Event_Volunteers WHERE volunteer_id = ? AND event_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);
            ps.setInt(2, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm một bản ghi apply mới
    public void applyToEvent(int volunteerId, int eventId, int hours, String note) {
        String sql = "INSERT INTO Event_Volunteers (volunteer_id, event_id, hours, note, apply_date) VALUES (?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, volunteerId);
            ps.setInt(2, eventId);
            ps.setInt(3, hours);
            ps.setString(4, note);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách các event đã apply của volunteer
    public List<EventVolunteer> getMyApplications(int volunteerId) {
        List<EventVolunteer> list = new ArrayList<>();
        String sql = """
        SELECT 
            ev.id,
            ev.event_id,
            ev.volunteer_id,
            ev.apply_date,
            ev.status,
            ev.hours,
            ev.note,
            o.username AS organization_name,
            c.name AS category_name,
            u.full_name AS volunteer_name
        FROM Event_Volunteers ev
        JOIN Events e ON ev.event_id = e.id
        JOIN Accounts o ON e.organization_id = o.id
        JOIN Categories c ON e.category_id = c.category_id
        JOIN Users u ON ev.volunteer_id = u.account_id
        WHERE ev.volunteer_id = ?
        ORDER BY ev.apply_date DESC;
    """;

        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, volunteerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EventVolunteer ev = new EventVolunteer(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("volunteer_id"),
                        rs.getTimestamp("apply_date"),
                        rs.getString("status"),
                        rs.getInt("hours"),
                        rs.getString("note"),
                        rs.getString("organization_name"),
                        rs.getString("category_name"),
                        rs.getString("volunteer_name")
                );
                list.add(ev);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
