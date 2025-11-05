/*
 * A friendly reminder to drink enough water
 */

package controller_organization;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import service.FileStorageService;
import service.OrganizationNewsManagementService;

/**
 *
 * @author Mirinae
 */
@MultipartConfig
@WebServlet(name="OrganizationNewsCreateServlet", urlPatterns={"/OrganizationNewsCreate"})
public class OrganizationNewsCreateServlet extends HttpServlet {
    private final OrganizationNewsManagementService service = new OrganizationNewsManagementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/organization/create_news_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String imageFileName = null;
        try {
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                FileStorageService storage = new FileStorageService();
                imageFileName = storage.saveNewsImage(
                        filePart.getInputStream(),
                        filePart.getSubmittedFileName()
                );
            }
            int newId = service.createNewsWithImage(request, imageFileName);
            if (newId > 0) {
                response.sendRedirect(request.getContextPath() + "/OrganizationManageNews?success=created");
            } else {
                response.sendRedirect(request.getContextPath() + "/organization/create_news_org.jsp?error=failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/organization/create_news_org.jsp?error=exception");
        }
    }
}