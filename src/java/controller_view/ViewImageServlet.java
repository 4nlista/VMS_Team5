package controller_view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import service.FileStorageService;

// servlet giao diện hiển thị ảnh cho toàn bộ các phần
@WebServlet(name = "ViewImageServlet", urlPatterns = {"/viewImage"})

public class ViewImageServlet extends HttpServlet {

    private FileStorageService fileService;

    @Override
    public void init() {
        fileService = new FileStorageService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tên file và loại (avatar hoặc news) từ URL parameters
        String fileName = request.getParameter("file");
        String type = request.getParameter("type"); // "avatar" hoặc "news"

        if (fileName == null || fileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file parameter");
            return;
        }

        // Lấy file từ disk
        Optional<File> fileOpt;
        if ("avatar".equals(type)) {
            // Lấy file từ disk dựa vào type
            fileOpt = fileService.getAvatarFile(fileName);
        } else {
            // Lấy từ thư mục news
            fileOpt = fileService.getNewsFile(fileName);
        }

        if (!fileOpt.isPresent()) {
            // File không tồn tại → trả về lỗi 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        File file = fileOpt.get();

        // Detect content type (image/jpeg, image/png...) từ extension
        String contentType = fileService.detectContentType(fileName);
        response.setContentType(contentType);
        response.setContentLength((int) file.length());

        // Đọc file và ghi ra response output stream
        try (FileInputStream fis = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            // Đọc và ghi từng chunk cho đến hết file
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.flush();
        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
