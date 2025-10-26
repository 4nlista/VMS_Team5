/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Donation;
import model.Event;
import model.New;
import service.DisplayDonateService;
import service.DisplayEventService;
import service.DisplayNewService;
import service.SumDisplayService;

/**
 *
 * @author ADDMIN
 */
@WebServlet("/home")
public class GuessHomeServlet extends HttpServlet {

    private SumDisplayService sumService;
    private DisplayEventService eventService;
    private DisplayDonateService donateService;
    private DisplayNewService displayNewService;
    

    @Override
    public void init() {
        sumService = new SumDisplayService();
        eventService = new DisplayEventService();
        donateService = new DisplayDonateService();
        displayNewService = new DisplayNewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double totalDonationSystem = sumService.getTotalDonations();
        // lấy danh sách event đang hoạt động công khai (đã phân trang)
        List<Event> lastEvents = eventService.getLatestActivePublicEvents();
        List<Donation> topDonates = donateService.getTop3UserDonation();
        List<New> allNews = displayNewService.getTop3PostNews();
        
        request.setAttribute("totalDonationSystem", totalDonationSystem);
        request.setAttribute("lastEvents", eventService.getLatestActivePublicEvents());
        request.setAttribute("topDonates", donateService.getTop3UserDonation());
        request.setAttribute("allNews", displayNewService.getTop3PostNews());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
