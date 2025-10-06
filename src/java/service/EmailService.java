/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import utils.EmailUtil;

/**
 *
 * @author Admin
 */

// code logic chức năng gửi mail
public class EmailService {

    // Gửi email xác nhận đăng ký
    public boolean sendEmail(String toEmail, String subject, String message) {
        try {
            EmailUtil.sendEmail(toEmail, subject, message);
            return true;
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
            return false;
        }
    }
}
