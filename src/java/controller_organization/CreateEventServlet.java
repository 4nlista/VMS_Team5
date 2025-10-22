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
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import model.Category;
import model.Event;
import service.EventService;

/**
 *
 * @author locng
 */
public class CreateEventServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet CreateEventServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateEventServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // optional: set encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 1. Lấy danh sách categories từ DB
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getAllCategories();
  

        // 2. Đưa vào request để JSP sử dụng
        request.setAttribute("categories", categories);       

        // 3. Forward tới create_event.jsp
        // Chú ý: chỉnh đường dẫn forward cho đúng nơi file JSP của bạn nằm
        request.getRequestDispatcher("/organization/create_event.jsp")
               .forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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

        // ✅ Nếu có session để xác định tổ chức
//        HttpSession session = request.getSession();
//        Integer organizationId = (Integer) session.getAttribute("accountId");
//        if (organizationId == null) {
//            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
//            return;
//        }

        try {
            // ✅ Lấy dữ liệu từ form
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String startDateStr = request.getParameter("start_date");
            String endDateStr = request.getParameter("end_date");
            String location = request.getParameter("location");
            int neededVolunteers = Integer.parseInt(request.getParameter("needed_volunteers"));
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            String status = request.getParameter("status");
            double totalDonation = Double.parseDouble(request.getParameter("total_donation"));

            // ✅ Chuyển đổi ngày
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            // ✅ Tạo đối tượng Event
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setStartDate(startDate);
            event.setEndDate(endDate);
            event.setLocation(location);
            event.setNeededVolunteers(neededVolunteers);
            event.setCategoryId(categoryId);
            event.setStatus(status);
            event.setTotalDonation(totalDonation);
            event.setOrganizationId(2); // Tạm thời fix cứng, sau này lấy từ session

            // ✅ Lưu xuống DB
            EventService service = new EventService();
            boolean success = service.createEvent(event);
            int id = event.getId();
                            request.setAttribute("id", id);

            if (success) {
                // Thành công → quay lại danh sách sự kiện
                response.sendRedirect(request.getContextPath() + "/ListEventsServlet?success=true");
            } else {
                // Thất bại → quay lại form
                request.setAttribute("errorMessage", "Không thể lưu sự kiện. Vui lòng thử lại!");
                request.getRequestDispatcher("/organization/create_event.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi xử lý dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/organization/create_event.jsp").forward(request, response);
        }
    }


    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
