<%-- 
    Document   : profile_admin
    Created on : Sep 30, 2025, 1:26:44 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <div class="main-content container  pt-4">
                    <h1 class="text-center mb-4">Hồ sơ quản trị viên</h1>
                    <div class="profile-card shadow-sm p-4 bg-white rounded">
                        <div class="row">
                            <!-- Cột trái: Avatar + đổi mật khẩu -->
                            <div class="col-md-3 border-end">
                                <!-- Avatar -->
                                <div class="text-center mb-4">
                                    <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                                         class="img-fluid rounded-circle border p-2"
                                         style="width: 150px; height: 150px;"
                                         alt="Avatar Admin" />
                                </div>

                                <!-- Đổi mật khẩu -->
                                <h6 class="fw-bold mb-3 text-center">Đổi mật khẩu</h6>
                                <div class="mb-2">
                                    <label class="form-label">Mật khẩu hiện tại</label>
                                    <input type="password" class="form-control">
                                </div>
                                <div class="mb-2">
                                    <label class="form-label">Mật khẩu mới</label>
                                    <input type="password" class="form-control">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Xác nhận mật khẩu</label>
                                    <input type="password" class="form-control">
                                </div>
                                <button class="btn btn-primary w-100">Cập nhật</button>
                            </div>

                            <!-- Cột phải: Thông tin admin -->
                            <div class="col-md-9 ps-4">
                                <form>
                                    <h5 class="mb-3 fw-bold">Thông tin cá nhân</h5>
                                    <div class="row g-3">
                                        <!-- Row 1 -->
                                        <div class="col-md-6">
                                            <label class="form-label">ID</label>
                                            <input type="text" class="form-control" value="A001" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Họ và tên</label>
                                            <input type="text" class="form-control" value="Nguyễn Văn Admin" readonly>
                                        </div>

                                        <!-- Row 2 -->
                                        <div class="col-md-6">
                                            <label class="form-label">Vai trò</label>
                                            <input type="text" class="form-control fw-bold text-danger" value="Admin" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Email</label>
                                            <input type="email" class="form-control" value="admin@system.com" readonly>
                                        </div>

                                        <!-- Row 3 -->
                                        <div class="col-md-6">
                                            <label class="form-label">Số điện thoại</label>
                                            <input type="text" class="form-control" value="0123456789" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Địa chỉ</label>
                                            <input type="text" class="form-control" value="Hà Nội, Việt Nam" readonly>
                                        </div>
                                    </div>

                                    <hr class="my-4">

                                    <h5 class="mb-3 fw-bold">Giới thiệu</h5>
                                    <div class="mb-3">
                                        <textarea class="form-control" rows="4" readonly>
                                        Quản trị viên hệ thống, 
                                        chịu trách nhiệm quản lý tài khoản, 
                                        sự kiện và toàn bộ hệ thống.
                                        </textarea>
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
