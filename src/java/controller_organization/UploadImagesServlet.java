package controller_organization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet(name = "UploadImagesServlet", urlPatterns = {"/UploadImagesServlet"})
@MultipartConfig
public class UploadImagesServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "C:\\Users\\Admin\\Downloads\\uploads\\user_avatars";

    // ✅ POST = upload file
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("eventImage");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Lưu file
            String filePath = UPLOAD_DIR + File.separator + fileName;
            filePart.write(filePath);

            // Lưu tên file vào request để JSP hiển thị
            request.setAttribute("uploadedFileName", fileName);
        }

        // Forward về form tạo sự kiện
        request.getRequestDispatcher("/organization/create_events_org.jsp").forward(request, response);
    }

    // ✅ GET = hiển thị ảnh
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("file");
        if (fileName == null || fileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name is required");
            return;
        }

        File file = new File(UPLOAD_DIR, fileName);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        // Xác định MIME type
        response.setContentType(getServletContext().getMimeType(file.getName()));
        response.setContentLengthLong(file.length());

        // Gửi file về client
        java.nio.file.Files.copy(file.toPath(), response.getOutputStream());
    }
}
