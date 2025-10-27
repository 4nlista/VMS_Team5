/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DBContext;

/**
 *
 * @author ADMIN
 */
public class DonationDAO {

    private Connection conn;

    public DonationDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // truy vấn tổng tiền donation trong database
    public double getTotalDonationAmount() {
        double total = 0;
        String sql = "select SUM(amount) as total_success from Donations where status = 'success'";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    
    

}
