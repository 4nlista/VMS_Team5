/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AdminHomeDAO;
import dao.StatisticsDAO;
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
    private StatisticsDAO statisticsDAO;

    public AdminHomeService() {
        adminHomeDAO = new AdminHomeDAO();
        statisticsDAO = new StatisticsDAO();
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
    
    // thống kê số tài khoản
    public Map<String, Integer> getAccountStatistics() {
        return adminHomeDAO.getAccountStatistics();
    }
    
    // danh sách các sự kiện đang diễn ra
    public int getTotalEventsActive() {
        return statisticsDAO.getTotalEventsActive();
    }
    
    // Lấy thống kê donate theo 5 tháng gần nhất
    public Map<String, Double> getDonationByMonth() {
        return adminHomeDAO.getDonationByMonth();
    }
    
    // Lấy top 3 người donate nhiều nhất
    public List<model.Donation> getTop3Donors() {
        return adminHomeDAO.getTop3Donors();
    }
    
    // Lấy thống kê tài khoản đầy đủ (active + inactive)
    public Map<String, Integer> getAllAccountStats() {
        return adminHomeDAO.getAllAccountStats();
    }
}
