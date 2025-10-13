/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AccountDAO;
import utils.PasswordUtil;

/**
 *
 * @author Admin
 */
public class ChangePasswordService {

    private AccountDAO accountDAO;

    public ChangePasswordService() {
        this.accountDAO = new AccountDAO();
    }

    public String changePassword(int accountId, String currentPassword, String newPassword, String confirmPassword) {

        try {
            // 1. Lấy mật khẩu hiện tại từ DB
            String currentPasswordHash = accountDAO.getPasswordHashById(accountId);
            if (currentPasswordHash == null) {
                return "Tài khoản không tồn tại.";
            }

            // 2. Kiểm tra mật khẩu cũ
            if (!PasswordUtil.hashPassword(currentPassword).equals(currentPasswordHash)) {
                return "Mật khẩu hiện tại không đúng.";
            }

            // 3. Kiểm tra confirm password
            if (!newPassword.equals(confirmPassword)) {
                return "Xác nhận mật khẩu không khớp.";
            }

            // 4. Hash mật khẩu mới và update DB
            String newPasswordHash = PasswordUtil.hashPassword(newPassword);
            boolean updated = accountDAO.updatePasswordByUser(accountId, newPasswordHash);
            if (!updated) {
                return "Đổi mật khẩu thất bại, thử lại.";
            }

            // 5. Thành công
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return "Có lỗi xảy ra, thử lại sau.";
        }
    }
}
