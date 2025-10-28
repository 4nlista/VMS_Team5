/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class User {
    
    private int id;
    private int accountId;
    private String fullName;
    private Date dob;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String jobTitle;
    private String bio;
    
    public User() {
    }
    
    // Constructor đầy đủ
    public User(int id, int accountId, String fullName, Date dob, String gender, 
                String phone, String email, String address, String avatar, String jobTitle, String bio) {
        this.id = id;
        this.accountId = accountId;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
        this.jobTitle = jobTitle;
        this.bio = bio;
    }
    
    // Constructor không có id (dùng khi tạo mới)
    public User(int accountId, String fullName, Date dob, String gender, 
                String phone, String email, String address, String avatar, String jobTitle, String bio) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
        this.jobTitle = jobTitle;
        this.bio = bio;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getAccountId() {
        return accountId;
    }
    
    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
    
    @Override
    public String toString() {
        return "User{" 
                + "id=" + id 
                + ", accountId=" + accountId 
                + ", fullName='" + fullName + '\'' 
                + ", email='" + email + '\'' 
                + ", phone='" + phone + '\'' 
                + '}';
    }
    
}
