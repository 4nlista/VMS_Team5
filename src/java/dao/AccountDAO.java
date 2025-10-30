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
    
    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        List<Account> list = dao.getAllAccounts();

        System.out.println("Account size = " + list.size());
        for (Account acc : list) {
            System.out.println(acc.getId() + " - " + acc.getUsername() + " - " + acc.getRole());
        }
    }

}
