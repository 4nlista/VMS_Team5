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
public class ProfileVolunteer {
    // User vs Events

    private int id;
    private String images;
    private String fullName;
    private Date dob;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String jobTitle;
    private String bio;

    private int totalEvents; // tổng số sự kiện đã tham gia
    private double totalDonated;    // tổng số tiền đã donated của tất cả các sự kiện

    private String eventName;       // sự kiện đó tên là gì
    private String organizationName;    // tên người tạo ra sự kiện đó

    public ProfileVolunteer() {
    }

    public ProfileVolunteer(String images, String fullName, Date dob, String gender, String phone, String email, String address, String jobTitle, String bio, int totalEvents, double totalDonated) {
        this.images = images;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.totalEvents = totalEvents;
        this.totalDonated = totalDonated;
    }

    public ProfileVolunteer(int id, String images, String fullName, Date dob, String gender, String phone, String email, String address, String jobTitle, String bio, int totalEvents, double totalDonated) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.totalEvents = totalEvents;
        this.totalDonated = totalDonated;
    }

    public ProfileVolunteer(int id, String images, String fullName, Date dob, String gender, String phone, String email, String address, String jobTitle, String bio, int totalEvents, double totalDonated, String eventName, String organizationName) {
        this.id = id;
        this.images = images;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.totalEvents = totalEvents;
        this.totalDonated = totalDonated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }

    public double getTotalDonated() {
        return totalDonated;
    }

    public void setTotalDonated(double totalDonated) {
        this.totalDonated = totalDonated;
    }

    public String getEventName() {
        return eventName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

}
