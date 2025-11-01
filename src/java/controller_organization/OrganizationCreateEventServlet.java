package controller_organization;

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
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Account;
import model.Event;
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

        // ✅ THÊM LOG KIỂM TRA
        HttpSession session = request.getSession(false);
        System.out.println("========== doGet() được gọi ==========");
        System.out.println("Session ID: " + (session != null ? session.getId() : "NULL"));
        System.out.println("uploadedFileName trong session: " + (session != null ? session.getAttribute("uploadedFileName") : "NULL"));
        System.out.println("======================================");

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

        System.out.println("========== doPost() ĐƯỢC GỌI =========="); // ✅ THÊM DÒNG NÀY
        System.out.println("Method: " + request.getMethod());
        System.out.println("URI: " + request.getRequestURI());

        // Lấy tên file đã upload từ hidden input (đã upload qua UploadImagesServlet)
        String fileName = request.getParameter("uploadedImage");
        System.out.println("uploadedImage param: " + fileName); // ✅ THÊM DÒNG NÀY
        // Nếu không có file đã upload, báo lỗi
        if (fileName == null || fileName.trim().isEmpty()) {
            request.setAttribute("errorMsg", "Vui lòng chọn ảnh sự kiện trước khi tạo!");
            request.getRequestDispatcher("/organization/create_events_org.jsp").forward(request, response);
            return;
        }

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

        try {
            int categoryId = Integer.parseInt(categoryIdStr);
            int neededVolunteers = Integer.parseInt(neededVolunteersStr);

            // Parse java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

            // ✅ THÊM LOG ĐỂ KIỂM TRA DỮ LIỆU
            System.out.println("========== DEBUG EVENT ==========");
            System.out.println("Title: " + event.getTitle());
            System.out.println("Images: " + event.getImages());
            System.out.println("Location: " + event.getLocation());
            System.out.println("OrganizationId: " + event.getOrganizationId());
            System.out.println("CategoryId: " + event.getCategoryId());
            System.out.println("Start Date: " + event.getStartDate());
            System.out.println("End Date: " + event.getEndDate());
            System.out.println("==================================");

            boolean success = createEventsService.createEvent(event);

            if (success) {
                session.removeAttribute("uploadedFileName"); // 
                response.sendRedirect(request.getContextPath() + "/OrganizationCreateEventServlet?msg=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/OrganizationCreateEventServlet?msg=error");
            }

        } catch (NumberFormatException | ParseException ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/OrganizationCreateEventServlet?msg=invalid");

        }
    }

}
