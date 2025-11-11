/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ADDMIN
 */
public class Event {

    private int id;                         // ID sự kiện
    private String images;                  // anh su kien
    private String title;                   // Tiêu đề
    private String description;             // Mô tả
    private Date startDate;                 // Ngày bắt đầu
    private Date endDate;                   // Ngày kết thúc
    private String location;                // Địa điểm
    private int neededVolunteers;           // Số lượng tình nguyện viên cần
    private String status;                  // Trạng thái (active, inactive, closed)
    private String visibility;
    private int organizationId;             // ID tổ chức
    private int categoryId;             // ID danh mục (có thể null)
    private double totalDonation;
    private String organizationName;
    private String categoryName;

    // khai báo thêm
    private double donateVolunteer;  // tổng số tiền donate

    private boolean hasApplied;  // Đã đăng ký chưa
    private boolean hasDonated;  // Đã donate chưa
    private int rejectedCount;  // biến đếm số lần đăng kí
    private boolean isFull;

    public Event() {
    }

    public Event(int id, String title, String description, Date startDate, Date endDate, String location, int neededVolunteers, String status, String visibility, int organizationId, int categoryId, double totalDonation, String organizationName, String categoryName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.neededVolunteers = neededVolunteers;
        this.status = status;
        this.visibility = visibility;
        this.organizationId = organizationId;
        this.categoryId = categoryId;
        this.totalDonation = totalDonation;
        this.organizationName = organizationName;
        this.categoryName = categoryName;
    }

    public Event(int id, String images, String title, String description, Date startDate, Date endDate, String location, int neededVolunteers, String status, String visibility, int organizationId, int categoryId, double totalDonation, String organizationName, String categoryName) {
        this.id = id;
        this.images = images;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.neededVolunteers = neededVolunteers;
        this.status = status;
        this.visibility = visibility;
        this.organizationId = organizationId;
        this.categoryId = categoryId;
        this.totalDonation = totalDonation;
        this.organizationName = organizationName;
        this.categoryName = categoryName;
    }

    public Event(int id, String images, String title, String description, Date startDate, Date endDate, String location, int neededVolunteers, String status, String visibility, int organizationId, int categoryId, double totalDonation, String organizationName, String categoryName, double donateVolunteer) {
        this.id = id;
        this.images = images;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.neededVolunteers = neededVolunteers;
        this.status = status;
        this.visibility = visibility;
        this.organizationId = organizationId;
        this.categoryId = categoryId;
        this.totalDonation = totalDonation;
        this.organizationName = organizationName;
        this.categoryName = categoryName;
        this.donateVolunteer = donateVolunteer;
    }

    public Event(int id, String images, String title, String description, Date startDate, Date endDate, String location, int neededVolunteers, String status, String visibility, int organizationId, int categoryId, double totalDonation, String organizationName, String categoryName, double donateVolunteer, boolean hasApplied, boolean hasDonated, int rejectedCount, boolean isFull) {
        this.id = id;
        this.images = images;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.neededVolunteers = neededVolunteers;
        this.status = status;
        this.visibility = visibility;
        this.organizationId = organizationId;
        this.categoryId = categoryId;
        this.totalDonation = totalDonation;
        this.organizationName = organizationName;
        this.categoryName = categoryName;
        this.donateVolunteer = donateVolunteer;
        this.hasApplied = hasApplied;
        this.hasDonated = hasDonated;
        this.rejectedCount = rejectedCount;
        this.isFull = isFull;
    }

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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


    public double getDonateVolunteer() {
        return donateVolunteer;
    }

    public void setDonateVolunteer(double donateVolunteer) {
        this.donateVolunteer = donateVolunteer;
    }

    public boolean isHasApplied() {
        return hasApplied;
    }

    public void setHasApplied(boolean hasApplied) {
        this.hasApplied = hasApplied;
    }

    public boolean isHasDonated() {
        return hasDonated;
    }

    public void setHasDonated(boolean hasDonated) {
        this.hasDonated = hasDonated;
    }

    public int getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public boolean isIsFull() {
        return isFull;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

   

}
