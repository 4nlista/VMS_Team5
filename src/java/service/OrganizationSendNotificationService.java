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
public class OrganizationSendNotificationService {

    private NotificationDAO notificationDAO;

    public OrganizationSendNotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    //Gửi thông báo cá nhân cho 1 volunteer

    public String sendIndividualNotification(int senderId, int eventId, int volunteerId,
            String message, String type) {
        // Validate 1: Event chưa kết thúc
        if (!notificationDAO.isEventActive(eventId)) {
            return "Sự kiện đã kết thúc, không thể gửi thông báo!";
        }

        // Validate 2: Event có volunteer
        if (!notificationDAO.hasApprovedVolunteers(eventId)) {
            return "Sự kiện chưa có tình nguyện viên nào tham gia!";
        }

        // Validate 3: Message không rỗng
        if (message == null || message.trim().isEmpty()) {
            return "Nội dung thông báo không được để trống!";
        }

        // Tạo notification
        Notification notification = new Notification();
        notification.setSenderId(senderId);
        notification.setReceiverId(volunteerId);
        notification.setMessage(message.trim());
        notification.setType(type);
        notification.setEventId(eventId);

        // Insert
        boolean result = notificationDAO.insertNotification(notification);

        if (result) {
            return "Gửi thông báo thành công!";
        } else {
            return "Gửi thông báo thất bại!";
        }
    }

    // Gửi thông báo chung cho tất cả volunteer của event
    public String sendAllNotification(int senderId, int eventId, String message, String type) {
        // Validate 1: Event chưa kết thúc
        if (!notificationDAO.isEventActive(eventId)) {
            return "Sự kiện đã kết thúc, không thể gửi thông báo!";
        }

        // Validate 2: Event có volunteer
        if (!notificationDAO.hasApprovedVolunteers(eventId)) {
            return "Sự kiện chưa có tình nguyện viên nào tham gia!";
        }

        // Validate 3: Message không rỗng
        if (message == null || message.trim().isEmpty()) {
            return "Nội dung thông báo không được để trống!";
        }

        // Lấy danh sách volunteer approved
        List<Integer> volunteerIds = notificationDAO.getApprovedVolunteerIds(eventId);

        if (volunteerIds.isEmpty()) {
            return "Không có tình nguyện viên nào để gửi!";
        }

        // Gửi cho từng volunteer
        int successCount = 0;
        for (Integer volunteerId : volunteerIds) {
            Notification notification = new Notification();
            notification.setSenderId(senderId);
            notification.setReceiverId(volunteerId);
            notification.setMessage(message.trim());
            notification.setType(type);
            notification.setEventId(eventId);

            if (notificationDAO.insertNotification(notification)) {
                successCount++;
            }
        }

        return "Đã gửi thông báo thành công cho " + successCount + "/" + volunteerIds.size() + " tình nguyện viên!";
    }
}
