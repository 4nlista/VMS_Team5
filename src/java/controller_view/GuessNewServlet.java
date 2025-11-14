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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        // Get page parameter
        int page = 1;
        int limit = 3;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int offset = (page - 1) * limit;

        // Check if filtering by date range
        String startDateTime = request.getParameter("startDateTime");
        String endDateTime = request.getParameter("endDateTime");

        if (startDateTime != null && !startDateTime.isEmpty()
                && endDateTime != null && !endDateTime.isEmpty()) {

            // Validate date range
            try {
                // Convert to SQL format (from HTML datetime-local format)
                String startDateTimeSQL = startDateTime.replace("T", " ") + ":00";
                String endDateTimeSQL = endDateTime.replace("T", " ") + ":00";

                // Parse dates for validation
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = sdf.parse(startDateTimeSQL);
                Date endDate = sdf.parse(endDateTimeSQL);
                Date currentDate = new Date();

                // Validation checks
                if (startDate.after(endDate)) {
                    // Start date is after end date - error
                    request.setAttribute("errorMessage", "Ngày bắt đầu không thể sau ngày kết thúc!");
                    request.setAttribute("startDateTime", startDateTime);
                    request.setAttribute("endDateTime", endDateTime);
                    request.setAttribute("allNews", new ArrayList<New>());
                } else if (startDate.after(currentDate)) {
                    // Start date is in the future - error
                    request.setAttribute("errorMessage", "Ngày bắt đầu không thể là ngày tương lai!");
                    request.setAttribute("startDateTime", startDateTime);
                    request.setAttribute("endDateTime", endDateTime);
                    request.setAttribute("allNews", new ArrayList<New>());
                } else {
                    // Valid date range - filter news WITH PAGINATION
                    List<New> listNews = displayNewService.getNewsByDateTimeRangePaged(
                            startDateTimeSQL, endDateTimeSQL, offset, limit
                    );
                    int totalNews = displayNewService.countNewsByDateTimeRange(
                            startDateTimeSQL, endDateTimeSQL
                    );
                    int totalPages = (int) Math.ceil((double) totalNews / limit);

                    request.setAttribute("allNews", listNews);
                    request.setAttribute("isFiltered", true);
                    request.setAttribute("startDateTime", startDateTime);
                    request.setAttribute("endDateTime", endDateTime);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("totalNews", totalNews);

                    // Warning if end date is in the future
                    if (endDate.after(currentDate)) {
                        request.setAttribute("warningMessage", "Ngày kết thúc là tương lai, chỉ hiển thị tin tức đã đăng.");
                    }
                }
            } catch (ParseException e) {
                // Invalid date format
                request.setAttribute("errorMessage", "Định dạng ngày giờ không hợp lệ!");
                request.setAttribute("allNews", new ArrayList<New>());
                e.printStackTrace();
            }
        } else {
            // Regular pagination - no filter
            List<New> listNews = displayNewService.getActiveNewsPaged(offset, limit);
            int totalNews = displayNewService.getTotalActiveNews();
            int totalPages = (int) Math.ceil((double) totalNews / limit);

            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("allNews", listNews);
            request.setAttribute("isFiltered", false);
        }

        request.getRequestDispatcher("blog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
