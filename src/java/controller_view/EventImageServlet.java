package controller_view;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Servlet để serve ảnh events từ thư mục upload
 * @author VMS_Team5
 */
@WebServlet(name="EventImageServlet", urlPatterns={"/EventImage"})
public class EventImageServlet extends HttpServlet {

    private static final String UPLOAD_DIR =
            System.getProperty("user.home") + File.separator + "VMS_Uploads" + File.separator + "events";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String fileName = req.getParameter("file");
        if (fileName == null || fileName.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file parameter");
            return;
        }

        File file = new File(UPLOAD_DIR, fileName);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + fileName);
            return;
        }

        resp.setContentType(getServletContext().getMimeType(fileName));
        resp.setContentLengthLong(file.length());
        java.nio.file.Files.copy(file.toPath(), resp.getOutputStream());
    }
}
