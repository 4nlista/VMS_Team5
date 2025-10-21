package model;

import java.util.Date;

public class Event {
    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private int neededVolunteers;
    private String status;
    private int organizationId;
    private int categoryId;
    private double totalDonation;

    public Event() {}

    public Event(int id, String title, String description, Date startDate, Date endDate,
                 String location, int neededVolunteers, String status,
                 int organizationId, int categoryId, double totalDonation) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.neededVolunteers = neededVolunteers;
        this.status = status;
        this.organizationId = organizationId;
        this.categoryId = categoryId;
        this.totalDonation = totalDonation;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getNeededVolunteers() { return neededVolunteers; }
    public void setNeededVolunteers(int neededVolunteers) { this.neededVolunteers = neededVolunteers; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getOrganizationId() { return organizationId; }
    public void setOrganizationId(int organizationId) { this.organizationId = organizationId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getTotalDonation() { return totalDonation; }
    public void setTotalDonation(double totalDonation) { this.totalDonation = totalDonation; }
}
