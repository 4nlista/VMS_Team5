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

	public String saveAvatar(InputStream avatarInputStream, String originalFileName, int accountId) {
		if (avatarInputStream == null || originalFileName == null || originalFileName.isEmpty()) {
			return null;
		}
		try {
			// ensure directories
			getAvatarUploadPath();
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
			String uniqueFileName = "avatar_" + accountId + "_" + UUID.randomUUID() + fileExtension;
			String uploadDir = getAvatarUploadPath();
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			Path filePath = uploadPath.resolve(uniqueFileName);
			Files.copy(avatarInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			return uniqueFileName;
		} catch (IOException e) {
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

	public String saveNewsImage(InputStream fileStream, String originalFileName) {
		if (fileStream == null || originalFileName == null || originalFileName.isEmpty()) {
			return null;
		}

		try {
			getNewsUploadPath(); // ensure directory exists

			String extension = "";
			int dotIndex = originalFileName.lastIndexOf('.');
			if (dotIndex != -1) {
				extension = originalFileName.substring(dotIndex);
			}

			String uniqueName = "news_" + UUID.randomUUID() + extension;

			String uploadDir = getNewsUploadPath();
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path filePath = uploadPath.resolve(uniqueName);
			Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);

			return uniqueName;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Optional<File> getNewsFile(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return Optional.empty();
		}

		File file = new File(getNewsUploadPath(), fileName);
		return (file.exists() && file.isFile()) ? Optional.of(file) : Optional.empty();
	}
}
