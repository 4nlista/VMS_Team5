/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_organization;

import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Category;
import model.Event;
import service.EventService;

/**
 *
 * @author locng
 */
public class UpdateEvenServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateEvenServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateEvenServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        EventService service = new EventService();
        CategoryDAO categoryDAO = new CategoryDAO();

        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ListEventsServlet");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            Event event = service.getEventById(id);
            if (event == null) {
                request.setAttribute("event", event);

                request.setAttribute("errorMessage", "Không tìm thấy sự kiện.");

                request.getRequestDispatcher("/organization/edit_event.jsp").forward(request, response);
                return;
            }
            request.setAttribute("event", event);
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/organization/edit_event.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/ListEventsServlet");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy dữ liệu từ form
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String status = request.getParameter("status");
            int neededVolunteers = Integer.parseInt(request.getParameter("needed_volunteers"));
            double totalDonation = Double.parseDouble(request.getParameter("total_donation"));
            int categoryId = Integer.parseInt(request.getParameter("category_id"));

            // Xử lý ngày
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(request.getParameter("start_date"));
            Date endDate = sdf.parse(request.getParameter("end_date"));

            // Tạo đối tượng Event
            Event event = new Event();
            event.setId(id);
            event.setTitle(title);
            event.setDescription(description);
            event.setLocation(location);
            event.setStatus(status);
            event.setNeededVolunteers(neededVolunteers);
            event.setTotalDonation(totalDonation);
            event.setCategoryId(categoryId);
            event.setStartDate(startDate);
            event.setEndDate(endDate);

            // Mặc định organizationId = 2
            event.setOrganizationId(2);

            // Gọi service cập nhật
            EventService service = new EventService();
            boolean success = service.updateEvent(event);

            if (success) {
                // ✅ Cập nhật thành công — quay về danh sách + thông báo popup
                request.getSession().setAttribute("successMessage", "Cập nhật sự kiện thành công!");
            } else {
                // ❌ Cập nhật thất bại
                request.getSession().setAttribute("errorMessage", event.getTitle());
            }

            // Quay lại danh sách
            response.sendRedirect(request.getContextPath() + "/ListEventsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật!");
            response.sendRedirect(request.getContextPath() + "/ListEventsServlet");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
