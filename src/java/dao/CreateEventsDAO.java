/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import model.Event;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class CreateEventsDAO {

    private Connection conn;

    public CreateEventsDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tạo sự kiện mới
    public boolean createEvent(Event e) {
        if (e == null) {
            return false;
        }

        String sql = """
                INSERT INTO Events (images, title, description, startDate, endDate, location, 
                neededVolunteers, status, visibility, organizationId, categoryId, totalDonation) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getImages());
            ps.setString(2, e.getTitle());
            ps.setString(3, e.getDescription());

            // Convert java.util.Date sang java.sql.Date bên trong DAO
            Date start = e.getStartDate();
            Date end = e.getEndDate();
            if (start == null || end == null) {
                return false;
            }

            ps.setDate(4, new java.sql.Date(start.getTime()));
            ps.setDate(5, new java.sql.Date(end.getTime()));

            ps.setString(6, e.getLocation());
            ps.setInt(7, e.getNeededVolunteers());
            ps.setString(8, e.getStatus());
            ps.setString(9, e.getVisibility());
            ps.setInt(10, e.getOrganizationId());

            // categoryId bắt buộc không null
            if (e.getCategoryId() <= 0) {
                return false;
            }
            ps.setInt(11, e.getCategoryId());

            ps.setDouble(12, e.getTotalDonation());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
