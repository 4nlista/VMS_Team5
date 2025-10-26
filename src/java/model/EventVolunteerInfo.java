/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

public class EventVolunteerInfo {
    private int applyId;
    private String eventTitle;
    private String volunteerName;
    private String volunteerEmail;
    private String status;
    private Timestamp applyDate;

    // Getters & Setters
    public int getApplyId() { return applyId; }
    public void setApplyId(int applyId) { this.applyId = applyId; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public String getVolunteerName() { return volunteerName; }
    public void setVolunteerName(String volunteerName) { this.volunteerName = volunteerName; }

    public String getVolunteerEmail() { return volunteerEmail; }
    public void setVolunteerEmail(String volunteerEmail) { this.volunteerEmail = volunteerEmail; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getApplyDate() { return applyDate; }
    public void setApplyDate(Timestamp applyDate) { this.applyDate = applyDate; }
}
