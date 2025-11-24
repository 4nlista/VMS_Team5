package controller_organization;

import dao.AccountDAO;
import dao.AdminUserDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

import java.io.IOException;
import java.util.Map;
import service.UnifiedImageUploadService;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Account;
import model.Event;
import model.Notification;
import model.User;
import service.AccountService;
import service.CreateEventsService;

@WebServlet(name = "OrganizationCreateEventServlet", urlPatterns = {"/OrganizationCreateEventServlet"})
@MultipartConfig
public class OrganizationCreateEventServlet extends HttpServlet {

    private CreateEventsService createEventsService = new CreateEventsService();
    private AccountService accountService;

    @Override
    public void init() {
        createEventsService = new CreateEventsService();
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        Account acc = (Account) session.getAttribute("account");
        acc = accountService.getAccountById(acc.getId()); // cập nhật từ DB
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        // Lưu fullname vào session
        request.setAttribute("account", acc);
        // Forward đến JSP, không redirect
        request.getRequestDispatcher("/organization/create_events_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        if (acc == null || !"organization".equals(acc.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        // Lấy dữ liệu từ form
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String status = request.getParameter("status");
        String visibility = request.getParameter("visibility");
        String categoryIdStr = request.getParameter("categoryId");
        String neededVolunteersStr = request.getParameter("neededVolunteers");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        // Upload ảnh sự kiện
        Part filePart = request.getPart("eventImage");
        UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
        Map<String, Object> uploadResult = uploadService.uploadEventImage(filePart, 0);
        
        String fileName = null;
        if (uploadResult.get("success") != null && (boolean) uploadResult.get("success")) {
            fileName = (String) uploadResult.get("fileName");
        } else {
            // Nếu upload thất bại, báo lỗi
            String errorMsg = uploadResult.get("error") != null ? (String) uploadResult.get("error") : "Vui lòng chọn ảnh sự kiện!";
            session.setAttribute("errorMessage", errorMsg);
            response.sendRedirect(request.getContextPath() + "/OrganizationCreateEventServlet");
            return;
        }

        try {
            int categoryId = Integer.parseInt(categoryIdStr);
            int neededVolunteers = Integer.parseInt(neededVolunteersStr);

            // Parse java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            java.util.Date startDate = sdf.parse(startDateStr);
            java.util.Date endDate = sdf.parse(endDateStr);

            // Tạo object Event
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            // Gán vào event.setImages()
            event.setImages(fileName);
            event.setLocation(location);
            event.setStatus(status);
            event.setVisibility(visibility);
            event.setCategoryId(categoryId);
            event.setOrganizationId(acc.getId()); // lấy từ account session
            event.setNeededVolunteers(neededVolunteers);
            event.setStartDate(startDate);
            event.setEndDate(endDate);
            event.setTotalDonation(0); // mặc định

            // Gọi service và nhận kết quả
            String result = createEventsService.createEvent(event);

            if ("success".equals(result)) {

                // GỬI THÔNG BÁO CHO ADMIN
                try {
                    // Lấy fullName từ Users
                    AdminUserDAO accDAO = new AdminUserDAO();
                    User user = accDAO.getUserByAccountId(acc.getId());
                    String fullName = (user != null) ? user.getFull_name() : acc.getUsername();

                    NotificationDAO notiDAO = new NotificationDAO();
                    Notification noti = new Notification();
                    noti.setSenderId(acc.getId());
                    noti.setReceiverId(1); // Admin ID = 1
                    noti.setMessage("Tổ chức " + fullName + " đã tạo 1 sự kiện mới");
                    noti.setType("system");
                    noti.setEventId(0); 

                    notiDAO.insertNotification(noti);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.removeAttribute("uploadedFileName");
                session.setAttribute("successMessage", "Tạo sự kiện thành công!");
                response.sendRedirect(request.getContextPath() + "/OrganizationListServlet");
            } else {
                // Set error message vào session
                session.setAttribute("errorMessage", result);
                response.sendRedirect(request.getContextPath() + "/OrganizationCreateEventServlet");
            }

        } catch (NumberFormatException | ParseException ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
            response.sendRedirect(request.getContextPath() + "/OrganizationCreateEventServlet");
        }
    }

}
