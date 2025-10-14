<%-- 
    Document   : change_password_org
    Created on : Sep 17, 2025, 7:35:52 PM
    Author     : Organization
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đổi mật khẩu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
     <body>
        <div class="content-container">

            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <!-- Main Content -->
            <div class="main-content">
                <div class="container mt-5">
                    <h2 class="mb-4 text-center">Trang đổi mật khẩu</h2>

                    <% String error = (String) request.getAttribute("error"); %>
                    <% String success = (String) request.getAttribute("success"); %>

                    <% if (error != null) { %>
                        <div class="alert alert-danger"><%= error %></div>
                    <% } %>
                    <% if (success != null) { %>
                        <div class="alert alert-success"><%= success %></div>
                    <% } %>

                    <form action="<%= request.getContextPath() %>/ChangePasswordServlet" method="post" class="col-md-6 offset-md-3">
                        <div class="mb-3">
                            <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                            <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                        </div>

                        <div class="mb-3">
                            <label for="newPassword" class="form-label">Mật khẩu mới</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                        </div>

                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                        </div>

                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
