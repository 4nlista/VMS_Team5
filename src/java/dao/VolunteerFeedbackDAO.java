/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Feedback;
import utils.DBContext;

/**
 * DAO cho việc quản lý Feedback của Volunteer
 */
public class VolunteerFeedbackDAO {

    private Connection conn;

    public VolunteerFeedbackDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra volunteer đã feedback cho event này chưa
     *
     * @return Feedback nếu đã có, null nếu chưa
     */
    public Feedback getFeedbackByEventAndVolunteer(int eventId, int volunteerId) {
        // BỎ ĐIỀU KIỆN status != 'deleted' vì không tồn tại trong database
        String sql = "SELECT f.id, f.event_id, f.volunteer_id, f.rating, f.comment, "
                + "f.feedback_date, f.status, "
                + "e.title AS event_title, "
                + "u_vol.full_name AS volunteer_name, "
                + "u_org.full_name AS organization_name "
                + "FROM Feedback f "
                + "JOIN Events e ON f.event_id = e.id "
                + "JOIN Users u_vol ON f.volunteer_id = u_vol.account_id "
                + "JOIN Users u_org ON e.organization_id = u_org.account_id "
                + "WHERE f.event_id = ? AND f.volunteer_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ps.setInt(2, volunteerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Feedback(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("volunteer_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getDate("feedback_date"),
                        rs.getString("status"),
                        rs.getString("event_title"),
                        rs.getString("volunteer_name"),
                        rs.getString("organization_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy thông tin event + org + volunteer để hiển thị form (khi chưa
     * feedback)
     *
     * @return Feedback object chứa thông tin cơ bản để hiển thị form
     */
    public Feedback getEventInfoForFeedback(int eventId, int volunteerId) {
        String sql = "SELECT e.id AS event_id, "
                + "e.title AS event_title, "
                + "u_vol.full_name AS volunteer_name, "
                + "u_org.full_name AS organization_name "
                + "FROM Events e "
                + "JOIN Users u_org ON e.organization_id = u_org.account_id "
                + "CROSS JOIN Users u_vol "
                + "WHERE e.id = ? AND u_vol.account_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ps.setInt(2, volunteerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setEventId(rs.getInt("event_id"));
                feedback.setVolunteerId(volunteerId);
                feedback.setEventTitle(rs.getString("event_title"));
                feedback.setVolunteerName(rs.getString("volunteer_name"));
                feedback.setOrganizationName(rs.getString("organization_name"));
                return feedback;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tạo feedback mới (status mặc định = 'valid')
     *
     * @return true nếu tạo thành công
     */
    public boolean createFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (event_id, volunteer_id, rating, comment, status) "
                + "VALUES (?, ?, ?, ?, 'valid')";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, feedback.getEventId());
            ps.setInt(2, feedback.getVolunteerId());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComment());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật feedback (chỉ update rating và comment)
     *
     * @return true nếu cập nhật thành công
     */
    public boolean updateFeedback(Feedback feedback) {
        String sql = "UPDATE Feedback SET rating = ?, comment = ? "
                + "WHERE event_id = ? AND volunteer_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, feedback.getRating());
            ps.setString(2, feedback.getComment());
            ps.setInt(3, feedback.getEventId());
            ps.setInt(4, feedback.getVolunteerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kiểm tra volunteer có đủ điều kiện feedback không: - Đã tham gia event
     * (status = 'approved' trong Event_Volunteers) - Event đã kết thúc
     *
     * @return true nếu đủ điều kiện
     */
    public boolean canFeedback(int eventId, int volunteerId) {
        String sql = "SELECT COUNT(*) AS can_feedback "
                + "FROM Event_Volunteers ev "
                + "JOIN Events e ON ev.event_id = e.id "
                + "WHERE ev.event_id = ? AND ev.volunteer_id = ? "
                + "AND ev.status = 'approved' AND e.end_date < GETDATE()";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ps.setInt(2, volunteerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("can_feedback") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
