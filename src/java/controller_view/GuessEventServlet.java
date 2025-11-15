package controller_view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.Event;
import service.DisplayEventService;
import service.VolunteerApplyService;

@WebServlet(name = "GuessEventServlet", urlPatterns = {"/GuessEventServlet"})
public class GuessEventServlet extends HttpServlet {

    private DisplayEventService displayService;
    private VolunteerApplyService volunteerapplyService;

    @Override
    public void init() {
        displayService = new DisplayEventService();
        volunteerapplyService = new VolunteerApplyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int limit = 6;
        String pageParam = request.getParameter("page");

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int offset = (page - 1) * limit;

        // Lấy filter parameters
        String categoryParam = request.getParameter("category");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String sortOrder = request.getParameter("sort");
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "desc"; // mặc định mới nhất
        }
        
        // Lấy các filter mới
        String slotFilter = request.getParameter("slotFilter"); // "full", "available", "all"
        String donateFilter = request.getParameter("donateFilter"); // "donated", "not_donated", "all"
        String applyStatusFilter = request.getParameter("applyStatusFilter"); // "rejected", "applied", "all"
        
        Integer categoryId = null;
        if (categoryParam != null && !categoryParam.isEmpty() && !"all".equals(categoryParam)) {
            try {
                categoryId = Integer.parseInt(categoryParam);
            } catch (NumberFormatException e) {
                categoryId = null;
            }
        }

        HttpSession session = request.getSession(false);
        Integer volunteerId = null;

        if (session != null) {
            Account acc = (Account) session.getAttribute("account");
            if (acc != null && "volunteer".equals(acc.getRole())) {
                volunteerId = acc.getId();
            }
        }
        
        // Lấy events với filter
        List<Event> events = displayService.getFilteredEventsPaged(categoryId, startDate, endDate, 
                                                                    sortOrder, offset, limit);
        
        // Kiểm tra trạng thái cho từng event
        if (volunteerId != null) {
            for (Event e : events) {
                boolean hasApplied = volunteerapplyService.hasApplied(volunteerId, e.getId());
                int rejectedCount = volunteerapplyService.countRejected(e.getId(), volunteerId);
                boolean isFull = volunteerapplyService.isEventFull(e.getId());
                boolean hasDonated = displayService.hasDonated(volunteerId, e.getId());

                e.setHasApplied(hasApplied);
                e.setRejectedCount(rejectedCount);
                e.setIsFull(isFull);
                e.setHasDonated(hasDonated);
            }
            
            // Áp dụng các filter nếu volunteer đã login
            if (slotFilter != null && !"all".equals(slotFilter)) {
                events.removeIf(e -> {
                    if ("full".equals(slotFilter)) {
                        return !e.isIsFull();
                    } else if ("available".equals(slotFilter)) {
                        return e.isIsFull();
                    }
                    return false;
                });
            }
            
            if (donateFilter != null && !"all".equals(donateFilter)) {
                events.removeIf(e -> {
                    if ("donated".equals(donateFilter)) {
                        return !e.isHasDonated();
                    } else if ("not_donated".equals(donateFilter)) {
                        return e.isHasDonated();
                    }
                    return false;
                });
            }
            
            if (applyStatusFilter != null && !"all".equals(applyStatusFilter)) {
                events.removeIf(e -> {
                    if ("rejected".equals(applyStatusFilter)) {
                        return e.getRejectedCount() < 1;
                    } else if ("applied".equals(applyStatusFilter)) {
                        return !e.isHasApplied();
                    }
                    return false;
                });
            }
        } else {
            // Nếu chưa login thì vẫn check full
            for (Event e : events) {
                boolean isFull = volunteerapplyService.isEventFull(e.getId());
                e.setIsFull(isFull);
            }
            
            // Chỉ cho phép lọc slot khi chưa login
            if (slotFilter != null && !"all".equals(slotFilter)) {
                events.removeIf(e -> {
                    if ("full".equals(slotFilter)) {
                        return !e.isIsFull();
                    } else if ("available".equals(slotFilter)) {
                        return e.isIsFull();
                    }
                    return false;
                });
            }
        }

        // Tính tổng pages sau khi filter
        int totalEvents = displayService.getTotalFilteredEvents(categoryId, startDate, endDate);
        int totalPages = (int) Math.ceil((double) totalEvents / limit);

        // Set attributes
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("events", events);
        request.setAttribute("categories", displayService.getAllCategories());
        request.setAttribute("selectedCategory", categoryParam != null ? categoryParam : "all");
        request.setAttribute("startDate", startDate != null ? startDate : "");
        request.setAttribute("endDate", endDate != null ? endDate : "");
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("slotFilter", slotFilter != null ? slotFilter : "all");
        request.setAttribute("donateFilter", donateFilter != null ? donateFilter : "all");
        request.setAttribute("applyStatusFilter", applyStatusFilter != null ? applyStatusFilter : "all");
        
        request.getRequestDispatcher("event.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
