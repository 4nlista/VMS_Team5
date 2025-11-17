/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ViewDonorsDAO;
import java.util.List;
import model.Donation;

/**
 *
 * @author ADDMIN
 */
// hiển thị danh sách người donate
public class DisplayDonateService {

    private ViewDonorsDAO viewUserDonationDAO;

    public DisplayDonateService() {
        viewUserDonationDAO = new ViewDonorsDAO();
    }

    // trả về danh sách 3 sự kiện mới nhất
    public List<Donation> getTop3UserDonation() {
        return viewUserDonationDAO.getTop3UserDonation();
    }

    // trả về danh sách những người donate + tổng số sự kiện họ donate
    public List<Donation> getAllUserDonation() {
        return viewUserDonationDAO.getAllUserDonation();
    }

    // trả về danh sách vá nhân xem lịch sử donate
    public List<Donation> getUserDonationsPaged(int volunteerId, int pageIndex, int pageSize) {
        return viewUserDonationDAO.getUserDonationsPaged(volunteerId, pageIndex, pageSize);
    }
    
    // Lấy danh sách với filter ngày
    public List<Donation> getUserDonationsPagedWithDateFilter(int volunteerId, int page, int pageSize, String startDate, String endDate) {
        return viewUserDonationDAO.getUserDonationsPagedWithDateFilter(volunteerId, page, pageSize, startDate, endDate);
    }
    
    public int getTotalDonationsByVolunteer(int volunteerId) {
        return viewUserDonationDAO.getTotalDonationsByVolunteer(volunteerId);
    }
    
    // Đếm tổng số với filter ngày
    public int getTotalDonationsByVolunteerWithDateFilter(int volunteerId, String startDate, String endDate) {
        return viewUserDonationDAO.getTotalDonationsByVolunteerWithDateFilter(volunteerId, startDate, endDate);
    }
    
    // Lọc donations với filter đầy đủ (ngày + trạng thái)
    public List<Donation> getUserDonationsWithAllFilters(int volunteerId, int page, int pageSize, 
                                                         String startDate, String endDate, String status) {
        return viewUserDonationDAO.getUserDonationsWithAllFilters(volunteerId, page, pageSize, startDate, endDate, status);
    }
    
    // Đếm tổng số với filter đầy đủ
    public int getTotalDonationsWithAllFilters(int volunteerId, String startDate, String endDate, String status) {
        return viewUserDonationDAO.getTotalDonationsWithAllFilters(volunteerId, startDate, endDate, status);
    }
    
    // trả về phân trang
    public int getTotalDonors(){
        return viewUserDonationDAO.getTotalDonors();
    }
    
    public List<Donation> getDonorsPaged(int offset, int limit) {
        return viewUserDonationDAO.getDonorsPaged(offset, limit);
    }
    
    // Lấy chi tiết donation theo ID và volunteer ID
    public Donation getDonationById(int donationId, int volunteerId) {
        return viewUserDonationDAO.getDonationById(donationId, volunteerId);
    }

    // Lấy chi tiết donation theo ID và event ID (for organization)
    public Donation getDonationByIdForOrganization(int donationId, int eventId) {
        return viewUserDonationDAO.getDonationByIdForOrganization(donationId, eventId);
    }
}
