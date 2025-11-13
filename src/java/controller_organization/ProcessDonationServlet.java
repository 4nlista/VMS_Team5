package controller_organization;

import dao.AdminUserDAO;
import dao.NotificationDAO;
import dao.OrganizationDetailEventDAO;
import dao.ViewEventsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import model.Account;
import model.Event;
import model.Notification;
import model.User;

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
        String volunteerIdParam = request.getParameter("volunteerId");
        String pageParam = request.getParameter("page");
        if (donationIdParam == null || action == null || eventIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/organization/events_org.jsp");
            return;
        }

        try {
            int donationId = Integer.parseInt(donationIdParam);
            int eventId = Integer.parseInt(eventIdParam);
            int volunteerId = Integer.parseInt(volunteerIdParam);

            OrganizationDetailEventDAO dao = new OrganizationDetailEventDAO();
            boolean success = false;

            if ("approve".equals(action)) {
                success = dao.approveDonation(donationId, eventId);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Đã chấp nhận đơn donate!");
                    // GỬI THÔNG BÁO CHO VOLUNTEER
                    try {
                        HttpSession session = request.getSession();
                        Account acc = (Account) session.getAttribute("account");

                        AdminUserDAO userDAO = new AdminUserDAO();
                        User orgUser = userDAO.getUserByAccountId(acc.getId());
                        String orgName = (orgUser != null) ? orgUser.getFull_name() : "Tổ chức";

                        ViewEventsDAO viewEventDAO = new ViewEventsDAO();
                        Event event = viewEventDAO.getEventById(eventId);
                        String eventTitle = (event != null) ? event.getTitle() : "sự kiện";

                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId());
                        noti.setReceiverId(volunteerId);
                        noti.setMessage("Tổ chức " + orgName + " đã chấp nhận đơn donate vào sự kiện \"" + eventTitle + "\" của bạn");
                        noti.setType("donation");
                        noti.setEventId(eventId);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if ("reject".equals(action)) {
                success = dao.rejectDonation(donationId);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Đã từ chối đơn donate!");
                    // GỬI THÔNG BÁO CHO VOLUNTEER
                    try {
                        HttpSession session = request.getSession();
                        Account acc = (Account) session.getAttribute("account");

                        AdminUserDAO userDAO = new AdminUserDAO();
                        User orgUser = userDAO.getUserByAccountId(acc.getId());
                        String orgName = (orgUser != null) ? orgUser.getFull_name() : "Tổ chức";

                        ViewEventsDAO viewEventDAO = new ViewEventsDAO();
                        Event event = viewEventDAO.getEventById(eventId);
                        String eventTitle = (event != null) ? event.getTitle() : "sự kiện";

                        NotificationDAO notiDAO = new NotificationDAO();
                        Notification noti = new Notification();
                        noti.setSenderId(acc.getId());
                        noti.setReceiverId(volunteerId);
                        noti.setMessage("Tổ chức " + orgName + " đã từ chối đơn donate vào sự kiện \"" + eventTitle + "\" của bạn");
                        noti.setType("donation");
                        noti.setEventId(eventId);

                        notiDAO.insertNotification(noti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        String redirectUrl = request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventIdParam;
        if (pageParam != null && !pageParam.isEmpty()) {
            redirectUrl += "&page=" + pageParam;
        }

        // Redirect về trang detail
        response.sendRedirect(request.getContextPath() + "/OrganizationDetailEventServlet?eventId=" + eventIdParam);
    }
}
