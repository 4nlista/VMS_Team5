<%-- 
    Document   : profile_edit_admin
    Created on : 26 Oct 2025, 03:26:56
    Author     : Mirinae
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chỉnh Sửa Hồ Sơ Quản Trị Viên</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/user_admin.css" rel="stylesheet" />
    </head>

    <body>
        <div class="content-container">
            <jsp:include page="layout_admin/sidebar_admin.jsp" />
<!--<<<<<<< HEAD
            <div class="card mx-auto shadow-sm border-0" style= "margin-top: 15px">
                <div class="card-header bg-dark text-white text-center py-1 rounded-top">
                    <h6 class="mb-0"><i class="bi bi-person-circle me-1"></i>Chỉnh sửa tài khoản ${user.account.username}</h6>
                </div>
                <form action="AdminProfileEditServlet" method="post" class="mt-1">
                    <div class="card-body bg-light py-1">
                        <div class="container py-1">
                            <div class="profile-card bg-white rounded-3 shadow-sm p-2">
                                <div class="row align-items-start">
                                     Avatar 
                                    <div class="col-md-3 text-center border-end pe-2">
                                        <img src="${not empty user.avatar ? user.avatar : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}" 
                                             name="avatar"
                                             class="img-fluid rounded-circle border border-2 border-secondary-subtle shadow-sm mb-1"
                                             style="width: 100px; height: 100px; object-fit: cover;"
                                             alt="Avatar" />
                                        <p class="fw-semibold mt-0 small">${user.full_name}</p>
                                    </div>

                                     Info 
                                    <div class="col-md-9 ps-md-2 mt-1 mt-md-0">
                                        <h6 class="fw-bold mb-1 text-dark">Chỉnh sửa chi tiết</h6>
=======-->

            <div class="edit-wrapper">
                <form action="AdminProfileEditServlet" method="post" enctype="multipart/form-data" class="w-100 d-flex gap-3">

                    <!-- Avatar Section -->
                    <div class="profile-side">
                        <img id="avatarPreview"
                             src="${not empty user.avatar ? user.avatar : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}"
                             alt="Avatar" />
                        <h5>${user.full_name}</h5>
                        <small>@${user.account.username}</small>

<!--<<<<<<< HEAD
                                             Row 1 
                                            <div class="col-md-6 position-relative">
                                                <label class="fw-bold form-label small text-muted mb-0">Họ tên</label>
                                                <div class="error-container" style="height: 1rem; position: relative;">
                                                    <span class="field-error small text-danger"
                                                          style="${empty errors['full_name'] ? 'opacity: 0;' : ''}">
                                                        ${errors['full_name']}
                                                    </span>
                                                </div>
                                                <input type="text" name="full_name" class="form-control form-control-sm"
                                                       value="${not empty param.full_name ? param.full_name : user.full_name}">
                                            </div>

                                            <div class="col-md-6 position-relative">
                                                <label class="fw-bold form-label small text-muted mb-0">Nghề nghiệp</label>
                                                <div class="error-container" style="height: 1rem; position: relative;">
                                                    <span class="field-error small text-danger"
                                                          style="${empty errors['job_title'] ? 'opacity: 0;' : ''}">
                                                        ${errors['job_title']}
                                                    </span>
                                                </div>
                                                <input type="text" name="job_title" class="form-control form-control-sm"
                                                       value="${not empty param.job_title ? param.job_title : user.job_title}">
                                            </div>

                                             Row 2 
                                            <div class="col-md-6">
                                                <label class="fw-bold form-label small text-muted mb-3">Giới tính</label>
                                                <select name="gender" class="form-select form-select-sm">
                                                    <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Nam</option>
                                                    <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Nữ</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="fw-bold form-label small text-muted mb-3">Ngày sinh</label>
                                                <input type="date" name="dob" class="form-control form-control-sm"
                                                       value="${not empty param.dob ? param.dob : user.dob}">
                                                <c:if test="${not empty errors.dob}">
                                                    <div class="field-error">${errors.dob}</div>
                                                </c:if>
                                            </div>

                                                 Row 3 
                                            <div class="col-md-6 position-relative">
                                                <label class="fw-bold form-label small text-muted mb-0">Địa chỉ</label>
                                                <div class="error-container" style="height: 1rem; position: relative;">
                                                    <span class="field-error small text-danger"
                                                          style="${empty errors['address'] ? 'opacity: 0;' : ''}">
                                                        ${errors['address']}
                                                    </span>
                                                </div>
                                                <input type="text" name="address" class="form-control form-control-sm"
                                                       value="${not empty param.address ? param.address : user.address}">
                                            </div>

                                            <div class="col-md-6 position-relative">
                                                <label class="fw-bold form-label small text-muted mb-0">Số điện thoại</label>
                                                <div class="error-container" style="height: 1rem; position: relative;">
                                                    <span class="field-error small text-danger"
                                                          style="${empty errors['phone'] ? 'opacity: 0;' : ''}">
                                                        ${errors['phone']}
                                                    </span>
                                                </div>
                                                <input type="text" name="phone" class="form-control form-control-sm"
                                                       value="${not empty param.phone ? param.phone : user.phone}">
                                            </div>

                                             Row 4 
                                            <div class="col-md-12 position-relative">
                                                <label class="fw-bold form-label small text-muted mb-0">Email</label>
                                                <div class="error-container" style="height: 1rem; position: relative;">
                                                    <span class="field-error small text-danger"
                                                          style="${empty errors['email'] ? 'opacity: 0;' : ''}">
                                                        ${errors['email']}
                                                    </span>
                                                </div>
                                                <input type="email" name="email" class="form-control form-control-sm"
                                                       value="${not empty param.email ? param.email : user.email}">
                                            </div>
                                            
                                             Row 5 
                                        <hr class="my-1">
                                        <h6 class="fw-bold text-dark mb-3 mt-3">Giới thiệu</h6>
                                        <textarea class="form-control form-control-sm" style="resize:none;"name="bio" rows="6">${user.bio}</textarea>
                                    </div>
                                </div>
                            </div>

                            <div class="text-center mt-1 mt-2">
                                <a href="AdminProfileServlet?id=1" class="btn btn-warning btn-sm px-2 rounded-pill shadow-sm">
                                    Quay lại
                                </a>
                                <button type="submit" class="btn btn-success btn-sm px-2 rounded-pill shadow-sm">Cập nhật</button>
                            </div>
