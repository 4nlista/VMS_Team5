<%-- 
    Document   : profile_volunteer
    Created on : Sep 22, 2025, 8:12:47 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ khách hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/volunteer/css/profile_volunteer.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <%
            Object sessionId = session.getId();
            String username = (String) session.getAttribute("username");
            if (username == null) {
                username = "Khách";
            }
        %>


        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5">
            <h1 class="text-center">Hồ sơ cá nhân _[ <%= username %> ]_ </h1>
            <!-- Nơi hiển thị dữ liệu khác -->
            <div class="profile-data">
                <!-- Ví dụ bảng thông tin, cards, form,... -->
                <div class="container py-5">
                    <div class="profile-card">
                        <div class="row">

                            <!-- Cột A (3 phần) -->
                            <div class="col-md-3">
                                <!-- Avatar -->
                                <div class="text-center mb-4">
                                    <img src="https://img.allfootballapp.com/www/M00/2F/26/720x-/-/-/CgAGVWKec0SATHQ9AAC5pv_vzd0496.jpg.webp" class="profile-avatar border" alt="Avatar">
                                </div>

                                <!-- Upload -->
                                <div class="mb-3">
                                    <label class="form-label">Upload Photo</label>
                                    <input type="file" class="form-control">
                                </div>

                                <!-- Old Password -->

                                <div class="mb-2">
                                    <label class="form-label">Tài khoản</label>
                                    <input type="text" class="form-control">
                                </div>

                                <div class="mb-2">
                                    <label class="form-label">Mật khẩu hiện tại</label>
                                    <input type="password" class="form-control">
                                </div>

                                <!-- New Password -->
                                <div class="mb-2">
                                    <label class="form-label">Mật khẩu mới</label>
                                    <input type="password" class="form-control">
                                </div>

                                <div class="mb-2">
                                    <label class="form-label">Xác nhận mật khẩu</label>
                                    <input type="password" class="form-control">
                                </div>

                                <!-- Change Password -->
                                <button class="btn btn-primary w-100">Đổi mật khẩu </button>
                            </div>

                            <!-- Cột B (7 phần) -->
                            <div class="col-md-9">
                                <form action="${pageContext.request.contextPath}/VolunteerProfileServlet" method="post">
                                    <input type="hidden" name="accountId" value="${user.accountId}">

                                    <h5 class="mb-3">Thông tin cá nhân</h5>
                                    <div class="row g-3">
                                        <!-- ID -->
                                        <div class="col-md-6">
                                            <label class="form-label">ID</label>
                                            <input type="text" class="form-control" value="${user.accountId}" disabled>
                                        </div>

                                        <!-- Họ và tên -->
                                        <div class="col-md-6">
                                            <label class="form-label">Họ và tên</label>
                                            <input type="text" class="form-control" name="full_name" value="${user.fullName}">
                                        </div>

                                        <!-- Nickname -->
                                        <div class="col-md-6">
                                            <label class="form-label">Nickname</label>
                                            <input type="text" class="form-control" name="job_title" value="${user.jobTitle}">
                                        </div>

                                        <!-- Giới tính -->
                                        <div class="col-md-6">
                                            <label class="form-label">Giới tính</label>
                                            <select class="form-select" name="gender">
                                                <option value="">Chọn giới tính</option>
                                                <option value="Nam" ${user.gender eq 'Nam' ? 'selected' : ''}>Nam</option>
                                                <option value="Nữ" ${user.gender eq 'Nữ' ? 'selected' : ''}>Nữ</option>
                                            </select>
                                        </div>

                                        <!-- Ngày sinh -->
                                        <div class="col-md-6">
                                            <label class="form-label">Ngày sinh</label>
                                            <input type="date" class="form-control" name="dob"
                                                   value="<fmt:formatDate value='${user.dob}' pattern='yyyy-MM-dd'/>">
                                        </div>

                                        <!-- Địa chỉ -->
                                        <div class="col-md-6">
                                            <label class="form-label">Địa chỉ</label>
                                            <input type="text" class="form-control" name="address" value="${user.address}">
                                        </div>
                                    </div>

                                    <hr class="my-4">

                                    <h5 class="mb-3">Thông tin liên hệ</h5>
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label class="form-label">Email</label>
                                            <input type="email" class="form-control" name="email" value="${user.email}">
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Số điện thoại</label>
                                            <input type="text" class="form-control" name="phone" value="${user.phone}">
                                        </div>
                                    </div>

                                    <hr class="my-4">

                                    <h5 class="mb-3">Giới thiệu bản thân</h5>
                                    <div class="mb-3">
                                        <textarea class="form-control" rows="4" name="bio">${user.bio}</textarea>
                                    </div>

                                    <div class="text-end">
                                        <button type="submit" class="btn btn-success">Lưu cập nhật</button>
                                    </div>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </div>



        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

