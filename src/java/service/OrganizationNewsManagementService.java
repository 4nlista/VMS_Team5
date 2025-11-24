/*
 * A friendly reminder to drink enough water
 */
package service;

import dao.OrganizationNewsManagementDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.New;

/**
 *
 * @author Mirinae
 */
public class OrganizationNewsManagementService {

    private final OrganizationNewsManagementDAO newsManDAO = new OrganizationNewsManagementDAO();
    private final int pageSize = 7; // configurable if needed
    private static final long MAX_IMAGE_SIZE = 2 * 1024 * 1024; // 2MB

    // Validate input and return field errors
    public Map<String, String> validateNewsInput(HttpServletRequest request, Part filePart) {
        Map<String, String> errors = new HashMap<>();

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (title == null || title.trim().isEmpty()) {
            errors.put("title", "Tiêu đề không được để trống.");
        }

        if (content == null || content.trim().isEmpty()) {
            errors.put("content", "Nội dung không được để trống.");
        }

        // Image validation (optional)
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName().toLowerCase();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")
                    && !fileName.endsWith(".png") && !fileName.endsWith(".gif")) {
                errors.put("image", "Chỉ cho phép các định dạng ảnh JPG, PNG, GIF.");
            }
            if (filePart.getSize() > MAX_IMAGE_SIZE) {
                errors.put("image", "Ảnh quá lớn. Kích thước tối đa 2MB.");
            }
        }

        return errors;
    }

    public List<New> getNewsByPage(int page, int organizationId, String status, String search) throws SQLException {
        if (page < 1) {
            page = 1;
        }
        return newsManDAO.getNewsWithFiltersAndPagination(page, pageSize, organizationId, status, search);
    }

    public int getTotalPages(int organizationId, String status, String search) throws SQLException {
        int total = newsManDAO.getFilteredNewsCount(organizationId, status, search);
        return (int) Math.ceil((double) total / pageSize);
    }

    public void loadNewsList(HttpServletRequest request) throws SQLException, ServletException {
        Integer organizationId = getOrganizationIdFromSession(request);
        if (organizationId == null) {
            throw new ServletException("No logged-in organization found in session.");
        }

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {
            }
        }

        String status = request.getParameter("status");
        String search = request.getParameter("search");

        List<New> news = getNewsByPage(page, organizationId, status, search);
        int totalPages = getTotalPages(organizationId, status, search);

        request.setAttribute("news", news);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentStatus", status == null ? "" : status);
        request.setAttribute("currentSearch", search == null ? "" : search);
    }

    public Integer getOrganizationIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        //  check "account" attribute
        Object acc = session.getAttribute("account");
        if (acc != null) {
            try {
                // attempt common methods via reflection to avoid compile dependency
                java.lang.reflect.Method method;
                try {
                    method = acc.getClass().getMethod("getOrganizationId");
                    Object val = method.invoke(acc);
                    if (val instanceof Integer) {
                        return (Integer) val;
                    }
                    if (val instanceof Number) {
                        return ((Number) val).intValue();
                    }
                } catch (NoSuchMethodException ignored) {
                }

                try {
                    method = acc.getClass().getMethod("getOrgId");
                    Object val = method.invoke(acc);
                    if (val instanceof Integer) {
                        return (Integer) val;
                    }
                    if (val instanceof Number) {
                        return ((Number) val).intValue();
                    }
                } catch (NoSuchMethodException ignored) {
                }

                try {
                    method = acc.getClass().getMethod("getId");
                    Object val = method.invoke(acc);
                    if (val instanceof Integer) {
                        return (Integer) val;
                    }
                    if (val instanceof Number) {
                        return ((Number) val).intValue();
                    }
                } catch (NoSuchMethodException ignored) {
                }
            } catch (Exception e) {
                return null;
            }
        }
        // nothing found
        return null;
    }

    // Load the details of news
    public New loadNewsDetail(HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Integer orgId = getOrganizationIdFromSession(request);
        if (orgId == null) {
            return null;
        }
        return newsManDAO.getNewsDetailById(id, orgId);
    }

    //Update news
    public boolean updateNews(HttpServletRequest request, String imageFileName) throws Exception {
        Integer orgId = getOrganizationIdFromSession(request);
        if (orgId == null) {
            throw new Exception("Không tìm thấy tổ chức trong phiên đăng nhập.");
        }

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title").trim();
        String content = request.getParameter("content").trim();
        String status = request.getParameter("status").trim();

        return newsManDAO.updateNews(id, orgId, title, content, imageFileName, status);
    }

    public New buildNewsFromRequest(HttpServletRequest request) {
        Integer orgId = getOrganizationIdFromSession(request);
        return new New(
                request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0,
                request.getParameter("title"),
                request.getParameter("content"),
                request.getParameter("existingImage"),
                null, null,
                orgId != null ? orgId : 0,
                request.getParameter("status"),
                null
        );
    }

    // Create news
    public int createNewsWithImage(HttpServletRequest request, String imageFileName) throws Exception {
        Integer orgId = getOrganizationIdFromSession(request);
        if (orgId == null) {
            throw new Exception("Không tìm thấy tổ chức trong phiên đăng nhập.");
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        Map<String, String> errors = new HashMap<>();
        if (title == null || title.trim().isEmpty()) {
            errors.put("title", "Tiêu đề không được để trống.");
        }
        if (content == null || content.trim().isEmpty()) {
            errors.put("content", "Nội dung không được để trống.");
        }
        if (!errors.isEmpty()) {
            // throw a single exception or return the errors to the servlet — you already send fieldErrors separately,
            // but to keep the old behavior, throw an exception
            throw new Exception("Vui lòng điền đầy đủ thông tin.");
        }

        int newId = newsManDAO.insertNews(orgId, title.trim(), content.trim(), imageFileName);
        if (newId <= 0) {
            throw new Exception("Không thể tạo bài viết. Vui lòng thử lại.");
        }

        return newId;
    }

    public New buildNewsFromRequest(HttpServletRequest request, Part filePart) {
        Integer orgId = getOrganizationIdFromSession(request);
        String uploadedImage = null;
        if (filePart != null && filePart.getSize() > 0) {
            uploadedImage = filePart.getSubmittedFileName(); // temporary preview only
        }
        return new New(
                0,
                request.getParameter("title"),
                request.getParameter("content"),
                uploadedImage,
                null, null,
                orgId != null ? orgId : 0,
                null, // status will not be set
                null
        );
    }

    // Delete
    public boolean deleteNews(HttpServletRequest request) throws Exception {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            return false;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            return false;
        }

        Integer organizationId = getOrganizationIdFromSession(request);
        if (organizationId == null) {
            // no org in session -> unauthorized
            return false;
        }

        // ensure the news exists and belongs to this organization
        New existing = newsManDAO.getNewsDetailById(id, organizationId);
        if (existing == null) {
            // not found or not owned by this org
            return false;
        }

        // attempt DB delete
        boolean deleted = newsManDAO.deleteNewsByIdAndOrgId(id, organizationId);
        if (!deleted) {
            return false;
        }

        // best-effort: try to delete image file from server if present
        try {
            String imageFile = existing.getImages(); // or getImages() / getImagesFileName() depending on your model
            if (imageFile != null && !imageFile.trim().isEmpty()) {
                String uploadsDir = request.getServletContext().getRealPath("/uploads/news");
                java.io.File file = new java.io.File(uploadsDir, imageFile);
                if (file.exists()) {
                    file.delete(); // ignore result; it's just cleanup
                }
            }
        } catch (Exception e) {
            // don't fail the delete because of file-system issues; log and continue
            e.printStackTrace();
        }
        return true;
    }
}
