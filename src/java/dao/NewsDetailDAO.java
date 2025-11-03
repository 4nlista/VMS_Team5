/*
 * A friendly reminder to drink enough water
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.New;
import utils.DBContext;

/**
 *
 * @author Mirinae
 */
public class NewsDetailDAO {
	
	private Connection conn;

	public NewsDetailDAO() {
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
}
