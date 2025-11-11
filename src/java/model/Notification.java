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
public class Notification {
    private int id;
    private int senderId;
    private int receiverId;
    private String message;
    private String type; // 'reminder','approval','donation','system'
    private Date createdAt;
    private boolean isRead; // 0 = chưa đọc, 1 = đã đọc
    private Integer relatedEventId;
    
    // THÊM CÁC TRƯỜNG JOIN:
    private String senderName;    // full_name từ Users
    private String receiverName;  // full_name từ Users
    private String eventTitle;    // title từ Events (nếu có relatedEventId)

    public Notification() {
    }

    public Notification(int id, int senderId, int receiverId, String message, String type, Date createdAt, boolean isRead, Integer relatedEventId, String senderName, String receiverName, String eventTitle) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.relatedEventId = relatedEventId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.eventTitle = eventTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getRelatedEventId() {
        return relatedEventId;
    }

    public void setRelatedEventId(Integer relatedEventId) {
        this.relatedEventId = relatedEventId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    
    
    
    
}
