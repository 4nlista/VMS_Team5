/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.EventVolunteer;
import service.EventVolunteerService;
/**
 *
 * @author HP
 */
@WebServlet(name = "VolunteerEventServlet", urlPatterns = {"/VolunteerEventServlet"})
public class VolunteerHistoryEventServlet extends HttpServlet {

     private EventVolunteerService service = new EventVolunteerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer volunteerId = (Integer) session.getAttribute("accountId");

        if (volunteerId != null) {
            // Lấy params từ request
            String statusFilter = request.getParameter("status");
            if (statusFilter == null || statusFilter.isEmpty()) {
                statusFilter = "all";
            }
            
            String sortOrder = request.getParameter("sort");
            if (sortOrder == null || sortOrder.isEmpty()) {
                sortOrder = "desc"; // Mặc định mới nhất trước
            }
            
            String pageParam = request.getParameter("page");
            int currentPage = 1;
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }
            
            int pageSize = 10;
            
            // Lấy danh sách với filter + paging
            List<EventVolunteer> registrations = service.getEventRegistrationsFiltered(
                volunteerId, statusFilter, sortOrder, currentPage, pageSize
            );
            
            // Đếm tổng số records
            int totalRecords = service.countEventRegistrations(volunteerId, statusFilter);
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
            
            // Set attributes
            request.setAttribute("eventRegistrations", registrations);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("sortOrder", sortOrder);
        }

        request.getRequestDispatcher("/volunteer/history_event_volunteer.jsp").forward(request, response);
    }
}
