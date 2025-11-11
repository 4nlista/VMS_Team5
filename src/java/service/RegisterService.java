/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AccountDAO;
import dao.UserDAO;
import java.util.Date;
import java.util.regex.Pattern;
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
        // 1. Validate các field không được trống
        if (acc.getUsername() == null || acc.getUsername().trim().isEmpty()) {
            return "Tên đăng nhập không được để trống!";
        }

        if (acc.getPassword() == null || acc.getPassword().trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }

        if (user.getFull_name() == null || user.getFull_name().trim().isEmpty()) {
            return "Họ và tên không được để trống!";
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }

        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return "Số điện thoại không được để trống!";
        }

        //  2. Validate email đúng định dạng
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.matches(emailRegex, user.getEmail())) {
            return "Email không đúng định dạng!";
        }

        //  3. Validate phone: chỉ số, bắt đầu bằng 0, 10-11 số
        String phoneRegex = "^0\\d{9,10}$";
        if (!Pattern.matches(phoneRegex, user.getPhone())) {
            return "Số điện thoại phải bắt đầu bằng 0 và có 10-11 chữ số!";
        }

        //  4. Validate ngày sinh không được ở tương lai
        if (user.getDob() != null) {
            Date now = new Date();
            if (user.getDob().after(now)) {
                return "Ngày sinh không được ở tương lai!";
            }
        }

        //  5. Kiểm tra username trùng
        if (accountDAO.isUsernameExists(acc.getUsername())) {
            return "Tên tài khoản đã tồn tại!";
        }

        //  6. Kiểm tra email trùng
        if (userDAO.isEmailExists(user.getEmail())) {
            return "Email đã tồn tại!";
        }

        //  7. Kiểm tra phone trùng
        if (userDAO.isPhoneExists(user.getPhone())) {
            return "Số điện thoại đã tồn tại!";
        }

        //  8. Mã hóa mật khẩu trước khi lưu
        String hashedPassword = PasswordUtil.hashPassword(acc.getPassword());
        acc.setPassword(hashedPassword);

        //  9. Thêm Account trước
        int accountId = accountDAO.insertAccount(acc);
        if (accountId <= 0) {
            return "Lỗi tạo tài khoản!";
        }

        //  10. Liên kết account_id vào user và lưu user
        user.setAccount_id(accountId);
        boolean inserted = userDAO.insertUserWithAccount(user);

        if (!inserted) {
            return "Có lỗi khi lưu thông tin người dùng!";
        }

        //  11. Gửi email xác nhận
        String subject = "Register Accounts System";
        String body = """
            <html>
                <body style='font-family: Arial, sans-serif;'>
                    <p>Xin chào <b>%s</b>,</p>
                    <p>Bạn đã đăng ký tài khoản thành công.</p>
                    <p>Tên đăng nhập: <b>%s</b></p>
                    <p>Vai trò: <b>%s</b></p>
                    <p>Cảm ơn bạn đã tham gia hệ thống!</p>
                 </body>
            </html>
            """.formatted(user.getFull_name(), acc.getUsername(), acc.getRole());

        try {
            emailService.sendEmail(user.getEmail(), subject, body);
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi hệ thống!";
        }

        return "success";
    }
}
