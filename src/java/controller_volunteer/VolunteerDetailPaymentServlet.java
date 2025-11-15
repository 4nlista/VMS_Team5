package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import model.Donation;
import model.Event;
import service.AccountService;
import service.VolunteerDetailPaymentService;

@WebServlet(name = "VolunteerDetailPaymentServlet", urlPatterns = {"/VolunteerDetailPaymentServlet"}) 

public class VolunteerDetailPaymentServlet extends HttpServlet {
    
    private VolunteerDetailPaymentService detailPaymentService;
    private AccountService accountService;
    
    @Override
    public void init() {
        detailPaymentService = new VolunteerDetailPaymentService();
        accountService = new AccountService();
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        
        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId());
        
        if (acc == null || !"volunteer".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }
        
        // Lấy donationId từ parameter
        String donationIdParam = request.getParameter("donationId");
        if (donationIdParam == null || donationIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/VolunteerDonateServlet");
            return;
        }
        
        try {
            int donationId = Integer.parseInt(donationIdParam);
            
            // Lấy chi tiết donation
            Donation donation = detailPaymentService.getDonationDetailById(donationId);
            
            if (donation == null) {
                session.setAttribute("errorMessage", "Không tìm thấy thông tin donation!");
                response.sendRedirect(request.getContextPath() + "/VolunteerDonateServlet");
                return;
            }
            
            // Kiểm tra donation có thuộc về volunteer hiện tại không
            if (donation.getVolunteerId() != acc.getId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền xem donation này");
                return;
            }
            
            // Lấy thông tin chi tiết event
            Event event = detailPaymentService.getEventById(donation.getEventId());
            
            // Set attributes
            request.setAttribute("donation", donation);
            request.setAttribute("event", event);
            request.setAttribute("account", acc);
            
            // Forward đến JSP
            request.getRequestDispatcher("/volunteer/detail_history_payment_volunteer.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/VolunteerDonateServlet");
        }
    } 

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
