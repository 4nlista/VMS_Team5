<%-- 
    Document   : edit_news_org
    Created on : 4 Nov 2025, 13:31:21
    Author     : Mirinae
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <h3 class="fw-bold mb-4 text-center">Chỉnh sửa bài viết</h3>

                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">
                                <form action="OrganizationNewsEdit" method="post" class="row g-3">
                                    <!-- Title -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tiêu đề</label>
                                        <input type="text" class="form-control" name="title" value="${news.title}">
                                    </div>

                                    <!-- Trạng thái -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Trạng thái</label>
                                        <select class="form-select" name="status">
                                            <option value="published" ${news.status == 'published' ? 'selected' : ''}>Hiển thị</option>
                                            <option value="hidden" ${news.status == 'hidden' ? 'selected' : ''}>Ẩn</option>
                                        </select>
                                    </div>

                                    <!-- Nội dung bài viết -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Nội dung</label>
                                        <textarea class="form-control" name="content" rows="6">
                                            ${news.content}
                                        </textarea>
                                    </div>

                                    <!-- Nút thao tác -->
                                    <div class="col-12 mt-3">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <a href="OrganizationManageNews" class="btn btn-secondary">
                                                <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách
                                            </a>

                                            <div>
                                                <form action="OrganizationNewsEdit" method="get" style="display:inline;">
                                                    <input type="hidden" name="id" value="${news.id}">
                                                    <button type="submit" class="btn btn-primary btn-sm me-1" title="Chỉnh sửa">
                                                        <i class="bi bi-save me-1"></i> Sửa
                                                    </button>
                                                </form>
                                                <a href="OrganizationManageNews" class="btn btn-outline-danger">
                                                    <i class="bi bi-x-circle me-1"></i> Hủy
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
