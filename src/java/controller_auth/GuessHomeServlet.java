/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.SumDisplayService;

/**
 *
 * @author ADDMIN
 */
//@WebServlet(name = "GuessHomeServlet", urlPatterns = {"/guess"})
@WebServlet("/home")
public class GuessHomeServlet extends HttpServlet {

    private SumDisplayService sumService;

    @Override
    public void init() {
        sumService = new SumDisplayService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double totalDonations = sumService.getTotalDonations();
        request.setAttribute("totalDonations", totalDonations);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
