<%-- 
    Document   : manage_new_org
    Created on : Nov 2, 2025, 2:56:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Báo cáo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container d-flex">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4 flex-grow-1">
                <div class="container" style="max-width: 1000px;">
                    <h3 class="fw-bold mb-4 text-center">Danh sách bài viết</h3>

                    <!-- Bộ lọc + nút tạo mới -->
                    <form method="get" action="OrganizationManageNews" 
                          class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">

                        <div class="d-flex gap-2 align-items-end flex-wrap">
                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái</label>
                                <select name="status" class="form-select form-select-sm" style="width: 140px;">
                                    <option value="">Tất cả</option>
                                    <option value="published" ${currentStatus == 'published' ? 'selected' : ''}>Đã hiển thị</option>
                                    <option value="hidden" ${currentStatus == 'hidden' ? 'selected' : ''}>Đã bị ẩn</option>
                                </select>
                            </div>

                            <!-- Nút Lọc -->
                            <button type="submit" class="btn btn-primary btn-sm" style="min-width:100px;">
                                <i class="bi bi-search"></i> Lọc
                            </button>

                            <!-- Nút Reset -->
                            <a href="OrganizationManageNews" class="btn btn-secondary btn-sm" style="min-width:100px;">
                                <i class="bi bi-arrow-counterclockwise"></i> Reset
                            </a>
                        </div>

                        <!-- Nút tạo mới bài viết -->
                        <a href="${pageContext.request.contextPath}/organization/create_news_org.jsp" 
                           class="btn btn-success btn-sm" style="min-width:130px;">
                            <i class="bi bi-plus-lg"></i> Tạo bài đăng mới
                        </a>
                    </form>

                    <!-- Bảng dữ liệu -->
                    <div class="table-responsive" style="max-height: 500px; overflow-y:auto;">
                        <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                            <thead class="table-secondary">
                                <tr>
                                    <th style="width:5%;">STT</th>
                                    <th style="width:50%;">Tên bài viết</th>
                                    <th style="width:15%;">Trạng thái</th>
                                    <th style="width:30%;">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="newItem" items="${news}" varStatus="loop">
                                    <tr>
                                        <td>${newItem.id}</td>
                                        <td class="text-truncate" title="">${newItem.title}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${newItem.status == 'published'}">
                                                    <span class="badge bg-success">Hiển thị</span>
                                                </c:when>
                                                <c:when test="${newItem.status == 'hidden'}">
                                                    <span class="badge bg-warning text-dark">Bị Ẩn</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">UNKNOWN</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <form action="OrganizationNewsDetail" method="get" style="display:inline;">
                                                <input type="hidden" name="id" value="${newItem.id}">
                                                <button type="submit" class="btn btn-primary btn-sm me-1" title="Xem Thông Tin Chi Tiết">
                                                    Xem
                                                </button>
                                            </form>
                                            <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Pagination Section -->
                    <div class="card shadow-sm border-0 p-3">
                        <div class="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
                            <!-- Left: Page info -->
                            <div class="text-muted small">
                                Trang số <strong>${currentPage}</strong> trong tổng số <strong>${totalPages}</strong>
                            </div>

                            <!-- Center: Pagination Controls -->
                            <nav aria-label="User list pagination">
                                <ul class="pagination mb-0 flex-wrap justify-content-center">
                                    <!-- Previous -->
                                    <c:url var="prevUrl" value="OrganizationManageNews">
                                        <c:param name="page" value="${currentPage - 1}" />
                                        <c:param name="status" value="${fn:escapeXml(currentStatus)}" />
                                        <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                    </c:url>
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${prevUrl}">Trước</a>
                                    </li>
                                    <!-- Page numbers -->
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <c:url var="pageUrl" value="OrganizationManageNews">
                                            <c:param name="page" value="${i}" />
                                            <c:param name="status" value="${fn:escapeXml(currentStatus)}" />
                                            <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                        </c:url>
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrl}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <!-- Next -->
                                    <c:url var="nextUrl" value="OrganizationManageNews">
                                        <c:param name="page" value="${currentPage + 1}" />
                                        <c:param name="status" value="${fn:escapeXml(currentStatus)}" />
                                        <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                    </c:url>
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${nextUrl}">Tiếp</a>
                                    </li>
                                </ul>
                            </nav>

                            <!-- Right: Go to page -->
                            <form action="OrganizationManageNews" method="get" class="d-flex align-items-center gap-2">
                                <label for="gotoPage" class="form-label mb-0 small text-muted">Đi tới trang:</label>
                                <input type="number" id="gotoPage" name="page" min="1" max="${totalPages}" value="${currentPage}"
                                       class="form-control form-control-sm" style="width: 80px;">
                                <input type="hidden" name="status" value="${fn:escapeXml(currentStatus)}" />
                                <input type="hidden" name="search" value="${fn:escapeXml(currentSearch)}" />
                                <button type="submit" class="btn btn-primary btn-sm">Đi!</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

