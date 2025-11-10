package controller_organization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Account;
import model.Event;
import model.ProfileVolunteer;
import model.User;
import service.AdminAccountService;
import service.OrganizationHomeService;
import service.OrganizationProfileService;

@WebServlet(name = "OrganizationHomeServlet", urlPatterns = {"/OrganizationHomeServlet"})

public class OrganizationHomeServlet extends HttpServlet {

	private AdminAccountService adminAccountService;
	private OrganizationProfileService profileService;
	private OrganizationHomeService organizationHomeService;

	@Override
	public void init() {
		adminAccountService = new AdminAccountService();
		profileService = new OrganizationProfileService(); // Initialize profile service
		organizationHomeService = new OrganizationHomeService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("account") == null) {
			// Nếu session không hợp lệ, điều hướng về login
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		Account acc = (Account) session.getAttribute("account");
		acc = adminAccountService.getAccountById(acc.getId());

		if (acc == null || !acc.getRole().equals("organization")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
			return;
		}

		// Lưu fullname vào session
		session.setAttribute("username", acc.getUsername());
		try {
			User profile = profileService.loadProfile(request); // uses account_id from session
			session.setAttribute("user", profile); // Sidebar has user on first load 
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Get organization statistics
		int organizationId = acc.getId();

		// Total statistics
		int totalEvents = organizationHomeService.getTotalEvents(organizationId);
		int totalVolunteerHours = organizationHomeService.getTotalVolunteerHours(organizationId);
		int totalVolunteers = organizationHomeService.getTotalVolunteers(organizationId);
		double totalDonations = organizationHomeService.getTotalDonations(organizationId);

		// Top 3 lists
		List<Event> topEvents = organizationHomeService.getTop3EventsByDonation(organizationId);
		List<ProfileVolunteer> topDonors = organizationHomeService.getTop3DonorVolunteers(organizationId);

		// Upcoming events
		List<Event> upcomingEvents = organizationHomeService.getUpcomingEvents(organizationId);

		// Monthly donation data for chart
		List<Double> monthlyDonations = organizationHomeService.getMonthlyDonations(organizationId);

		// Set attributes for JSP
		request.setAttribute("totalEvents", totalEvents);
		request.setAttribute("totalVolunteerHours", totalVolunteerHours);
		request.setAttribute("totalVolunteers", totalVolunteers);
		request.setAttribute("totalDonations", totalDonations);
		request.setAttribute("topEvents", topEvents);
		request.setAttribute("topDonors", topDonors);
		request.setAttribute("upcomingEvents", upcomingEvents);
		request.setAttribute("monthlyDonations", monthlyDonations);

		// Forward đến JSP, không redirect
		request.getRequestDispatcher("/organization/home_org.jsp").forward(request, response);
	}

	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

	}
}
