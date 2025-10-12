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
        // 1. Kiểm tra username trùng
        if (accountDAO.isUsernameExists(acc.getUsername())) {
            return "Tên tài khoản đã tồn tại!";
        }
        // 2. Kiểm tra username trùng
        if (userDAO.isEmailExists(user.getEmail())) {
            return "Email đã tồn tại!";
        }
        // 3. Kiểm tra phone trùng
        if (userDAO.isPhoneExists(user.getPhone())) {
            return "Số điện thoại đã tồn tại!";
        }
        // 2. Mã hóa mật khẩu trước khi lưu
        String hashedPassword = PasswordUtil.hashPassword(acc.getPassword());
        acc.setPassword(hashedPassword);

        // 3. Thêm Account trước
        int accountId = accountDAO.insertAccount(acc);
        if (accountId <= 0) {
            return "Lỗi tạo tài khoản!";
        }

        // 4. Liên kết account_id vào user và lưu user
        user.setAccount_id(accountId);
        boolean inserted = userDAO.insertUser(user);

        if (!inserted) {
            return "Có lỗi khi lưu thông tin người dùng!";
        }

        // 5. Gửi email xác nhận (tùy chọn)
        String subject = "Xác nhận đăng ký Volunteer System";
        String body
                = """
            <html>
                <body style='font-family: Arial, sans-serif;'>
                    <p>Xin chào <b>%s</b>,</p>
                    <p>Bạn đã đăng ký tài khoản thành công.</p>
                    <p>Tên đăng nhập: <b>%s</b></p>
                    <p>Cảm ơn bạn đã tham gia hệ thống!</p>
                 </body>
            </html>
            """.formatted(user.getFull_name(), acc.getUsername());

        try {
            emailService.sendEmail(user.getEmail(), subject, body);
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi hệ thống!";
        }
        return "success";
    }
}
