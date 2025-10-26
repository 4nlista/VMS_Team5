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
        List<Donation> allDonates = displayDonateService.getAllUserDonation();
        request.setAttribute("allDonates", displayDonateService.getAllUserDonation());
        request.getRequestDispatcher("donate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
