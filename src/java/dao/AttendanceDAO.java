package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Attendance;
import utils.DBContext;

public class AttendanceDAO {

    public List<Attendance> getAttendanceHistoryByVolunteerId(int volunteerId) {
        List<Attendance> list = new ArrayList<>();

        String sql = """
            SELECT 
                a.volunteer_id,
                v.full_name AS volunteerName,
                a.status,
                e.title AS eventTitle,
                o.full_name AS organizationName,
                e.start_date,
                e.end_date
            FROM Attendance a
            JOIN Events e ON a.event_id = e.id
            JOIN Users v ON a.volunteer_id = v.account_id
            JOIN Users o ON e.organization_id = o.account_id
            WHERE a.volunteer_id = ?
            ORDER BY e.start_date DESC
        """;

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, volunteerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance att = new Attendance(
                        rs.getInt("volunteer_id"),
                        rs.getString("volunteerName"),
                        rs.getString("status"),
                        rs.getString("eventTitle"),
                        rs.getString("organizationName"),
                        new java.util.Date(rs.getTimestamp("start_date").getTime()),
                        new java.util.Date(rs.getTimestamp("end_date").getTime())
                );
                list.add(att);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
