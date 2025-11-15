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
import model.Account;
import model.Donation;
import service.AccountService;
import service.DisplayDonateService;

/**
 *
 * @author HP
 */
@WebServlet(name = "VolunteerDonateServlet", urlPatterns = {"/VolunteerDonateServlet"})
public class VolunteerDonateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private DisplayDonateService displayDonateService = new DisplayDonateService();
    private AccountService accountService = new AccountService();

    @Override
    public void init() {
        displayDonateService = new DisplayDonateService();
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());

        if (acc == null || !acc.getRole().equals("volunteer")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        session.setAttribute("username", acc.getUsername());
        int volunteerId = acc.getId();

        // Lấy thông tin phân trang và filter
        int pageIndex = 1;
        int pageSize = 5; // Đổi từ 10 thành 5
        String pageParam = request.getParameter("page");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String statusFilter = request.getParameter("status");

        if (pageParam != null) {
            try {
                pageIndex = Integer.parseInt(pageParam);
                if (pageIndex < 1) {
                    pageIndex = 1;
                }
            } catch (NumberFormatException e) {
                pageIndex = 1;
            }
        }

        // Kiểm tra có filter không
        boolean hasDateFilter = (startDate != null && !startDate.trim().isEmpty()) 
                             || (endDate != null && !endDate.trim().isEmpty());
        boolean hasStatusFilter = (statusFilter != null && !statusFilter.trim().isEmpty() && !"all".equals(statusFilter));
        boolean hasAnyFilter = hasDateFilter || hasStatusFilter;

        List<Donation> volunteerDonations;
        int totalDonations;
        
        if (hasAnyFilter) {
            // Lấy với filter đầy đủ
            volunteerDonations = displayDonateService.getUserDonationsWithAllFilters(
                volunteerId, pageIndex, pageSize, startDate, endDate, statusFilter
            );
            totalDonations = displayDonateService.getTotalDonationsWithAllFilters(
                volunteerId, startDate, endDate, statusFilter
            );
        } else {
            // Lấy bình thường
            volunteerDonations = displayDonateService.getUserDonationsPaged(volunteerId, pageIndex, pageSize);
            totalDonations = displayDonateService.getTotalDonationsByVolunteer(volunteerId);
        }

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalDonations / pageSize);

        // Lấy top3 và all (nếu cần)
        List<Donation> top3Donations = displayDonateService.getTop3UserDonation();
        List<Donation> allDonations = displayDonateService.getAllUserDonation();

        // Set attributes
        request.setAttribute("volunteerDonations", volunteerDonations);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("statusFilter", statusFilter != null ? statusFilter : "all");
        request.setAttribute("top3Donations", top3Donations);
        request.setAttribute("allDonations", allDonations);

        request.getRequestDispatcher("/volunteer/history_transaction_volunteer.jsp").forward(request, response);
    }

}
