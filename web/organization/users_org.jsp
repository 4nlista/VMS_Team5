<%-- 
    Document   : users_org
    Created on : Sep 17, 2025, 7:34:33 PM
    Author     : Organization
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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


            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <!-- Main Content -->
            <div class="main-content p-4">
                <div class="container-fluid">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h3 class="fw-bold mb-0">Danh sách tình nguyện viên</h3>
                        <a href="<%= request.getContextPath() %>/OrganizationListServlet" class="btn btn-secondary btn-sm">
                            <i class="bi bi-arrow-left"></i> Quay về quản lí sự kiện
                        </a>
                    </div>

                    <!-- Bộ lọc + nút tạo mới (nếu cần) -->
                    <form method="get" action="OrganizationVolunteersServlet" class="d-flex align-items-end mb-3 flex-wrap gap-3">
                        <c:if test="${not empty eventId}">
                            <input type="hidden" name="eventId" value="${eventId}" />
                        </c:if>
                        <!-- Inputs row + buttons ngay sau thanh search -->
                        <div class="d-flex align-items-end flex-wrap gap-3">
                            <!-- Giới tính -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Giới tính:</label>
                                <select name="gender" class="form-select" style="min-width:220px;">
                                    <option value="all" ${gender == 'all' || empty gender ? 'selected' : ''}>Tất cả</option>
                                    <option value="Male" ${gender == 'Male' ? 'selected' : ''}>Nam</option>
                                    <option value="Female" ${gender == 'Female' ? 'selected' : ''}>Nữ</option>
                                </select>
                            </div>
                            <!-- Bỏ tìm kiếm theo tên sự kiện -->
                            <!-- Tên tình nguyện viên -->
                            <div class="form-group">
                <label class="form-label fw-semibold">Tên tình nguyện viên</label>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <input class="form-control" style="min-width:320px" name="q" value="${q}" placeholder="Nhập tên..." />
                                </div>
                            </div>
                            <!-- Buttons placed right after search fields -->
                            <div class="d-flex align-items-end gap-2">
                                <button type="submit" class="btn btn-primary" style="min-width:140px;">
                                    <i class="bi bi-search"></i> Lọc và tìm kiếm
                                </button>
                                <c:url var="resetUrl" value="OrganizationVolunteersServlet">
                                    <c:if test="${not empty eventId}">
                                        <c:param name="eventId" value="${eventId}" />
                                    </c:if>
                                </c:url>
                                <a href="${resetUrl}" class="btn btn-secondary" style="min-width:110px;">
                                    <i class="bi bi-arrow-counterclockwise"></i> Làm mới
                                </a>
                            </div>
                        </div>
                    </form>

                    <!-- Bảng dữ liệu -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                            <thead class="table-secondary">
                                <tr>
                                    <th style="width:5%;">STT</th>
                                    <th style="width:25%;">Tên</th>
                                    <th style="width:10%;">Giới tính</th>
                                    <th style="width:15%;">Ngày sinh</th>
                                    <th style="width:15%;">Số điện thoại</th>
                                    <th style="width:15%;">Địa chỉ</th>
                                    <th style="width:15%;">Thao tác</th>
                                </tr>
                            </thead>
                            
                            <tbody>
                                <c:forEach var="p" items="${profiles}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/OrganizationVolunteerDetailServlet?volunteerId=${p.id}" class="text-decoration-none text-dark">${p.fullName}</a>
                                        </td>
                                        <td>
                                            <c:choose>
<c:when test="${p.gender == 'Male' || p.gender == 'male' || p.gender == 'Nam'}">Nam</c:when>
                                                <c:when test="${p.gender == 'Female' || p.gender == 'female' || p.gender == 'Nữ'}">Nữ</c:when>
                                                <c:otherwise>Khác</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${p.dob}" pattern="yyyy/MM/dd" /></td>
                                        <td>${p.phone}</td>
                                        <td>${p.address}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/OrganizationVolunteerDetailServlet?volunteerId=${p.id}" class="btn btn-primary btn-sm me-1">Chi tiết</a>
                                            <a href="<%= request.getContextPath() %>/organization/send_notification_org.jsp" class="btn btn-info btn-sm">Thông báo</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty profiles}">
                                    <tr>
                                        <td colspan="7" class="text-center">Không có dữ liệu</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>

                    <!-- Phân trang -->                            
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <span>Hiển thị ${profiles.size()} người dùng</span>
                        <ul class="pagination pagination-sm mb-0">
                            <li class="page-item disabled"><a class="page-link" href="#">Trước</a></li>
                            <li class="page-item active"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">Sau</a></li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>