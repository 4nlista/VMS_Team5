<%-- 
    Document   : detail_new_admin
    Created on : Nov 4, 2025, 11:26:39 PM
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
    <title>Chi tiết Bài viết</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet">
    <style>
        body { 
            background: #f4f6f9; 
        }
        .content-container {
            min-height: 100vh;
        }
        .card {
            border: 1px solid #dee2e6;
            border-radius: 0;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        .card-header {
            background: linear-gradient(to right, #f8f9fa, #e9ecef);
            border-bottom: 2px solid #0d6efd;
            font-weight: 600;
            padding: 0.65rem 1rem;
            font-size: 0.95rem;
        }
        .card-body {
            padding: 1rem;
        }
        .info-row {
            padding: 0.5rem 0;
            border-bottom: 1px solid #e9ecef;
        }
        .info-row:last-child {
            border-bottom: none;
        }
        .info-label {
            font-weight: 600;
            color: #495057;
            min-width: 140px;
            font-size: 0.9rem;
        }
        .info-value {
            color: #6c757d;
            font-size: 0.9rem;
        }
        .image-container {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            padding: 0.75rem;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .image-container img {
            max-width: 100%;
            max-height: 280px;
            object-fit: contain;
            display: block;
        }
        .content-box {
            background: #fff;
            border: 1px solid #dee2e6;
            padding: 1rem;
            min-height: 280px;
            max-height: 450px;
            overflow-y: auto;
            line-height: 1.6;
            color: #212529;
            white-space: pre-wrap;
            word-wrap: break-word;
            font-size: 0.9rem;
            text-align: left;
        }
        .status-badge {
            padding: 0.4rem 1rem;
            border-radius: 0;
            font-weight: 500;
            display: inline-block;
            font-size: 0.9rem;
        }
        .badge-pending {
            background: #fff3cd;
            color: #856404;
            border: 1px solid #ffc107;
        }
        .badge-published {
            background: #d4edda;
            color: #155724;
            border: 1px solid #28a745;
        }
        .badge-rejected {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #dc3545;
        }
        .badge-hidden {
            background: #e2e3e5;
            color: #383d41;
            border: 1px solid #6c757d;
        }
        .btn {
            border-radius: 0;
            padding: 0.45rem 1.2rem;
            font-weight: 500;
            font-size: 0.9rem;
        }
        .action-card {
            background: #fff;
            border: 1px solid #dee2e6;
            padding: 1rem;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        .alert {
            border-radius: 0;
            padding: 0.75rem 1rem;
            font-size: 0.875rem;
        }
        .page-header {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            color: white;
            padding: 1rem 1.25rem;
            margin: -1rem -1rem 1rem -1rem;
            border-bottom: 3px solid #0a58ca;
        }
    </style>
</head>
<body>
    <div class="content-container">
        <jsp:include page="layout_admin/sidebar_admin.jsp" />
        
        <div class="main-content">
            <div class="container-fluid p-4">
                
                <!-- Header -->
                <div class="page-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h4 class="mb-1">
                                <i class="bi bi-file-earmark-text me-2"></i>Chi tiết Bài viết
                            </h4>
                            <p class="mb-0 opacity-75" style="font-size: 0.9rem;">Kiểm tra và duyệt nội dung bài viết</p>
                        </div>
                        <a href="${pageContext.request.contextPath}/AdminNewsServlet" class="btn btn-light">
                            <i class="bi bi-arrow-left me-2"></i>Quay lại
                        </a>
                    </div>
                </div>

                <!-- Row 1: Thông tin cơ bản và Trạng thái -->
                <div class="row g-3 mb-3">
                    <div class="col-lg-9">
                        <div class="card h-100">
                            <div class="card-header">
                                <i class="bi bi-info-circle me-2"></i>Thông tin bài viết
                            </div>
                            <div class="card-body">
                                <div class="info-row d-flex">
                                    <div class="info-label">
                                        <i class="bi bi-card-heading me-2"></i>Tiêu đề:
                                    </div>
                                    <div class="info-value flex-grow-1 ms-3 fw-semibold">
                                        ${news.title}
                                    </div>
                                </div>
                                <div class="info-row d-flex">
                                    <div class="info-label">
                                        <i class="bi bi-building me-2"></i>Tổ chức:
                                    </div>
                                    <div class="info-value flex-grow-1 ms-3">
                                        ${news.organizationName}
                                    </div>
                                </div>
                                <div class="info-row d-flex">
                                    <div class="info-label">
                                        <i class="bi bi-calendar me-2"></i>Ngày tạo:
                                    </div>
                                    <div class="info-value flex-grow-1 ms-3">
                                        <fmt:formatDate value="${news.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                                    </div>
                                </div>
                                <c:if test="${news.updatedAt != null}">
                                    <div class="info-row d-flex">
                                        <div class="info-label">
                                            <i class="bi bi-clock me-2"></i>Cập nhật:
                                        </div>
                                        <div class="info-value flex-grow-1 ms-3">
                                            <fmt:formatDate value="${news.updatedAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3">
                        <div class="card h-100">
                            <div class="card-header">
                                <i class="bi bi-flag me-2"></i>Trạng thái
                            </div>
                            <div class="card-body d-flex align-items-center justify-content-center">
                                <c:choose>
                                    <c:when test="${news.status == 'pending'}">
                                        <span class="status-badge badge-pending">
                                            <i class="bi bi-clock-history me-2"></i>Chờ duyệt
                                        </span>
                                    </c:when>
                                    <c:when test="${news.status == 'published'}">
                                        <span class="status-badge badge-published">
                                            <i class="bi bi-check-circle-fill me-2"></i>Đã duyệt
                                        </span>
                                    </c:when>
                                    <c:when test="${news.status == 'rejected'}">
                                        <span class="status-badge badge-rejected">
                                            <i class="bi bi-x-circle-fill me-2"></i>Đã từ chối
                                        </span>
                                    </c:when>
                                    <c:when test="${news.status == 'hidden'}">
                                        <span class="status-badge badge-hidden">
                                            <i class="bi bi-eye-slash-fill me-2"></i>Đang ẩn
                                        </span>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Row 2: Hình ảnh và Nội dung -->
                <div class="row g-3 mb-3">
                    <!-- Cột Hình ảnh -->
                    <div class="col-lg-5">
                        <div class="card h-100">
                            <div class="card-header">
                                <i class="bi bi-image me-2"></i>Hình ảnh
                            </div>
                            <div class="card-body p-0">
                                <div class="image-container">
                                    <c:choose>
                                        <c:when test="${not empty news.images}">
                                            <img src="${pageContext.request.contextPath}${news.images}" 
                                                 alt="Hình ảnh bài viết"
                                                 onerror="this.src='https://via.placeholder.com/400x300?text=Loi+tai+anh'">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center text-muted py-5">
                                                <i class="bi bi-image" style="font-size: 3rem;"></i>
                                                <p class="mt-2 mb-0">Không có hình ảnh</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Cột Nội dung -->
                    <div class="col-lg-7">
                        <div class="card h-100">
                            <div class="card-header">
                                <i class="bi bi-file-text me-2"></i>Nội dung bài viết
                            </div>
                            <div class="card-body p-0">
                                <div class="content-box">
                                    ${news.content}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Row 3: Actions -->
                <div class="row g-3">
                    <div class="col-12">
                        <div class="action-card">
                            <h5 class="mb-3 pb-2 border-bottom border-primary">
                                <i class="bi bi-gear me-2"></i>Hành động quản trị
                            </h5>
                            
                            <!-- Hướng dẫn -->
                            <div class="alert alert-light border mb-3">
                                <strong><i class="bi bi-info-circle me-2"></i>Hướng dẫn:</strong>
                                <ul class="mb-0 mt-2">
                                    <c:if test="${news.status == 'pending'}">
                                        <li><strong>Duyệt bài:</strong> Bài viết sẽ được công khai</li>
                                        <li><strong>Từ chối:</strong> Bài viết sẽ bị từ chối</li>
                                    </c:if>
                                    <c:if test="${news.status == 'published'}">
                                        <li><strong>Ẩn bài:</strong> Tạm thời ẩn bài viết</li>
                                    </c:if>
                                    <c:if test="${news.status == 'hidden'}">
                                        <li><strong>Hiện bài:</strong> Hiển thị lại bài viết</li>
                                    </c:if>
                                    <c:if test="${news.status == 'rejected'}">
                                        <li><strong>Duyệt lại:</strong> Duyệt bài viết đã từ chối</li>
                                    </c:if>
                                </ul>
                            </div>

                            <!-- Action Buttons -->
                            <div class="d-flex gap-2 flex-wrap">
                                <!-- Pending: Duyệt / Từ chối -->
                                <c:if test="${news.status == 'pending'}">
                                    <form method="post" action="${pageContext.request.contextPath}/AdminNewsServlet" class="d-inline">
                                        <input type="hidden" name="newsId" value="${news.id}">
                                        <input type="hidden" name="action" value="approve">
                                        <button type="submit" class="btn btn-success">
                                            <i class="bi bi-check-circle me-2"></i>Duyệt bài
                                        </button>
                                    </form>
                                    <form method="post" action="${pageContext.request.contextPath}/AdminNewsServlet" class="d-inline">
                                        <input type="hidden" name="newsId" value="${news.id}">
                                        <input type="hidden" name="action" value="reject">
                                        <button type="submit" class="btn btn-danger">
                                            <i class="bi bi-x-circle me-2"></i>Từ chối
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Published: Ẩn -->
                                <c:if test="${news.status == 'published'}">
                                    <form method="post" action="${pageContext.request.contextPath}/AdminNewsServlet" class="d-inline">
                                        <input type="hidden" name="newsId" value="${news.id}">
                                        <input type="hidden" name="action" value="hide">
                                        <button type="submit" class="btn btn-warning">
                                            <i class="bi bi-eye-slash me-2"></i>Ẩn bài
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Hidden: Hiện -->
                                <c:if test="${news.status == 'hidden'}">
                                    <form method="post" action="${pageContext.request.contextPath}/AdminNewsServlet" class="d-inline">
                                        <input type="hidden" name="newsId" value="${news.id}">
                                        <input type="hidden" name="action" value="publish">
                                        <button type="submit" class="btn btn-success">
                                            <i class="bi bi-eye me-2"></i>Hiện bài
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Rejected: Duyệt lại -->
                                <c:if test="${news.status == 'rejected'}">
                                    <form method="post" action="${pageContext.request.contextPath}/AdminNewsServlet" class="d-inline">
                                        <input type="hidden" name="newsId" value="${news.id}">
                                        <input type="hidden" name="action" value="approve">
                                        <button type="submit" class="btn btn-success">
                                            <i class="bi bi-arrow-repeat me-2"></i>Duyệt lại
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Nút Hủy -->
                                <a href="${pageContext.request.contextPath}/AdminNewsServlet" class="btn btn-secondary">
                                    <i class="bi bi-x-lg me-2"></i>Hủy
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>