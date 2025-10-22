/**
 *
 * @author locng
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_organization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.EventService;
import java.io.IOException;
import java.util.List;
import model.Account;
import model.Event;
import model.Account;
import service.AccountService;

@WebServlet("/organization/events")
public class ListEventsServlet extends HttpServlet {
        private AccountService accountService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
//        Account acc = (Account) session.getAttribute("account");
//        acc = accountService.getAccountById(acc.getId());
//        Integer organizationId = (Integer) session.getAttribute("accountId");  // Từ session sau login
//        if (organizationId == null) {
//            response.sendRedirect("../auth/login.jsp");
//            return;
//        }

        EventService service = new EventService();
        List<Event> events = service.getAllEvent();
        request.setAttribute("events", events);
        request.getRequestDispatcher("organization/events_organization.jsp").forward(request, response);
    }
}