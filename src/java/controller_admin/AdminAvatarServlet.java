package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import service.FileStorageService;

/**
 * Servlet để serve avatar images từ thư mục upload cố định
 * URL pattern: /avatar/{filename}
 */
@WebServlet(name = "AdminAvatarServlet", urlPatterns = {"/avatar/*"})
public class AdminAvatarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        String fileName = pathInfo.substring(1);
        if (fileName.contains("..") || fileName.contains(File.separator) || fileName.contains("/")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        FileStorageService storage = new FileStorageService();
        Optional<File> avatarOpt = storage.getAvatarFile(fileName);
        if (!avatarOpt.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        File avatarFile = avatarOpt.get();
        
        String contentType = storage.detectContentType(fileName);
        response.setContentType(contentType);
        response.setContentLengthLong(avatarFile.length());
        response.setHeader("Cache-Control", "public, max-age=31536000");
        
        try (java.io.InputStream fis = storage.openAvatarStream(avatarFile);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }
    }
}


