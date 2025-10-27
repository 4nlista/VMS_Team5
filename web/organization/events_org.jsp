<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Event"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lí sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <%
                String fullname = (String) session.getAttribute("fullname");
                if (fullname == null) {
                    fullname = "Khách";
                }
            %>

            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <!-- Main Content -->
            <div class="main-content container mt-4">
                <h1>Chào mừng <%= fullname %> đến trang hỗ trợ viên!</h1>
                <h4>Màn hình quản lí sự kiện.</h4>

                <!-- Logout -->
                <form action="<%= request.getContextPath() %>/LogoutServlet" method="get" class="mb-3">
                    <button type="submit" class="btn btn-danger">Logout</button>
                </form>

                <!-- Thêm Event -->
                <a href="<%= request.getContextPath() %>/OrganizationEventServlet?action=createForm" class="btn btn-success mb-3">
                    <i class="bi bi-plus-circle"></i> Thêm sự kiện
                </a>

                <!-- Danh sách Event -->
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tiêu đề</th>
                            <th>Ngày bắt đầu</th>
                            <th>Ngày kết thúc</th>
                            <th>Địa điểm</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Event> events = (List<Event>) request.getAttribute("events");
                            if (events != null) {
                                for (Event e : events) {
                        %>
                        <tr>
                            <td><%= e.getId() %></td>
                            <td><%= e.getTitle() %></td>
                            <td><%= e.getStartDate() %></td>
                            <td><%= e.getEndDate() %></td>
                            <td><%= e.getLocation() %></td>
                            <td><%= e.getStatus() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/OrganizationEventEditServlet?eventId=<%= e.getId() %>" class="btn btn-primary btn-sm">
                                    <i class="bi bi-pencil-square"></i> Sửa
                                </a>
                                <a href="<%= request.getContextPath() %>/OrganizationEventServlet?action=delete&eventId=<%= e.getId() %>" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Bạn có chắc muốn xóa sự kiện này không?');">
                                    <i class="bi bi-trash"></i> Xóa
                                </a>
                            </td>
                        </tr>
                        <% 
                                }
                            } else { 
                        %>
                        <tr>
                            <td colspan="7" class="text-center">Không có sự kiện nào.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
