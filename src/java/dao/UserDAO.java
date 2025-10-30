/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import model.Account;
import model.User;
import java.sql.SQLException;

import utils.DBContext;

/**
 *
 * @author Admin
 */
public class UserDAO {

    private Connection conn;

    public UserDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Kiểm tra email đã tồn tại chưa
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Kiểm tra phone đã tồn tại chưa
    public boolean isPhoneExists(String phone) {
        String sql = "SELECT COUNT(*) FROM Users WHERE phone = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//     3. Thêm mới user sau khi tạo account
    public boolean insertUserWithAccount(User user) {
        String sql = "INSERT INTO Users (account_id, full_name, dob, gender, phone, email, address, avatar, job_title, bio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getAccount_id());
            ps.setString(2, user.getFull_name());
            if (user.getDob() != null) {
                ps.setDate(3, new java.sql.Date(user.getDob().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            ps.setString(4, user.getGender());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getAvatar());
            ps.setString(9, user.getJob_title());
            ps.setString(10, user.getBio());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    // 4. Tạo user profile sau khi tạo account
//    public boolean insertUser(int accountId, String fullName, String email, String phone, 
//                              String gender, String dob, String address, String avatar, String jobTitle, String bio) {
//        String sql = "INSERT INTO Users (account_id, full_name, dob, gender, phone, email, address, avatar, job_title, bio) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, accountId);
//            ps.setString(2, fullName);
//            ps.setDate(3, dob != null && !dob.isEmpty() ? Date.valueOf(dob) : null);
//            ps.setString(4, gender != null && !gender.isEmpty() ? gender : null);
//            ps.setString(5, phone != null && !phone.isEmpty() ? phone : null);
//            ps.setString(6, email);
//            ps.setString(7, address != null && !address.isEmpty() ? address : null);
//            ps.setString(8, avatar != null && !avatar.isEmpty() ? avatar : null);
//            ps.setString(9, jobTitle != null && !jobTitle.isEmpty() ? jobTitle : null);
//            ps.setString(10, bio != null && !bio.isEmpty() ? bio : null);
//            
//            int rows = ps.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
