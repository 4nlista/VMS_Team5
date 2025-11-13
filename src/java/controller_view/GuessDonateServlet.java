/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_view;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Donation;
import service.DisplayDonateService;

/**
 *
 * @author ADDMIN
 */
@WebServlet(name = "GuessDonateServlet", urlPatterns = {"/GuessDonateServlet"})
public class GuessDonateServlet extends HttpServlet {

    private DisplayDonateService displayDonateService;

    @Override
    public void init() {
        displayDonateService = new DisplayDonateService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1; // mặc định là trang đầu
        int limit = 6; // số donors mỗi trang
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * limit;
        // lấy danh sách donors đã phân trang
        List<Donation> listDonors = displayDonateService.getDonorsPaged(offset, limit);
        //Tính tổng số donors
        int totalDonors = displayDonateService.getTotalDonors();
        int totalPages = (int) Math.ceil((double) totalDonors / limit);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Set danh sách donors đã phân trang
        request.setAttribute("allDonates", listDonors);
        request.getRequestDispatcher("donate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
