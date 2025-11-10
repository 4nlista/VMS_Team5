/*
 * A friendly reminder to drink enough water
 */

package controller_organization;

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
@WebServlet(name="OrganizationAvatarServlet", urlPatterns={"/OrganizationAvatar"})
public class OrganizationAvatarServlet extends HttpServlet {
    private final FileStorageService storage = new FileStorageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = req.getParameter("file");
        if (fileName == null || fileName.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file");
            return;
        }

        Optional<File> maybe = storage.getAvatarFile(fileName);
        if (maybe.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        File file = maybe.get();

        // Determine content type
        String contentType = storage.detectContentType(file.getName());
        String servletType = getServletContext().getMimeType(file.getName());
        if (servletType != null && !servletType.isBlank()) {
            contentType = servletType;
        }
        resp.setContentType(contentType);
        resp.setContentLengthLong(file.length());

        // Cache control: short cache to reduce immediate staleness but still allow revalidation.
        // You can set to no-cache if you want immediate reload always.
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // strong: avoid caching
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        // Stream file content
        try (InputStream is = storage.openAvatarStream(file);
             OutputStream os = resp.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int r;
            while ((r = is.read(buffer)) != -1) {
                os.write(buffer, 0, r);
            }
            os.flush();
        } catch (IOException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading file");
        }
    }
}
