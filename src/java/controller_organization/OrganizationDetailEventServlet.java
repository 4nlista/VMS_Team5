package controller_organization;

import dao.OrganizationDetailEventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Donation;
import model.Event;

@WebServlet(name = "OrganizationDetailEventServlet", urlPatterns = {"/OrganizationDetailEventServlet"})
@MultipartConfig(
    maxFileSize = 2 * 1024 * 1024,      // 2MB
    maxRequestSize = 5 * 1024 * 1024    // 5MB total request
)
public class OrganizationDetailEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/organization/events_org.jsp");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);
            String pageParam = request.getParameter("page");
            int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
            int pageSize = 5; // số donates mỗi trang
            OrganizationDetailEventDAO dao = new OrganizationDetailEventDAO();

            Event event = dao.getEventById(eventId);
            List<Event> categories = dao.getAllCategories();
            List<Donation> donations;
            int totalPages;
            // tổng số donate
            int totalDonations = dao.countDonationsByEventId(eventId);

            if (totalDonations <= pageSize) {
                donations = dao.getDonationsByEventId(eventId); // Lấy tất cả
                totalPages = 1;
                currentPage = 1;
            } else {
                donations = dao.getDonationsByEventIdPaging(eventId, currentPage, pageSize);
                totalPages = (int) Math.ceil((double) totalDonations / pageSize);
            }
            if (event == null) {
                request.setAttribute("errorMessage", "Không tìm thấy sự kiện!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            dao.close();

            request.setAttribute("event", event);
            request.setAttribute("donations", donations);
            request.setAttribute("categories", categories);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalDonations", totalDonations);
            request.getRequestDispatcher("/organization/detail_event_org.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/organization/events_org.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String eventIdParam = request.getParameter("eventId");

        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/organization/events_org.jsp");
            return;
        }

        int eventId = Integer.parseInt(eventIdParam);
        OrganizationDetailEventDAO dao = new OrganizationDetailEventDAO();

        try {
            if ("update".equals(action)) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String location = request.getParameter("location");
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");
                int neededVolunteers = Integer.parseInt(request.getParameter("neededVolunteers"));
                String status = request.getParameter("status");
                String visibility = request.getParameter("visibility");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                java.util.Date startDate = sdf.parse(startDateStr);
                java.util.Date endDate = sdf.parse(endDateStr);

                //  THÊM VALIDATION
                java.util.Date now = new java.util.Date();
                Event currentEvent = dao.getEventById(eventId);

                // 1. Không cập nhật nếu sự kiện đã kết thúc (end_date < now)
                if (currentEvent.getEndDate().before(now)) {
                    request.getSession().setAttribute("errorMessage",
                            "Sự kiện đã kết thúc, không thể cập nhật!");
                    response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
                    return;
                }

                // 2. Không cập nhật trong vòng 24h trước khi sự kiện bắt đầu
                long hoursUntilStart = (currentEvent.getStartDate().getTime() - now.getTime()) / (60 * 60 * 1000);
                if (hoursUntilStart <= 24) {
                    request.getSession().setAttribute("errorMessage",
                            "Không thể cập nhật trong vòng 24h trước khi sự kiện diễn ra!");
                    response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
                    return;
                }

                // 3. Kiểm tra ngày bắt đầu phải >= hiện tại
                if (startDate.before(now)) {
                    request.getSession().setAttribute("errorMessage",
                            "Ngày bắt đầu không được ở quá khứ!");
                    response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
                    return;
                }

                // 4. Kiểm tra ngày kết thúc > ngày bắt đầu
                if (endDate.before(startDate) || endDate.equals(startDate)) {
                    request.getSession().setAttribute("errorMessage",
                            "Ngày kết thúc phải sau ngày bắt đầu!");
                    response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
                    return;
                }

                // 5. Không giảm số lượng volunteer
                int currentVolunteers = dao.countRegisteredVolunteers(eventId);
                if (neededVolunteers < currentVolunteers) {
                    request.getSession().setAttribute("errorMessage",
                            "Số lượng tình nguyện viên tối thiểu: " + currentVolunteers);
                    response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
                    return;
                }

                // 6. Kiểm tra trùng với event khác cùng org
                boolean isOverlap = dao.checkOverlapWithOtherEvents(currentEvent.getOrganizationId(), startDate, endDate, eventId);
                if (isOverlap) {
                    request.getSession().setAttribute("errorMessage",
                            "Có sự kiện khác đang active trùng thời gian!");
                    response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
                    return;
                }

                // Update event
                boolean success = dao.updateEvent(eventId, title, description, location,
                        new java.sql.Timestamp(startDate.getTime()),
                        new java.sql.Timestamp(endDate.getTime()),
                        neededVolunteers, status, visibility, categoryId);

                if (success) {
                    request.getSession().setAttribute("successMessage", "Cập nhật sự kiện thành công!");
                } else {
                    request.getSession().setAttribute("errorMessage", "Cập nhật thất bại!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
        } finally {
            dao.close();
        }

        response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventId);
    }
}
