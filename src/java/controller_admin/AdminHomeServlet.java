package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Donation;
import model.Event;
import service.AccountService;
import service.AdminHomeService;

@WebServlet(name = "AdminHomeServlet", urlPatterns = {"/AdminHomeServlet"})

public class AdminHomeServlet extends HttpServlet {

    private AccountService accountService;
    private AdminHomeService adminHomeService;

    @Override
    public void init() {
        accountService = new AccountService();
        adminHomeService = new AdminHomeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        Account sessionAccount = (Account) session.getAttribute("account");
        Account acc = accountService.getAccountById(sessionAccount.getId());
        if (acc == null || !acc.getRole().equals("admin")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        List<Event> topEvents = adminHomeService.getTop3EventsMoneyDonate();
        List<Event> eventsComing = adminHomeService.getTop3EventsComing();
        Map<String, Integer> accountStats = adminHomeService.getAccountStatistics();
        int totalAccounts = adminHomeService.getTotalAccount();
        int totalEventsActive = adminHomeService.getTotalEventsActive();
        double totalMoneyDonate = adminHomeService.getTotalMoneyDonate();
        
        // Lấy thống kê donate theo tháng (5 tháng gần nhất)
        Map<String, Double> donationByMonth = adminHomeService.getDonationByMonth();
        
        // Lấy top 3 người donate nhiều nhất
        List<Donation> top3Donors = adminHomeService.getTop3Donors();
        
        // Lấy thống kê tài khoản đầy đủ (active + inactive)
        Map<String, Integer> allAccountStats = adminHomeService.getAllAccountStats();

        request.setAttribute("topEvents", topEvents);
        request.setAttribute("accountStats", accountStats);
        request.setAttribute("totalEventsActive", totalEventsActive);
        request.setAttribute("eventsComing", eventsComing);
        request.setAttribute("totalAccounts", totalAccounts);
        request.setAttribute("totalMoneyDonate", totalMoneyDonate);
        request.setAttribute("donationByMonth", donationByMonth);
        request.setAttribute("top3Donors", top3Donors);
        request.setAttribute("allAccountStats", allAccountStats);
        request.setAttribute("username", acc.getUsername());
        request.getRequestDispatcher("/admin/home_admin.jsp").forward(request, response);
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
