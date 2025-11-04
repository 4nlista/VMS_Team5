<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.AttendanceHistory"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang lịch sử điểm danh</title>
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
                                <th scope="col">STT</th>
                                <th scope="col">Tên sự kiện</th>
                                <th scope="col">Tên tổ chức</th>
                                <th scope="col">Ngày bắt đầu</th>
                                <th scope="col">Ngày kết thúc</th>
                                <th scope="col">Trạng thái</th>
                            </tr>
                        </thead>

                        <tbody>
                            <%
                                List<AttendanceHistory> attendanceList = (List<AttendanceHistory>) request.getAttribute("attendanceList");
                                if (attendanceList != null && !attendanceList.isEmpty()) {
                                    int index = 1;
                                    for (AttendanceHistory a : attendanceList) {
                                        String status = a.getStatus();
                                        String badgeClass = "bg-secondary";
                                        String statusText = "Không xác định";

                                        if ("present".equalsIgnoreCase(status)) {
                                            badgeClass = "bg-success";
                                            statusText = "Đã tham gia";
                                        } else if ("absent".equalsIgnoreCase(status)) {
                                            badgeClass = "bg-danger text-white";
                                            statusText = "Đã vắng";
                                        } else if ("pending".equalsIgnoreCase(status)) {
                                            badgeClass = "bg-warning text-white";
                                            statusText = "Chưa điểm danh";
                                        }
                            %>

                            <tr>
                                <td><%= index++ %></td>
                                <td><%= a.getEventTitle() %></td>
                                <td><%= a.getOrganizationName() %></td>
                                <td><%= new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(a.getStartDate()) %></td>
                                <td><%= new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(a.getEndDate()) %></td>
                                <td><span class="badge <%= badgeClass %>"><%= statusText %></span></td>
                            </tr>

                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="6" class="text-center text-muted py-4">
                                    Không có lịch sử điểm danh nào.
                                </td>
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
