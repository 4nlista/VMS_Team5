<%-- 
    Document   : home_org
    Created on : Sep 7, 2025, 4:14:28 PM
    Author     : Organization
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang quản trị</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <%
                      Object sessionId = session.getId();
                      String fullname = (String) session.getAttribute("fullname");
                      if (fullname == null) {
                          fullname = "Khách";
                      }
            %>
            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />


            <!-- Main Content -->
            <div class="main-content">
                <h1>Chào mừng <%= fullname %> đến trang hỗ trợ viên!</h1>
                <h4>Đây là trang chủ dành riêng cho hỗ trợ viên.</h4>

                <form action="<%= request.getContextPath() %>/LogoutServlet" method="get">
                    <button type="submit">Logout</button>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
