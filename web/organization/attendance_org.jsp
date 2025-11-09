<%-- 
    Document   : attendance_org
    Created on : Nov 9, 2025, 5:04:24 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Điểm danh sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
         <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <h3 class="fw-bold mb-4">Điểm danh sự kiện</h3>

                    <!-- Bộ lọc -->
                    <form method="get" action="AttendanceEventServlet" class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <input type="hidden" name="eventId" value="${eventId}">
                        
                        <!-- Nhóm dropdown + nút lọc/reset -->
                        <div class="d-flex gap-2 align-items-center flex-wrap">
                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái:</label>
                                <select name="status" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="all" ${statusFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                                    <option value="pending" ${statusFilter == 'pending' ? 'selected' : ''}>Chưa xử lý</option>
                                    <option value="present" ${statusFilter == 'present' ? 'selected' : ''}>Tham gia</option>
                                    <option value="absent" ${statusFilter == 'absent' ? 'selected' : ''}>Vắng</option>
                                </select>
                            </div>

                            <!-- Nút Lọc -->
                            <button type="submit" class="btn btn-primary btn-sm" style="min-width:110px; align-self:end;">
                                <i class="bi bi-search"></i> Lọc
                            </button>

                            <!-- Nút Reset -->
                            <a href="AttendanceEventServlet?eventId=${eventId}" class="btn btn-secondary btn-sm" style="min-width:110px; align-self:end;">
                                <i class="bi bi-arrow-counterclockwise"></i> Reset
                            </a>
                        </div>
                    </form>

                    <!-- Form cập nhật -->
                    <form method="post" action="AttendanceEventServlet">
                        <input type="hidden" name="eventId" value="${eventId}">
                        <input type="hidden" name="action" value="update">
                        
                        <!-- Bảng dữ liệu -->
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                                <thead class="table-secondary">
                                    <tr>
                                        <th style="width:5%;">STT</th>
                                        <th style="width:10%;">Mã ID</th>
                                        <th style="width:20%;">Họ tên</th>
                                        <th style="width:15%;">Số điện thoại</th>
                                        <th style="width:20%;">Email</th>
                                        <th style="width:10%;">Trạng thái</th>
                                        <th style="width:20%;">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty volunteers}">
                                            <tr>
                                                <td colspan="7" class="text-center text-muted">
                                                    Không có tình nguyện viên nào
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="vol" items="${volunteers}" varStatus="status">
                                                <tr>
                                                    <td>${status.index + 1}</td>
                                                    <td>${vol.volunteerId}</td>
                                                    <td>${vol.volunteerName}</td>
                                                    <td>${vol.phone}</td>
                                                    <td>${vol.email}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${vol.status == 'pending'}">
                                                                <span class="badge bg-warning text-dark">Chưa xử lý</span>
                                                            </c:when>
                                                            <c:when test="${vol.status == 'present'}">
                                                                <span class="badge bg-success">Tham gia</span>
                                                            </c:when>
                                                            <c:when test="${vol.status == 'absent'}">
                                                                <span class="badge bg-danger">Vắng</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="volunteerId" value="${vol.volunteerId}">
                                                        <select name="status_${vol.volunteerId}" class="form-select form-select-sm">
                                                            <option value="pending" ${vol.status == 'pending' ? 'selected' : ''}>Chưa xử lý</option>
                                                            <option value="present" ${vol.status == 'present' ? 'selected' : ''}>Tham gia</option>
                                                            <option value="absent" ${vol.status == 'absent' ? 'selected' : ''}>Vắng</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>

                        <!-- Nút hành động -->
                        <div class="d-flex gap-2 mt-3">
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-check-circle"></i> Cập nhật
                            </button>
                            <a href="<%= request.getContextPath() %>/OrganizationListServlet" class="btn btn-secondary">
                                <i class="bi bi-x-circle"></i> Hủy
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
