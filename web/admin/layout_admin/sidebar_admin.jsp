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
            <a href="<%= request.getContextPath() %>/AdminHomeServlet"
               class="nav-link text-white <%= currentPath.endsWith("/home_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-speedometer2 me-2"></i>
                Bảng điều khiển
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/AdminProfileServlet?id=1"
               class="nav-link text-white <%= ( 
                         currentPath.contains("/profile_admin.jsp") || 
                         currentPath.contains("/profile_edit_admin.jsp")
                         ) ? "active" : "" %>">
                <i class="bi bi-person-circle me-2"></i>
                Hồ sơ cá nhân
            </a>

        </li>
        <li>
            <a href="<%= request.getContextPath() %>/AdminAccountServlet"
               class="nav-link text-white <%= (currentPath.endsWith("/AdminAccountServlet") 
                       || currentPath.endsWith("/admin/accounts_admin.jsp") 
                       || currentPath.endsWith("/accounts_admin.jsp") 
                       || currentPath.endsWith("/admin/detail_accounts_admin.jsp") 
                       || currentPath.endsWith("/detail_accounts_admin.jsp") 
                       || currentPath.endsWith("/admin/add_account_admin.jsp") 
                       || currentPath.endsWith("/add_account_admin.jsp")) ? "active" : "" %>">

                <i class="bi bi-person-lines-fill me-2"></i>
                Quản lí tài khoản
            </a>

        </li>
        <li>
            <a href="<%= request.getContextPath() %>/AdminUserServlet"
               class="nav-link text-white <%= (
       currentPath.contains("detail_user_admin.jsp") ||
       currentPath.contains("edit_user_admin.jsp") ||
       currentPath.endsWith("manage_user_admin.jsp")
   ) ? "active" : "" %>">
                <i class="bi bi-people me-2"></i>
                Quản lí người dùng
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/AdminEventsServlet"
               class="nav-link text-white <%= (currentPath.endsWith("/AdminEventsServlet") 
                       || currentPath.endsWith("/events_admin.jsp")
                       || currentPath.endsWith("/detail_events_admin.jsp")) ? "active" : "" %>">
                <i class="bi bi-calendar-event me-2"></i>
                Quản lí sự kiện
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/AdminReportServlet"
               class="nav-link text-white <%= currentPath.endsWith("/manage_report_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-file-earmark-bar-graph me-2"></i>
                Kiểm duyệt nội dung
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/AdminNewsServlet"
               class="nav-link text-white <%= currentPath.endsWith("/manage_news_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-bar-chart-line me-2"></i>
                Quản lí tin tức
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
            <a href="<%= request.getContextPath() %>/admin/notifications_admin.jsp"
               class="nav-link text-white <%= currentPath.endsWith("/notifications_admin.jsp") ? "active" : "" %>">
                <i class="bi bi-gear me-2"></i>
                Thông báo
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
