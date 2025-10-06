/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AccountDAO;
import java.util.List;
import model.Account;

/**
 *
 * @author Admin
 */
// xử lí nghiệp vụ logic login và đăng nhập
public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    // 1. Truy xuất account theo id
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    // 1.1 Hiển thị dữ liệu danh sách các Accounts
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

}
