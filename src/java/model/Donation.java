/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ADDMIN
 */
public class Donation {

    private int id;
    private int eventId;
    private int volunteerId;
    private double amount;
    private Date donateDate;
    private String status;
    private String paymentMethod;
    private String qrCode;
    private String note;

    // Thông tin bổ sung để hiển thị
    private String volunteerUsername;       // tài khoản volunteer
    private String volunteerFullName;       // tên của volunteer
    private String volunteerAvatar;        // avartar của volunteer
    private String eventTitle;              // tiêu đề sự kiện

    // Thống kê
    private double totalAmountDonated;  // tổng số tiền donate của mỗi cá nhân
    private int numberOfEventsDonated; // số lượng sự kiện donate của mỗi cá nhân
    
    // khai báo thêm thông tin của organization
    private String organizationName;
    private String emailOrganization;
    private String phoneOrganization;
    
    

    public Donation() {
    }

    public Donation(int id, int eventId, int volunteerId, double amount, Date donateDate, String status, String paymentMethod, String qrCode, String note, String volunteerUsername, String volunteerFullName, String volunteerAvatar, String eventTitle, double totalAmountDonated, int numberOfEventsDonated, String organizationName, String emailOrganization, String phoneOrganization) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.amount = amount;
        this.donateDate = donateDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.qrCode = qrCode;
        this.note = note;
        this.volunteerUsername = volunteerUsername;
        this.volunteerFullName = volunteerFullName;
        this.volunteerAvatar = volunteerAvatar;
        this.eventTitle = eventTitle;
        this.totalAmountDonated = totalAmountDonated;
        this.numberOfEventsDonated = numberOfEventsDonated;
        this.organizationName = organizationName;
        this.emailOrganization = emailOrganization;
        this.phoneOrganization = phoneOrganization;
    }
    
    

    public Donation(int id, int eventId, int volunteerId, double amount, Date donateDate, String status, String paymentMethod, String qrCode, String note, String volunteerUsername, String volunteerFullName, String eventTitle, double totalAmountDonated, int numberOfEventsDonated) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.amount = amount;
        this.donateDate = donateDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.qrCode = qrCode;
        this.note = note;
        this.volunteerUsername = volunteerUsername;
        this.volunteerFullName = volunteerFullName;
        this.eventTitle = eventTitle;
        this.totalAmountDonated = totalAmountDonated;
        this.numberOfEventsDonated = numberOfEventsDonated;
    }
    
    
    

    public Donation(int id, int eventId, int volunteerId, double amount, Date donateDate, String status, String paymentMethod, String qrCode, String note, String volunteerUsername, String volunteerFullName, String volunteerAvatar, String eventTitle, double totalAmountDonated, int numberOfEventsDonated) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.amount = amount;
        this.donateDate = donateDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.qrCode = qrCode;
        this.note = note;
        this.volunteerUsername = volunteerUsername;
        this.volunteerFullName = volunteerFullName;
        this.volunteerAvatar = volunteerAvatar;
        this.eventTitle = eventTitle;
        this.totalAmountDonated = totalAmountDonated;
        this.numberOfEventsDonated = numberOfEventsDonated;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDonateDate() {
        return donateDate;
    }

    public void setDonateDate(Date donateDate) {
        this.donateDate = donateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVolunteerUsername() {
        return volunteerUsername;
    }

    public void setVolunteerUsername(String volunteerUsername) {
        this.volunteerUsername = volunteerUsername;
    }

    public String getVolunteerFullName() {
        return volunteerFullName;
    }

    public void setVolunteerFullName(String volunteerFullName) {
        this.volunteerFullName = volunteerFullName;
    }

    public String getVolunteerAvatar() {
        return volunteerAvatar;
    }

    public void setVolunteerAvatar(String volunteerAvatar) {
        this.volunteerAvatar = volunteerAvatar;
    }
    
    

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public double getTotalAmountDonated() {
        return totalAmountDonated;
    }

    public void setTotalAmountDonated(double totalAmountDonated) {
        this.totalAmountDonated = totalAmountDonated;
    }

    public int getNumberOfEventsDonated() {
        return numberOfEventsDonated;
    }

    public void setNumberOfEventsDonated(int numberOfEventsDonated) {
        this.numberOfEventsDonated = numberOfEventsDonated;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getEmailOrganization() {
        return emailOrganization;
    }

    public void setEmailOrganization(String emailOrganization) {
        this.emailOrganization = emailOrganization;
    }

    public String getPhoneOrganization() {
        return phoneOrganization;
    }

    public void setPhoneOrganization(String phoneOrganization) {
        this.phoneOrganization = phoneOrganization;
    }

    
}
