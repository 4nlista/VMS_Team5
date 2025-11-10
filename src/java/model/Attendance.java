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
    private int eventId;
    private int volunteerId;
    private String volunteerName;
    private String status; 
    // khai báo thêm 
    private String eventTitle;  // tiêu đề sự kiện
    private String organizationName;    // tên người tạo sự kiện
    private Date startDate;                 // Ngày bắt đầu của sự kiện
    private Date endDate;                   // ngày kết thúc của sự kiện
    
    private String email;   // email của volunteer
    private String phone;   // phone của volunteer

    public Attendance() {
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
    
    

    public Attendance(int volunteerId, String volunteerName, String eventTitle, String organizationName, Date startDate) {
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.eventTitle = eventTitle;
        this.organizationName = organizationName;
        this.startDate = startDate;
    }
    
    

    public Attendance(int eventId, int volunteerId, String volunteerName, String status, String email, String phone) {
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.status = status;
        this.email = email;
        this.phone = phone;
    }

    public Attendance(int eventId, int volunteerId, String volunteerName, String status, String eventTitle, String organizationName, Date startDate, Date endDate, String email, String phone) {
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.status = status;
        this.eventTitle = eventTitle;
        this.organizationName = organizationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.email = email;
        this.phone = phone;
    }
    
    

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
    

    
    
    
}
