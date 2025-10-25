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
import java.util.List;
import model.Event;
import service.DisplayEventService;
import service.SumDisplayService;

/**
 *
 * @author ADDMIN
 */
@WebServlet("/home")
public class GuessHomeServlet extends HttpServlet {

    private SumDisplayService sumService;
    private DisplayEventService eventService;

    @Override
    public void init() {
        sumService = new SumDisplayService();
        eventService = new DisplayEventService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double totalDonationSystem = sumService.getTotalDonations();
        // lấy danh sách event đang hoạt động công khai (đã phân trang)
        List<Event> lastEvents = eventService.getLatestActivePublicEvents();
        
        request.setAttribute("totalDonationSystem", totalDonationSystem);
        request.setAttribute("lastEvents", eventService.getLatestActivePublicEvents());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
