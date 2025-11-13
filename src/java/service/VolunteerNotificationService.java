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
public class VolunteerNotificationService {

    private NotificationDAO notificationDAO;

    public VolunteerNotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    // Lấy danh sách thông báo của volunteer
    public List<Notification> getNotificationsByVolunteerId(int volunteerId) {
        return notificationDAO.getNotificationsByReceiverId(volunteerId);
    }
    
    // Lấy danh sách thông báo với phân trang và sắp xếp
    public List<Notification> getNotificationsPaginated(int volunteerId, int page, int pageSize, String sortOrder) {
        return notificationDAO.getNotificationsByReceiverIdPaginated(volunteerId, page, pageSize, sortOrder);
    }
    // Đếm tổng số thông báo
    public int getTotalNotifications(int volunteerId) {
        return notificationDAO.getTotalNotifications(volunteerId);
    }

    // Đánh dấu 1 thông báo đã đọc
    public boolean markAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    // Đánh dấu tất cả thông báo đã đọc
    public boolean markAllAsRead(int volunteerId) {
        return notificationDAO.markAllAsRead(volunteerId);
    }

    // Đếm số thông báo chưa đọc
    public int getUnreadCount(int volunteerId) {
        return notificationDAO.getUnreadCount(volunteerId);
    }
}
