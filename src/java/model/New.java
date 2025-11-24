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
public class New {

    private int id;
    private String title;
    private String content;
    private String images;
    private Date createdAt;
    private Date updatedAt;
    private int organizationId;
    private String status;  // trạng thái public/private
    // lấy thêm tên của người tổ chức
    private String organizationName;

    public New() {
    }

    public New(int id, String title, String content, String images, int organizationId, String status, String organizationName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.images = images;
        this.organizationId = organizationId;
        this.status = status;
        this.organizationName = organizationName;
    }

    public New(int id, String title, String content, String images, Date createdAt, Date updatedAt, int organizationId, String status, String organizationName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.images = images;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.organizationId = organizationId;
        this.status = status;
        this.organizationName = organizationName;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

}