=======-->
                        <div class="upload-section">
                            <label class="form-label">Đổi Ảnh</label>
                            <input type="file" name="avatar" id="avatarInput" class="form-control form-control-sm mt-1" />
                            <c:if test="${not empty errors['avatar']}">
                                <div class="text-danger small mt-1">${errors['avatar']}</div>
                            </c:if>
                        </div>
                    </div>

                    <!-- Main Edit Form -->
                    <div class="edit-form-card flex-grow-1">
                        <h5><i class="bi bi-pencil-square me-2"></i>Đang Thay Đổi Thông Tin Quản Trị Viên.</h5>

                        <input type="hidden" name="id" value="${user.id}" />
                        <input type="hidden" name="username" value="${user.account.username}" />

                        <div class="row g-3">
                            <div class="col-md-6 position-relative">
                                <label class="form-label">Họ Tên <span class="form-error">${errors['full_name']}</span></label>
                                <input type="text" name="full_name" class="form-control form-control-sm"
                                       value="${not empty param.full_name ? param.full_name : user.full_name}">
                            </div>

                            <div class="col-md-6 position-relative">
                                <label class="form-label">Nghề Nghiệp <span class="form-error">${errors['job_title']}</span></label>
                                <input type="text" name="job_title" class="form-control form-control-sm"
                                       value="${not empty param.job_title ? param.job_title : user.job_title}">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Giới Tính</label>
                                <select name="gender" class="form-select form-select-sm">
                                    <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Nam</option>
                                    <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Nữ</option>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Ngày Sinh <span class="form-error">${errors['dob']}</span></label>
                                <input type="date" name="dob" class="form-control form-control-sm"
                                       value="${not empty param.dob ? param.dob : user.dob}">
                            </div>

                            <div class="col-md-6 position-relative">
                                <label class="form-label">Địa Chỉ <span class="form-error">${errors['address']}</span></label>
                                <input type="text" name="address" class="form-control form-control-sm"
                                       value="${not empty param.address ? param.address : user.address}">
                            </div>

                            <div class="col-md-6 position-relative">
                                <label class="form-label">Điện Thoại <span class="form-error">${errors['phone']}</span></label>
                                <input type="text" name="phone" class="form-control form-control-sm"
                                       value="${not empty param.phone ? param.phone : user.phone}">
                            </div>

                            <div class="col-md-12 position-relative">
                                <label class="form-label">Email <span class="form-error">${errors['email']}</span></label>
                                <input type="email" name="email" class="form-control form-control-sm"
                                       value="${not empty param.email ? param.email : user.email}">
                            </div>
                        </div>

                        <div class="form-section mt-3">
                            <label class="form-label">Giới Thiệu Bản Thân</label>
                            <textarea class="form-control form-control-sm" name="bio" rows="6" style="resize:none;">${not empty param.bio ? param.bio : user.bio}</textarea>
                        </div>

                        <div class="action-buttons mt-3">
                            <button type="button" class="btn btn-danger btn-sm btn-rounded px-3" onclick="history.back()">
                                ✕ Hủy
                            </button>
                            <button type="submit" class="btn btn-success btn-sm btn-rounded px-3 ms-2">
                                ✓ Lưu Thay Đổi
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<%= request.getContextPath() %>/admin/js/live_avatar_preview.js"></script>
    </body>
</html>


