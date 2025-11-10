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
public class Report {
    private int id;
    private int feedbackId;
    private int organizationId;
    private String reason;
    private String status;
    private Date createdAt;
    
    // khai báo thêm tùy chọn dùng
    private String organizationName;
    private String username;  // tài khoản tình nguyện viên bình luận;
    private String volunteerName;   // tên của tình nguyện viên đánh giá
    private String comment;     // comment của tình nguyện viên để admin sẽ check
    private int rating;         // điểm đánh giá của tình nguyện viên
    
    public Report() {
    }

    public Report(int id, int feedbackId, int organizationId, String reason, String status, Date createdAt, String organizationName, String username, String volunteerName, String comment, int rating) {
        this.id = id;
        this.feedbackId = feedbackId;
        this.organizationId = organizationId;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
        this.organizationName = organizationName;
        this.username = username;
        this.volunteerName = volunteerName;
        this.comment = comment;
        this.rating = rating;
    }

    


    public Report(int id, int feedbackId, int organizationId, String reason, String status, Date createdAt, String organizationName) {
        this.id = id;
        this.feedbackId = feedbackId;
        this.organizationId = organizationId;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
        this.organizationName = organizationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
    
    
}
