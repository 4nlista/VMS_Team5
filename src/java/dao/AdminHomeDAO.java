/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class AdminHomeDAO {

    private Connection conn;

    public AdminHomeDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalAccount() {
        String sql = "SELECT COUNT(*) AS total FROM Accounts";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public double getTotalMoneyDonate() {
        String sql = " SELECT \n"
                + "    SUM(amount) AS total_success_amount\n"
                + "FROM Donations\n"
                + "WHERE status = 'success';";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total_success_amount");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
