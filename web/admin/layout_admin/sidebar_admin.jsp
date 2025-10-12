<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String currentPath = request.getRequestURI();
%>

<div class="sidebar">
    <div class="logo">
        <img src="<%= request.getContextPath() %>/admin/images/logo.jpg" alt="Logo" />
        <span>Admin Panel</span>
    </div>

    <ul class="nav flex-column mb-auto">
        <li class="nav-item">
            <a href="<%= request.getContextPath() %>/admin/home_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/home_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-speedometer2 me-2"></i>
                Bảng điều khiển
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/profile_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/profile_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-person-circle me-2"></i>
                Hồ sơ cá nhân
            </a>

        </li>
        <li>
            <a href="<%= request.getContextPath() %>/AdminAccountServlet"
               class="nav-link text-white <%= (currentPath.endsWith("/AdminAccountServlet") || currentPath.endsWith("/admin/accounts_admin.jsp") || currentPath.endsWith("/accounts_admin.jsp")) ? "active" : "" %>">
                <i class="bi bi-person-lines-fill me-2"></i>
                Quản lí tài khoản
            </a>

        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/users_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/users_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-people me-2"></i>
                Quản lí người dùng
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/events_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/events_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-calendar-event me-2"></i>
                Thống kê sự kiện
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/feedback_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/feedback_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-file-earmark-bar-graph me-2"></i>
                Kiểm duyệt nội dung
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/reports_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/reports_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-bar-chart-line me-2"></i>
                Báo cáo và thống kê
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/change_password_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/change_password_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-key me-2"></i>
                Đổi mật khẩu
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/settings_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/settings_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-gear me-2"></i>
                Cài đặt
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/LogoutServlet"
               class="nav-link text-white">
                <i class="bi bi-box-arrow-right me-2"></i>
                Đăng xuất
            </a>
        </li>
    </ul>
</div>
