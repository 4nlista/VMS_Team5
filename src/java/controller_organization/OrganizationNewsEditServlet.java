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
import java.io.File;
import java.nio.file.Paths;
import model.New;
import service.FileStorageService;
import service.OrganizationNewsManagementService;

/**
 *
 * @author Mirinae
 */
@MultipartConfig
@WebServlet(name = "OrganizationNewsEditServlet", urlPatterns = {"/OrganizationNewsEdit"})
public class OrganizationNewsEditServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	}

	private final OrganizationNewsManagementService service = new OrganizationNewsManagementService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		try {
			New news = service.loadNewsDetail(request);
			if (news == null) {
				response.sendRedirect("OrganizationManageNews?error=notfound");
				return;
			}
			request.setAttribute("news", news);
			request.getRequestDispatcher("/organization/edit_news_org.jsp").forward(request, response);
		} catch (Exception e) {
			response.sendRedirect("OrganizationManageNews?error=invalid");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		    throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		// old image from hidden field
		String existingImage = req.getParameter("existingImage");
		String finalImageName = existingImage;

		// new upload
		Part imagePart = req.getPart("newsImage");
		if (imagePart != null && imagePart.getSize() > 0) {
			FileStorageService storage = new FileStorageService();
			String saved = storage.saveNewsImage(imagePart.getInputStream(), imagePart.getSubmittedFileName());
			if (saved != null) {
				finalImageName = saved;
			}
		}

		try {
			boolean success = service.updateNews(req, finalImageName);

			if (!success) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to update news");
				return;
			}

			String id = req.getParameter("id");
			resp.sendRedirect(req.getContextPath() + "/OrganizationNewsDetail?id=" + id);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
