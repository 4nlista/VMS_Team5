<%-- 
    Document   : manage_news_admin
    Created on : Sep 23, 2025, 8:56:21 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang kiểm duyệt nội dung</title>
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
                <div class="container-fluid">
                    <h3 class="fw-bold mb-4">Kiểm duyệt nội dung</h3>

                    <!-- Bộ lọc -->
                    <form method="get" action="ManageNewsServlet" class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <div class="d-flex gap-2 align-items-center flex-wrap">
                            <!-- Sắp xếp -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Sắp xếp:</label>
                                <select name="sort" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="newest">Ngày mới nhất</option>
                                    <option value="oldest">Ngày cũ nhất</option>
                                </select>
                            </div>

                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái:</label>
                                <select name="status" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="">Tất cả</option>
                                    <option value="pending">Chưa xử lý</option>
                                    <option value="published">Hiển thị</option>
                                    <option value="rejected">Từ chối</option>
                                    <option value="hidden">Ẩn</option>
                                </select>
                            </div>

                            <!-- Nút Lọc -->
                            <a href="ManageNewsServlet" class="btn btn-primary btn-sm" style="min-width:110px; align-self:end;">
                                <i class="bi bi-search"></i> Lọc
                            </a>
                           
                            <!-- Nút Reset -->
                            <a href="ManageNewsServlet" class="btn btn-secondary btn-sm" style="min-width:110px; align-self:end;">
                                <i class="bi bi-arrow-counterclockwise"></i> Reset
                            </a>
                        </div>
                    </form>

                    <!-- Bảng dữ liệu -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                            <thead class="table-secondary">
                                <tr>
                                    <th style="width:4%;">STT</th>
                                    <th style="width:30%;">Tên bài đăng</th>
                                    <th style="width:15%;">Người đăng</th>
                                    <th style="width:13%;">Ngày tạo</th>
                                    <th style="width:15%;">Trạng thái</th>
                                    <th style="width:23%;">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Bài pending -->
                                <tr>
                                    <td>1</td>
                                    <td>Hướng dẫn sử dụng Java Spring Boot cơ bản</td>
                                    <td>Nguyễn Văn A</td>
                                    <td>04/11/2025</td>
                                    <td>
                                        <span class="badge bg-warning text-dark">Chưa xử lý</span>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/admin/detail_new_admin.jsp" class="btn btn-primary btn-sm" class="btn btn-primary btn-sm">Xem</a>
                                        <a href="#" class="btn btn-success btn-sm">Duyệt</a>
                                        <a href="#" class="btn btn-danger btn-sm">Từ chối</a>
                                    </td>
                                </tr>

                                <!-- Bài published -->
                                <tr>
                                    <td>2</td>
                                    <td>10 mẹo tối ưu hiệu suất MySQL Database</td>
                                    <td>Trần Thị B</td>
                                    <td>03/11/2025</td>
                                    <td>
                                        <span class="badge bg-success">Hiển thị</span>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/admin/detail_new_admin.jsp" class="btn btn-primary btn-sm">Xem</a>
                                    </td>
                                </tr>

                                <!-- Bài rejected -->
                                <tr>
                                    <td>3</td>
                                    <td>Cách xây dựng RESTful API với Node.js</td>
                                    <td>Lê Văn C</td>
                                    <td>02/11/2025</td>
                                    <td>
                                        <span class="badge bg-danger">Từ chối</span>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/admin/detail_new_admin.jsp" class="btn btn-primary btn-sm">Xem</a>
                                    </td>
                                </tr>

                                <!-- Bài pending -->
                                <tr>
                                    <td>4</td>
                                    <td>Giới thiệu về React Hooks và cách sử dụng</td>
                                    <td>Phạm Thị D</td>
                                    <td>01/11/2025</td>
                                    <td>
                                        <span class="badge bg-warning text-dark">Chưa xử lý</span>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/admin/detail_new_admin.jsp" class="btn btn-primary btn-sm">Xem</a>
                                        <a href="#" class="btn btn-success btn-sm">Duyệt</a>
                                        <a href="#" class="btn btn-danger btn-sm">Từ chối</a>
                                    </td>
                                </tr>

                                <!-- Bài hidden -->
                                <tr>
                                    <td>5</td>
                                    <td>Bảo mật ứng dụng web với JWT Authentication</td>
                                    <td>Hoàng Văn E</td>
                                    <td>31/10/2025</td>
                                    <td>
                                        <span class="badge bg-secondary">Ẩn</span>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/admin/detail_new_admin.jsp" class="btn btn-primary btn-sm">Xem</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- Phân trang -->
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <span>Hiển thị 1 - 5 trong tổng 5 bài đăng</span>
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
