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
import model.Event;
import utils.DBContext;

/**
 *
 * @author ADDMIN
 */
public class ViewEventsDAO {

    private Connection conn;

    public ViewEventsDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public List<Event> getAllEvents() {
//        List<Event> list = new ArrayList<>();
//        String sql = "SELECT id, name, description, date, location, image FROM event";
//        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                rs.setId(rs.getInt("id"));
//                rs.setName(rs.getString("name"));
//                rs.setDescription(rs.getString("description"));
//                rs.setDate(rs.getDate("date"));
//                rs.setLocation(rs.getString("location"));
//                rs.setImage(rs.getString("image"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
