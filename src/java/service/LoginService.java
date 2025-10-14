/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.LoginDAO;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author Admin
 */
// xử lí nghiệp vụ logic login đăng nhập
public class LoginService {

    private LoginDAO loginDAO;

    public LoginService() {
        loginDAO = new LoginDAO();
    }

    // =======================
    // 1. processLogin()
    // =======================
    // Hàm này được gọi khi user submit form login (tức là login trực tiếp).
    // - Nó kiểm tra username/password có hợp lệ không.
    // - Nếu đúng → lưu account vào session.
    // - Sau đó gọi resolveRedirect() để quyết định chuyển hướng đi đâu (dashboard, volunteer home...).
    // - Nếu sai → trả về null (hoặc URL login fail).
    public String processLogin(String username, String password, HttpSession session) {
        Account acc = loginDAO.checkLogin(username, password);
        if (acc == null || !acc.isStatus()) {
            return "/auth/login.jsp?error=1"; // Login fail
        }

        session.setAttribute("account", acc);

        session.setAttribute("accountId", acc.getId());
        session.setAttribute("role", acc.getRole());

        String role = acc.getRole() == null ? "" : acc.getRole().toLowerCase();

        // Admin/Organization → luôn về dashboard, không xét redirectAfterLogin
        if ("admin".equals(role)) {
            return "/AdminHomeServlet";
        }
        if ("organization".equals(role)) {
            return "/OrganizationHomeServlet";
        }

        // Volunteer → mới xét redirectAfterLogin
        String redirect = (String) session.getAttribute("redirectAfterLogin");
        if (redirect != null) {
            session.removeAttribute("redirectAfterLogin");
            switch (redirect) {
                case "donate":
                    return "/donate.jsp";
                case "be_a_volunteer":
                    return "/VolunteerHomeServlet";
            }
        }

        // Nếu volunteer login trực tiếp, không có redirectAfterLogin
        if ("volunteer".equals(role)) {
            return "/VolunteerHomeServlet";
        }

        return "/index.jsp"; // fallback
    }

    // 2. resolveRedirect()
    // Hàm này đóng vai trò "bộ não phân quyền điều hướng".
    // Nó được dùng ở 2 nơi:
    //   a) Khi login thành công (processLogin gọi sang).
    //   b) Khi user đã login rồi mà click vào action (ví dụ: be_a_volunteer, donate) 
    //      → ActionRedirectServlet sẽ gọi sang đây.
    //
    // Input: 
    //   - acc: account hiện tại
    //   - session: session của user
    //   - action: hành động cụ thể (nếu có), ví dụ "donate" hoặc "be_a_volunteer"
    //
    // Xử lý:
    //   - Nếu có action → kiểm tra role có được phép hay không.
    //   - Nếu không có action → fallback về trang theo role (dashboard/admin/org hoặc volunteer)
    public String resolveRedirect(Account acc, HttpSession session, String action) {
        if (acc == null || !acc.isStatus()) {
            return "/auth/login.jsp"; // fallback
        }
        // lấy action ưu tiên: tham số trước, nếu null thì lấy từ session
        String act = action;
        if (act == null) {
            act = (String) session.getAttribute("redirectAfterLogin");
            if (act != null) {
                session.removeAttribute("redirectAfterLogin");
            }
        }
        String role = acc.getRole() == null ? "" : acc.getRole().toLowerCase();
        String actLower = act == null ? null : act.toLowerCase();

        // Nếu có action -> xử lý trước (chỉ cho role phù hợp)
        if (actLower != null) {
            switch (actLower) {
                case "donate":
                    if ("volunteer".equals(role)) {
                        return "/donate.jsp";
                    } else {
                        break;      // admin/org không được đi trang volunteer -> fallback về dashboard
                    }
                case "be_a_volunteer":
                    if ("volunteer".equals(role)) {
                        return "/VolunteerHomeServlet";
                    } else {
                        break;      // admin/org không được đi trang volunteer -> fallback về dashboard
                    }
                default:
                    return "/index.jsp";
            }
        }

        // Nếu không có action hợp lệ -> điều hướng theo role
        switch (role) {
            case "admin":
                return "/AdminHomeServlet";
            case "organization":
                return "/OrganizationHomeServlet";
            case "volunteer":
                return "/VolunteerHomeServlet";
            default:
                return "/index.jsp";
        }
    }

}
