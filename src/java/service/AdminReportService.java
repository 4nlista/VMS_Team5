/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AdminReportDAO;
import java.util.List;
import model.Report;

/**
 *
 * @author Admin
 */
public class AdminReportService {

    private AdminReportDAO reportDAO;

    public AdminReportService() {
        this.reportDAO = new AdminReportDAO();
    }

    /**
     * Lấy danh sách Reports có phân trang + filter + sort
     *
     * @param statusFilter: all, pending, resolved, rejected
     * @param sortOrder: newest, oldest
     * @param page: trang hiện tại
     * @param pageSize: số bản ghi mỗi trang
     * @return List<Report>
     */
    public List<Report> getAllReports(String statusFilter, String sortOrder, int page, int pageSize) {
        // Validate và set giá trị mặc định
        if (statusFilter == null || statusFilter.trim().isEmpty()) {
            statusFilter = "all";
        }

        if (sortOrder == null || (!sortOrder.equals("newest") && !sortOrder.equals("oldest"))) {
            sortOrder = "newest";
        }

        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 10;
        }

        return reportDAO.getAllReports(statusFilter, sortOrder, page, pageSize);
    }

    // Đếm tổng số Reports (để tính số trang)
    public int getTotalReports(String statusFilter) {
        if (statusFilter == null || statusFilter.trim().isEmpty()) {
            statusFilter = "all";
        }

        return reportDAO.getTotalReports(statusFilter);
    }

    // Tính tổng số trang - totalRecords: tổng số bản ghi - pageSize: số bản ghi mỗi trang
    public int getTotalPages(int totalRecords, int pageSize) {
        if (pageSize <= 0) {
            pageSize = 10;
        }

        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    // Lấy chi tiết 1 Report
    public Report getReportById(int id) {
        if (id <= 0) {
            return null;
        }
        return reportDAO.getReportById(id);
    }

    // Cập nhật trạng thái Report
    public boolean updateReportStatus(int id, String newStatus) {
        // Validate ID
        if (id <= 0) {
            return false;
        }

        // Validate status
        if (newStatus == null
                || (!newStatus.equals("pending")
                && !newStatus.equals("resolved")
                && !newStatus.equals("rejected"))) {
            return false;
        }

        return reportDAO.updateReportStatus(id, newStatus);
    }

    // Duyệt chấp nhận báo cáo (pending → resolved)
    public boolean approveReport(int id) {
        return updateReportStatus(id, "resolved");
    }

    // Từ chối báo cáo (pending → rejected)
    public boolean rejectReport(int id) {
        return updateReportStatus(id, "rejected");
    }

    //Khóa tài khoản Volunteer
    public boolean lockVolunteerAccount(int volunteerId) {
        if (volunteerId <= 0) {
            return false;
        }

        return reportDAO.lockVolunteerAccount(volunteerId);
    }

    // Lấy volunteer_id từ feedback_id
    public int getVolunteerIdByFeedbackId(int feedbackId) {
        if (feedbackId <= 0) {
            return -1;
        }

        return reportDAO.getVolunteerIdByFeedbackId(feedbackId);
    }

    // Duyệt báo cáo VÀ khóa tài khoản Volunteer
    public boolean approveReportAndLockAccount(int reportId, int feedbackId) {
        // Lấy volunteer_id từ feedback_id
        int volunteerId = getVolunteerIdByFeedbackId(feedbackId);

        if (volunteerId <= 0) {
            return false;
        }

        // Khóa tài khoản volunteer
        boolean lockSuccess = lockVolunteerAccount(volunteerId);

        // Duyệt báo cáo
        boolean approveSuccess = approveReport(reportId);

        // Chỉ thành công khi cả 2 đều OK
        return lockSuccess && approveSuccess;
    }
}
