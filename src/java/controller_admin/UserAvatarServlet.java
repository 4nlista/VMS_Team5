/*
 * A friendly reminder to drink enough water
 */

package controller_admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import service.FileStorageService;

/**
 *
 * @author Mirinae
 */
@WebServlet(name = "UserAvatarServlet", urlPatterns = {"/UserAvatar"})
public class UserAvatarServlet extends HttpServlet {
    private final FileStorageService storage = new FileStorageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file");
        if (fileName == null || fileName.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file parameter");
            return;
        }

        // Very basic path traversal protection
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file parameter");
            return;
        }

        Optional<File> opt = storage.getAvatarFile(fileName);
        if (opt.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Avatar not found");
            return;
        }

        File file = opt.get();
        String contentType = storage.detectContentType(file.getName());
        resp.setContentType(contentType);
        resp.setContentLengthLong(file.length());

        try (InputStream is = storage.openAvatarStream(file);
             OutputStream os = resp.getOutputStream()) {
            byte[] buf = new byte[8192];
            int r;
            while ((r = is.read(buf)) != -1) {
                os.write(buf, 0, r);
            }
            os.flush();
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error streaming avatar");
        }
    }
}