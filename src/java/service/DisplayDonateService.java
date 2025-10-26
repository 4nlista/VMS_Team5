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
}
