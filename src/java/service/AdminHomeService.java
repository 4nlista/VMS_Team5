/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AdminHomeDAO;
import java.util.List;
import model.Account;

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
    
}
