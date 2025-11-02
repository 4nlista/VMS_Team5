<%-- 
    Document   : create_news_org
    Created on : Nov 2, 2025, 2:59:57 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gửi đơn báo cáo</title>
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
                        <h3 class="fw-bold mb-4 text-center">Tạo bài viết tin tức</h3>

                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">
                                <form action="<%= request.getContextPath() %>/organization/submit_news" 
                                      method="post" enctype="multipart/form-data" class="row g-3">

                                    <!-- Tiêu đề bài viết -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Tiêu đề</label>
                                        <input type="text" class="form-control" name="title" placeholder="Nhập tiêu đề bài viết" required>
                                    </div>

                                    <!-- Tên người tạo  -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tên người tạo</label>
                                        <input type="text" class="form-control" name="author" value="Nguyễn Bảo An" readonly>
                                    </div>

                                    <!-- Trạng thái -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Chế độ hiển thị</label>
                                        <select class="form-select" name="status" required>
                                            <option value="published">Hiển thị</option>
                                            <option value="hidden">Ẩn</option>
                                        </select>
                                    </div>
                                    <!-- Upload ảnh -->
                                    <div class="col-12">


                                        <!-- Khung xem trước ảnh -->
                                        <div class="mt-3">
                                            <label class="form-label fw-semibold">Ảnh tin tức</label>
                                            <div class="border p-2 text-center mb-4" 
                                                 style="width:300px; height:250px; margin:auto; display:flex; align-items:center; justify-content:center; overflow:hidden;">
                                                <img src="<%= request.getAttribute("imageUrl") != null ? request.getAttribute("imageUrl") : "" %>" 
                                                     alt="Ảnh bài viết" 
                                                     style="max-width:100%; max-height:100%; object-fit:contain;">
                                            </div>
                                        </div>
                                        <input type="file" class="form-control" name="image" accept="image/*">

                                    </div>
                                    <!-- Nội dung bài viết -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Nội dung</label>
                                        <textarea class="form-control" name="content" rows="6" placeholder="Nhập nội dung bài viết..." required></textarea>
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
                                                    <i class="bi bi-upload me-1"></i> Tạo bài viết
                                                </button>
                                                <a href="<%= request.getContextPath() %>/organization/create_news_org.jsp" 
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
