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
    // 2. Thêm mới user sau khi tạo account

    public boolean insertUser(User user) {
        String sql = "INSERT INTO Users (account_id, full_name, dob, gender, phone, email, address, avatar, job_title, bio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getAccount_id());
            ps.setString(2, user.getFull_name());
            ps.setDate(3, user.getDob() != null ? new java.sql.Date(user.getDob().getTime()) : null);
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

    public static void main(String[] args) {
        try {
            System.out.println("===== TEST REGISTER FULL (EXCEPT JOB & BIO) =====");

            // --- Khởi tạo DAO ---
            AccountDAO accountDAO = new AccountDAO();
            UserDAO userDAO = new UserDAO();

            // --- B1: Tạo tài khoản mới ---
            Account acc = new Account();
            acc.setUsername("an_fulltest");
            acc.setPassword("123456"); // Có thể hash nếu muốn
            acc.setRole("volunteer");
            acc.setStatus(true);

            // Gọi DAO để insert account
            int accountId = accountDAO.insertAccount(acc);

            if (accountId <= 0) {
                System.out.println("❌ Insert account thất bại!");
                return;
            }
            System.out.println("✅ Account inserted with ID = " + accountId);

            // --- B2: Tạo user đầy đủ thông tin ---
            User user = new User();
            user.setAccount_id(accountId);
            user.setFull_name("Nguyen Bao An Test");
            user.setEmail("an_fulltest@example.com");
            user.setGender("Male");
            user.setPhone("0989123123");
            user.setAddress("Hanoi, Vietnam");
//            user.setDob(Date.valueOf("2004-05-20")); // yyyy-MM-dd format

            // Bỏ qua job & bio
            boolean inserted = userDAO.insertUser(user);

            if (inserted) {
                System.out.println("✅ Insert user thành công!");
            } else {
                System.out.println("❌ Insert user thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
