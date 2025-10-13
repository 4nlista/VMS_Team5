/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AccountDAO;
import java.util.Random;
import model.Account;
import utils.PasswordUtil;

/**
 *
 * @author Admin
 */
public class ForgetPasswordService {

    private AccountDAO accountDAO = new AccountDAO();
    private EmailService emailService = new EmailService();

    public boolean processForgetPassword(String username, String email) {
        Account acc = accountDAO.getAccountByUsernameAndEmail(username, email);
        if (acc == null) {
            return false; // không hợp lệ
        }
        // Sinh mật khẩu ngẫu nhiên
        String newPass = generateRandomPassword(8);
        String hashed = PasswordUtil.hashPassword(newPass);

        // Cập nhật DB
        if (!accountDAO.updatePasswordRandom(acc.getId(), hashed)) {
            return false;
        }

        // Gửi email
        String subject = "Mật khẩu mới của bạn";
        String message = "<p>Mật khẩu mới của bạn là: <b>" + newPass + "</b></p>"
                + "<p>Vui lòng đăng nhập và đổi lại mật khẩu.</p>";
        return emailService.sendEmail(email, subject, message);
    }

    private String generateRandomPassword(int len) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
