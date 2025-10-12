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

	public User getUserById(int id) {
		String sql = "SELECT u.id AS id, a.id AS account_id, a.role, a.username, u.full_name, u.gender, u.avatar "
			    + "FROM Users u JOIN Accounts a ON u.account_id = a.id WHERE u.id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Account acc = new Account();
					acc.setUsername(rs.getString("username"));
					acc.setRole(rs.getString("role"));
					return new User(
						    rs.getInt("id"),
						    rs.getInt("account_id"),
						    rs.getString("full_name"),
						    rs.getString("gender"),
						    rs.getString("avatar"),
						    acc
					);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> getAllUsers() {
		List<User> list = new ArrayList<>();
		String sql = "SELECT u.id AS id, a.id AS account_id, a.role, a.username, u.full_name, u.gender, u.phone, u.email, u.address, u.avatar, u.job_title, u.bio, u.dob "
			    + "FROM Users u JOIN Accounts a ON u.account_id = a.id";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Account acc = new Account();
				acc.setUsername(rs.getString("username"));
				acc.setRole(rs.getString("role"));
				User user = new User(
					    rs.getInt("id"),
					    rs.getInt("account_id"),
					    acc,
					    rs.getString("full_name"),
					    rs.getString("gender"),
					    rs.getString("phone"),
					    rs.getString("email"),
					    rs.getString("address"),
					    rs.getString("avatar"),
					    rs.getString("job_title"),
					    rs.getString("bio"),
					    rs.getDate("dob")
				);
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean updateUser(int id, String fullName, String gender, String phone,
		    String email, String address, String jobTitle, String bio) {
		String sql = "UPDATE Users SET full_name=?, gender=?, phone=?, email=?, address=?, job_title=?, bio=? WHERE id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, fullName);
			ps.setString(2, gender);
			ps.setString(3, phone);
			ps.setString(4, email);
			ps.setString(5, address);
			ps.setString(6, jobTitle);
			ps.setString(7, bio);
			ps.setInt(8, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
