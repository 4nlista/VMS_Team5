package model;

import java.util.Date; // dùng java.util.Date cho model

public class Event {
    private int id;
    private String title;
    private String description;
    private Date startDate;       // java.util.Date
    private Date endDate;         // java.util.Date
    private String location;
    private int neededVolunteers;
    private String status;
    private int organizationId;
    private int categoryId;
    private double totalDonation;

    // thêm categoryName để hiển thị tên category khi join
    private String categoryName;

    public Event() {
    }

    // ===== Getters & Setters =====
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // LƯU Ý: sử dụng java.util.Date ở cả getter/setter
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

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getNeededVolunteers() {
        return neededVolunteers;
    }
    public void setNeededVolunteers(int neededVolunteers) {
        this.neededVolunteers = neededVolunteers;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrganizationId() {
        return organizationId;
    }
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getTotalDonation() {
        return totalDonation;
    }
    public void setTotalDonation(double totalDonation) {
        this.totalDonation = totalDonation;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}