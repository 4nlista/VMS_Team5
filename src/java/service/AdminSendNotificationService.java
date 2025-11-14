/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.NotificationDAO;
import dao.AccountDAO;
import model.Notification;
import model.Account;
import java.util.List;

/**
 * Service xử lý gửi thông báo từ Admin
 * @author Admin
 */
public class AdminSendNotificationService {
    
    private NotificationDAO notificationDAO;
    private AccountDAO accountDAO;
    
    public AdminSendNotificationService() {
        this.notificationDAO = new NotificationDAO();
        this.accountDAO = new AccountDAO();
    }
    
    // Gửi thông báo cá nhân từ Admin đến 1 account
    public boolean sendIndividualNotification(int adminId, int receiverId, String message) {
        // Validate message
        if (message == null || message.trim().length() < 10 || message.trim().length() > 500) {
            return false;
        }
        
        // Kiểm tra receiver tồn tại
        Account receiver = accountDAO.getAccountById(receiverId);
        if (receiver == null) {
            return false;
        }
        
        // Tạo notification
        Notification notification = new Notification();
        notification.setSenderId(adminId);
        notification.setReceiverId(receiverId);
        notification.setMessage(message.trim());
        notification.setType("admin_announcement"); // Loại thông báo từ admin
        notification.setEventId(0); // Admin gửi không liên quan event
        
        return notificationDAO.insertNotification(notification);
    }
    
    // Gửi thông báo chung cho nhiều accounts (filter theo roles và status)
    public int sendBulkNotification(int adminId, String message, List<String> roles, String statusFilter) {
        // Validate message
        if (message == null || message.trim().length() < 10 || message.trim().length() > 1000) {
            return 0;
        }
        
        // Lấy danh sách accounts theo filter
        List<Account> recipients = accountDAO.getAccountsByRolesAndStatus(roles, statusFilter);
        
        int successCount = 0;
        for (Account recipient : recipients) {
            Notification notification = new Notification();
            notification.setSenderId(adminId);
            notification.setReceiverId(recipient.getId());
            notification.setMessage(message.trim());
            notification.setType("admin_announcement");
            notification.setEventId(0);
            
            if (notificationDAO.insertNotification(notification)) {
                successCount++;
            }
        }
        
        return successCount;
    }
    
    // Đếm số lượng recipients theo filter
    public int countRecipients(List<String> roles, String statusFilter) {
        List<Account> recipients = accountDAO.getAccountsByRolesAndStatus(roles, statusFilter);
        return recipients.size();
    }
}
