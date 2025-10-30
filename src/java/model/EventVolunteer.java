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
public class EventVolunteer {

    private int id;
    private int eventId;
    private int volunteerId;
    private Date applyDate;
    private String status;
    private int hour;
    private String note;

    private String organizationName;
    private String categoryName;
    private String volunteerName;

    public EventVolunteer() {
    }

    public EventVolunteer(int id, int eventId, int volunteerId, Date applyDate, String status, int hour, String note, String organizationName, String categoryName, String volunteerName) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.applyDate = applyDate;
        this.status = status;
        this.hour = hour;
        this.note = note;
        this.organizationName = organizationName;
        this.categoryName = categoryName;
        this.volunteerName = volunteerName;
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

}
