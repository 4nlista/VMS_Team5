<%-- 
    Document   : detail_news_org
    Created on : Nov 2, 2025, 3:04:10 PM
    Author     : Admin
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
                        <h3 class="fw-bold mb-4 text-center">Chi tiết bài viết</h3>

                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">
                                <form action="<%= request.getContextPath() %>/organization/update_news" method="post" class="row g-3">

                                    <!-- ID bài viết -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">ID Bài viết</label>
                                        <input type="text" class="form-control" name="newsId" value="1" readonly>
                                    </div>

                                    <!-- Người đăng -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Người đăng</label>
                                        <input type="text" class="form-control" name="authorName" value="OrganizationName" readonly>
                                    </div>

                                    <!-- Ngày tạo -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Ngày tạo</label>
                                        <input type="text" class="form-control" name="createdAt" value="01/11/2025" readonly>
                                    </div>

                                    <!-- Trạng thái -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Trạng thái</label>
                                        <select class="form-select" name="status">
                                            <option value="published">Hiển thị</option>
                                            <option value="hidden">Ẩn</option>
                                        </select>
                                    </div>

                                    <!-- Nội dung bài viết -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Nội dung</label>
                                        <textarea class="form-control" name="content" rows="6" readonly>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                                        Phasellus euismod, justo nec consectetur malesuada, 
                                        nisl nisi vehicula metus, ac facilisis mi eros in ligula.
                                        </textarea>
                                    </div>

                                    <!-- Nút thao tác -->
                                    <div class="col-12 mt-3">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <a href="<%= request.getContextPath() %>/organization/manage_new_org.jsp" 
                                               class="btn btn-secondary">
                                                <i class="bi bi-arrow-left me-1"></i> Quay lại
                                            </a>

                                            <div>
                                                <button type="submit" class="btn btn-primary me-2">
                                                    <i class="bi bi-save me-1"></i> Cập nhật
                                                </button>
                                                <a href="<%= request.getContextPath() %>/organization/detail_news_org.jsp" 
                                                   class="btn btn-outline-danger">
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
