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
import model.New;
import service.DisplayNewService;

/**
 *
 * @author ADDMIN
 */
@WebServlet(name = "GuessNewServlet", urlPatterns = {"/GuessNewServlet"})
public class GuessNewServlet extends HttpServlet {
    
    private DisplayNewService displayNewService;

    @Override
    public void init() {
        displayNewService = new DisplayNewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<New> allNews = displayNewService.getAllPostNews();
        request.setAttribute("allNews", displayNewService.getAllPostNews());
        request.getRequestDispatcher("blog.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
