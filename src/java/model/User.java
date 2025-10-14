package model;

import java.util.Date;

/**
 * Đại diện cho bảng Users — thông tin chi tiết của 1 volunteer.
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

    public User() {}

    public User(int id, int accountId, String fullName, Date dob, String gender,
                String phone, String email, String address,
                String avatar, String jobTitle, String bio) {
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

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
