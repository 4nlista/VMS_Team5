<%-- 
    Document   : navbar
    Created on : Sep 16, 2025, 2:01:10 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>

<%
    Account acc = (Account) session.getAttribute("account");
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
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/index.jsp" class="nav-link <%= currentPage.contains("index.jsp") ? "active" : "" %>">Trang Chủ</a>
                </li>
                <!-- Chỉ hiện khi chưa login -->
                <% if (acc == null) { %>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/about.jsp" class="nav-link <%= currentPage.contains("about.jsp") ? "active" : "" %>">Giới thiệu</a>
                </li>
                <% } %>



                <!-- Dropdown Khám phá -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle <%= (currentPage.contains("causes.jsp") 
                                                            || currentPage.contains("blog.jsp") 
                                                            || currentPage.contains("gallery.jsp") 
                                                            || currentPage.contains("event.jsp")) 
                                                            ? "active" : "" %>" 
                       href="#" id="exploreDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Khám phá
                    </a>
                    <div class="dropdown-menu" aria-labelledby="exploreDropdown">
                        <a class="dropdown-item <%= currentPage.contains("causes.jsp") ? "active" : "" %>" href="<%= request.getContextPath() %>/causes.jsp">Hoạt động</a>
                        <a class="dropdown-item <%= currentPage.contains("blog.jsp") ? "active" : "" %>" href="<%= request.getContextPath() %>/blog.jsp">Bài viết</a>
                        <a class="dropdown-item <%= currentPage.contains("gallery.jsp") ? "active" : "" %>" href="<%= request.getContextPath() %>/gallery.jsp">Hình ảnh</a>
                        <a class="dropdown-item <%= currentPage.contains("event.jsp") ? "active" : "" %>" href="<%= request.getContextPath() %>/EventListServlet">Sự kiện</a>
                    </div>
                </li>
                <!-- End Dropdown Khám phá -->

                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/donate.jsp" class="nav-link <%= currentPage.contains("donate.jsp") ? "active" : "" %>">Tài trợ</a>
                </li>

                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/contact.jsp" class="nav-link <%= currentPage.contains("contact.jsp") ? "active" : "" %>">Liên hệ</a>
                </li>

                <% if (acc != null) { %>
                
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle <%= currentPage.contains("/volunteer/history_volunteer.jsp") ? "active" : "" %>" 
                       href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Lịch sử
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/volunteer/history_transaction_volunteer.jsp">Lịch sử giao dịch</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/volunteer/history_event_volunteer.jsp">Lịch sử sự kiện</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a href="<%= request.getContextPath() %>/VolunteerProfileServlet" 
                       class="nav-link <%= currentPage.contains("/volunteer/profile_volunteer.jsp") ? "active" : "" %>">
                        Hồ sơ của <%= acc.getUsername() %>
                    </a>
                </li>

                <li class="nav-item" style="align-content: center">
                    <a href="<%= request.getContextPath() %>/LogoutServlet" 
                       class="nav-link btn btn-outline-secondary" 
                       style="padding:15px 10px; border-radius:25px;">
                        Đăng xuất
                    </a>
                </li>
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

