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
                SELECT a.volunteer_id,
                       acc.fullname AS volunteerName,
                       a.status,
                       e.title AS eventTitle,
                       org.fullname AS organizationName,
                       e.start_date,
                       e.end_date
                FROM Attendance a
                JOIN Events e ON a.event_id = e.id
                JOIN Accounts acc ON a.volunteer_id = acc.id
                JOIN Accounts org ON e.organization_id = org.id
                WHERE a.volunteer_id = ?
                ORDER BY e.start_date DESC
                """;

        // ✅ tạo connection từ utils.DBContext
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
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                );
                list.add(att);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
