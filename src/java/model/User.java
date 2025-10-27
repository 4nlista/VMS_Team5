package model;

import java.util.Date;

public class User {

    private int id, account_id;
    private String full_name, gender, phone, email, address, avatar, job_title, bio;
    private Date dob;  // Sử dụng java.util.Date
    private Account account;

    // Constructor mặc định
    public User() {
    }

    // Constructor với Account
    public User(int id, int account_id, String full_name, String gender, String avatar, Account account) {
        this.id = id;
        this.account_id = account_id;
        this.full_name = full_name;
        this.gender = gender;
        this.avatar = avatar;
        this.account = account;
    }

    // Constructor đầy đủ với tất cả các thuộc tính
    public User(int id, int account_id, Account account, String full_name, String gender, String phone, String email, String address, String avatar, String job_title, String bio, Date dob) {
        this.id = id;
        this.account_id = account_id;
        this.account = account;
        this.full_name = full_name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
        this.job_title = job_title;
        this.bio = bio;
        this.dob = dob;
    }

    // Getter và setter cho các thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return account_id;
    }

    public void setAccountId(int account_id) {
        this.account_id = account_id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", account_id=" + account_id
                + ", account=" + account
                + ", full_name='" + full_name + '\''
                + ", gender='" + gender + '\''
                + ", phone='" + phone + '\''
                + ", email='" + email + '\''
                + ", address='" + address + '\''
                + ", avatar='" + avatar + '\''
                + ", job_title='" + job_title + '\''
                + ", bio='" + bio + '\''
                + ", dob=" + dob
                + '}';
    }
}
