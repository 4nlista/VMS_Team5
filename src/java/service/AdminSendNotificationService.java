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
        System.out.println("🔍 [Service] sendIndividualNotification called");
        System.out.println("   - adminId: " + adminId);
        System.out.println("   - receiverId: " + receiverId);
        System.out.println("   - message length: " + (message != null ? message.trim().length() : 0));
        
        // Validate message
        if (message == null || message.trim().length() < 10 || message.trim().length() > 500) {
            System.out.println("❌ [Service] Message validation FAILED");
            return false;
        }
        System.out.println("✅ [Service] Message validation passed");
        
        // Kiểm tra receiver tồn tại
        Account receiver = accountDAO.getAccountById(receiverId);
        if (receiver == null) {
            System.out.println("❌ [Service] Receiver account NOT FOUND");
            return false;
        }
        System.out.println("✅ [Service] Receiver account found: " + receiver.getUsername());
        
        // Tạo notification
        Notification notification = new Notification();
        notification.setSenderId(adminId);
        notification.setReceiverId(receiverId);
        notification.setMessage(message.trim());
        notification.setType("system"); // Loại thông báo từ admin - dùng type 'system'
        notification.setEventId(0); // Admin gửi không liên quan event
        
        System.out.println("📤 [Service] Calling NotificationDAO.insertNotification...");
        boolean result = notificationDAO.insertNotification(notification);
        System.out.println((result ? "✅" : "❌") + " [Service] Insert result: " + result);
        
        return result;
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
            // Bỏ qua chính mình - Admin ko thể gửi cho chính mình
            if (recipient.getId() == adminId) {
                continue;
            }
            Notification notification = new Notification();
            notification.setSenderId(adminId);
            notification.setReceiverId(recipient.getId());
            notification.setMessage(message.trim());
            notification.setType("system"); // Loại thông báo từ admin - dùng type 'system'
            notification.setEventId(0);
            
            if (notificationDAO.insertNotification(notification)) {
                successCount++;
            }
        }
        
        return successCount;
    }
    
    // Đếm số lượng recipients theo filter (không bao gồm chính admin đang gửi)
    public int countRecipients(List<String> roles, String statusFilter, int adminId) {
        List<Account> recipients = accountDAO.getAccountsByRolesAndStatus(roles, statusFilter);
        // Trừ đi 1 nếu admin đang gửi nằm trong danh sách
        long count = recipients.stream().filter(acc -> acc.getId() != adminId).count();
        return (int) count;
    }
}
