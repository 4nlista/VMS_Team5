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

    // 1. Lấy ra danh sách các tài khoản
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

    // 2. Cập nhật trạng thái khóa/mở tài khoản
    public boolean updateAccountStatus(int id, boolean status) {
        String sql = "UPDATE Accounts SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Tìm kiếm + lọc tài khoản theo role, status, search (username)
    public List<Account> findAccounts(String role, Boolean status, String search) {
        List<Account> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, username, password, role, status FROM Accounts WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (role != null && !role.isEmpty()) {
            sql.append(" AND role = ?");
            params.add(role);
        }
        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (username LIKE ?)");
            params.add("%" + search + "%");
        }
        sql.append(" ORDER BY id ASC");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof String) {
                    ps.setString(i + 1, (String) p);
                } else if (p instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) p);
                } else if (p instanceof Integer) {
                    ps.setInt(i + 1, (Integer) p);
                } else {
                    ps.setObject(i + 1, p);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new Account(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getBoolean("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 4. Đếm tổng số bản ghi theo cùng tiêu chí lọc
    public int countAccounts(String role, Boolean status, String search) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS total FROM Accounts WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (role != null && !role.isEmpty()) {
            sql.append(" AND role = ?");
            params.add(role);
        }
        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (username LIKE ?)");
            params.add("%" + search + "%");
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof String) {
                    ps.setString(i + 1, (String) p);
                } else if (p instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) p);
                } else if (p instanceof Integer) {
                    ps.setInt(i + 1, (Integer) p);
                } else {
                    ps.setObject(i + 1, p);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 5. Lấy danh sách theo trang (offset/limit) cùng tiêu chí lọc hiện tại
    public List<Account> findAccountsPaged(String role, Boolean status, String search, int offset, int limit) {
        List<Account> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, username, password, role, status FROM Accounts WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (role != null && !role.isEmpty()) {
            sql.append(" AND role = ?");
            params.add(role);
        }
        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (username LIKE ?)");
            params.add("%" + search + "%");
        }

        sql.append(" ORDER BY id ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            for (Object p : params) {
                if (p instanceof String) {
                    ps.setString(idx++, (String) p);
                } else if (p instanceof Boolean) {
                    ps.setBoolean(idx++, (Boolean) p);
                } else if (p instanceof Integer) {
                    ps.setInt(idx++, (Integer) p);
                } else {
                    ps.setObject(idx++, p);
                }
            }
            ps.setInt(idx++, offset);
            ps.setInt(idx, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new Account(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getBoolean("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        List<Account> list = dao.getAllAccounts();

        System.out.println("Account size = " + list.size());
        for (Account acc : list) {
            System.out.println(acc.getId() + " - " + acc.getUsername() + " - " + acc.getRole());
        }
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
