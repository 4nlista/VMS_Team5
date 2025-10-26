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
import model.New;
import utils.DBContext;

/**
 *
 * @author ADDMIN
 */
public class NewDAO {

    private Connection conn;

    public NewDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // lấy danh sách tất cả các Bài viết đang xuất bản
    public List<New> getAllPostNews() {
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
                        WHERE n.status = 'published'
                        ORDER BY n.created_at DESC;
                     """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                New e = new New(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("images"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getInt("organization_id"),
                        rs.getString("status"),
                        rs.getString("organization_name")
                );
                list.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // lấy danh sách top 3 bài viết mới nhất
    public List<New> getTop3PostNews() {
        List<New> list = new ArrayList<>();
        String sql = """
                        SELECT TOP 3 
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
                        WHERE n.status = 'published'
                        ORDER BY n.created_at DESC;
                     """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                New e = new New(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("images"),
                        rs.getTimestamp("create_at"),
                        rs.getTimestamp("update_at"),
                        rs.getInt("organization_id"),
                        rs.getString("status"),
                        rs.getString("organization_name")
                );
                list.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
