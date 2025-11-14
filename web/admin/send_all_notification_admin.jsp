<%-- 
    Document   : send_all_notification_admin
    Created on : Nov 14, 2025, 1:29:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Gửi thông báo cá nhân</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
    </head>
    <body>
         <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content -->
            <div class="main-content p-4">
            </div>
        </div>
    </body>
</html>
