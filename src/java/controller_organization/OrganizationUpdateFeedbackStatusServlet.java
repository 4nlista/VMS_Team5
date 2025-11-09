package controller_organization;

import service.OrganizationFeedbackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;

@WebServlet(name = "OrganizationUpdateFeedbackStatusServlet", urlPatterns = {"/OrganizationUpdateFeedbackStatusServlet"})
public class OrganizationUpdateFeedbackStatusServlet extends HttpServlet {

    private OrganizationFeedbackService feedbackService;

    @Override
    public void init() {
        feedbackService = new OrganizationFeedbackService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        String feedbackIdParam = request.getParameter("feedbackId");
        String status = request.getParameter("status");

        if (feedbackIdParam == null || status == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số");
            return;
        }

        try {
            int feedbackId = Integer.parseInt(feedbackIdParam);
            
            // Map "Hiện" to "valid" and "Ẩn" to "invalid"
            String dbStatus;
            if ("Hiện".equals(status) || "valid".equalsIgnoreCase(status)) {
                dbStatus = "valid";
            } else if ("Ẩn".equals(status) || "invalid".equalsIgnoreCase(status)) {
                dbStatus = "invalid";
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Trạng thái không hợp lệ");
                return;
            }

            boolean success = feedbackService.updateFeedbackStatus(feedbackId, dbStatus);
            
            if (success) {
                // Redirect back to feedback list with success message
                String redirectUrl = request.getContextPath() + "/OrganizationManageFeedbackServlet";
                String eventId = request.getParameter("eventId");
                if (eventId != null && !eventId.isEmpty()) {
                    redirectUrl += "?eventId=" + eventId;
                }
                redirectUrl += (redirectUrl.contains("?") ? "&" : "?") + "statusUpdated=1";
                response.sendRedirect(redirectUrl);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể cập nhật trạng thái");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID feedback không hợp lệ");
        }
    }
}

