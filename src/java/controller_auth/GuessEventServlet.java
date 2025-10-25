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

@WebServlet(name = "GuessEventServlet", urlPatterns = {"/GuessEventServlet"})
//@WebServlet("/events")
public class GuessEventServlet extends HttpServlet {

    private DisplayEventService displayService;

    @Override
    public void init() {
        displayService = new DisplayEventService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1; // mặc định là trang đầu
        int limit = 3; // số event mỗi trang
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * limit;
        // lấy danh sách event đang hoạt động công khai (đã phân trang)
        List<Event> events = displayService.getActiveEventsPaged(offset, limit);
        //Tính tổng số event 
        int totalEvents = displayService.getTotalActiveEvents();
        int totalPages = (int) Math.ceil((double) totalEvents / limit);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("events", displayService.getActiveEventsPaged(offset, limit));
        request.getRequestDispatcher("event.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
