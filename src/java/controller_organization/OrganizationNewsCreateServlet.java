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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import service.FileStorageService;
import service.OrganizationNewsManagementService;

/**
 *
 * @author Mirinae
 */
@MultipartConfig
@WebServlet(name = "OrganizationNewsCreateServlet", urlPatterns = {"/OrganizationNewsCreate"})
public class OrganizationNewsCreateServlet extends HttpServlet {

	private final OrganizationNewsManagementService service = new OrganizationNewsManagementService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);
    Object acc = (session != null) ? session.getAttribute("account") : null;
    if (acc == null && session != null) {
        // try alternative key
        acc = session.getAttribute("user");
    }

    // Debug: list session attrs so you can see what's available in logs
    if (session != null) {
        System.out.println("=== session attributes dump ===");
        java.util.Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            String n = names.nextElement();
            Object val = session.getAttribute(n);
            System.out.println("session.attr -> " + n + " = " + (val == null ? "null" : val.getClass().getName() + " : " + val.toString()));
        }
        System.out.println("=== end session dump ===");
    } else {
        System.out.println("No session found in OrganizationNewsCreateServlet.doGet");
    }

    String fullName = "";
    Integer organizationId = null;

    if (acc != null) {
        try {
            // Try several possible getter names for "full name"
            String[] nameGetters = {"getFullName", "getFullname", "getName", "getDisplayName", "getFull_name"};
            for (String getter : nameGetters) {
                try {
                    java.lang.reflect.Method m = acc.getClass().getMethod(getter);
                    Object val = m.invoke(acc);
                    if (val != null) {
                        fullName = val.toString();
                        break;
                    }
                } catch (NoSuchMethodException ignored) { }
            }

            // Try several possible getters for organization id
            String[] idGetters = {"getOrganizationId", "getOrgId", "getOrgID", "getOrganization_id", "getId", "getUserId", "getAccountId"};
            for (String getter : idGetters) {
                try {
                    java.lang.reflect.Method m = acc.getClass().getMethod(getter);
                    Object val = m.invoke(acc);
                    if (val instanceof Number) {
                        organizationId = ((Number) val).intValue();
                        break;
                    } else if (val != null) {
                        try {
                            organizationId = Integer.parseInt(val.toString());
                            break;
                        } catch (NumberFormatException ignored) { }
                    }
                } catch (NoSuchMethodException ignored) { }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("No account/user object found in session.");
    }

    // ensure non-null JSP attributes
    request.setAttribute("orgName", fullName == null ? "" : fullName);
    request.setAttribute("orgId", organizationId == null ? 0 : organizationId);

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
