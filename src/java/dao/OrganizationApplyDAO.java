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
public class OrganizationApplyDAO {

    private Connection conn;

    public OrganizationApplyDAO() {
        try {
            DBContext db = new DBContext();

            this.conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lấy các danh sách VolunteerApplytheo từng event_id;
    public List<EventVolunteer> getVolunteersByEvent(int eventId, int organizationId) {
        List<EventVolunteer> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         ev.id,
                         ev.event_id,
                         ev.volunteer_id,
                         ev.hours,
                         ev.note,
                         ev.apply_date,
                         ev.status,
                         o.username AS organization_name,
                         c.name AS category_name,
                         u.full_name AS volunteer_name
                     FROM Event_Volunteers ev
                     JOIN Events e ON ev.event_id = e.id
                     JOIN Accounts o ON e.organization_id = o.id
                     JOIN Categories c ON e.category_id = c.category_id
                     JOIN Users u ON ev.volunteer_id = u.account_id
                     WHERE ev.event_id = ?
                       AND e.organization_id = ?
                     ORDER BY ev.apply_date DESC;
                     """;
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setInt(2, organizationId);
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
                        rs.getString("volunteer_name"));
                list.add(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // hàm xử lý thao túc
    public void updateVolunteerStatus(int volunteerId, String status) {
        String sql = "UPDATE Event_Volunteers SET status = ? WHERE id = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, volunteerId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // hàm xử lý nút lọc theo trạng thái
    public List<EventVolunteer> getFilterVolunteersByEvent(int organizationId, int eventId, String statusFilter) {
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
            o.username AS organizationName,  
            c.name AS categoryName,
            u.full_name AS volunteerName
        FROM Event_Volunteers ev
        JOIN Events e ON ev.event_id = e.id
        JOIN Accounts o ON e.organization_id = o.id
        JOIN Categories c ON e.category_id = c.category_id
        JOIN Users u ON ev.volunteer_id = u.account_id
        WHERE e.organization_id = ?
          AND e.id = ?
    """;

        if (statusFilter != null && !statusFilter.equals("all")) {
            sql += " AND ev.status = ?";
        }

        sql += " ORDER BY ev.apply_date DESC";

        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, organizationId);
            ps.setInt(2, eventId);
            if (statusFilter != null && !statusFilter.equals("all")) {
                ps.setString(3, statusFilter);
            }

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
                        rs.getString("organizationName"),
                        rs.getString("categoryName"),
                        rs.getString("volunteerName")
                );
                list.add(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
