package controller_auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hủy session khi logout
        HttpSession session = request.getSession(false); // Lấy session nếu có
        if (session != null) {
            session.invalidate(); // Xóa session
        }

        // Thêm header để ngăn cache
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  // Cache Control
        response.setHeader("Pragma", "no-cache");  // HTTP/1.0
        response.setDateHeader("Expires", 0);  // Expiration date set to 0

        // Sau khi logout, điều hướng về login page
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
