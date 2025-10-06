/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AccountDAO;
import dao.UserDAO;
import model.Account;
import model.User;
import utils.PasswordUtil;

/**
 *
 * @author Admin
 */
public class RegisterService {

    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private EmailService emailService;

    public RegisterService() {
        accountDAO = new AccountDAO();
        userDAO = new UserDAO();
        emailService = new EmailService();
    }

    public String register(Account acc, User user) {
        try {
            // 1. Kiểm tra username/email trùng
            if (accountDAO.isUsernameExists(acc.getUsername())) {
                return "Username already exists!";
            }
            if (userDAO.isEmailExists(user.getEmail())) {
                return "Email already exists!";
            }

            // 2. Mã hóa mật khẩu trước khi lưu
            String hashedPassword = PasswordUtil.hashPassword(acc.getPassword());
            acc.setPassword(hashedPassword);

            // 3. Thêm Account trước
            int accountId = accountDAO.insertAccount(acc);
            if (accountId <= 0) {
                return "Error creating account!";
            }

            // 4. Liên kết account_id vào user và lưu user
            user.setAccount_id(accountId);
            boolean inserted = userDAO.insertUser(user);

            if (!inserted) {
                return "Error saving user info!";
            }

            // 5. Gửi email xác nhận (tùy chọn)
            String subject = "Xác nhận đăng ký Volunteer System";
            String body = "Xin chào " + user.getFull_name() + ",<br>"
                    + "Bạn đã đăng ký tài khoản thành công.<br>"
                    + "Tên đăng nhập: <b>" + acc.getUsername() + "</b><br>"
                    + "Cảm ơn bạn đã tham gia hệ thống!";
            emailService.sendEmail(user.getEmail(), subject, body);

            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "System error occurred!";
        }
    }
}
