/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utils.DBContext;
import model.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NotificationDAO {

    private Connection conn;

    public NotificationDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Insert 1 thông báo
    public boolean insertNotification(Notification notification) {
        String sql = "INSERT INTO Notifications (sender_id, receiver_id, message, type, event_id) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, notification.getSenderId());
            ps.setInt(2, notification.getReceiverId());
            ps.setString(3, notification.getMessage());
            ps.setString(4, notification.getType());
            ps.setInt(5, notification.getEventId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Lấy danh sách thông báo theo receiverId (JOIN lấy thêm tên sender, event)
    public List<Notification> getNotificationsByReceiverId(int receiverId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.id, n.sender_id, n.receiver_id, n.message, n.type, "
                + "n.created_at, n.is_read, n.event_id, "
                + "us.full_name AS sender_name, "
                + "ur.full_name AS receiver_name, "
                + "e.title AS event_title "
                + "FROM Notifications n "
                + "LEFT JOIN Users us ON n.sender_id = us.account_id "
                + "LEFT JOIN Users ur ON n.receiver_id = ur.account_id "
                + "LEFT JOIN Events e ON n.event_id = e.id "
                + "WHERE n.receiver_id = ? "
                + "ORDER BY n.created_at DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, receiverId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setId(rs.getInt("id"));
                n.setSenderId(rs.getInt("sender_id"));
                n.setReceiverId(rs.getInt("receiver_id"));
                n.setMessage(rs.getString("message"));
                n.setType(rs.getString("type"));
                n.setCreatedAt(rs.getTimestamp("created_at"));
                n.setIsRead(rs.getBoolean("is_read"));
                n.setEventId(rs.getInt("event_id"));
                n.setSenderName(rs.getString("sender_name"));
                n.setReceiverName(rs.getString("receiver_name"));
                n.setEventTitle(rs.getString("event_title"));

                list.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Đánh dấu đã đọc
    public boolean markAsRead(int notificationId) {
        String sql = "UPDATE Notifications SET is_read = 1 WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Đếm số thông báo chưa đọc
    public int getUnreadCount(int receiverId) {
        String sql = "SELECT COUNT(*) FROM Notifications WHERE receiver_id = ? AND is_read = 0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, receiverId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 5. Đánh dấu tất cả là đã đọc (optional - tiện lợi)
    public boolean markAllAsRead(int receiverId) {
        String sql = "UPDATE Notifications SET is_read = 1 WHERE receiver_id = ? AND is_read = 0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, receiverId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Validate: Kiểm tra event chưa kết thúc
    public boolean isEventActive(int eventId) {
        String sql = "SELECT COUNT(*) FROM Events WHERE id = ? AND end_date >= GETDATE()";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. Validate: Kiểm tra event có volunteer approved không
    public boolean hasApprovedVolunteers(int eventId) {
        String sql = "SELECT COUNT(*) FROM Event_Volunteers WHERE event_id = ? AND status = 'approved'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 8. Lấy danh sách volunteerId đã approved của event (dùng cho gửi chung)
    public List<Integer> getApprovedVolunteerIds(int eventId) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT volunteer_id FROM Event_Volunteers WHERE event_id = ? AND status = 'approved'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("volunteer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
