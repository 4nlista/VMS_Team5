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
public class OrganizationNotificationService {

    private NotificationDAO notificationDAO;

    public OrganizationNotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    // Lấy danh sách thông báo của organization
    public List<Notification> getNotificationsByOrganizationId(int organizationId) {
        return notificationDAO.getNotificationsByReceiverId(organizationId);
    }

    // Lấy danh sách thông báo với phân trang và sắp xếp
    public List<Notification> getNotificationsPaginated(int organizationId, int page, int pageSize, String sortOrder) {
        return notificationDAO.getNotificationsByReceiverIdPaginated(organizationId, page, pageSize, sortOrder);
    }

    ///Đếm tổng số thông báo
    public int getTotalNotifications(int organizationId) {
        return notificationDAO.getTotalNotifications(organizationId);
    }

    // Đánh dấu 1 thông báo đã đọc
    public boolean markAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    // Đánh dấu tất cả thông báo đã đọc
    public boolean markAllAsRead(int organizationId) {
        return notificationDAO.markAllAsRead(organizationId);
    }

    // Đếm số thông báo chưa đọc
    public int getUnreadCount(int organizationId) {
        return notificationDAO.getUnreadCount(organizationId);
    }
}
