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
public class Donation {

    private int id;
    private int eventId;
    private int volunteerId;
    private double amount;
    private Date donateDate;
    private String status;
    private String paymentMethod;
    private String paymentTxnRef;  // Mã tham chiếu giao dịch (dùng lưu txn ref từ cổng thanh toán như VNPay)
    private String note;

    // Thông tin bổ sung để hiển thị
    private String volunteerUsername;       // tài khoản volunteer
    private String volunteerFullName;       // tên của volunteer
    private String volunteerAvatar;        // avartar của volunteer
    private String volunteerEmail;
    private String volunteerPhone;
    private String eventTitle;              // tiêu đề sự kiện

    // Thống kê
    private double totalAmountDonated;  // tổng số tiền donate của mỗi cá nhân
    private int numberOfEventsDonated; // số lượng sự kiện donate của mỗi cá nhân
    
    // khai báo thêm thông tin của organization
    private String organizationName;
    private String emailOrganization;
    private String phoneOrganization;
    
    // Thông tin của donors 
    private Integer donorId;
    private String donorType;
    private String donorFullName;
    private String donorPhone;
    private String donorEmail;
    private Boolean donorAnonymous;
    

    public Donation() {
    }

    public Donation(int id, int eventId, int volunteerId, double amount, Date donateDate, String status, String paymentMethod, String paymentTxnRef, String note, String volunteerUsername, String volunteerFullName, String eventTitle, double totalAmountDonated, int numberOfEventsDonated) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.amount = amount;
        this.donateDate = donateDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentTxnRef = paymentTxnRef;
        this.note = note;
        this.volunteerUsername = volunteerUsername;
        this.volunteerFullName = volunteerFullName;
        this.eventTitle = eventTitle;
        this.totalAmountDonated = totalAmountDonated;
        this.numberOfEventsDonated = numberOfEventsDonated;
    }
    
    
    

    public Donation(int id, int eventId, int volunteerId, double amount, Date donateDate, String status, String paymentMethod, String paymentTxnRef, String note, String volunteerUsername, String volunteerFullName, String volunteerAvatar, String eventTitle, double totalAmountDonated, int numberOfEventsDonated) {
        this.id = id;
        this.eventId = eventId;
        this.volunteerId = volunteerId;
        this.amount = amount;
        this.donateDate = donateDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentTxnRef = paymentTxnRef;
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

    public String getPaymentTxnRef() {
        return paymentTxnRef;
    }

    public void setPaymentTxnRef(String paymentTxnRef) {
        this.paymentTxnRef = paymentTxnRef;
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

    public String getVolunteerEmail() {
        return volunteerEmail;
    }

    public void setVolunteerEmail(String volunteerEmail) {
        this.volunteerEmail = volunteerEmail;
    }

    public String getVolunteerPhone() {
        return volunteerPhone;
    }

    public void setVolunteerPhone(String volunteerPhone) {
        this.volunteerPhone = volunteerPhone;
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

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public String getDonorType() {
        return donorType;
    }

    public void setDonorType(String donorType) {
        this.donorType = donorType;
    }

    public String getDonorFullName() {
        return donorFullName;
    }

    public void setDonorFullName(String donorFullName) {
        this.donorFullName = donorFullName;
    }

    public String getDonorPhone() {
        return donorPhone;
    }

    public void setDonorPhone(String donorPhone) {
        this.donorPhone = donorPhone;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    public Boolean getDonorAnonymous() {
        return donorAnonymous;
    }

    public void setDonorAnonymous(Boolean donorAnonymous) {
        this.donorAnonymous = donorAnonymous;
    }

    public String getDisplayDonorName() {
        if ("guest".equalsIgnoreCase(donorType)) {
            if (Boolean.TRUE.equals(donorAnonymous)) {
                return "Không rõ";
            }
            return (donorFullName != null && !donorFullName.trim().isEmpty()) ? donorFullName : "Khách ẩn danh";
        }
        return (volunteerFullName != null && !volunteerFullName.trim().isEmpty())
                ? volunteerFullName
                : (donorFullName != null ? donorFullName : "Không rõ");
    }

    public String getDisplayDonorPhone() {
        if ("guest".equalsIgnoreCase(donorType)) {
            if (Boolean.TRUE.equals(donorAnonymous)) {
                return "Không rõ";
            }
            return (donorPhone != null && !donorPhone.trim().isEmpty()) ? donorPhone : "Không rõ";
        }
        return (volunteerPhone != null && !volunteerPhone.trim().isEmpty()) ? volunteerPhone : "Không rõ";
    }

    public String getDisplayDonorEmail() {
        if ("guest".equalsIgnoreCase(donorType)) {
            if (Boolean.TRUE.equals(donorAnonymous)) {
                return "Không rõ";
            }
            return (donorEmail != null && !donorEmail.trim().isEmpty()) ? donorEmail : "Không rõ";
        }
        return (volunteerEmail != null && !volunteerEmail.trim().isEmpty()) ? volunteerEmail : "Không rõ";
    }

    
}
