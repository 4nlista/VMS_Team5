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
import dao.VolunteerFeedbackDAO;
import dao.ViewEventsDAO;
import model.Feedback;
import model.Event;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ViewFeedbackEventsServlet", urlPatterns = {"/ViewFeedbackEventsServlet"})
public class ViewFeedbackEventsServlet extends HttpServlet {

    private VolunteerFeedbackDAO feedbackDAO;
    private ViewEventsDAO eventDAO;

    @Override
    public void init() {
        feedbackDAO = new VolunteerFeedbackDAO();
        eventDAO = new ViewEventsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventIdParam = request.getParameter("eventId");
        
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);
            
            // Lấy thông tin event để hiển thị tên
            Event event = eventDAO.getEventById(eventId);
            if (event == null) {
                response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
                return;
            }
            
            // Lấy danh sách feedback valid của event
            List<Feedback> feedbacks = feedbackDAO.getValidFeedbacksByEventId(eventId);
            
            // Lấy tham số page để quay lại đúng trang
            String pageParam = request.getParameter("page");
            int page = 1;
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }
            
            // Set attributes
            request.setAttribute("event", event);
            request.setAttribute("feedbacks", feedbacks);
            request.setAttribute("returnPage", page);
            
            // Forward to JSP
            request.getRequestDispatcher("/feedback_events.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/GuessEventServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

