package controller_volunteer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import dao.UserDAO;
import model.Account;
import model.Event;
import model.User;
import service.VolunteerDonationService;

@WebServlet(name = "VolunteerDonateFormServlet", urlPatterns = {"/VolunteerDonateFormServlet"})
public class VolunteerDonateFormServlet extends HttpServlet {

    private VolunteerDonationService donationService;

    @Override
    public void init() {
        donationService = new VolunteerDonationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy `eventId` từ tham số URL
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Lấy `volunteerId` và `username` của volunteer từ session
        HttpSession session = request.getSession();
        Integer volunteerId = (Integer) session.getAttribute("accountId");
        String volunteerName = (String) session.getAttribute("username");

        // Ưu tiên lấy thông tin từ đối tượng `Account` trong session nếu có
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            volunteerId = account.getId();
            volunteerName = account.getUsername();
        }

        if (volunteerId == null) {
            response.sendRedirect(request.getContextPath() + "/VolunteerHomeServlet");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);

                // Kiểm tra volunteer đã donate cho event này chưa
            boolean alreadyDonated = donationService.hasVolunteerDonated(volunteerId, eventId);
            if (alreadyDonated) {
                session.setAttribute("errorMessage", "Bạn đã ủng hộ sự kiện này rồi!");
                response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
                return;
            }

            // Lấy thông tin `Event` để hiển thị form donate
            Event event = donationService.getEventById(eventId);

            if (event == null) {
                request.setAttribute("errorMessage", "Không tìm thấy sự kiện!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // Lấy thông tin chi tiết của volunteer từ bảng `User` (full_name, email, phone)
            UserDAO userDAO = new UserDAO();
            User volunteerUser = userDAO.getUserByAccountId(volunteerId);
            
            // Nếu `full_name` tồn tại thì hiển thị; nếu không thì dùng username
            String displayName = volunteerName;
            String volunteerEmail = null;
            String volunteerPhone = null;
            
            if (volunteerUser != null) {
                if (volunteerUser.getFull_name() != null && !volunteerUser.getFull_name().trim().isEmpty()) {
                    displayName = volunteerUser.getFull_name();
                }
                volunteerEmail = volunteerUser.getEmail();
                volunteerPhone = volunteerUser.getPhone();
            }

            // Đưa dữ liệu cần thiết vào request để JSP hiển thị form thanh toán
            request.setAttribute("event", event);
            request.setAttribute("volunteerId", volunteerId);
            request.setAttribute("volunteerName", displayName);
            request.setAttribute("volunteerEmail", volunteerEmail);
            request.setAttribute("volunteerPhone", volunteerPhone);

            request.getRequestDispatcher("/volunteer/payment_volunteer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/VolunteerEventServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect POST to GET
        doGet(request, response);
    }

    @Override
    public void destroy() {
        // Close service when servlet is destroyed
        if (donationService != null) {
            donationService.close();
        }
    }
}
