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
        accountDAO = new AccountDAO();
    }

    public String changePassword(int accountId, String currentPassword, String newPassword, String confirmPassword) {
        try {
            // 1. Lấy mật khẩu hiện tại từ database
            String currentPasswordHash = accountDAO.getPasswordHashById(accountId);
            System.out.println("Current password hash in DB: " + currentPasswordHash);

            if (currentPasswordHash == null) {
                System.out.println("Không tìm thấy tài khoản trong DB");
                return "Tài khoản không tồn tại.";
            }

            // 2. Kiểm tra mật khẩu cũ
            boolean isMatch = currentPassword.equals(currentPasswordHash)
                    || PasswordUtil.hashPassword(currentPassword).equals(currentPasswordHash);
            
            if (!isMatch) {
                System.out.println("Mật khẩu cũ không khớp (đã kiểm tra cả text và hash)!");
                return "Mật khẩu hiện tại không đúng.";
            }

            // 3. Kiểm tra mật khẩu mới không được giống mật khẩu cũ
            if (currentPassword.equals(newPassword)) {
                System.out.println("❌ Mật khẩu mới giống mật khẩu cũ!");
                return "Mật khẩu mới không được giống mật khẩu cũ.";
            }

            // 4. Kiểm tra confirm password
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("❌ Xác nhận mật khẩu không khớp!");
                return "Xác nhận mật khẩu không khớp.";
            }

            // 5. Hash mật khẩu mới và update database
            String newPasswordHash = PasswordUtil.hashPassword(newPassword);
            System.out.println("New password hash: " + newPasswordHash);

            boolean updated = accountDAO.updatePasswordByUser(accountId, newPasswordHash);
            System.out.println("Update result from DAO: " + updated);

            if (!updated) {
                System.out.println("Update thất bại!");
                return "Đổi mật khẩu thất bại, thử lại.";
            }

            System.out.println("Đổi mật khẩu thành công!");
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return "Có lỗi xảy ra, thử lại sau nhé.";
        }
    }

}
