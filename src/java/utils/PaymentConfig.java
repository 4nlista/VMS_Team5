package utils;

import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class cấu hình và tiện ích cho Cổng thanh toán - VMS Team 5
 * Hiện tại hỗ trợ VNPay, có thể mở rộng cho các cổng thanh toán khác
 */
public class PaymentConfig {

    // Cấu hình VNPay
    // LƯU Ý: Các thông tin này PHẢI khớp với tài khoản VNPay Merchant của bạn
    // Lấy từ: https://sandbox.vnpayment.vn/merchantv2/
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_TmnCode = "4YUP19I4";  // TODO: Replace with your TMN Code from VNPay Merchant Portal
    public static String secretKey = "MDUIFDCRAKLNBPOFIAFNEKFRNMFBYEPX";  // TODO: Replace with your Hash Secret from VNPay Merchant Portal
    public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    /**
     * Tạo mã hash MD5
     */
    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    /**
     * Tạo mã hash SHA256
     */
    public static String sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    /**
     * Hash tất cả các field để tạo chữ ký VNPay
     * LƯU Ý: Các field phải được URL encode trước khi truyền vào method này
     */
    public static String hashAllFields(Map<String, String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        List<String> hashDataList = new ArrayList<>();
        
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Fields are already URL encoded, just concatenate
                hashDataList.add(fieldName + "=" + fieldValue);
            }
        }
        
        String hashData = String.join("&", hashDataList);
        System.out.println("DEBUG hashAllFields - Hash Data: " + hashData);
        String hash = hmacSHA512(secretKey, hashData);
        System.out.println("DEBUG hashAllFields - Generated Hash: " + hash);
        return hash;
    }

    /**
     * Tạo chữ ký HMAC SHA512 để bảo mật giao dịch
     */
    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Lấy địa chỉ IP của client
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "Invalid IP:" + e.getMessage();
        }
        return ipAddress;
    }

    /**
     * Tạo chuỗi số ngẫu nhiên (dùng cho mã giao dịch)
     */
    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Lấy return URL động từ request cho guest donations
     * Đảm bảo return URL hoạt động trên mọi môi trường (localhost, IP, hoặc domain)
     *
     * @param request HttpServletRequest để lấy thông tin server
     * @return URL đầy đủ để VNPay callback về
     */
    public static String getGuestDonationReturnUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // http or https
        String serverName = request.getServerName(); // localhost, IP, or domain
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath(); // /VMS_Team5 or /Volunteer_System_Team5

        // Build base URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        // Add port if it's not the default port (80 for http, 443 for https)
        if ((scheme.equals("http") && serverPort != 80) ||
            (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        // Thêm context path và đường dẫn return
        url.append(contextPath).append("/guest-payment-donation-return");

        return url.toString();
    }

    /**
     * Lấy return URL động từ request cho volunteer donations
     * Đảm bảo return URL hoạt động trên mọi môi trường (localhost, IP, hoặc domain)
     *
     * @param request HttpServletRequest để lấy thông tin server
     * @return URL đầy đủ để VNPay callback về
     */
    public static String getVolunteerDonationReturnUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // http or https
        String serverName = request.getServerName(); // localhost, IP, or domain
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath(); // /VMS_Team5 or /Volunteer_System_Team5

        // Build base URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        // Add port if it's not the default port (80 for http, 443 for https)
        if ((scheme.equals("http") && serverPort != 80) ||
            (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        // Add context path and return path
        url.append(contextPath).append("/volunteer-payment-donation-return");

        return url.toString();
    }
}

