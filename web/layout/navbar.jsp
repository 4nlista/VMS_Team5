<%-- 
    Document   : navbar
    Created on : Sep 16, 2025, 2:01:10 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%@ page import="dao.NotificationDAO" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

<%
     Account acc = (Account) session.getAttribute("account");
    int unreadCountVol = 0;
    if (acc != null) {
        NotificationDAO notiDAO = new NotificationDAO();
        unreadCountVol = notiDAO.getUnreadCount(acc.getId());
    }
%>

<style>
    /* CSS riêng cho navbar */

    /* Navbar background và shadow */
    #ftco-navbar {
        background-color: rgba(30, 30, 30, 0.8) !important; /* nền tối, trong hơn */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4); /* đổ bóng tối */
    }

    .navbar-nav .nav-link.active {
        color: #fff !important;
        background-color: #6c757d !important; /* nền xám sáng */
        border-radius: 50px;
        padding: 20px 15px;
    }

    .ftco-navbar-light {
        top: 0px;
    }

    .navbar-nav .nav-link:hover {
        background-color: #CCC !important; /* hover tối hơn một chút */
        border-radius: 50px;
        color: #fff !important;
    }
    .navbar-nav .nav-link.dropdown-toggle:hover {
        background-color: #CCC !important;
        border-radius: 50px;
        color: #fff !important;
    }
</style>


<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
    <div class="container">

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" 
                aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="oi oi-menu"></span> Menu
        </button>

        <div class="collapse navbar-collapse" id="ftco-nav">
            <%
                String currentPage = request.getRequestURI();
            %>
            <ul class="navbar-nav ml-auto">
                <% if (acc == null) { %>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/home" class="nav-link <%= currentPage.contains("home") ? "active" : "" %>">Trang Chủ</a>
                </li>

                <% } else { %>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/VolunteerHomeServlet" class="nav-link <%= currentPage.contains("VolunteerHomeServlet") ? "active" : "" %>">Trang Chủ</a>
                </li>
                <% } %>

                <!-- Chỉ hiện khi chưa login -->
                <% if (acc == null) { %>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/about.jsp" class="nav-link <%= currentPage.contains("about.jsp") ? "active" : "" %>">Giới thiệu</a>
                </li>
                <% }  %>

                <!-- Dropdown Khám phá -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle <%= (currentPage.contains("GuessNewServlet") 
                                                            || currentPage.contains("gallery.jsp") 
                                                            || currentPage.contains("GuessEventServlet")) 
                                                            ? "active" : "" %>" 
                       href="#" id="exploreDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Khám phá
                    </a>
                    <div class="dropdown-menu" aria-labelledby="exploreDropdown">
                        <a class="dropdown-item <%= currentPage.contains("GuessNewServlet") ? "active" : "" %>" href="<%= request.getContextPath() %>/GuessNewServlet">Bài viết</a>
                        <a class="dropdown-item <%= currentPage.contains("gallery.jsp") ? "active" : "" %>" href="<%= request.getContextPath() %>/gallery.jsp">Hình ảnh</a>
                        <a class="dropdown-item <%= currentPage.contains("event.jsp") ? "active" : "" %>" href="<%= request.getContextPath() %>/GuessEventServlet">Sự kiện</a>
                    </div>
                </li>
                <!-- End Dropdown Khám phá -->

                <% if (acc == null) { %>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/GuessDonateServlet" class="nav-link <%= currentPage.contains("GuessDonateServlet") ? "active" : "" %>">Tài trợ</a>
                </li>
                <% } else { %>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/GuessDonateServlet" class="nav-link <%= currentPage.contains("GuessDonateServlet") ? "active" : "" %>">Tài trợ</a>
                </li>
                <% } %>

                <!--nếu đăng nhập , tạo session thì mới hiển thị lịch sử giao dịch + lịch sử sự kiện-->
                <% if (acc != null) { %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle <%= currentPage.contains("/volunteer/history_volunteer.jsp") ? "active" : "" %>" 
                       id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Lịch sử
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/VolunteerDonateServlet">Lịch sử giao dịch</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/VolunteerEventServlet">Lịch sử sự kiện</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/VolunteerAttendanceServlet">Lịch sử điểm danh</a></li>
                    </ul>
                </li>
                
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/VolunteerProfileServlet" 
                       class="nav-link <%= currentPage.contains("VolunteerProfileServlet") ? "active" : "" %>">
                        Hồ sơ cá nhân
                    </a>
                </li>
                
                <% if (acc != null) { %>
                <!-- Chuông thông báo - đặt TRƯỚC nút Đăng xuất -->
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/VolunteerNotificationServlet" 
                       class="nav-link btn btn-outline position-relative" 
                       style="padding: 12px 20px; border-radius: 25px; border-width: 2px;">
                        <i class="bi bi-bell-fill fs-5" style="color: white;"></i>
                        <% if (unreadCountVol > 0) { %>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" 
                              style="font-size: 11px; padding: 4px 7px;">
                            <%= unreadCountVol %>
                        </span>
                        <% } %>
                    </a>
                </li>
                <% } %>



                <li class="nav-item" style="align-content: center; margin-left: 2px">
                    <a href="<%= request.getContextPath() %>/LogoutServlet" 
                       class="nav-link btn btn-outline-secondary" 
                       style="padding:15px 10px; border-radius:25px;">
                        Đăng xuất
                    </a>
                </li>
                <!--                nếu có thì hiển thị đăng xuất / nếu không thì thay bằng đăng nhập-->
                <% } else { %>
                <li class="nav-item" style="align-content: center">
                    <a href="<%= request.getContextPath() %>/LoginServlet" 
                       class="nav-link btn btn-outline-secondary" 
                       style="padding:15px 10px; border-radius:25px;">
                        Đăng Nhập
                    </a>
                </li>
                <% }  %>
            </ul>
        </div>
    </div>
</nav>

