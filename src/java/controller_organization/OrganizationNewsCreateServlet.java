/*
 * A friendly reminder to drink enough water
 */
package controller_organization;

import dao.AdminUserDAO;
import dao.NotificationDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.Map;
import model.New;
import model.Notification;
import model.User;
import service.FileStorageService;
import service.OrganizationNewsManagementService;
import service.UnifiedImageUploadService;

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
                    } catch (NoSuchMethodException ignored) {
                    }
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
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    } catch (NoSuchMethodException ignored) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No account/user object found in session.");
        }

        // Preserve JSP attributes
        request.setAttribute("orgName", fullName == null ? "" : fullName);
        request.setAttribute("orgId", organizationId == null ? 0 : organizationId);

        // Preserve any previous input (after failed POST)
        if (request.getAttribute("newsInput") == null) {
            request.setAttribute("newsInput", new New(0, "", "", null, null, null, organizationId, null, null));
        }
        request.getRequestDispatcher("/organization/create_news_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FileStorageService storage = new FileStorageService();
        Part filePart = request.getPart("newsImage");
        Map<String, String> fieldErrors = service.validateNewsInput(request, filePart);

        // Preserve input
        New newsInput = service.buildNewsFromRequest(request, filePart);
        request.setAttribute("newsInput", newsInput);
        request.setAttribute("fieldErrors", fieldErrors);

        if (!fieldErrors.isEmpty()) {
            request.getRequestDispatcher("/organization/create_news_org.jsp").forward(request, response);
            return;
        }

        String imageFileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
            Map<String, Object> uploadResult = uploadService.uploadNewsImage(filePart, 0);
            if ((boolean) uploadResult.get("success")) {
                imageFileName = (String) uploadResult.get("fileName");
            } else {
                request.setAttribute("errorMessage", uploadResult.get("error"));
                request.getRequestDispatcher("/organization/create_news_org.jsp").forward(request, response);
                return;
            }
        }

        try {
            int newId = service.createNewsWithImage(request, imageFileName);
            // GỬI THÔNG BÁO CHO ADMIN
            try {
                HttpSession session = request.getSession();
                Integer orgId = (Integer) session.getAttribute("accountId");

                if (orgId != null) {
                    // Lấy fullName từ Users
                    AdminUserDAO accDAO = new AdminUserDAO();
                    User user = accDAO.getUserByAccountId(orgId);
                    String fullName = (user != null) ? user.getFull_name(): "Tổ chức";

                    NotificationDAO notiDAO = new NotificationDAO();
                    Notification noti = new Notification();
                    noti.setSenderId(orgId);
                    noti.setReceiverId(1); // Admin ID = 1
                    noti.setMessage("Tổ chức " + fullName + " đã tạo 1 tin tức mới");
                    noti.setType("system");
                    noti.setEventId(0);

                    notiDAO.insertNotification(noti);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/OrganizationManageNews?success=created");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/organization/create_news_org.jsp").forward(request, response);
        }
    }
}
