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
import jakarta.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
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

            // Encode subject to UTF-8
            try {
                msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            } catch (UnsupportedEncodingException ex) {
                msg.setSubject(subject); // Fallback if encoding fails
            }

            msg.setContent(messageContent, "text/html; charset=UTF-8");
            Transport.send(msg);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    // Gửi email thông báo admin đã cấp tài khoản kèm thông tin đăng nhập
    public static void sendEmailWithAdmin(String toEmail, String organizationName, String username, String plainPassword) {
        String safeOrg = organizationName == null ? "" : organizationName;
        
        
        
        String subject = "[Volunteer System] Tài khoản đã được cấp";
        StringBuilder content = new StringBuilder();
        content.append("<div style=\"font-family:Arial, Helvetica, sans-serif; line-height:1.6;\">")
               .append("<h2>Xin chào ").append(escapeHtml(safeOrg)).append("</h2>")
               .append("<p>Quản trị viên đã tạo tài khoản cho vai trò Người Tổ Chức của bạn trên hệ thống Volunteer System.</p>")
               .append("<p><strong>Thông tin đăng nhập:</strong></p>")
               .append("<ul>")
               .append("<li><strong>Tên đăng nhập:</strong> ").append(escapeHtml(username)).append("</li>")
               .append("<li><strong>Mật khẩu:</strong> ").append(escapeHtml(plainPassword)).append("</li>")
               .append("</ul>")
               .append("<p>Vui lòng đăng nhập và đổi mật khẩu ngay sau khi truy cập để bảo mật.</p>")
               .append("<p>Trân trọng,<br/>Volunteer System</p>")
               .append("</div>");

        sendEmail(toEmail, subject, content.toString());
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
