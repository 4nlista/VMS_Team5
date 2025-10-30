<%-- 
    Document   : sidebar_org
    Created on : Sep 16, 2025, 9:15:43 PM
    Author     : Organization
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String currentPath = request.getRequestURI();
%>

<div class="content-container">
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="logo">
            <img src="<%= request.getContextPath() %>/organization/images/logo_org.jpg" alt="Logo" />
            <span>Organization Panel</span>
        </div>
        <ul class="nav flex-column mb-auto">
            <li class="nav-item">
                <a href="<%= request.getContextPath() %>/organization/home_org.jsp"
                   class="nav-link text-white <%= currentPath.endsWith("/home_org.jsp") ? "active" : "" %>">
                    <i class="bi bi-house-door me-2"></i>
                    Bảng điều khiển
                </a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/organization/profile_org.jsp"
                   class="nav-link text-white <%= currentPath.endsWith("/profile_org.jsp") ? "active" : "" %>">
                    <i class="bi bi-person-circle me-2"></i>
                    Hồ sơ cá nhân
                </a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/organization/users_org.jsp"
                   class="nav-link text-white <%= currentPath.endsWith("/users_org.jsp") ? "active" : "" %>">
                    <i class="bi bi-person-lines-fill me-2"></i>
                    Quản lí người dùng
                </a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/OrganizationListServlet"
                   class="nav-link text-white <%= currentPath.endsWith("/OrganizationListServlet") ? "active" : "" %>">
                    <i class="bi bi-calendar-event me-2"></i>
                    Quản lí sự kiện
                </a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/organization/reports_org.jsp"
                   class="nav-link text-white <%= currentPath.endsWith("/reports_org.jsp") ? "active" : "" %>">
                    <i class="bi bi-file-earmark-bar-graph me-2"></i>
                    Báo cáo & thống kê
                </a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/organization/change_password_org.jsp"
                   class="nav-link text-white <%= currentPath.endsWith("/change_password_org.jsp") ? "active" : "" %>">
                    <i class="bi bi-key me-2"></i>
                    Đổi mật khẩu
                </a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/organization/settings_org.jsp"
                   class="nav-link text-white <%= currentPath.endsWith("/settings_org.jsp") ? "active" : "" %>">
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
</div>
