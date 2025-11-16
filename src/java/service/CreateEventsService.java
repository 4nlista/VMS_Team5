/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CreateEventsDAO;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import model.Event;

/**
 *
 * @author Admin
 */
public class CreateEventsService {

    private CreateEventsDAO createEventsDAO;

    public CreateEventsService() {
        createEventsDAO = new CreateEventsDAO();
    }

    private Date now = new Date();

    public String createEvent(Event e) {
        if (e == null) {
            return "Thông tin sự kiện không hợp lệ!";
        }

        // Kiểm tra các trường bắt buộc
        if (e.getTitle() == null || e.getTitle().isEmpty()) {
            return "Tiêu đề sự kiện không được để trống!";
        }
        if (e.getStartDate() == null || e.getEndDate() == null) {
            return "Ngày bắt đầu và kết thúc không được để trống!";
        }

        // Validate 1: Ngày bắt đầu phải trước ngày kết thúc và ko đc bắt đầu ở quá khứ
        if (e.getStartDate().after(e.getEndDate())) {
            return "Ngày bắt đầu phải trước ngày kết thúc!";
        }

        // Validate 1.5: Ngày bắt đầu không được ở quá khứ
        if (e.getStartDate().before(now)) {
            return "Ngày bắt đầu phải trước ngày kết thúc!";
        }

        // Validate 2: Sự kiện không được kéo dài quá 7 ngày
        long diffInMillis = e.getEndDate().getTime() - e.getStartDate().getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
        if (diffInDays > 7) {
            return "Sự kiện không được kéo dài quá 7 ngày!";
        }

        // Validate 3: Kiểm tra trùng thời gian với sự kiện khác của cùng organization
        if (createEventsDAO.hasOverlappingEvent(e.getOrganizationId(), e.getStartDate(), e.getEndDate())) {
            return "Bạn đã có sự kiện khác trùng thời gian! Vui lòng chọn thời gian khác.";
        }

        if (e.getNeededVolunteers() <= 0) {
            return "Số lượng tình nguyện viên phải lớn hơn 0!";
        }
        if (e.getCategoryId() <= 0) {
            return "Vui lòng chọn loại sự kiện!";
        }

        // Set mặc định
        if (e.getStatus() == null) {
            e.setStatus("active");
        }
        if (e.getVisibility() == null) {
            e.setVisibility("public");
        }

        // Thực hiện tạo event
        boolean result = createEventsDAO.createEvent(e);

        if (result) {
            return "success";
        } else {
            return "Có lỗi xảy ra khi tạo sự kiện!";
        }
    }

    // Validate riêng: Kiểm tra thời gian có trùng không
    public boolean hasOverlappingEvent(int organizationId, java.util.Date startDate, java.util.Date endDate) {
        return createEventsDAO.hasOverlappingEvent(organizationId, startDate, endDate);
    }

}
