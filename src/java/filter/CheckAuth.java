/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import jakarta.servlet.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Admin
 */

// chống cache để trình duyệt không lưu trang ....
// Tránh tình trạng : "khi đã logout rồi mà ấn quay lại trang thì vẫn vào được tài khoản"
public class CheckAuth implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);  

        // Set header chống cache để trình duyệt không lưu trang
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0
        res.setDateHeader("Expires", 0);

        // Kiểm tra xem đã login chưa
        if (session == null || session.getAttribute("account") == null) {
            res.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        // Nếu đã login thì cho đi tiếp
        chain.doFilter(request, response);
    }
}
