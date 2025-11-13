package controller_organization;

import java.io.IOException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;

/**
 *
 * @author ADDMIN
 */
@MultipartConfig
@WebServlet(name="NewsImageServlet", urlPatterns={"/NewsImage"})
public class NewsImageServlet extends HttpServlet {

    private static final String UPLOAD_DIR =
            System.getProperty("user.home") + File.separator + "VMS_Uploads" + File.separator + "news";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String fileName = req.getParameter("file");
        if (fileName == null || fileName.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file");
            return;
        }

        File file = new File(UPLOAD_DIR, fileName);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        resp.setContentType(getServletContext().getMimeType(fileName));
        resp.setContentLengthLong(file.length());
        java.nio.file.Files.copy(file.toPath(), resp.getOutputStream());
    }
}