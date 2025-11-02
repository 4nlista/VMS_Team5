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
public class Feedback {
    private int id;
    private int eventId;
    private int volunteerId;
    private int rating;
    private String comment;
    private Date feedbackDate;
    private String status;
    
    
    // khai báo thêm để tiện tra cứu + hiển thị tên (dùng hay không thì tùy , khai báo vào constructor)
    private String eventTitle;  // đây là tên sự kiện
    private String volunteerName;   // tên của tình nguyện viên đánh giá
    private String organizationName;    // tên của người tổ chức

    public Feedback() {
    }

    public Feedback(int id, int eventId, int volunteerId, int rating, String comment, Date feedbackDate, String status, String eventTitle, String volunteerName, String organizationName) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.rating = rating;
        this.comment = comment;
        this.feedbackDate = feedbackDate;
        this.status = status;
        this.eventTitle = eventTitle;
        this.volunteerName = volunteerName;
        this.organizationName = organizationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
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

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    
    
}
