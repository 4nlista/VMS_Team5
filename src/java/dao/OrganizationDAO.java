package dao;

import java.sql.*;
import model.User;
import utils.DBContext;

/**
 * DAO trung gian cho các tài khoản có role = 'organization'
 * Thực chất dùng bảng Users + Accounts (role='organization')
 */
public class OrganizationDAO {

    /**
     * Lấy thông tin "organization" (thực chất là user có role = 'organization')
     */
    public User getOrganizationById(int accountId) {
        User org = null;
        String sql = """
            SELECT a.id AS account_id, a.username, a.password, a.status, a.created_at,
                   u.id AS user_id, u.full_name, u.dob, u.gender, u.phone, u.email,
                   u.address, u.avatar, u.job_title, u.bio
            FROM Accounts a
            JOIN Users u ON a.id = u.account_id
            WHERE a.id = ? AND a.role = 'organization'
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    org = new User();
                    org.setAccountId(rs.getInt("account_id"));
                    org.setId(rs.getInt("user_id"));
                    org.setFullName(rs.getString("full_name"));
                    org.setDob(rs.getDate("dob"));
                    org.setGender(rs.getString("gender"));
                    org.setPhone(rs.getString("phone"));
                    org.setEmail(rs.getString("email"));
                    org.setAddress(rs.getString("address"));
                    org.setAvatar(rs.getString("avatar"));
                    org.setJobTitle(rs.getString("job_title"));
                    org.setBio(rs.getString("bio"));
                    // các thông tin thuộc Account (nếu cần)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return org;
    }

    /**
     * Cập nhật hồ sơ "organization" (thực chất là cập nhật bảng Users)
     */
    public boolean updateOrganization(User org) {
        String sql = """
            UPDATE Users
            SET full_name=?, dob=?, gender=?, phone=?, email=?,
                address=?, avatar=?, job_title=?, bio=?
            WHERE account_id=?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, org.getFullName());
            if (org.getDob() != null) {
                ps.setDate(2, new java.sql.Date(org.getDob().getTime()));
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }
            ps.setString(3, org.getGender());
            ps.setString(4, org.getPhone());
            ps.setString(5, org.getEmail());
            ps.setString(6, org.getAddress());
            ps.setString(7, org.getAvatar());
            ps.setString(8, org.getJobTitle());
            ps.setString(9, org.getBio());
            ps.setInt(10, org.getAccountId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
