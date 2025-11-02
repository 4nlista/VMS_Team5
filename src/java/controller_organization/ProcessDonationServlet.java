package controller_organization;

import dao.OrganizationDetailEventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ProcessDonationServlet", urlPatterns = {"/ProcessDonationServlet"})

public class ProcessDonationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String donationIdParam = request.getParameter("donationId");
        String action = request.getParameter("action"); // "approve" hoặc "reject"
        String eventIdParam = request.getParameter("eventId");

        if (donationIdParam == null || action == null || eventIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/organization/events_org.jsp");
            return;
        }

        try {
            int donationId = Integer.parseInt(donationIdParam);
            int eventId = Integer.parseInt(eventIdParam);

            OrganizationDetailEventDAO dao = new OrganizationDetailEventDAO();
            boolean success = false;

            if ("approve".equals(action)) {
                success = dao.approveDonation(donationId, eventId);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Đã chấp nhận đơn donate!");
                }
            } else if ("reject".equals(action)) {
                success = dao.rejectDonation(donationId);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Đã từ chối đơn donate!");
                }
            }

            if (!success) {
                request.getSession().setAttribute("errorMessage", "Xử lý đơn thất bại!");
            }

            dao.close();

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra!");
        }

        // Redirect về trang detail
        response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventIdParam);
    }
}
