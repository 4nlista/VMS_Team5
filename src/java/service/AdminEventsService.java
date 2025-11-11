/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AdminEventsDAO;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import model.Category;
import model.Event;

/**
 *
 * @author Admin
 */
public class AdminEventsService {

    private final AdminEventsDAO adminEventsDAO;

    public AdminEventsService() {
        this.adminEventsDAO = new AdminEventsDAO();
    }

    // Load danh sách sự kiện với filter và phân trang
    public void loadEventList(HttpServletRequest request) {
        String status = request.getParameter("status");
        String category = request.getParameter("category");
        String visibility = request.getParameter("visibility");
        String pageParam = request.getParameter("page");
        
        int page = 1;
        int pageSize = 5; // 5 items per page
        try {
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException ignored) {
        }

        // Normalize filter values
        if (status != null && status.isEmpty()) {
            status = null;
        }
        if (category != null && (category.isEmpty() || "Tất cả".equals(category))) {
            category = null;
        }
        if (visibility != null && (visibility.isEmpty() || "Tất cả".equals(visibility))) {
            visibility = null;
        }

        // Count total events
        int totalItems = adminEventsDAO.countEvents(status, category, visibility);
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        // Get paged events
        int offset = (page - 1) * pageSize;
        List<Event> events = adminEventsDAO.getEventsPaged(status, category, visibility, offset, pageSize);

        List<Category> categories = adminEventsDAO.getAllCategories();

        request.setAttribute("events", events);
        request.setAttribute("categories", categories);
        request.setAttribute("currentStatus", status == null ? "" : status);
        request.setAttribute("currentCategory", category == null ? "" : category);
        request.setAttribute("currentVisibility", visibility == null ? "" : visibility);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);
    }

    // Load chi tiết sự kiện
    public Event loadEventDetail(HttpServletRequest request) {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            Event event = adminEventsDAO.getEventDetailById(eventId);
            if (event != null) {
                int approvedCount = adminEventsDAO.getApprovedVolunteersCount(eventId);
                request.setAttribute("approvedVolunteersCount", approvedCount);
                request.setAttribute("event", event);
            }
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Khóa sự kiện (set status = inactive)
    public boolean lockEvent(int eventId) {
        return adminEventsDAO.updateEventStatus(eventId, "inactive");
    }
}

