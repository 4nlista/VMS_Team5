package model;

import java.util.Date;

/**
 * Model cho lịch sử đăng ký sự kiện của volunteer
 */
public class EventVolunteer {

    private int id;                  // ID bản ghi đăng ký
    private int eventId;             // ID sự kiện
    private String eventTitle;       // Tên sự kiện
    private String categoryName;     // Tên danh mục
    private String organizationName; // Tên tổ chức
    private int volunteerId;         // ID volunteer
    private String volunteerName;    // Tên volunteer
    private Date applyDate;          // Ngày đăng ký
    private String status;           // Trạng thái: pending, approved, rejected, cancelled
    private int hours;               // Số giờ tham gia
    private String note;             // Ghi chú

    public EventVolunteer() {
    }

    public EventVolunteer(int id, int eventId, String eventTitle, String categoryName, String organizationName,
                          int volunteerId, String volunteerName, Date applyDate, String status, int hours, String note) {
        this.id = id;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.categoryName = categoryName;
        this.organizationName = organizationName;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.applyDate = applyDate;
        this.status = status;
        this.hours = hours;
        this.note = note;
    }
    
    public EventVolunteer(int id, int eventId, int volunteerId, Date applyDate, String status, int hours, String note, String organizationName, String categoryName, String volunteerName) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.applyDate = applyDate;
        this.status = status;
        this.hours = hours;
        this.note = note;
        this.organizationName = organizationName;
        this.categoryName = categoryName;
        this.volunteerName = volunteerName;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public int getVolunteerId() { return volunteerId; }
    public void setVolunteerId(int volunteerId) { this.volunteerId = volunteerId; }

    public String getVolunteerName() { return volunteerName; }
    public void setVolunteerName(String volunteerName) { this.volunteerName = volunteerName; }

    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
