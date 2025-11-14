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
            
            // Lấy tham số page cho phân trang feedback
            String feedbackPageParam = request.getParameter("feedbackPage");
            int feedbackPage = 1;
            if (feedbackPageParam != null && !feedbackPageParam.isEmpty()) {
                try {
                    feedbackPage = Integer.parseInt(feedbackPageParam);
                } catch (NumberFormatException e) {
                    feedbackPage = 1;
                }
            }
            
            int pageSize = 5;
            
            // Lấy danh sách feedback với phân trang
            List<Feedback> feedbacks = feedbackDAO.getValidFeedbacksByEventIdPaged(eventId, feedbackPage, pageSize);
            
            // Đếm tổng số feedback
            int totalFeedbacks = feedbackDAO.countValidFeedbacksByEventId(eventId);
            int totalPages = (int) Math.ceil((double) totalFeedbacks / pageSize);
            
            // Lấy tham số page để quay lại đúng trang sự kiện
            String eventPageParam = request.getParameter("page");
            int eventPage = 1;
            if (eventPageParam != null && !eventPageParam.isEmpty()) {
                try {
                    eventPage = Integer.parseInt(eventPageParam);
                } catch (NumberFormatException e) {
                    eventPage = 1;
                }
            }
            
            // Set attributes
            request.setAttribute("event", event);
            request.setAttribute("feedbacks", feedbacks);
            request.setAttribute("currentPage", feedbackPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("returnPage", eventPage);
            request.setAttribute("eventId", eventId);
            
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

