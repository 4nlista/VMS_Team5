/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.VolunteerFeedbackDAO;
import model.Feedback;

/**
 * Service xử lý logic nghiệp vụ cho Feedback của Volunteer
 */
public class VolunteerFeedbackService {

    private VolunteerFeedbackDAO feedbackDAO;

    public VolunteerFeedbackService() {
        this.feedbackDAO = new VolunteerFeedbackDAO();
    }

    /**
     * Lấy thông tin feedback hoặc thông tin event để hiển thị form
     *
     * @return Feedback object (có thể đã có feedback hoặc chỉ có thông tin
     * event)
     */
    public Feedback getFeedbackOrEventInfo(int eventId, int volunteerId) {
        // Kiểm tra đã có feedback chưa
        Feedback existingFeedback = feedbackDAO.getFeedbackByEventAndVolunteer(eventId, volunteerId);

        if (existingFeedback != null) {
            // Đã có feedback → trả về để hiển thị form sửa
            return existingFeedback;
        } else {
            // Chưa có feedback → lấy thông tin event để hiển thị form tạo mới
            return feedbackDAO.getEventInfoForFeedback(eventId, volunteerId);
        }
    }

    /**
     * Kiểm tra volunteer có đủ điều kiện feedback không
     *
     * @return true nếu đủ điều kiện (approved + event started)
     */
    public boolean canVolunteerFeedback(int eventId, int volunteerId) {
        return feedbackDAO.canFeedback(eventId, volunteerId);
    }

    /**
     * Kiểm tra điều kiện feedback và trả về thông báo lỗi cụ thể
     *
     * @return null nếu OK, hoặc thông báo lỗi cụ thể
     */
    public String checkFeedbackEligibilityMessage(int eventId, int volunteerId) {
        int code = feedbackDAO.checkFeedbackEligibility(eventId, volunteerId);

        switch (code) {
            case 0:
                return null; // OK, không có lỗi
            case 1:
                return "Bạn chưa đăng ký tham gia sự kiện này!";
            case 2:
                return "Đơn đăng ký của bạn chưa được duyệt. Vui lòng đợi tổ chức phê duyệt!";
            case 3:
                return "Sự kiện chưa bắt đầu! Bạn chỉ có thể đánh giá sau khi sự kiện diễn ra.";
            default:
                return "Có lỗi xảy ra. Vui lòng thử lại sau!";
        }
    }

    /**
     * Tạo feedback mới
     *
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public boolean createFeedback(int eventId, int volunteerId, int rating, String comment) {
        // Validate dữ liệu
        if (rating < 1 || rating > 5) {
            System.out.println("Rating phải từ 1-5");
            return false;
        }

        if (comment == null || comment.trim().isEmpty()) {
            System.out.println("Comment không được để trống");
            return false;
        }

        // Kiểm tra điều kiện được feedback
        if (!canVolunteerFeedback(eventId, volunteerId)) {
            System.out.println("Volunteer chưa đủ điều kiện feedback");
            return false;
        }

        // Kiểm tra đã feedback chưa (tránh trùng)
        Feedback existing = feedbackDAO.getFeedbackByEventAndVolunteer(eventId, volunteerId);
        if (existing != null) {
            System.out.println("Đã tồn tại feedback cho event này");
            return false;
        }

        // Tạo feedback mới
        Feedback feedback = new Feedback();
        feedback.setEventId(eventId);
        feedback.setVolunteerId(volunteerId);
        feedback.setRating(rating);
        feedback.setComment(comment);

        return feedbackDAO.createFeedback(feedback);
    }

    /**
     * Cập nhật feedback đã có
     *
     * @return true nếu cập nhật thành công
     */
    public boolean updateFeedback(int eventId, int volunteerId, int rating, String comment) {
        // Validate dữ liệu
        if (rating < 1 || rating > 5) {
            System.out.println("Rating phải từ 1-5");
            return false;
        }

        if (comment == null || comment.trim().isEmpty()) {
            System.out.println("Comment không được để trống");
            return false;
        }

        // Kiểm tra feedback có tồn tại không
        Feedback existing = feedbackDAO.getFeedbackByEventAndVolunteer(eventId, volunteerId);
        if (existing == null) {
            System.out.println("Không tìm thấy feedback để cập nhật");
            return false;
        }

        // Cập nhật feedback
        Feedback feedback = new Feedback();
        feedback.setEventId(eventId);
        feedback.setVolunteerId(volunteerId);
        feedback.setRating(rating);
        feedback.setComment(comment);

        return feedbackDAO.updateFeedback(feedback);
    }

    /**
     * Kiểm tra volunteer đã feedback cho event này chưa
     *
     * @return true nếu đã feedback
     */
    public boolean hasFeedback(int eventId, int volunteerId) {
        return feedbackDAO.getFeedbackByEventAndVolunteer(eventId, volunteerId) != null;
    }
}
