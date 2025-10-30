/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Account;
import utils.DBContext;
import utils.PasswordUtil;

/**
 *
 * @author Admin
 */
public class LoginDAO {

    private Connection conn;

    public LoginDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Kiểm tra đăng nhập (username và password) 
    // Hỗ trợ cả mật khẩu plain text (để tương thích ngược) và mật khẩu đã hash
    public Account checkLogin(String username, String password) {
//<<<<<<< HEAD
        String sql = "SELECT * FROM Accounts WHERE username = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPass = rs.getString("password");

                System.out.println("Stored pass in DB = " + storedPass);
                System.out.println("Input password = " + password);
                System.out.println("Input hashed = " + PasswordUtil.hashPassword(password));

                // ✅ 1️⃣ Nếu DB lưu plain text
                if (storedPass.equals(password)) {
                    System.out.println("👉 Match: plain text");
                    return mapAccount(rs);
//=======
//        String sql = "SELECT id, username, password, role, status, created_at "
//                + "FROM Accounts WHERE username = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, username);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    String storedPassword = rs.getString("password");
//                    boolean passwordMatch = false;
//                    
//                    // Kiểm tra nếu mật khẩu đã được hash (64 ký tự hex SHA-256)
//                    if (storedPassword != null && storedPassword.length() == 64) {
//                        // So sánh với mật khẩu đã hash
//                        passwordMatch = PasswordUtil.verifyPassword(password, storedPassword);
//                    } else {
//                        // Tương thích ngược: so sánh plain text cho các tài khoản cũ
//                        passwordMatch = password != null && password.equals(storedPassword);
//                    }
//                    
//                    if (passwordMatch) {
//                        return new Account(
//                                rs.getInt("id"),
//                                rs.getString("username"),
//                                rs.getString("password"),
//                                rs.getString("role"),
//                                rs.getBoolean("status"), // Gán giá trị status
//                                new java.util.Date(rs.getTimestamp("created_at").getTime())
//                        );
//                    }
//>>>>>>> hoang
                }

                // ✅ 2️⃣ Nếu DB lưu hash SHA-256
                String hashedInput = PasswordUtil.hashPassword(password);
                if (storedPass.equals(hashedInput)) {
                    System.out.println("👉 Match: hashed (SHA-256)");
                    return mapAccount(rs);
                }

                System.out.println("❌ Password không khớp (plain & hashed đều sai)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Account mapAccount(ResultSet rs) throws Exception {
        return new Account(
                rs.getInt("id"),
                    rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getBoolean("status"),
                rs.getDate("created_at")
        );
    }

}
