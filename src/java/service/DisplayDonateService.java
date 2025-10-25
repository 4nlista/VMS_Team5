/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ViewUserDonationDAO;
import java.util.List;
import model.Donation;

/**
 *
 * @author ADDMIN
 */
public class DisplayDonateService {

    private ViewUserDonationDAO viewUserDonationDAO;

    public DisplayDonateService() {
        viewUserDonationDAO = new ViewUserDonationDAO();
    }

    // trả về danh sách 3 sự kiện mới nhất
    public List<Donation> getTop3UserDonation() {
        return viewUserDonationDAO.getTop3UserDonation();
    }
}
