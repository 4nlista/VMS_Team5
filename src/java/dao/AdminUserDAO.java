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
import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class AdminUserDAO {

	private Connection conn;

	public AdminUserDAO() {
		try {
			DBContext db = new DBContext();
			this.conn = db.getConnection();
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

	//To update user detail as an admin
	public boolean updateUser(int id, String username, String fullName, String gender, String phone,
		    String email, String address, String jobTitle, String bio, Date dob) {
		String updateUserSql = "UPDATE Users SET full_name=?, gender=?, phone=?, email=?, address=?, job_title=?, bio=?, dob=? WHERE id=?";
		String updateAccountSql = "UPDATE Accounts SET username=? WHERE id=(SELECT account_id FROM Users WHERE id=?)";
		try {
			conn.setAutoCommit(false); // start transaction

			// Update Users
			try (PreparedStatement psUser = conn.prepareStatement(updateUserSql)) {
				psUser.setString(1, fullName);
				psUser.setString(2, gender);
				psUser.setString(3, phone);
				psUser.setString(4, email);
				psUser.setString(5, address);
				psUser.setString(6, jobTitle);
				psUser.setString(7, bio);
				if (dob != null) {
					psUser.setDate(8, dob);
				} else {
					psUser.setNull(8, java.sql.Types.DATE);
				}
				psUser.setInt(9, id);
				psUser.executeUpdate();
			}

			// Update Accounts
			try (PreparedStatement psAccount = conn.prepareStatement(updateAccountSql)) {
				psAccount.setString(1, username);
				psAccount.setInt(2, id);
				psAccount.executeUpdate();
			}

			conn.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ignored) {
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException ignored) {
			}
		}

		return false;
	}

	//Full user details based on their id
	public User getUserDetailById(int id) {
		String sql = "SELECT u.id AS id, a.id AS account_id, a.role, a.username, u.full_name, u.gender, u.phone, u.email, u.address, u.avatar, u.job_title, u.bio, u.dob "
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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//Get user list but now has paging logic
	public List<User> getAllUsersWithPagination(int page, int pageSize) {
		List<User> list = new ArrayList<>();
		String sql = "SELECT u.id, a.id as account_id, a.role, a.username, u.full_name, u.gender, u.avatar\n"
			    + "FROM Users u JOIN Accounts a ON u.account_id = a.id\n"
			    + "ORDER BY u.id\n"
			    + "OFFSET ? ROWS\n"
			    + "FETCH NEXT ? ROWS ONLY";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			int offset = (page - 1) * pageSize;
			ps.setInt(1, offset);
			ps.setInt(2, pageSize);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account account = new Account();
				account.setId(rs.getInt("account_id"));
				account.setRole(rs.getString("role"));
				account.setUsername(rs.getString("username"));

				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFull_name(rs.getString("full_name"));
				user.setGender(rs.getString("gender"));
				user.setAvatar(rs.getString("avatar"));
				user.setAccount(account);

				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//Get the count of all user
	public int getTotalUserCount() {
		String sql = "SELECT COUNT(*) FROM Users";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	//Get userlist for filter
	public List<User> getUsersWithFiltersAndPagination(int page, int pageSize, String role, String search, String sort) {
		List<User> list = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.id, a.id as account_id, a.role, a.username, u.full_name, u.gender, u.avatar ");
		sql.append("FROM Users u JOIN Accounts a ON u.account_id = a.id ");

		// WHERE clauses if provided
		List<String> whereClauses = new ArrayList<>();
		if (role != null && !role.trim().isEmpty()) {
			whereClauses.add("a.role = ?");
		}
		if (search != null && !search.trim().isEmpty()) {
			whereClauses.add("a.username LIKE ?");
		}

		if (!whereClauses.isEmpty()) {
			sql.append("WHERE ").append(String.join(" AND ", whereClauses)).append(" ");
		}

		// ORDER BY handling
		if ("id_desc".equalsIgnoreCase(sort)) {
			sql.append("ORDER BY u.id DESC ");
		} else {
			// default ascending
			sql.append("ORDER BY u.id ASC ");
		}

		// Pagination (SQL Server style OFFSET FETCH)
		sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

		try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			int idx = 1;
			if (role != null && !role.trim().isEmpty()) {
				ps.setString(idx++, role.trim());
			}
			if (search != null && !search.trim().isEmpty()) {
				ps.setString(idx++, "%" + search.trim() + "%");
			}
			int offset = (page - 1) * pageSize;
			ps.setInt(idx++, offset);
			ps.setInt(idx, pageSize); // last param

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Account account = new Account();
					account.setId(rs.getInt("account_id"));
					account.setRole(rs.getString("role"));
					account.setUsername(rs.getString("username"));

					User user = new User();
					user.setId(rs.getInt("id"));
					user.setFull_name(rs.getString("full_name"));
					user.setGender(rs.getString("gender"));
					user.setAvatar(rs.getString("avatar"));
					user.setAccount(account);

					list.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//Return the filtered usercount
	public int getFilteredUserCount(String role, String search) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) ");
		sql.append("FROM Users u JOIN Accounts a ON u.account_id = a.id ");

		List<String> whereClauses = new ArrayList<>();
		if (role != null && !role.trim().isEmpty()) {
			whereClauses.add("a.role = ?");
		}
		if (search != null && !search.trim().isEmpty()) {
			whereClauses.add("a.username LIKE ?");
		}

		if (!whereClauses.isEmpty()) {
			sql.append("WHERE ").append(String.join(" AND ", whereClauses));
		}

		try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			int idx = 1;
			if (role != null && !role.trim().isEmpty()) {
				ps.setString(idx++, role.trim());
			}
			if (search != null && !search.trim().isEmpty()) {
				ps.setString(idx++, "%" + search.trim() + "%");
			}
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
