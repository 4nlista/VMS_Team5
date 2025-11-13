/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.NotificationDAO;
import java.util.List;
import model.Notification;

/**
 *
 * @author Admin
 */
public class AdminNotificationService {

    private NotificationDAO notificationDAO;

    public AdminNotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    //Lấy danh sách thông báo của admin
    public List<Notification> getNotificationsByAdminId(int adminId) {
        return notificationDAO.getNotificationsByReceiverId(adminId);
    }

    //Lấy danh sách thông báo với phân trang và sắp xếp
    public List<Notification> getNotificationsPaginated(int adminId, int page, int pageSize, String sortOrder) {
        return notificationDAO.getNotificationsByReceiverIdPaginated(adminId, page, pageSize, sortOrder);
    }

    // Đếm tổng số thông báo
    public int getTotalNotifications(int adminId) {
        return notificationDAO.getTotalNotifications(adminId);
    }

    // Đánh dấu 1 thông báo đã đọc
    public boolean markAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    // Đánh dấu tất cả thông báo đã đọc
    public boolean markAllAsRead(int adminId) {
        return notificationDAO.markAllAsRead(adminId);
    }

    // Đếm số thông báo chưa đọc
    public int getUnreadCount(int adminId) {
        return notificationDAO.getUnreadCount(adminId);
    }
}
