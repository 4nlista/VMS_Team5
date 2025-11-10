package service;

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

        String fileName = request.getParameter("file");
        String type = request.getParameter("type"); // "avatar" hoặc "news"

        if (fileName == null || fileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file parameter");
            return;
        }

        // Lấy file từ disk
        Optional<File> fileOpt;
        if ("avatar".equals(type)) {
            fileOpt = fileService.getAvatarFile(fileName);
        } else {
            fileOpt = fileService.getNewsFile(fileName);
        }

        if (!fileOpt.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        File file = fileOpt.get();

        // Set content type
        String contentType = fileService.detectContentType(fileName);
        response.setContentType(contentType);
        response.setContentLength((int) file.length());

        // Ghi file ra response
        try (FileInputStream fis = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

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
