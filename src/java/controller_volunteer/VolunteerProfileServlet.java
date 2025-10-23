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
        User user = userDAO.getUserByAccountId(accountId);

        if (user == null) {
            request.setAttribute("message", "Không tìm thấy thông tin người dùng!");
            request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
            return;
        }

        // 🔹 Lấy dữ liệu từ form
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String jobTitle = request.getParameter("job_title");
        String bio = request.getParameter("bio");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dob");

        // 🔹 Validate dữ liệu nhập vào
        String errorMsg = validateProfile(fullName, email, phone, dobStr);
        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
            return;
        }

        // 🔹 Gán lại giá trị hợp lệ
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setJobTitle(jobTitle);
        user.setBio(bio);
        user.setGender(gender);

        if (dobStr != null && !dobStr.isEmpty()) {
            user.setDob(java.sql.Date.valueOf(dobStr));
        }

        boolean success = userDAO.updateUser(user);

        request.setAttribute("message", success ? "Cập nhật thành công!" : "Lỗi khi cập nhật!");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
    }

    /**
     *
     */
    private String validateProfile(String fullName, String email, String phone, String dobStr) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ và tên không được để trống!";
        }
        if (email == null || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            return "Email không hợp lệ!";
        }
        if (phone != null && !phone.matches("^\\d{9,11}$")) {
            return "Số điện thoại chỉ gồm 9-11 chữ số!";
        }
        if (dobStr != null && !dobStr.isEmpty()) {
            try {
                java.sql.Date dob = java.sql.Date.valueOf(dobStr);
                if (dob.after(new java.util.Date())) {
                    return "Ngày sinh không được lớn hơn hiện tại!";
                }
            } catch (IllegalArgumentException e) {
                return "Ngày sinh không hợp lệ!";
            }
        }
        return null; // 
    }
}
