/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AdminAccountDAO;
import dao.AccountDAO;
import java.util.List;
import model.Account;

/**
 *
 * @author Admin
 */
// Service dành riêng cho quản lí tài khoản bởi Admin
public class AdminAccountService {
    
    private AdminAccountDAO adminAccountDAO;
    private AccountDAO accountDAO;
    
    public AdminAccountService() {
        adminAccountDAO = new AdminAccountDAO();
        accountDAO = new AccountDAO();
    }
    
    // 1. Truy xuất account theo id (sử dụng AccountDAO để tránh trùng lặp)
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }
    
    // 2. Hiển thị dữ liệu danh sách các Accounts (sử dụng AccountDAO để tránh trùng lặp)
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
    
    // 3. Khóa / Mở khóa tài khoản (business rule: không cho tự khóa admin hiện hành)
    public boolean toggleAccountStatus(int id) {
        Account acc = accountDAO.getAccountById(id);
        if (acc == null) {
            return false;
        }
        boolean newStatus = !acc.isStatus();
        // Không cho phép khóa tài khoản có role admin
        if ("admin".equalsIgnoreCase(acc.getRole()) && !newStatus) {
            return false;
        }
        return adminAccountDAO.updateAccountStatus(id, newStatus);
    }
    
    // 4. Lọc & tìm kiếm account theo role/status/search
    public List<Account> findAccounts(String role, Boolean status, String search) {
        // Normalize role to lowercase expected by DB data
        String normalizedRole = role == null ? null : role.trim().toLowerCase();
        String trimmedSearch = search == null ? null : search.trim();
        return adminAccountDAO.findAccounts(normalizedRole, status, trimmedSearch);
    }
    
    // 5. Tổng số account theo tiêu chí để phân trang
    public int countAccounts(String role, Boolean status, String search) {
        String normalizedRole = role == null ? null : role.trim().toLowerCase();
        String trimmedSearch = search == null ? null : search.trim();
        return adminAccountDAO.countAccounts(normalizedRole, status, trimmedSearch);
    }
    
    // 6. Lấy danh sách account theo trang
    public List<Account> findAccountsPaged(String role, Boolean status, String search, int page, int pageSize, String sortOrder) {
        String normalizedRole = role == null ? null : role.trim().toLowerCase();
        String trimmedSearch = search == null ? null : search.trim();
        int safePage = Math.max(1, page);
        int safePageSize = Math.max(1, pageSize);
        int offset = (safePage - 1) * safePageSize;
        return adminAccountDAO.findAccountsPaged(normalizedRole, status, trimmedSearch, offset, safePageSize, sortOrder);
    }
    
    // 7. Xóa account (không cho xóa admin) và toàn bộ dữ liệu liên quan
    public boolean deleteAccount(int id) {
        Account acc = accountDAO.getAccountById(id);
        if (acc == null) return false;
        if ("admin".equalsIgnoreCase(acc.getRole())) return false;
        // Thực hiện xóa cascade an toàn trong transaction
        return adminAccountDAO.deleteAccountCascade(id);
    }
    
    // 8. Tạo tài khoản mới (chỉ cho tạo organization hoặc volunteer)
    public boolean createAccount(String username, String password, String role, boolean status) {
        // Validate role - không cho tạo admin từ đây
        if (role == null || "admin".equalsIgnoreCase(role)) {
            return false;
        }
        
        // Kiểm tra username đã tồn tại
        if (adminAccountDAO.usernameExists(username)) {
            return false;
        }
        
        // Tạo tài khoản
        return adminAccountDAO.insertAccount(username, password, role, status);
    }
    
    // 8b. Tạo tài khoản mới và trả về ID
    public int createAccountAndGetId(String username, String password, String role, boolean status) {
        // Validate role - không cho tạo admin từ đây
        if (role == null || "admin".equalsIgnoreCase(role)) {
            return -1;
        }
        
        // Kiểm tra username đã tồn tại
        if (adminAccountDAO.usernameExists(username)) {
            return -1;
        }
        
        // Tạo tài khoản và trả về ID
        return adminAccountDAO.insertAccountAndGetId(username, password, role, status);
    }
    
    // 9. Kiểm tra username đã tồn tại
    public boolean usernameExists(String username) {
        return adminAccountDAO.usernameExists(username);
    }
    
    // 10. Lấy ID tài khoản vừa tạo (method này chỉ dùng trong một số trường hợp đặc biệt)
    public int getLastInsertedAccountId() {
        return adminAccountDAO.getLastInsertedAccountId();
    }
}

