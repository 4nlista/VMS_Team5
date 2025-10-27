/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Account;
import utils.DBContext;
import java.sql.*;
import java.util.List;
import utils.PasswordUtil;

/**
 *
 * @author Admin
 */
public class AccountDAO {

    private Connection conn;

    public AccountDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Truy xuất thông tin theo ID
    public Account getAccountById(int id) {
        String sql = "SELECT id, username, password, role, status "
                + "FROM Accounts WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Trả về đối tượng Account với các giá trị tương ứng
                    return new Account(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getBoolean("status") // Gán giá trị status
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy account
    }

    // 1.1. Lấy ra danh sách các tài khoản - dùng để hiển thị dữ liệu 
    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT id, username, password, role, status FROM Accounts";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account acc = new Account(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getBoolean("status")
                );
                list.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    // check email đã tồn tại trong sql ? - dùng cho đăng kí tài khoản
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM Accounts WHERE username = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // đăng kí tài khoản -> sẽ tự động insert vào database
    public int insertAccount(Account account) {
        String sql = "INSERT INTO Accounts (username, password, role, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getRole());
            ps.setBoolean(4, account.isStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Trả về account_id vừa tạo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // -------- Quên mật khẩu -----------
    // lấy thông tin username + email . nếu đúng -> trả về Account
    public Account getAccountByUsernameAndEmail(String username, String email) {
        String sql = "SELECT a.id, a.username, a.password, a.role, a.status "
                + "FROM Accounts a JOIN Users u ON a.id = u.account_id "
                + "WHERE a.username = ? AND u.email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getBoolean("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật mật khẩu mới được random (đã mã hóa) ghi mật khẩu random mới vào DB
    public boolean updatePasswordRandom(int accountId, String newPassword) {
        String sql = "UPDATE Accounts SET password = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ----------------- Đổi mật khẩu -----------------
    // đổi mật khẩu của từng tài khoản
    public boolean updatePasswordByUser(int accountId, String newPassword) {
        String sql = "UPDATE Accounts SET password = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("🔹 SQL: " + sql);
            System.out.println("🔹 newPassword = " + newPassword);
            System.out.println("🔹 accountId = " + accountId);

            ps.setString(1, newPassword);
            ps.setInt(2, accountId);

            int rowsAffected = ps.executeUpdate();
            System.out.println("🔹 Rows affected = " + rowsAffected);

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//
    // Lấy hash mật khẩu hiện tại từ DB theo accountId
    public String getPasswordHashById(int accountId) {
        String sql = "SELECT password FROM Accounts WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 6. Kiểm tra ràng buộc dữ liệu liên quan (FK) trước khi xóa
    public boolean hasRelatedRecords(int accountId) {
        String[] queries = new String[] {
            // Users is intentionally excluded because we delete it first in a transaction
            "SELECT COUNT(*) FROM Events WHERE organization_id = ?",
            "SELECT COUNT(*) FROM Event_Volunteers WHERE volunteer_id = ?",
            "SELECT COUNT(*) FROM Donations WHERE volunteer_id = ?",
            "SELECT COUNT(*) FROM Feedback WHERE volunteer_id = ?",
            "SELECT COUNT(*) FROM Reports WHERE organization_id = ?",
            "SELECT COUNT(*) FROM Notifications WHERE receiver_id = ?",
            "SELECT COUNT(*) FROM News WHERE author_id = ?"
        };
        try {
            for (String q : queries) {
                try (PreparedStatement ps = conn.prepareStatement(q)) {
                    ps.setInt(1, accountId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // an toàn: coi như có ràng buộc để tránh xóa nhầm
        }
        return false;
    }

    // 7. Xóa account (đã kiểm tra điều kiện ở Service). Xóa Users trước cho chắc.
    public boolean deleteAccount(int id) {
        String deleteUser = "DELETE FROM Users WHERE account_id = ?";
        String deleteAccount = "DELETE FROM Accounts WHERE id = ?";
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(deleteUser)) {
                ps1.setInt(1, id);
                ps1.executeUpdate();
            }
            int rows;
            try (PreparedStatement ps2 = conn.prepareStatement(deleteAccount)) {
                ps2.setInt(1, id);
                rows = ps2.executeUpdate();
            }
            conn.commit();
            return rows > 0;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignore) {}
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignore) {}
        }
    }

    // 8. Xóa account và toàn bộ dữ liệu liên quan (cascading delete bằng code)
    public boolean deleteAccountCascade(int id) {
        String delReportsByEventOrg = "DELETE FROM Reports WHERE feedback_id IN (SELECT id FROM Feedback WHERE event_id IN (SELECT id FROM Events WHERE organization_id = ?))";
        String delDonationsByEventOrg = "DELETE FROM Donations WHERE event_id IN (SELECT id FROM Events WHERE organization_id = ?)";
        String delEVByEventOrg = "DELETE FROM Event_Volunteers WHERE event_id IN (SELECT id FROM Events WHERE organization_id = ?)";
        String delFeedbackByEventOrg = "DELETE FROM Feedback WHERE event_id IN (SELECT id FROM Events WHERE organization_id = ?)";
        String delEventsByOrg = "DELETE FROM Events WHERE organization_id = ?";

        String delDonationsByVolunteer = "DELETE FROM Donations WHERE volunteer_id = ?";
        String delEVByVolunteer = "DELETE FROM Event_Volunteers WHERE volunteer_id = ?";
        String delFeedbackByVolunteer = "DELETE FROM Feedback WHERE volunteer_id = ?";

        String delReportsByOrg = "DELETE FROM Reports WHERE organization_id = ?";
        String delNewsByAuthor = "DELETE FROM News WHERE author_id = ?";
        String delNotificationsByReceiver = "DELETE FROM Notifications WHERE receiver_id = ?";
        String delUserProfile = "DELETE FROM Users WHERE account_id = ?";
        String delAccount = "DELETE FROM Accounts WHERE id = ?";

        try {
            conn.setAutoCommit(false);

            // Xóa dữ liệu liên quan tới vai trò organization (nếu có)
            try (PreparedStatement ps = conn.prepareStatement(delReportsByEventOrg)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delDonationsByEventOrg)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delEVByEventOrg)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delFeedbackByEventOrg)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delEventsByOrg)) { ps.setInt(1, id); ps.executeUpdate(); }

            // Xóa dữ liệu liên quan tới vai trò volunteer (nếu có)
            try (PreparedStatement ps = conn.prepareStatement(delReportsByOrg)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delDonationsByVolunteer)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delEVByVolunteer)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delFeedbackByVolunteer)) { ps.setInt(1, id); ps.executeUpdate(); }

            // Các bảng liên quan trực tiếp tới account
            try (PreparedStatement ps = conn.prepareStatement(delNewsByAuthor)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delNotificationsByReceiver)) { ps.setInt(1, id); ps.executeUpdate(); }
            try (PreparedStatement ps = conn.prepareStatement(delUserProfile)) { ps.setInt(1, id); ps.executeUpdate(); }

            int rows;
            try (PreparedStatement ps = conn.prepareStatement(delAccount)) {
                ps.setInt(1, id);
                rows = ps.executeUpdate();
            }

            conn.commit();
            return rows > 0;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignore) {}
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignore) {}
        }
    }

}
