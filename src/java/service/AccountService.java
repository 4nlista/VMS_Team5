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
// test
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

    // 1. Hiển thị dữ liệu danh sách các Accounts
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    // 2. Khóa / Mở khóa tài khoản (business rule: không cho tự khóa admin hiện hành)
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
        return accountDAO.updateAccountStatus(id, newStatus);
    }

    // 3. Lọc & tìm kiếm account theo role/status/search
    public List<Account> findAccounts(String role, Boolean status, String search) {
        // Normalize role to lowercase expected by DB data
        String normalizedRole = role == null ? null : role.trim().toLowerCase();
        String trimmedSearch = search == null ? null : search.trim();
        return accountDAO.findAccounts(normalizedRole, status, trimmedSearch);
    }

    // 4. Tổng số account theo tiêu chí để phân trang
    public int countAccounts(String role, Boolean status, String search) {
        String normalizedRole = role == null ? null : role.trim().toLowerCase();
        String trimmedSearch = search == null ? null : search.trim();
        return accountDAO.countAccounts(normalizedRole, status, trimmedSearch);
    }

    // 5. Lấy danh sách account theo trang
    public List<Account> findAccountsPaged(String role, Boolean status, String search, int page, int pageSize) {
        String normalizedRole = role == null ? null : role.trim().toLowerCase();
        String trimmedSearch = search == null ? null : search.trim();
        int safePage = Math.max(1, page);
        int safePageSize = Math.max(1, pageSize);
        int offset = (safePage - 1) * safePageSize;
        return accountDAO.findAccountsPaged(normalizedRole, status, trimmedSearch, offset, safePageSize);
    }

    // 6. Xóa account (không cho xóa admin) và toàn bộ dữ liệu liên quan
    public boolean deleteAccount(int id) {
        Account acc = accountDAO.getAccountById(id);
        if (acc == null) return false;
        if ("admin".equalsIgnoreCase(acc.getRole())) return false;
        // Thực hiện xóa cascade an toàn trong transaction
        return accountDAO.deleteAccountCascade(id);
    }

}
