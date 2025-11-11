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
import model.Donation;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy danh sách top 3 donate mới nhất
        List<Donation> top3Donations = displayDonateService.getTop3UserDonation();

        // Lấy danh sách tất cả donate
        List<Donation> allDonations = displayDonateService.getAllUserDonation();

        // Gửi dữ liệu sang JSP
        request.setAttribute("top3Donations", top3Donations);
        request.setAttribute("allDonations", allDonations);

        // Chuyển hướng đến trang JSP hiển thị
        request.getRequestDispatcher("/volunteer/history_transaction_volunteer.jsp").forward(request, response);
    }

}
