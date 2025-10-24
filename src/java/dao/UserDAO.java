/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import utils.DBContext;

public class UserDAO {


    public User getUserByAccountId(int accountId) {
        User user = null;
        String sql = "SELECT * FROM Users WHERE account_id = ?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setAccountId(rs.getInt("account_id"));
                user.setFullName(rs.getString("full_name"));
                user.setDob(rs.getDate("dob"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAvatar(rs.getString("avatar"));
                user.setJobTitle(rs.getString("job_title"));
                user.setBio(rs.getString("bio"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public boolean updateUser(User user) {
        String sql = "UPDATE Users "
                   + "SET full_name=?, dob=?, gender=?, phone=?, email=?, "
                   + "address=?, avatar=?, job_title=?, bio=? "
                   + "WHERE id=?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setDate(2, user.getDob() != null ? new java.sql.Date(user.getDob().getTime()) : null);
            ps.setString(3, user.getGender());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getAvatar());
            ps.setString(8, user.getJobTitle());
            ps.setString(9, user.getBio());
            ps.setInt(10, user.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

