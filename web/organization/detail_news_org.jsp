<%-- 
    Document   : detail_news_org
    Created on : Nov 2, 2025, 3:04:10 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết bài viết</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />
            <div class="main-content p-4">
                <div class="container d-flex justify-content-center">
                    <div class="w-100" style="max-width: 800px;">
                        <h3 class="fw-bold mb-4 text-center">Chi tiết bài viết</h3>
                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">
                                <div class="row g-3">
                                    <div class="text-center">
                                        <img src="${pageContext.request.contextPath}/NewsImage?file=${news.images}" style="max-width:250px;max-height:250px;object-fit:contain;">
                                    </div>
                                    <!-- ID bài viết -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">ID Bài viết</label>
                                        <input type="text" class="form-control" value="${news.id}" readonly>
                                    </div>
                                    <!-- Title -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tiêu đề</label>
                                        <input type="text" class="form-control" value="${news.title}" readonly>
                                    </div>
                                    <!-- Organization id -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">ID quản trị viên</label>
                                        <input type="text" class="form-control" value="${news.organizationId}" readonly>
                                    </div>
                                    <!-- Người đăng -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Người đăng</label>
                                        <input type="text" class="form-control" value="${news.organizationName}" readonly>
                                    </div>
                                    <!-- Ngày tạo -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Ngày tạo</label>
                                        <input type="text" class="form-control" value="${news.createdAt}" readonly>
                                    </div>
                                    <!-- Trạng thái -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Trạng thái</label>
                                        <c:choose>
                                            <c:when test="${news.status == 'published'}">
                                                <input type="text" class="form-control" value="Hiển Thị" readonly>
                                            </c:when>
                                            <c:when test="${news.status == 'hidden'}">
                                                <input type="text" class="form-control" value="Ẩn" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control" value="Unknown" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Nội dung bài viết -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Nội dung</label>
                                        <textarea class="form-control" rows="12" style="resize:none;" readonly>${news.content}</textarea>
                                    </div>
                                    <!-- Nút thao tác -->
                                    <div class="col-12 mt-3">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <a href="${pageContext.request.contextPath}/OrganizationManageNews" class="btn btn-secondary">
                                                <i class="bi bi-arrow-left me-1"></i> Quay lại
                                            </a>
                                            <div>
                                                <a href="${pageContext.request.contextPath}/OrganizationNewsEdit?id=${news.id}" class="btn btn-primary">
                                                    <i class="bi bi-pencil me-1"></i> Sửa
                                                </a>
                                            </div>
                                        </div>
                                    </div>
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