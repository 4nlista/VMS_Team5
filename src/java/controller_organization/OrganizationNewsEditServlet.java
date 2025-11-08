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
import java.util.Map;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

		FileStorageService storage = new FileStorageService();
		Part filePart = request.getPart("newsImage");
		Map<String, String> fieldErrors = service.validateNewsInput(request, filePart);

		// Preserve input
		New newsInput = service.buildNewsFromRequest(request);
		request.setAttribute("news", newsInput);
		request.setAttribute("fieldErrors", fieldErrors);

		if (!fieldErrors.isEmpty()) {
			request.getRequestDispatcher("/organization/edit_news_org.jsp").forward(request, response);
			return;
		}

		// Save image if uploaded
		String imageFileName = null;
		if (filePart != null && filePart.getSize() > 0) {
			imageFileName = storage.saveNewsImage(filePart.getInputStream(), filePart.getSubmittedFileName());
		}

		try {
			service.updateNews(request, imageFileName);
			response.sendRedirect(request.getContextPath() + "/OrganizationManageNews?success=updated");
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("/organization/edit_news_org.jsp").forward(request, response);
		}
	}
}
