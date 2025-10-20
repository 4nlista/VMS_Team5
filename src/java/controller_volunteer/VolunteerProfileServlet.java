package controller_volunteer;

import dao.UserDAO;
import model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/VolunteerProfileServlet")
public class VolunteerProfileServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        User user = userDAO.getUserByAccountId(accountId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int accountId = Integer.parseInt(request.getParameter("accountId"));

        // 🔹 Lấy thông tin user hiện tại để có id, tránh null
        User user = userDAO.getUserByAccountId(accountId);
        if (user == null) {
            request.setAttribute("message", "Không tìm thấy thông tin người dùng!");
            request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
            return;
        }

        // 🔹 Cập nhật dữ liệu mới từ form
        user.setFullName(request.getParameter("full_name"));
user.setPhone(request.getParameter("phone"));
user.setEmail(request.getParameter("email"));
user.setAddress(request.getParameter("address"));
user.setJobTitle(request.getParameter("job_title"));
user.setBio(request.getParameter("bio"));
user.setGender(request.getParameter("gender"));

String dobStr = request.getParameter("dob");
if (dobStr != null && !dobStr.isEmpty()) {
    try {
        java.sql.Date dob = java.sql.Date.valueOf(dobStr);
        user.setDob(dob);
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    }
}

        boolean success = userDAO.updateUser(user);

        // 🔹 Thông báo kết quả
        request.setAttribute("message", success ? " Cập nhật thành công!" : " Lỗi khi cập nhật!");
        request.setAttribute("user", userDAO.getUserByAccountId(accountId));

        request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
    }
}
