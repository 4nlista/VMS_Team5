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
    public static String vnp_TmnCode = "4YUP19I4";  // TODO: Thay bằng TMN Code của merchant VNPay của bạn
    public static String secretKey = "MDUIFDCRAKLNBPOFIAFNEKFRNMFBYEPX";  // TODO: Thay bằng Hash Secret (secret key) của merchant VNPay
    public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    /**
     * Tạo mã hash MD5
     */
    public static String md5(String message) {
        // Hàm tiện ích: trả về MD5 hash của chuỗi input
        // Thường dùng cho mục đích kiểm tra nhanh hoặc legacy, không dùng cho chữ ký VNPay
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
        // Hàm tiện ích: trả về SHA-256 hash của chuỗi input
        // Có thể dùng khi cần checksum, nhưng VNPay yêu cầu HMAC SHA512 cho chữ ký
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
        // Mục đích:
        // - Nhận một map các trường (đã URL-encode)
        // - Sắp xếp tên trường theo thứ tự chữ (ascending)
        // - Nối thành chuỗi theo format key=value&key2=value2...
        // - Tạo HMAC SHA512 bằng secretKey và trả về hex string
        // Đây là bước trung tâm để tạo `vnp_SecureHash` trước khi gửi lên VNPay,
        // và cũng dùng để verify chữ ký khi VNPay callback trả về.
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        List<String> hashDataList = new ArrayList<>();
        
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Các trường đã được URL-encode, chỉ việc nối chuỗi lại
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
        // Tạo HMAC-SHA512 hex digest cho `data` dùng `key`.
        // Trả về chuỗi hex để dùng làm `vnp_SecureHash`.
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
        // Lấy IP thật của client, ưu tiên header `X-FORWARDED-FOR` (nếu có proxy/nginx),
        // fallback về request.getRemoteAddr(). Dùng cho tham số `vnp_IpAddr` gửi tới VNPay.
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
        // Sinh chuỗi số ngẫu nhiên độ dài `len` (0-9) dùng để tạo `vnp_TxnRef` hoặc mã tham chiếu tạm
        // (không bảo mật tuyệt đối nhưng đủ để tách biệt giao dịch trên hệ thống).
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
        String scheme = request.getScheme(); // giao thức (http hoặc https)
        String serverName = request.getServerName(); // tên máy chủ (localhost, IP hoặc domain)
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath(); // context path như /VMS_Team5 hoặc /Volunteer_System_Team5

        // Xây dựng URL cơ sở (scheme://server[:port])
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        // Thêm port nếu không phải port mặc định (80 cho http, 443 cho https)
        if ((scheme.equals("http") && serverPort != 80) ||
            (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        // Thêm context path và đường dẫn return cụ thể cho guest
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
        String scheme = request.getScheme(); // giao thức (http hoặc https)
        String serverName = request.getServerName(); // tên máy chủ (localhost, IP hoặc domain)
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath(); // context path như /VMS_Team5 hoặc /Volunteer_System_Team5

        // Xây dựng URL cơ sở (scheme://server[:port])
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        // Thêm port nếu không phải port mặc định (80 cho http, 443 cho https)
        if ((scheme.equals("http") && serverPort != 80) ||
            (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        // Thêm context path và đường dẫn return riêng cho volunteer
        url.append(contextPath).append("/volunteer-payment-donation-return");

        return url.toString();
    }
}

