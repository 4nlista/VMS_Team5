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
    private int id, account_id;
    private String full_name, gender, phone, email, address, avatar, job_title, bio;
    private Date dob;
    private Account account;

	public User() {
	}

	public User(int id, int account_id, String full_name, String gender, String avatar, Account account) {
		this.id = id;
		this.account_id = account_id;
		this.full_name = full_name;
		this.gender = gender;
		this.avatar = avatar;
		this.account = account;
	}

	public User(int id, int account_id, Account account,String full_name, String gender, String phone, String email, String address, String avatar, String job_title, String bio, Date dob) {
		this.id = id;
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

	public int getId() {
		return id;
	}
	
	public int getAccountId() {
		return account_id;
	}

	public Account getAccount() {
		return account;
	}

	public String getFull_name() {
		return full_name;
	}

	public String getGender() {
		return gender;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getJob_title() {
		return job_title;
	}

	public String getBio() {
		return bio;
	}

	public Date getDob() {
		return dob;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setAccountId(int account_id) {
		this.account_id = account_id;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id 
			    + ", account_id=" + account_id
			    + ", username=" + account
			    + ", full_name=" + full_name 
			    + ", gender=" + gender 
			    + ", phone=" + phone 
			    + ", email=" + email 
			    + ", address=" + address 
			    + ", avatar=" + avatar 
			    + ", job_title=" + job_title 
			    + ", bio=" + bio 
			    + ", dob=" + dob + '}';
	}
}
