<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Attendance"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <h1 class="mb-4 text-center">Lịch sử điểm danh</h1>

            <!-- Form lọc trạng thái -->
            <div class="card mb-4 shadow-sm">
                <div class="card-body">
                    <form method="GET" action="<%= request.getContextPath() %>/VolunteerAttendanceServlet" class="row g-3">
                        <div class="col-md-6">
                            <label for="status" class="form-label fw-bold">
                                <i class="bi bi-funnel"></i> Trạng thái điểm danh
                            </label>
                            <select name="status" id="status" class="form-select">
                                <option value="all" ${statusFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                                <option value="present" ${statusFilter == 'present' ? 'selected' : ''}>Đã tham gia</option>
                                <option value="absent" ${statusFilter == 'absent' ? 'selected' : ''}>Đã vắng</option>
                                <option value="pending" ${statusFilter == 'pending' ? 'selected' : ''}>Chưa điểm danh</option>
                            </select>
                        </div>
                        <div class="col-md-6 d-flex align-items-end gap-2">
                            <input type="hidden" name="page" value="1">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-funnel-fill"></i> Lọc
                            </button>
                            <a href="<%= request.getContextPath() %>/VolunteerAttendanceServlet" class="btn btn-secondary">
                                <i class="bi bi-x-circle"></i> Xóa lọc
                            </a>
                        </div>
                    </form>
                </div>
            </div>

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
                                List<Attendance> attendanceList = (List<Attendance>) request.getAttribute("attendanceList");
                                if (attendanceList != null && !attendanceList.isEmpty()) {
                                    int index = 1;
                                    for (Attendance a : attendanceList) {
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
                    
                    <!-- Phân trang - LUÔN HIỆN -->
                    <c:if test="${totalPages >= 1}">
                        <nav aria-label="Attendance pagination" class="mt-4">
                            <ul class="pagination justify-content-center">
                                <!-- Nút Trước -->
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage - 1}&status=${statusFilter}">
                                        &lt; Trước
                                    </a>
                                </li>

                                <!-- Các số trang -->
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}&status=${statusFilter}">${i}</a>
                                    </li>
                                </c:forEach>

                                <!-- Nút Sau -->
                                <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage + 1}&status=${statusFilter}">
                                        Sau &gt;
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </c:if>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
