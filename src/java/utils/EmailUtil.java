/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author Admin
 */
//class dùng để gửi Mail cho email dùng để đăng kí.
public class EmailUtil {

    public static void sendEmail(String toEmail, String subject, String messageContent) {
        // Thông tin người gửi
        final String fromEmail = "chatboxaipro@gmail.com"; // Đổi thành Gmail người gửi
        final String password = "tgzkkuuhpwgeoiza";     // App password do google cung cấp 

        // Cấu hình server SMTP của Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        try {
            // Tạo session có xác thực
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            // Nội dung mail gửi đi
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setContent(messageContent, "text/html; charset=UTF-8");
//            msg.setHeader("Content-Type", "text/html; charset=UTF-8"); 
            Transport.send(msg);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
