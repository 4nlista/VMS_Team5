<%-- 
    Document   : add_account_admin
    Created on : Oct 15, 2025, 10:30:00 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Tạo tài khoản mới</title>
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
            <div class="main-content p-4">
                <h1 class="mb-4">
                    <i class="bi bi-person-plus-fill me-2"></i>
                    Tạo tài khoản mới
                </h1>
                
                <c:if test="${param.msg == 'error_username_exists'}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.msg == 'error_validation'}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        Vui lòng điền đầy đủ thông tin bắt buộc.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <c:if test="${param.msg == 'success'}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        Tạo tài khoản thành công!
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <div class="card">
                    <div class="card-body">
                        <form action="<%= request.getContextPath() %>/AddAccountServlet" method="post" enctype="multipart/form-data" id="createAccountForm">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="username" class="form-label">
                                        <i class="bi bi-person me-1"></i>
                                        Tên đăng nhập <span class="text-danger">*</span>
                                    </label>
                                    <input type="text" class="form-control" id="username" name="username" 
                                           placeholder="Nhập tên đăng nhập" required maxlength="50">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="role" class="form-label">
                                        <i class="bi bi-shield-check me-1"></i>
                                        Vai trò <span class="text-danger">*</span>
                                    </label>
                                    <!-- Fix cứng vai trò là Organization -->
                                    <select class="form-select" id="role" name="role" required disabled style="background-color: #e9ecef;">
                                        <option value="organization" selected>Tổ chức (Organization)</option>
                                    </select>
                                    <!-- Hidden field để gửi giá trị role khi form submit -->
                                    <input type="hidden" name="role" value="organization" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="password" class="form-label">
                                        <i class="bi bi-lock me-1"></i>
                                        Mật khẩu <span class="text-danger">*</span>
                                    </label>
                                    <input type="password" class="form-control" id="password" name="password" 
                                           placeholder="Nhập mật khẩu" required minlength="3">
                                    <small class="text-muted">Mật khẩu tối thiểu 3 ký tự</small>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="confirm_password" class="form-label">
                                        <i class="bi bi-lock-fill me-1"></i>
                                        Xác nhận mật khẩu <span class="text-danger">*</span>
                                    </label>
                                    <input type="password" class="form-control" id="confirm_password" 
                                           placeholder="Nhập lại mật khẩu" required minlength="3">
                                    <small class="text-danger" id="password-match-error"></small>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="full_name" class="form-label">
                                        <i class="bi bi-person-vcard me-1"></i>
                                        Họ và tên <span class="text-danger">*</span>
                                    </label>
                                    <input type="text" class="form-control" id="full_name" name="full_name" 
                                           placeholder="Nhập họ và tên" required maxlength="100">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="email" class="form-label">
                                        <i class="bi bi-envelope me-1"></i>
                                        Email <span class="text-danger">*</span>
                                    </label>
                                    <input type="email" class="form-control" id="email" name="email" 
                                           placeholder="example@email.com" required maxlength="100">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4 mb-3">
                                    <label for="phone" class="form-label">
                                        <i class="bi bi-telephone me-1"></i>
                                        Số điện thoại
                                    </label>
                                    <input type="text" class="form-control" id="phone" name="phone" 
                                           placeholder="0123456789" maxlength="20">
                                </div>

                                <div class="col-md-4 mb-3">
                                    <label for="gender" class="form-label">
                                        <i class="bi bi-gender-ambiguous me-1"></i>
                                        Giới tính
                                    </label>
                                    <select class="form-select" id="gender" name="gender">
                                        <option value="">-- Chọn giới tính --</option>
                                        <option value="male">Nam</option>
                                        <option value="female">Nữ</option>
                                    </select>
                                </div>

                                <div class="col-md-4 mb-3">
                                    <label for="status" class="form-label">
                                        <i class="bi bi-toggle-on me-1"></i>
                                        Trạng thái <span class="text-danger">*</span>
                                    </label>
                                    <select class="form-select" id="status" name="status" required>
                                        <option value="active" selected>Hoạt động (Active)</option>
                                        <option value="inactive">Khóa (Inactive)</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="avatar" class="form-label">
                                        <i class="bi bi-image me-1"></i>
                                        Ảnh đại diện (Avatar)
                                    </label>
                                    <input type="file" class="form-control" id="avatar" name="avatar" 
                                           accept="image/*" onchange="previewAvatar(this)">
                                    <div class="mt-2" id="avatar-preview" style="display: none;">
                                        <img id="preview-img" src="" alt="Preview" style="max-width: 150px; max-height: 150px; border-radius: 8px;">
                                    </div>
                                    <small class="text-muted">Chỉ chấp nhận file ảnh (JPG, PNG, etc.)</small>
                                </div>

                                <div class="col-md-6">
                                    <label for="address" class="form-label">
                                        <i class="bi bi-geo-alt me-1"></i>
                                        Địa chỉ
                                    </label>
                                    <input type="text" class="form-control" id="address" name="address" 
                                           placeholder="Nhập địa chỉ" maxlength="255">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="job_title" class="form-label">
                                        <i class="bi bi-briefcase me-1"></i>
                                        Chức vụ/Nghề nghiệp
                                    </label>
                                    <input type="text" class="form-control" id="job_title" name="job_title" 
                                           placeholder="Nhập chức vụ" maxlength="100">
                                </div>

                                <div class="col-md-6">
                                    <label for="dob" class="form-label">
                                        <i class="bi bi-calendar me-1"></i>
                                        Ngày sinh
                                    </label>
                                    <input type="date" class="form-control" id="dob" name="dob">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="bio" class="form-label">
                                    <i class="bi bi-file-text me-1"></i>
                                    Giới thiệu
                                </label>
                                <textarea class="form-control" id="bio" name="bio" rows="3" 
                                          placeholder="Nhập mô tả ngắn về tổ chức/tình nguyện viên" maxlength="1000"></textarea>
                                <small class="text-muted">Tối đa 1000 ký tự</small>
                            </div>

                            <div class="d-flex justify-content-end gap-2 mt-4">
                                <a href="<%= request.getContextPath() %>/AdminAccountServlet" class="btn btn-secondary">
                                    <i class="bi bi-x-circle me-1"></i>
                                    Hủy
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-circle me-1"></i>
                                    Tạo tài khoản
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Validate password match
            const form = document.getElementById('createAccountForm');
            const password = document.getElementById('password');
            const confirmPassword = document.getElementById('confirm_password');
            const errorMsg = document.getElementById('password-match-error');

            function validatePassword() {
                if (password.value !== confirmPassword.value) {
                    errorMsg.textContent = 'Mật khẩu không khớp';
                    confirmPassword.classList.add('is-invalid');
                    return false;
                } else {
                    errorMsg.textContent = '';
                    confirmPassword.classList.remove('is-invalid');
                    return true;
                }
            }

            confirmPassword.addEventListener('keyup', validatePassword);
            password.addEventListener('keyup', validatePassword);

            form.addEventListener('submit', function(e) {
                if (!validatePassword()) {
                    e.preventDefault();
                    return false;
                }
            });

            // Preview avatar
            function previewAvatar(input) {
                const preview = document.getElementById('avatar-preview');
                const previewImg = document.getElementById('preview-img');
                if (input.files && input.files[0]) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        previewImg.src = e.target.result;
                        preview.style.display = 'block';
                    }
                    reader.readAsDataURL(input.files[0]);
                } else {
                    preview.style.display = 'none';
                }
            }

            // Auto dismiss success alert after 3 seconds
            const successAlert = document.querySelector('.alert-success');
            if (successAlert) {
                setTimeout(() => {
                    const alert = new bootstrap.Alert(successAlert);
                    alert.close();
                }, 3000);
            }
        </script>
    </body>
</html>

