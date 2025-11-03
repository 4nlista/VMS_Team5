/*
 * A friendly reminder to drink enough water
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;
import model.New;
import java.sql.SQLException;

/**
 *
 * @author Mirinae
 */
public class NewsManagementDAO {

	private Connection conn;

	public NewsManagementDAO() {
		try {
			DBContext db = new DBContext();
			this.conn = db.getConnection(); // lấy connection từ DBContext
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<New> getAllNews() {
		List<New> list = new ArrayList<>();
		String sql = """
                        SELECT 
                            n.id,
                            n.title,
                            n.content,
                            n.images,
                            n.created_at,
                            n.updated_at,
                            n.organization_id,
                            n.status,
                            u.full_name AS organization_name
                        FROM News AS n
                        JOIN Users AS u 
                            ON n.organization_id = u.id
                        ORDER BY n.created_at DESC;
                     """;
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				New e = new New(
					    rs.getInt("id"),
					    rs.getString("title"),
					    rs.getString("content"),
					    rs.getString("images"),
					    rs.getDate("createdAt"),
					    rs.getDate("updatedAt"),
					    rs.getInt("organizationId"),
					    rs.getString("status"),
					    rs.getString("organizationName")
				);
				list.add(e);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public int getTotalNewsCount() {
		String sql = "SELECT COUNT(*) FROM News";
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

	public List<New> getAllNewsWithPagination(int page, int pageSize) {
		List<New> list = new ArrayList<>();
		String sql = "SELECT id, title, images, status \n"
			    + "FROM News\n"
			    + "ORDER BY u.id\n"
			    + "OFFSET ? ROWS\n"
			    + "FETCH NEXT ? ROWS ONLY";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			int offset = (page - 1) * pageSize;
			ps.setInt(1, offset);
			ps.setInt(2, pageSize);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				New news = new New();
				news.setId(rs.getInt("id"));
				news.setTitle(rs.getString("title"));
				news.setImages(rs.getString("images"));
				news.setStatus(rs.getString("status"));

				list.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<New> getNewsWithFiltersAndPagination(int page, int pageSize, String status, String search) throws SQLException {
		List<New> list = new ArrayList<>();

		if (page < 1) {
			page = 1;
		}
		int offset = (page - 1) * pageSize;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, title, images, status ");
		sql.append("FROM News ");

		List<String> whereClauses = new ArrayList<>();
		if (status != null && !status.trim().isEmpty()) {
			whereClauses.add("status = ?");
		}
		if (search != null && !search.trim().isEmpty()) {
			whereClauses.add("title LIKE ?");
		}
		if (!whereClauses.isEmpty()) {
			sql.append("WHERE ").append(String.join(" AND ", whereClauses)).append(" ");
		}

		sql.append("ORDER BY id ASC ");
		sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

		try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			int idx = 1;
			if (status != null && !status.trim().isEmpty()) {
				ps.setString(idx++, status.trim());
			}
			if (search != null && !search.trim().isEmpty()) {
				ps.setString(idx++, "%" + search.trim() + "%");
			}
			ps.setInt(idx++, offset);
			ps.setInt(idx++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					New news = new New();
					news.setId(rs.getInt("id"));
					news.setTitle(rs.getString("title"));
					news.setImages(rs.getString("images"));
					news.setStatus(rs.getString("status"));
					list.add(news);
				}
			}
		}

		return list;
	}

	public int getFilteredNewsCount(String status, String search) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM News ");

		List<String> whereClauses = new ArrayList<>();
		if (status != null && !status.trim().isEmpty()) {
			whereClauses.add("status = ?");
		}
		if (search != null && !search.trim().isEmpty()) {
			whereClauses.add("title LIKE ?");
		}
		if (!whereClauses.isEmpty()) {
			sql.append("WHERE ").append(String.join(" AND ", whereClauses));
		}

		try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			int idx = 1;
			if (status != null && !status.trim().isEmpty()) {
				ps.setString(idx++, status.trim());
			}
			if (search != null && !search.trim().isEmpty()) {
				ps.setString(idx++, "%" + search.trim() + "%");
			}

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}

		return 0;
	}
}
