<%-- 
    Document   : manage_news_admin
    Created on : Sep 23, 2025, 8:56:21 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Bài viết</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet">
    <style>
        body { background: #f5f7fa; }
        .admin-card {
            background: white;
            border: 1px solid #e0e6ed;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }
        .admin-card-header {
            background: #fff;
            border-bottom: 2px solid #e0e6ed;
            padding: 20px;
            border-radius: 8px 8px 0 0;
        }
        .filter-box {
            background: white;
            border: 1px solid #e0e6ed;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
        }
        .table-container {
            border: 1px solid #e0e6ed;
            border-radius: 8px;
            overflow: hidden;
            background: white;
        }
        .table th {
            background: #f8f9fa;
            border-bottom: 2px solid #dee2e6;
            font-weight: 600;
            padding: 15px 12px;
        }
        .table td {
            padding: 15px 12px;
            vertical-align: middle;
            border-bottom: 1px solid #e9ecef;
        }
        .table tbody tr:hover { background: #f8f9fa; }
        .status-badge {
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 13px;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }
        .badge-pending { background: #fff3cd; color: #856404; border: 1px solid #ffeaa7; }
        .badge-published { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .badge-rejected { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .badge-hidden { background: #e2e3e5; color: #383d41; border: 1px solid #d6d8db; }
        .pagination-container {
            background: white;
            border: 1px solid #e0e6ed;
            border-radius: 8px;
            padding: 15px 20px;
        }
        .form-label { font-weight: 600; color: #495057; margin-bottom: 8px; font-size: 14px; }
        .form-select, .form-control {
            border: 1px solid #ced4da;
            border-radius: 6px;
            padding: 10px 12px;
        }
        .btn { padding: 10px 20px; border-radius: 6px; font-weight: 500; }
    </style>
</head>
<body>
    <div class="content-container">
        <jsp:include page="layout_admin/sidebar_admin.jsp" />
        
        <div class="main-content" style="padding: 20px;">
            <div class="container-fluid">
                
                <!-- Page Header -->
                <div class="admin-card">
                    <div class="admin-card-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h3 class="mb-1"><i class="bi bi-newspaper me-2"></i>Quản lý tin tức</h3>
                                <p class="text-muted mb-0">Duyệt và kiểm soát nội dung bài đăng</p>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-primary" style="font-size: 16px;">
                                    Tổng số : ${totalRecords} bài viết
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Success/Error Messages -->
                <c:if test="${param.success == 'true'}">
                    <div class="alert alert-success alert-dismissible fade show border" role="alert">
                        <i class="bi bi-check-circle-fill me-2"></i><strong>Thành công!</strong> Cập nhật trạng thái bài viết thành công.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${param.error == 'true'}">
                    <div class="alert alert-danger alert-dismissible fade show border" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i><strong>Lỗi!</strong> Có lỗi xảy ra. Vui lòng thử lại.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Filter Box -->
                <div class="filter-box">
                    <form method="get" action="${pageContext.request.contextPath}/AdminNewsServlet">
                        <div class="row g-3">
                            <div class="col-md-3">
                                <label class="form-label">
                                    <i class="bi bi-funnel me-1"></i>Trạng thái
                                </label>
                                <select name="status" class="form-select">
                                    <option value="all" ${statusFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                                    <option value="pending" ${statusFilter == 'pending' ? 'selected' : ''}>Chờ duyệt</option>
                                    <option value="published" ${statusFilter == 'published' ? 'selected' : ''}>Đã duyệt</option>
                                    <option value="rejected" ${statusFilter == 'rejected' ? 'selected' : ''}>Từ chối</option>
                                    <option value="hidden" ${statusFilter == 'hidden' ? 'selected' : ''}>Đang ẩn</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">
                                    <i class="bi bi-sort-down me-1"></i>Sắp xếp
                                </label>
                                <select name="sort" class="form-select">
                                    <option value="newest" ${sortOrder == 'newest' ? 'selected' : ''}>Mới nhất</option>
                                    <option value="oldest" ${sortOrder == 'oldest' ? 'selected' : ''}>Cũ nhất</option>
                                </select>
                            </div>
                            <div class="col-md-6 d-flex align-items-end gap-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-search me-1"></i>Lọc
                                </button>
                                <a href="${pageContext.request.contextPath}/AdminNewsServlet" class="btn btn-secondary">
                                    <i class="bi bi-arrow-counterclockwise me-1"></i>Reset
                                </a>
                            </div>
                        </div>
                    </form>
                </div>

                <!-- Table -->
                <div class="table-container">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th style="width: 5%;">STT</th>
                                <th style="width: 35%;">Tiêu đề</th>
                                <th style="width: 15%;">Tổ chức</th>
                                <th style="width: 13%;">Ngày tạo</th>
                                <th style="width: 12%;">Trạng thái</th>
                                <th style="width: 20%;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty newsList}">
                                    <tr>
                                        <td colspan="6" class="text-center py-5">
                                            <i class="bi bi-inbox fs-1 text-muted"></i>
                                            <p class="text-muted mt-3 mb-0">Không có bài viết nào</p>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="news" items="${newsList}" varStatus="status">
                                        <tr>
                                            <td class="text-center fw-bold">${startRecord + status.index}</td>
                                            <td>
                                                <div class="fw-semibold text-truncate" style="max-width: 400px;">
                                                    ${news.title}
                                                </div>
                                                <small class="text-muted text-truncate d-block" style="max-width: 400px;">
                                                    ${news.content.length() > 60 ? news.content.substring(0, 60).concat('...') : news.content}
                                                </small>
                                            </td>
                                            <td>${news.organizationName}</td>
                                            <td>
                                                <div><fmt:formatDate value="${news.createdAt}" pattern="dd/MM/yyyy" /></div>
                                                <small class="text-muted"><fmt:formatDate value="${news.createdAt}" pattern="HH:mm:ss" /></small>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${news.status == 'pending'}">
                                                        <span class="status-badge badge-pending">
                                                            <i class="bi bi-clock-history"></i>Chờ duyệt
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${news.status == 'published'}">
                                                        <span class="status-badge badge-published">
                                                            <i class="bi bi-check-circle-fill"></i>Đã duyệt
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${news.status == 'rejected'}">
                                                        <span class="status-badge badge-rejected">
                                                            <i class="bi bi-x-circle-fill"></i>Từ chối
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${news.status == 'hidden'}">
                                                        <span class="status-badge badge-hidden">
                                                            <i class="bi bi-eye-slash-fill"></i>Đang ẩn
                                                        </span>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/AdminNewsServlet?action=detail&id=${news.id}" 
                                                   class="btn btn-sm btn-warning">
                                                    <i></i>Xem chi tiết
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <!-- Pagination -->
                <c:if test="${not empty newsList}">
                    <div class="pagination-container d-flex justify-content-between align-items-center">
                        <div class="text-muted">
                            Hiển thị <strong>${startRecord}</strong> - <strong>${endRecord}</strong> 
                            trong tổng <strong>${totalRecords}</strong> bài viết
                        </div>
                        <ul class="pagination mb-0">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/AdminNewsServlet?status=${statusFilter}&sort=${sortOrder}&page=${currentPage - 1}">
                                    <i class="bi bi-chevron-left"></i> Trước
                                </a>
                            </li>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <c:if test="${i == currentPage || (i >= currentPage - 2 && i <= currentPage + 2)}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" 
                                           href="${pageContext.request.contextPath}/AdminNewsServlet?status=${statusFilter}&sort=${sortOrder}&page=${i}">
                                            ${i}
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" 
                                   href="${pageContext.request.contextPath}/AdminNewsServlet?status=${statusFilter}&sort=${sortOrder}&page=${currentPage + 1}">
                                    Sau <i class="bi bi-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                </c:if>

            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


