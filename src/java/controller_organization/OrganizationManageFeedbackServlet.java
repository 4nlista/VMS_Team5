package controller_organization;

import service.OrganizationFeedbackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Account;
import model.Feedback;

@WebServlet(name = "OrganizationManageFeedbackServlet", urlPatterns = {"/OrganizationManageFeedbackServlet"})
public class OrganizationManageFeedbackServlet extends HttpServlet {

    private OrganizationFeedbackService feedbackService;

    @Override
    public void init() {
        feedbackService = new OrganizationFeedbackService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        if (acc == null || !"organization".equalsIgnoreCase(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        String eventIdParam = request.getParameter("eventId");
        String ratingParam = request.getParameter("rating");
        String status = request.getParameter("status");
        String q = request.getParameter("q");

        Integer rating = null;
        try {
            if (ratingParam != null && !ratingParam.isEmpty()) {
                rating = Integer.parseInt(ratingParam);
            }
        } catch (NumberFormatException ignore) {}

        Integer eventId = null;
        try {
            if (eventIdParam != null && !eventIdParam.isEmpty()) {
                eventId = Integer.parseInt(eventIdParam);
            }
        } catch (NumberFormatException ignore) {}

        List<Feedback> feedbacks = feedbackService.listFeedbacksForOrganization(acc.getId(), eventId, rating, status, q);

        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("eventId", eventIdParam == null ? "" : eventIdParam);
        request.setAttribute("rating", ratingParam == null ? "" : ratingParam);
        request.setAttribute("status", status == null ? "" : status);
        request.setAttribute("q", q == null ? "" : q);

        request.getRequestDispatcher("/organization/manage_feedback_org.jsp").forward(request, response);
    }
}


