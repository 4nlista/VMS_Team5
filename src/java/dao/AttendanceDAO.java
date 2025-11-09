/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Attendance;
import utils.DBContext;

public class AttendanceDAO {

    private Connection connection;

    public AttendanceDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
     // Lấy danh sách volunteer đã được approved để điểm danh Có thể filter theo
     // status (pending/present/absent)
  
    public List<Attendance> getVolunteersForAttendance(int eventId, String statusFilter) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT "
                + "    acc.id AS volunteer_id, "
                + "    u.full_name AS volunteer_name, "
                + "    u.email, "
                + "    u.phone, "
                + "    COALESCE(a.status, 'pending') AS attendance_status "
                + "FROM Event_Volunteers ev "
                + "JOIN Accounts acc ON ev.volunteer_id = acc.id "
                + "JOIN Users u ON acc.id = u.account_id "
                + "LEFT JOIN Attendance a "
                + "    ON a.event_id = ev.event_id "
                + "   AND a.volunteer_id = ev.volunteer_id "
                + "WHERE ev.event_id = ? "
                + "  AND ev.status = 'approved'";

        // Thêm filter nếu không phải "all"
        if (statusFilter != null && !statusFilter.equals("all")) {
            sql += " AND COALESCE(a.status, 'pending') = ?";
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, eventId);

            if (statusFilter != null && !statusFilter.equals("all")) {
                ps.setString(2, statusFilter);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance att = new Attendance(
                        eventId,
                        rs.getInt("volunteer_id"),
                        rs.getString("volunteer_name"),
                        rs.getString("attendance_status"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                list.add(att);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Cập nhật trạng thái điểm danh
    // Nếu chưa có record thì INSERT, có rồi thì UPDATE
    public boolean updateAttendanceStatus(int eventId, int volunteerId, String status) {
        // Kiểm tra xem đã có record chưa
        String checkSql = "SELECT 1 FROM Attendance WHERE event_id = ? AND volunteer_id = ?";
        String insertSql = "INSERT INTO Attendance (event_id, volunteer_id, status) VALUES (?, ?, ?)";
        String updateSql = "UPDATE Attendance SET status = ? WHERE event_id = ? AND volunteer_id = ?";

        try {
            PreparedStatement checkPs = connection.prepareStatement(checkSql);
            checkPs.setInt(1, eventId);
            checkPs.setInt(2, volunteerId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Đã có => UPDATE
                PreparedStatement updatePs = connection.prepareStatement(updateSql);
                updatePs.setString(1, status);
                updatePs.setInt(2, eventId);
                updatePs.setInt(3, volunteerId);
                return updatePs.executeUpdate() > 0;
            } else {
                // Chưa có => INSERT
                PreparedStatement insertPs = connection.prepareStatement(insertSql);
                insertPs.setInt(1, eventId);
                insertPs.setInt(2, volunteerId);
                insertPs.setString(3, status);
                return insertPs.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
