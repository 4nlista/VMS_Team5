package model;

import java.util.Date;

public class Event {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private int organizationId;
    private String status; // active / inactive / finished / etc.

    public Event() {}

    public Event(int id, String name, String description, Date startDate, Date endDate, String location, int organizationId, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.organizationId = organizationId;
        this.status = status;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getOrganizationId() { return organizationId; }
    public void setOrganizationId(int organizationId) { this.organizationId = organizationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
