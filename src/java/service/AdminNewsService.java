/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AdminNewsDAO;
import java.util.List;
import model.New;

/**
 *
 * @author Admin
 */
public class AdminNewsService {

    private AdminNewsDAO newsDAO;

    public AdminNewsService() {
        this.newsDAO = new AdminNewsDAO();
    }

    // Lấy danh sách News có phân trang + filter + sort
    public List<New> getAllNews(String statusFilter, String sortOrder, int page, int pageSize) {
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

        return newsDAO.getAllNews(statusFilter, sortOrder, page, pageSize);
    }

    // Đếm tổng số bài News (để tính số trang)
    public int getTotalNews(String statusFilter) {
        if (statusFilter == null || statusFilter.trim().isEmpty()) {
            statusFilter = "all";
        }

        return newsDAO.getTotalNews(statusFilter);
    }

    // Tính tổng số trang
    public int getTotalPages(int totalRecords, int pageSize) {
        if (pageSize <= 0) {
            pageSize = 10;
        }

        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    // Lấy chi tiết 1 bài News
    public New getNewsById(int id) {
        if (id <= 0) {
            return null;
        }

        return newsDAO.getNewsById(id);
    }

    // Cập nhật trạng thái News
    public boolean updateNewsStatus(int id, String newStatus) {
        // Validate ID
        if (id <= 0) {
            return false;
        }

        // Validate status
        if (newStatus == null
                || (!newStatus.equals("pending")
                && !newStatus.equals("published")
                && !newStatus.equals("rejected")
                && !newStatus.equals("hidden"))) {
            return false;
        }

        return newsDAO.updateNewsStatus(id, newStatus);
    }

    // Duyệt bài (pending → published)
    public boolean approveNews(int id) {
        return updateNewsStatus(id, "published");
    }

    //Từ chối bài (pending → rejected)
    public boolean rejectNews(int id) {
        return updateNewsStatus(id, "rejected");
    }

    // Ẩn bài (published → hidden)
    public boolean hideNews(int id) {
        return updateNewsStatus(id, "hidden");
    }

    // Hiển thị lại bài (hidden → published)
    public boolean showNews(int id) {
        return updateNewsStatus(id, "published");
    }
}
