<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.EventVolunteerInfo"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lí người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <%
                String fullname = (String) session.getAttribute("fullname");
                if (fullname == null) fullname = "Khách";
            %>

            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <!-- Main Content -->
            <div class="main-content container mt-4">
                <h1>Chào mừng <%= fullname %> đến trang quản lý volunteer!</h1>
                <h4>Màn hình quản lý yêu cầu tham gia sự kiện.</h4>

                <form action="<%= request.getContextPath() %>/LogoutServlet" method="get" class="mb-3">
                    <button type="submit" class="btn btn-danger">Logout</button>
                </form>

                <!-- Danh sách Volunteer đã Apply -->
                <h5 class="mt-4">Danh sách Volunteer đã apply</h5>
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr class="text-center">
                            <th>ID Apply</th>
                            <th>Tên sự kiện</th>
                            <th>Tên tình nguyện viên</th>
                            <th>Email</th>
                            <th>Ngày Apply</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<EventVolunteerInfo> volunteers = (List<EventVolunteerInfo>) request.getAttribute("applications");
                            if (volunteers != null && !volunteers.isEmpty()) {
                                for (EventVolunteerInfo v : volunteers) {
                        %>
                        <tr>
                            <td><%= v.getApplyId() %></td>
                            <td><%= v.getEventTitle() %></td>
                            <td><%= v.getVolunteerName() %></td>
                            <td><%= v.getVolunteerEmail() %></td>
                            <td><%= v.getApplyDate() %></td>
                            <td>
                                <span class="badge bg-warning text-dark"><%= v.getStatus() %></span>
                            </td>
                            <td class="text-center">
                                <form action="<%= request.getContextPath() %>/organization/manage-applications" method="post" style="display:inline-block;">
                                    <input type="hidden" name="applyId" value="<%= v.getApplyId() %>"/>
                                    <button type="submit" name="action" value="approve" class="btn btn-success btn-sm">Duyệt</button>
                                    <button type="submit" name="action" value="reject" class="btn btn-danger btn-sm">Từ chối</button>
                                </form>
                            </td>
                        </tr>
                        <% 
                                }
                            } else { 
                        %>
                        <tr>
                            <td colspan="7" class="text-center text-muted">Chưa có volunteer nào apply.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
