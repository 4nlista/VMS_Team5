package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * SERVICE THỐNG NHẤT cho upload ảnh (Avatar, News, Events)
 * Tránh code trùng lặp và đảm bảo validation nhất quán
 * 
 * @author VMS_Team5
 */
public class UnifiedImageUploadService {
    
    private final FileStorageService fileStorage = new FileStorageService();
    
    /**
     * Upload ảnh avatar (giới hạn 1MB)
     * @param request HttpServletRequest
     * @param accountId ID tài khoản
     * @param partName Tên của Part trong form (thường là "avatar")
     * @return Map với key "success" (boolean), "fileName" (String), "error" (String)
     */
    public Map<String, Object> uploadAvatar(HttpServletRequest request, int accountId, String partName) {
        return uploadImage(request, accountId, partName, "avatar", FileStorageService.MAX_AVATAR_SIZE);
    }
    
    /**
     * Upload ảnh news (giới hạn 5MB)
     * @param request HttpServletRequest
     * @param newsId ID bài viết (có thể = 0 nếu tạo mới)
     * @param partName Tên của Part trong form (thường là "newsImage")
     * @return Map với key "success" (boolean), "fileName" (String), "error" (String)
     */
    public Map<String, Object> uploadNewsImage(HttpServletRequest request, int newsId, String partName) {
        return uploadImage(request, newsId, partName, "news", FileStorageService.MAX_IMAGE_SIZE);
    }
    
    /**
     * Upload ảnh news (giới hạn 5MB) - Nhận Part trực tiếp
     * @param filePart Part đã lấy từ request
     * @param newsId ID bài viết (có thể = 0 nếu tạo mới)
     * @return Map với key "success" (boolean), "fileName" (String), "error" (String)
     */
    public Map<String, Object> uploadNewsImage(Part filePart, int newsId) {
        return uploadImageFromPart(filePart, newsId, "news", FileStorageService.MAX_IMAGE_SIZE);
    }
    
    /**
     * Upload ảnh event (giới hạn 5MB)
     * @param request HttpServletRequest
     * @param eventId ID sự kiện (có thể = 0 nếu tạo mới)
     * @param partName Tên của Part trong form (thường là "eventImage")
     * @return Map với key "success" (boolean), "fileName" (String), "error" (String)
     */
    public Map<String, Object> uploadEventImage(HttpServletRequest request, int eventId, String partName) {
        return uploadImage(request, eventId, partName, "event", FileStorageService.MAX_IMAGE_SIZE);
    }
    
    /**
     * Upload ảnh event (giới hạn 5MB) - Nhận Part trực tiếp
     * @param filePart Part đã lấy từ request
     * @param eventId ID sự kiện (có thể = 0 nếu tạo mới)
     * @return Map với key "success" (boolean), "fileName" (String), "error" (String)
     */
    public Map<String, Object> uploadEventImage(Part filePart, int eventId) {
        return uploadImageFromPart(filePart, eventId, "event", FileStorageService.MAX_IMAGE_SIZE);
    }
    
    /**
     * PRIVATE METHOD - Xử lý upload ảnh chung
     */
    private Map<String, Object> uploadImage(HttpServletRequest request, int identifier, 
                                            String partName, String uploadType, long maxSize) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        
        try {
            Part filePart = request.getPart(partName);
            
            // 1. Check null hoặc empty
            if (filePart == null || filePart.getSize() == 0) {
                result.put("error", "Không có file được chọn.");
                return result;
            }
            
            // 2. Validate content type
            String contentType = filePart.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                result.put("error", "File phải là ảnh (JPG, PNG, GIF, WebP).");
                return result;
            }
            
            // 3. Validate size
            long fileSize = filePart.getSize();
            if (fileSize > maxSize) {
                String maxSizeMB = String.format("%.1f", maxSize / (1024.0 * 1024.0));
                result.put("error", "Kích thước file không được vượt quá " + maxSizeMB + "MB.");
                return result;
            }
            
            // 4. Get filename
            String originalFileName = getFileName(filePart);
            if (originalFileName == null || originalFileName.isEmpty()) {
                result.put("error", "Tên file không hợp lệ.");
                return result;
            }
            
            // 5. Upload via FileStorageService
            try (InputStream fileStream = filePart.getInputStream()) {
                String savedFileName = fileStorage.uploadImage(
                    fileStream, 
                    originalFileName, 
                    fileSize, 
                    contentType, 
                    uploadType, 
                    identifier, 
                    maxSize
                );
                
                if (savedFileName == null) {
                    result.put("error", "Lỗi khi lưu file. Vui lòng thử lại.");
                    return result;
                }
                
                // ✅ Success
                result.put("success", true);
                result.put("fileName", savedFileName);
                return result;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "Lỗi hệ thống: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * PRIVATE METHOD - Xử lý upload ảnh từ Part đã có sẵn
     */
    private Map<String, Object> uploadImageFromPart(Part filePart, int identifier, 
                                                     String uploadType, long maxSize) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        
        try {
            // 1. Check null hoặc empty
            if (filePart == null || filePart.getSize() == 0) {
                result.put("error", "Không có file được chọn.");
                return result;
            }
            
            // 2. Validate content type
            String contentType = filePart.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                result.put("error", "File phải là ảnh (JPG, PNG, GIF, WebP).");
                return result;
            }
            
            // 3. Validate size
            long fileSize = filePart.getSize();
            if (fileSize > maxSize) {
                String maxSizeMB = String.format("%.1f", maxSize / (1024.0 * 1024.0));
                result.put("error", "Kích thước file không được vượt quá " + maxSizeMB + "MB.");
                return result;
            }
            
            // 4. Get filename
            String originalFileName = getFileName(filePart);
            if (originalFileName == null || originalFileName.isEmpty()) {
                result.put("error", "Tên file không hợp lệ.");
                return result;
            }
            
            // 5. Upload via FileStorageService
            try (InputStream fileStream = filePart.getInputStream()) {
                String savedFileName = fileStorage.uploadImage(
                    fileStream, 
                    originalFileName, 
                    fileSize, 
                    contentType, 
                    uploadType, 
                    identifier, 
                    maxSize
                );
                
                if (savedFileName == null) {
                    result.put("error", "Không thể lưu file.");
                    return result;
                }
                
                // ✅ Success
                result.put("success", true);
                result.put("fileName", savedFileName);
                return result;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "Lỗi hệ thống: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * Extract filename từ Part header
     */
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        if (contentDisposition == null) return null;
        
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
