package model;

import java.util.Date;

public class EventVolunteer {
    private int id;
    private int eventId;
    private int volunteerId;
    private Date applyDate;
    private String status;
    private int hours;
    private String note;

    public EventVolunteer() {}

    public EventVolunteer(int eventId, int volunteerId, String status) {
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.status = status;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getVolunteerId() { return volunteerId; }
    public void setVolunteerId(int volunteerId) { this.volunteerId = volunteerId; }

    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
