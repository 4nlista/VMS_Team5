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


        User user = userDAO.getUserByAccountId(accountId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int accountId = Integer.parseInt(request.getParameter("accountId"));
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String jobTitle = request.getParameter("job_title");
        String bio = request.getParameter("bio");

        User user = new User();
        user.setAccountId(accountId);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setJobTitle(jobTitle);
        user.setBio(bio);

        boolean success = userDAO.updateUser(user);

        request.setAttribute("message", success ? "Cập nhật thành công!" : "Lỗi khi cập nhật!");
        request.setAttribute("user", userDAO.getUserByAccountId(accountId));
        request.getRequestDispatcher("/volunteer/profile_volunteer.jsp").forward(request, response);
    }
}
