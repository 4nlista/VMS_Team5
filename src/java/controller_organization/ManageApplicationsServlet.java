/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_organization;

import dao.EventVolunteerDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.EventVolunteerInfo;

@WebServlet("/ManageApplicationsServlet")
public class ManageApplicationsServlet extends HttpServlet {

    private final EventVolunteerDAO evDAO = new EventVolunteerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer orgId = (Integer) session.getAttribute("accountId");

        if (orgId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<EventVolunteerInfo> list = evDAO.getPendingVolunteersByOrganization(orgId);
        request.setAttribute("applications", list);
        request.getRequestDispatcher("/organization/users_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int applyId = Integer.parseInt(request.getParameter("applyId"));
        String action = request.getParameter("action"); // approve / reject
        String newStatus = action.equals("approve") ? "approved" : "rejected";

        evDAO.updateStatus(applyId, newStatus);

        response.sendRedirect(request.getContextPath() + "/organization/manage-applications");
    }
}
