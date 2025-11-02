/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Attendance {
    // dùng cho lịch sử điểm danh + tích điểm danh(org)
    private int volunteerId;
    private String volunteerName;
    private String status; 
    // khai báo thêm 
    private String eventTitle;  // tiêu đề sự kiện
    private String organizationName;    // tên người tạo sự kiện
    private Date startDate;                 // Ngày bắt đầu của sự kiện
    private Date endDate;                   // ngày kết thúc của sự kiện

    public Attendance() {
    }

    public Attendance(int volunteerId, String volunteerName, String status, String eventTitle, String organizationName) {
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.status = status;
        this.eventTitle = eventTitle;
        this.organizationName = organizationName;
    }

    public Attendance(int volunteerId, String volunteerName, String status, String eventTitle, String organizationName, Date startDate, Date endDate) {
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.status = status;
        this.eventTitle = eventTitle;
        this.organizationName = organizationName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
    
    
}
