<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.EventVolunteerInfo"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang lịch sử sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center">Lịch sử sự kiện đã tham gia</h1>

            <div class="card shadow-sm border">
                <div class="card-body">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Tên sự kiện</th>
                                <th scope="col">Ngày đăng ký</th>
                                <th scope="col">Trạng thái</th>
                                <th scope="col">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<EventVolunteerInfo> historyList = (List<EventVolunteerInfo>) request.getAttribute("historyList");
                                if (historyList != null && !historyList.isEmpty()) {
                                    int index = 1;
                                    for (EventVolunteerInfo ev : historyList) {
                                        String status = ev.getStatus();
                                        String badgeClass = "bg-secondary";
                                        if ("approved".equalsIgnoreCase(status)) badgeClass = "bg-success";
                                        else if ("rejected".equalsIgnoreCase(status)) badgeClass = "bg-danger";
                                        else if ("pending".equalsIgnoreCase(status)) badgeClass = "bg-warning text-dark";
                            %>
                            <tr>
                                <td><%= index++ %></td>
                                <td><%= ev.getEventTitle() %></td>
                                <td><%= ev.getApplyDate() != null ? new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(ev.getApplyDate()) : "" %></td>
                                <td><span class="badge <%= badgeClass %>"><%= status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase() %></span></td>
                                <td>
                                    <a href="<%= request.getContextPath() %>/eventDetail?applyId=<%= ev.getApplyId() %>" 
                                       class="btn btn-sm btn-outline-primary">
                                        Xem chi tiết
                                    </a>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="5" class="text-center text-muted py-4">Không có lịch sử đăng ký nào.</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
