<%-- 
    Document   : users_admin
    Created on : Sep 16, 2025, 9:30:33 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
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
            <div class="main-content">
                <h1>User management</h1> 
                <!-- Filter + Search -->
                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                    <!-- Filter (Role / Status) -->
                    <form action="AdminUserServlet" method="get" class="d-flex gap-3 flex-wrap" style="flex: 3;">
                        <select name="role" class="form-select w-25">
                            <option value="">-- Vai Trò --</option>
                            <option value="admin">Quản trị viên</option>
                            <option value="organization">Người tổ chức</option>
                            <option value="volunteer">Tình nguyện viên</option>
                        </select>

                        <input type="hidden" name="search" value="${fn:escapeXml(currentSearch)}" />
                        <input type="hidden" name="sort" value="${fn:escapeXml(currentSort)}" />
                        <button type="submit" class="btn btn-danger">
                            <i class="bi bi-filter"></i> Lọc
                        </button>
                        <a href="AdminUserServlet" class="btn btn-secondary">
                            <i class="bi bi-trash"></i> Reset
                        </a>
                    </form>

                    <form action="AdminUserServlet" method="get" class="d-flex" style="flex: 1; justify-content: end;">
                        <input type="text" name="search" class="form-control w-100" placeholder="Họ tên" value="${fn:escapeXml(currentSearch)}"/>
                        <input type="hidden" name="role" value="${fn:escapeXml(currentRole)}" />
                        <input type="hidden" name="sort" value="${fn:escapeXml(currentSort)}" />
                        <button type="submit" class="btn btn-primary ms-2">Tìm</button>
                    </form>
                </div>

                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID
                                <c:url var="sortAscUrl" value="AdminUserServlet">
                                    <c:param name="sort" value="id_asc" />
                                    <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                    <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                    <c:param name="page" value="${currentPage}" />
                                </c:url>
                                <a href="${sortAscUrl}"><i class="bi bi-caret-up-fill text-white ms-1"></i></a>

                                <c:url var="sortDescUrl" value="AdminUserServlet">
                                    <c:param name="sort" value="id_desc" />
                                    <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                    <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                    <c:param name="page" value="${currentPage}" />
                                </c:url>
                                <a href="${sortDescUrl}"><i class="bi bi-caret-down-fill text-white ms-1"></i></a>
                            </th>
                            <th>Ảnh</th>
                            <th>Tài khoản</th>
                            <th>Họ tên</th>
                            <th>Giới tính</th>
                            <th>Vai trò</th>
                            <th>Tính năng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty users}">
                            <!-- This practically never happens because to view this list you need admin to exist... unless well you mess up the database lmao -->
                            <tr><td colspan="7" class="text-center text-danger">Không tìm thấy người dùng hơp lệ</td></tr>
                        </c:if>

                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.id}</td>
                                <td>
                                    <img src="${user.avatar}" alt="avatar" class="rounded-circle" width="50" height="50">
                                </td>
                                <td>${user.account.username}</td>
                                <td>${user.full_name}</td>
                                <td>${user.gender}</td>
                                <td>${user.account.role}</td>
                                <td class="text-center">
                                    <form action="AdminUserDetailServlet" method="get" style="display:inline;">
                                        <input type="hidden" name="id" value="${user.id}">
                                        <button type="submit" class="btn btn-primary btn-sm" title="Xem chi tiết">
                                            <i class="bi bi-eye"></i>
                                        </button>
                                    </form>
                                    <form action="AdminUserEditServlet" method="get" style="display:inline;">
                                        <input type="hidden" name="id" value="${user.id}">
                                        <button type="submit" class="btn btn-warning btn-sm" title="Chỉnh sửa">
                                            <i class="bi bi-pencil-square"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <!-- Pagination -->
                <!-- Pagination Controls -->
                <div class="d-flex flex-column flex-md-row justify-content-between align-items-center mt-4 gap-3">

                    <!-- Left: Page info -->
                    <div class="text-muted small">
                        Page <strong>${currentPage}</strong> of <strong>${totalPages}</strong>
                    </div>

                    <!-- Center: Pagination (Prev / Numbers / Next) -->
                    <nav aria-label="User list pagination">
                        <ul class="pagination mb-0">
                            <!-- Previous button -->
                            <c:url var="prevUrl" value="AdminUserServlet">
                                <c:param name="page" value="${currentPage - 1}" />
                                <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                <c:param name="sort" value="${fn:escapeXml(currentSort)}" />
                            </c:url>
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${prevUrl}" tabindex="-1">Trước</a>
                            </li>

                            <!-- Page number links -->
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <c:url var="pageUrl" value="AdminUserServlet">
                                    <c:param name="page" value="${i}" />
                                    <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                    <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                    <c:param name="sort" value="${fn:escapeXml(currentSort)}" />
                                </c:url>
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageUrl}">${i}</a>
                                </li>
                            </c:forEach>

                            <!-- Next button -->
                            <c:url var="nextUrl" value="AdminUserServlet">
                                <c:param name="page" value="${currentPage + 1}" />
                                <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                <c:param name="sort" value="${fn:escapeXml(currentSort)}" />
                            </c:url>
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${nextUrl}">Sau</a>
                            </li>
                        </ul>
                    </nav>

                    <!-- Right: Go to page form -->
                    <form action="AdminUserServlet" method="get" class="d-flex align-items-center gap-2">
                        <label for="gotoPage" class="form-label mb-0">Trang hiện tại:</label>
                        <input type="number" id="gotoPage" name="page" min="1" max="${totalPages}" value="${currentPage}" class="form-control" style="width: 80px;">
                        <!-- Preserve filter/sort -->
                        <input type="hidden" name="role" value="${fn:escapeXml(currentRole)}" />
                        <input type="hidden" name="search" value="${fn:escapeXml(currentSearch)}" />
                        <input type="hidden" name="sort" value="${fn:escapeXml(currentSort)}" />
                        <button type="submit" class="btn btn-danger btn-sm">Chọn</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
