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
 * @author Admin
 */
public class OrganizationEventsDAO {

    private Connection conn;

    public OrganizationEventsDAO() {
        try {
            DBContext db = new DBContext();

            this.conn = db.getConnection(); // lấy connection từ DBContext
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // lấy top 3 sự kiện nhiều có tổng tiền donate nhiều nhất
    public List<Event> getEventsByOrganization(int organizationId) {
        List<Event> list = new ArrayList<>();
        String sql = """ 
                     SELECT * FROM Event WHERE organization_id = ?
                     """;
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, organizationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setImages(rs.getString("images"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setStartDate(rs.getDate("start_date"));
                e.setEndDate(rs.getDate("end_date"));
                e.setLocation(rs.getString("location"));
                e.setNeededVolunteers(rs.getInt("needed_volunteers"));
                e.setStatus(rs.getString("status"));
                e.setVisibility(rs.getString("visibility"));
                e.setOrganizationId(rs.getInt("organization_id"));
                e.setCategoryId(rs.getInt("category_id"));
                e.setTotalDonation(rs.getDouble("total_donation"));
                e.setOrganizationName(rs.getString("organization_name"));
                e.setCategoryName(rs.getString("category_name"));
                list.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
