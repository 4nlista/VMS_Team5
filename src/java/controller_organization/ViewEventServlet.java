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
import model.Event;
import service.EventService;

/**
 *
 * @author locng
 */
public class ViewEventServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewEventServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewEventServlet at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String ajax = request.getParameter("ajax"); // nếu có -> trả fragment
        EventService service = new EventService();
        CategoryDAO categoryDAO = new CategoryDAO();

        if (idStr == null || idStr.isEmpty()) {
            // nếu là ajax, trả lỗi 400 để client JS xử lý
            if ("true".equalsIgnoreCase(ajax)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
                return;
            } else {
                response.sendRedirect(request.getContextPath() + "/ListEventsServlet");
                return;
            }
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            if ("true".equalsIgnoreCase(ajax)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
                return;
            } else {
                response.sendRedirect(request.getContextPath() + "/ListEventsServlet");
                return;
            }
        }

        Event event = service.getEventById(id);
        if (event == null) {
            if ("true".equalsIgnoreCase(ajax)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Event not found");
                return;
            } else {
                request.setAttribute("errorMessage", "Không tìm thấy sự kiện.");
                request.getRequestDispatcher("/organization/edit_event.jsp").forward(request, response);
                return;
            }
        }

        // Đặt attribute chung cho cả 2 trường hợp
        request.setAttribute("event", event);
        request.setAttribute("categories", categoryDAO.getAllCategories());

        // Nếu ajax, forward tới fragment (chỉ nội dung modal)
        if ("true".equalsIgnoreCase(ajax)) {
            // đảm bảo fragment nằm ở đường dẫn này
            request.getRequestDispatcher("/organization/viewEventFragment.jsp").forward(request, response);
            return;
        }

        // normal full page (edit)
        request.getRequestDispatcher("/organization/edit_event.jsp").forward(request, response);
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
        processRequest(request, response);
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
