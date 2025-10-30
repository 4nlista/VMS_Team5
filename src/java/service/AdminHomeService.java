/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AdminHomeDAO;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Event;

//>>>>>>> an
/**
 *
 * @author Admin
 */
public class AdminHomeService {

    private AdminHomeDAO adminHomeDAO;

    public AdminHomeService() {
        adminHomeDAO = new AdminHomeDAO();
    }

    // 1.1 Hiển thị dữ liệu tổng số tài khoản
    public int getTotalAccount() {
        return adminHomeDAO.getTotalAccount();
    }

    // 1.2 Hiển thị dữ liệu tổng số tiền donate
    public double getTotalMoneyDonate() {
        return adminHomeDAO.getTotalMoneyDonate();
    }

    // 1.3 Hiển thị top 3 sự kiện có tổng số tiền donate nhiều nhất
    public List<Event> getTop3EventsMoneyDonate() {
        return adminHomeDAO.getTop3EventsMoneyDonate();
    }
    // 1.4 Hiển thị top 3 sắp diễn ra

    public List<Event> getTop3EventsComing() {
        return adminHomeDAO.getTop3EventsComing();
    }
    
    public Map<String, Integer> getAccountStatistics() {
        return adminHomeDAO.getAccountStatistics();
    }

//>>>>>>> an
}
