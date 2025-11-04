<%-- 
    Document   : detail_new_admin
    Created on : Nov 4, 2025, 11:26:39 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết bài đăng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />
            <div class="main-content" style="padding: 20px;">
                <div class="container-fluid">
                    <h3 class="fw-bold mb-4">Chi tiết bài đăng</h3>

                    <!-- Ảnh bài đăng -->
                    <div class="mb-4">
                        <label class="form-label fw-bold">Ảnh tiêu đề:</label>
                        <div class="border rounded p-3 bg-light text-center">
                            <img src="https://images.unsplash.com/photo-1499750310107-5fef28a66643?w=800" 
                                 alt="Ảnh bài đăng" 
                                 class="img-fluid rounded" 
                                 style="max-height: 400px; object-fit: cover;">
                        </div>
                    </div>

                    <!-- Thông tin bài đăng -->
                    <div class="row g-3 mb-4">
                        <!-- Cột trái -->
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Tiêu đề:</label>
                                <p class="form-control-plaintext border rounded p-2 bg-light">
                                    Hướng dẫn sử dụng Java Spring Boot cơ bản
                                </p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Người đăng:</label>
                                <p class="form-control-plaintext border rounded p-2 bg-light">
                                    Nguyễn Văn A
                                </p>
                            </div>
                        </div>

                        <!-- Cột phải -->
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Ngày tạo:</label>
                                <p class="form-control-plaintext border rounded p-2 bg-light">
                                    04/11/2025 14:30:25
                                </p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Trạng thái:</label>
                                <select class="form-select">
                                    <option value="public">Hiện</option>
                                    <option value="private">Ẩn</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- Nội dung bài đăng -->
                    <div class="mb-4">
                        <label class="form-label fw-bold">Nội dung:</label>
                        <div class="border rounded p-3 bg-light" style="min-height: 200px;">
                            <p>
                                Spring Boot là một framework mạnh mẽ giúp đơn giản hóa việc phát triển ứng dụng Java. 
                                Trong bài viết này, chúng ta sẽ tìm hiểu các khái niệm cơ bản và cách bắt đầu với Spring Boot.
                            </p>
                            <p>
                                <strong>1. Cài đặt môi trường</strong><br>
                                Đầu tiên, bạn cần cài đặt JDK 17 trở lên và một IDE như IntelliJ IDEA hoặc Eclipse.
                            </p>
                            <p>
                                <strong>2. Tạo project đầu tiên</strong><br>
                                Sử dụng Spring Initializr để tạo project nhanh chóng với các dependencies cần thiết.
                            </p>
                            <p>
                                <strong>3. Cấu trúc project</strong><br>
                                Hiểu về cấu trúc thư mục và các file cấu hình quan trọng như application.properties.
                            </p>
                        </div>
                    </div>

                    <!-- Nút thao tác -->
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="manage_news_admin.jsp" class="btn btn-secondary">
                            <i class="bi bi-arrow-left"></i> Quay lại
                        </a>

                        <div>
                            <button class="btn btn-primary me-2">
                                <i class="bi bi-pencil-square"></i> Cập nhật
                            </button>
                            <button class="btn btn-danger">
                                <i class="bi bi-trash"></i> Xóa
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>