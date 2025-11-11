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

        // Lấy thông tin phân trang
        int pageIndex = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");

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

        // Lấy tổng số donations
        int totalDonations = displayDonateService.getTotalDonationsByVolunteer(volunteerId);

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalDonations / pageSize);

        // Lấy danh sách donations theo trang
        List<Donation> volunteerDonations = displayDonateService.getUserDonationsPaged(volunteerId, pageIndex, pageSize);

        // Lấy top3 và all (nếu cần)
        List<Donation> top3Donations = displayDonateService.getTop3UserDonation();
        List<Donation> allDonations = displayDonateService.getAllUserDonation();

        // Set attributes
        request.setAttribute("volunteerDonations", volunteerDonations);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("top3Donations", top3Donations);
        request.setAttribute("allDonations", allDonations);

        request.getRequestDispatcher("/volunteer/history_transaction_volunteer.jsp").forward(request, response);
    }

}
