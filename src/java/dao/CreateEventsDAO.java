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
        String sql = "INSERT INTO Events (title, description, images, location, status, visibility, "
                + "category_id, organization_id, needed_volunteers, start_date, end_date, total_donation) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getTitle());                    // title
            ps.setString(2, e.getDescription());              // description
            ps.setString(3, e.getImages());                   // images
            ps.setString(4, e.getLocation());                 // location
            ps.setString(5, e.getStatus());                   // status
            ps.setString(6, e.getVisibility());               // visibility
            ps.setInt(7, e.getCategoryId());                  // category_id
            ps.setInt(8, e.getOrganizationId());              // organization_id
            ps.setInt(9, e.getNeededVolunteers());            // needed_volunteers

            // Convert sang java.sql.Timestamp
            Date start = e.getStartDate();
            Date end = e.getEndDate();
            if (start == null || end == null) {
                return false;
            }
            ps.setTimestamp(10, new java.sql.Timestamp(start.getTime())); // start_date
            ps.setTimestamp(11, new java.sql.Timestamp(end.getTime()));   // end_date
            ps.setDouble(12, e.getTotalDonation());              // total_donation

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
