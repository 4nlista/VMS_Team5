<%-- 
    Document   : sidebar_org
    Created on : Sep 16, 2025, 9:15:43 PM
    Author     : Organization
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User" %>
<%@ page import="dao.NotificationDAO" %>
<%
    String currentPath = request.getRequestURI();

        Integer accountIdOrg = (Integer) session.getAttribute("accountId");
        int unreadCountOrg = 0;
        if (accountIdOrg != null) {
            NotificationDAO notiDAO = new NotificationDAO();
            unreadCountOrg = notiDAO.getUnreadCount(accountIdOrg);
        }

%>
<%
    User currentUser = (User) session.getAttribute("user");

    String avatarUrl;
    if (currentUser != null && currentUser.getAvatar() != null && !currentUser.getAvatar().isEmpty()) {
        avatarUrl = request.getContextPath() + "/OrganizationAvatar?file=" + currentUser.getAvatar();
    } else {
        avatarUrl = request.getContextPath() + "/organization/images/default_avatar.png";
    }
%>

<link rel="stylesheet" href="<%= request.getContextPath() %>/organization/css/sidebar_org.css">

<div class="content-container">
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="sidebar-user">
            <img src="<%= avatarUrl %>" alt="Avatar" class="sidebar-avatar" />
            <p><%= currentUser != null ? currentUser.getFull_name() : "Unknown User" %></p>
        </div>



        <ul class="nav flex-column mb-auto">


            <li class="nav-item">
                <a href="<%= request.getContextPath() %>/OrganizationHomeServlet"
                   class="nav-link text-white <%= currentPath.endsWith("/home_org.jsp") ? "active" : "" %>">
                    <i class="bi bi-house-door me-2"></i>
                    Bảng điều khiển
                </a>
            </li>
            <li>
                <a href="<%= request.getContextPath() %>/OrganizationProfileDetail"
                   class="nav-link text-white <%= (currentPath.endsWith("/profile_org.jsp")
                             || currentPath.endsWith("/edit_org_profile.jsp"))
                             ? "active" : "" %>">
                    <i class="bi bi-person-circle me-2"></i>
                    Hồ sơ cá nhân
                </a>
            </li>
            <!--            <li>
                            <a href="<%= request.getContextPath() %>/organization/users_org.jsp"
                               class="nav-link text-white <%= currentPath.endsWith("/users_org.jsp") ? "active" : "" %>">
                                <i class="bi bi-person-lines-fill me-2"></i>
                                Quản lí người dùng
                            </a>
                        </li>-->
            <li>
                <a href="<%= request.getContextPath() %>/OrganizationListServlet"
                   class="nav-link text-white <%= currentPath.endsWith("/OrganizationListServlet") ? "active" : "" %>">
                    <i class="bi bi-calendar-event me-2"></i>
                    Quản lí sự kiện
                </a>
            </li>
            <!--            <li>
                            <a href="<%= request.getContextPath() %>/OrganizationManageFeedbackServlet"
                               class="nav-link text-white <%= currentPath.endsWith("/manage_feedback_org.jsp") ? "active" : "" %>">
                                <i class="bi bi-pencil me-2"></i>
                                Quản lí đánh giá
                            </a>
                        </li>-->
            <li>
                <a href="<%= request.getContextPath() %>/OrganizationManageNews"
                   class="nav-link text-white <%= (currentPath.endsWith("/manage_new_org.jsp")
                             || currentPath.endsWith("/detail_news_org.jsp")
                             || currentPath.endsWith("/create_news_org.jsp")
                             || currentPath.endsWith("/edit_news_org.jsp"))
                             ? "active" : "" %>">
                    <i class="bi bi-file-earmark-bar-graph me-2"></i>
                    Quản lí tin tức
                </a>
            </li>

            <li class="nav-item">
                <a href="<%= request.getContextPath() %>/OrganizationNotificationServlet"
                   class="nav-link text-white <%= currentPath.endsWith("/notifications_org.jsp") ? "active" : "" %>">
                    <i class="bi bi-bell me-2"></i>
                    Thông báo
                    <% if (unreadCountOrg > 0) { %>
                    <span class="badge bg-danger ms-2"><%= unreadCountOrg %></span>
                    <% } %>
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
                <a href="<%= request.getContextPath() %>/LogoutServlet"
                   class="nav-link text-white">
                    <i class="bi bi-box-arrow-right me-2"></i>
                    Đăng xuất
                </a>
            </li>
        </ul>
    </div>
</div>
