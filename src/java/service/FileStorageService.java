package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import java.io.File;

public class FileStorageService {

	public FileStorageService() {
	}

	private static final String BASE_DIR = System.getProperty("user.home") + File.separator + "VMS_Uploads";
	private static final String AVATAR_SUBDIR = "avatars";
	private static final String NEWS_SUBDIR = "news";
	private static final String EVENT_SUBDIR = "events";
	
	// Giới hạn kích thước file
	public static final long MAX_AVATAR_SIZE = 2L * 1024 * 1024; // 1MB cho avatar
	public static final long MAX_IMAGE_SIZE = 2L * 1024 * 1024; // 2MB cho news/event images

	private String getBaseUploadPath() {
		File dir = new File(BASE_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return BASE_DIR;
	}

	private String getAvatarUploadPath() {
		String path = getBaseUploadPath() + File.separator + AVATAR_SUBDIR;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}
	
	private String getEventUploadPath() {
		String path = getBaseUploadPath() + File.separator + EVENT_SUBDIR;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	/**
	 * UNIFIED UPLOAD METHOD - Validate và upload ảnh cho TẤT CẢ mục đích
	 * @param fileStream InputStream của file
	 * @param originalFileName Tên file gốc
	 * @param fileSize Kích thước file (bytes)
	 * @param contentType MIME type
	 * @param uploadType Loại upload: "avatar", "news", "event"
	 * @param identifier ID liên quan (accountId, newsId, eventId...)
	 * @param maxSizeBytes Giới hạn kích thước tối đa
	 * @return Tên file đã lưu hoặc null nếu lỗi
	 */
	public String uploadImage(InputStream fileStream, String originalFileName, long fileSize, 
	                          String contentType, String uploadType, int identifier, long maxSizeBytes) {
		// 1. Validate null
		if (fileStream == null || originalFileName == null || originalFileName.isEmpty()) {
			return null;
		}
		
		// 2. Validate content type
		if (contentType == null || !contentType.startsWith("image/")) {
			System.err.println("❌ Invalid content type: " + contentType);
			return null;
		}
		
		// 3. Validate file size
		if (fileSize > maxSizeBytes) {
			System.err.println("❌ File too large: " + fileSize + " > " + maxSizeBytes);
			return null;
		}
		
		// 4. Xác định thư mục upload
		String uploadDir;
		String prefix;
		switch (uploadType.toLowerCase()) {
			case "avatar":
				uploadDir = getAvatarUploadPath();
				prefix = "avatar_" + identifier + "_";
				break;
			case "news":
				uploadDir = getNewsUploadPath();
				prefix = "news_";
				break;
			case "event":
				uploadDir = getEventUploadPath();
				prefix = "event_";
				break;
			default:
				System.err.println("❌ Unknown upload type: " + uploadType);
				return null;
		}
		
		// 5. Tạo tên file unique
		String extension = "";
		int dotIndex = originalFileName.lastIndexOf('.');
		if (dotIndex != -1) {
			extension = originalFileName.substring(dotIndex);
		}
		String uniqueFileName = prefix + UUID.randomUUID() + extension;
		
		// 6. Lưu file
		try {
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			Path filePath = uploadPath.resolve(uniqueFileName);
			Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("✅ Uploaded: " + uniqueFileName + " (" + fileSize + " bytes)");
			return uniqueFileName;
		} catch (IOException e) {
			System.err.println("❌ Upload failed: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public Optional<File> getAvatarFile(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return Optional.empty();
		}
		File file = new File(getAvatarUploadPath(), fileName);
		return (file.exists() && file.isFile()) ? Optional.of(file) : Optional.empty();
	}

	public String detectContentType(String fileName) {
		String lower = fileName == null ? "" : fileName.toLowerCase();
		if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		if (lower.endsWith(".png")) {
			return "image/png";
		}
		if (lower.endsWith(".gif")) {
			return "image/gif";
		}
		if (lower.endsWith(".webp")) {
			return "image/webp";
		}
		return "application/octet-stream";
	}

	public InputStream openAvatarStream(File file) throws IOException {
		return new FileInputStream(file);
	}

	// News image handling
	private String getNewsUploadPath() {
		String path = getBaseUploadPath() + File.separator + NEWS_SUBDIR;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	public Optional<File> getNewsFile(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return Optional.empty();
		}

		File file = new File(getNewsUploadPath(), fileName);
		return (file.exists() && file.isFile()) ? Optional.of(file) : Optional.empty();
	}
	
	public Optional<File> getEventFile(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return Optional.empty();
		}
		File file = new File(getEventUploadPath(), fileName);
		return (file.exists() && file.isFile()) ? Optional.of(file) : Optional.empty();
	}
}
