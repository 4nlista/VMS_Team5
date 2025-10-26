package dao;

import java.sql.*;
import model.Organization;
import utils.DBContext;

public class OrganizationDAO {

    public Organization getOrganizationById(int accountId) {
        Organization org = null;
        String sql = """
            SELECT a.id, a.username, a.password, a.status, a.created_at,
                   u.full_name, u.dob, u.gender, u.phone, u.email,
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
                    org = new Organization(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("status"),
                        rs.getString("full_name"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("avatar"),
                        rs.getString("job_title"),
                        rs.getString("bio"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return org;
    }

    public boolean updateOrganization(Organization org) {
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
            ps.setInt(10, org.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
