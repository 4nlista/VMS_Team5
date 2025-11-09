/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AttendanceDAO;
import java.util.List;
import model.Attendance;

/**
 *
 * @author Admin
 */
public class AttendanceService {
     private AttendanceDAO attendanceDAO;
    
    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }
    

     // Lấy danh sách volunteer để điểm danh
    public List<Attendance> getVolunteersForAttendance(int eventId, String statusFilter) {
        return attendanceDAO.getVolunteersForAttendance(eventId, statusFilter);
    }
    

     // Cập nhật trạng thái điểm danh , check để cập nhật
    public boolean updateAttendanceStatus(int eventId, int volunteerId, String status) {
        // Validate status
        if (!status.equals("pending") && !status.equals("present") && !status.equals("absent")) {
            return false;
        }
        
        return attendanceDAO.updateAttendanceStatus(eventId, volunteerId, status);
    }
}
