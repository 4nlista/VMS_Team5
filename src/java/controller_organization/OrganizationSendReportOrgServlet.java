package controller_organization;

import dao.OrganizationFeedbackDAO;
import dao.OrganizationReportDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;
import model.Feedback;

@WebServlet(name = "OrganizationSendReportOrgServlet", urlPatterns = {"/organization/send_report_org"})
public class OrganizationSendReportOrgServlet extends HttpServlet {

    private OrganizationFeedbackDAO feedbackDAO;
    private OrganizationReportDAO reportDAO;

    @Override
    public void init() {
        feedbackDAO = new OrganizationFeedbackDAO();
        reportDAO = new OrganizationReportDAO();
    }
    // ok em

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String feedbackIdParam = request.getParameter("feedbackId");
        Integer feedbackId = null;
        try {
            feedbackId = Integer.parseInt(feedbackIdParam);
        } catch (Exception ignored) {
        }

        Feedback fb = feedbackId == null ? null : feedbackDAO.findByIdWithJoin(feedbackId);
        request.setAttribute("feedback", fb);
        request.getRequestDispatcher("/organization/send_report_org.jsp").forward(request, response);
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

        int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
        String reason = request.getParameter("reason");

        reportDAO.insertPendingReport(feedbackId, acc.getId(), reason);

        response.sendRedirect(request.getContextPath() + "/OrganizationManageFeedbackServlet?reported=1");
    }
}
