<%-- 
    Document   : edit_org_profile
    Created on : 9 Nov 2025, 12:48:01
    Author     : Mirinae
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Chỉnh sửa hồ sơ tổ chức</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container d-flex">
            <jsp:include page="layout_org/sidebar_org.jsp" />
            <div class="main-content p-4 flex-grow-1">
                <div class="container" style="max-width: 900px;">
                    <h3>Chỉnh sửa hồ sơ tổ chức</h3>
                    <c:if test="${not empty errors['general']}">
                        <div class="alert alert-danger">${errors['general']}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/OrganizationProfileEdit" method="post"
          enctype="multipart/form-data" class="row g-3">
        <input type="hidden" name="id" value="${user.id}" />
        <input type="hidden" name="existingAvatar" value="${user.avatar}" />

        <div class="col-md-4 text-center">
            <c:choose>
                <c:when test="${not empty user.avatar}">
                    <img id="avatarPreview" src="${pageContext.request.contextPath}/Avatar?file=${user.avatar}"
                         class="rounded-circle" style="width:180px;height:180px;object-fit:cover;" />
                </c:when>
                <c:otherwise>
                    <img id="avatarPreview" src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                         class="rounded-circle" style="width:180px;height:180px;object-fit:cover;" />
                </c:otherwise>
            </c:choose>
            <div class="mt-2">
                <label class="form-label">Đổi ảnh</label>
                <input type="file" name="avatar" class="form-control form-control-sm" />
                <c:if test="${not empty errors['avatar']}">
                    <div class="text-danger small">${errors['avatar']}</div>
                </c:if>
            </div>
        </div>

        <div class="col-md-8">
            <c:set var="fullNameValue" value="${not empty full_name ? full_name : (not empty param.full_name ? param.full_name : user.full_name)}"/>
            <c:set var="emailValue" value="${not empty email ? email : (not empty param.email ? param.email : user.email)}"/>
            <c:set var="phoneValue" value="${not empty phone ? phone : (not empty param.phone ? param.phone : user.phone)}"/>
            <c:set var="addressValue" value="${not empty address ? address : (not empty param.address ? param.address : user.address)}"/>
            <c:set var="jobTitleValue" value="${not empty job_title ? job_title : (not empty param.job_title ? param.job_title : user.job_title)}"/>
            <c:set var="dobValue" value="${not empty dob ? dob : (not empty param.dob ? param.dob : user.dob)}"/>
            <c:set var="bioValue" value="${not empty bio ? bio : (not empty param.bio ? param.bio : user.bio)}"/>

            <div class="mb-2">
                <label class="form-label">Tên tổ chức</label>
                <input type="text" name="full_name" class="form-control"
                       value="${fullNameValue}">
                <c:if test="${not empty errors['full_name']}">
                    <div class="text-danger small">${errors['full_name']}</div>
                </c:if>
            </div>

            <div class="mb-2">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" value="${emailValue}">
                <c:if test="${not empty errors['email']}">
                    <div class="text-danger small">${errors['email']}</div>
                </c:if>
            </div>

            <div class="mb-2">
                <label class="form-label">Số điện thoại</label>
                <input type="text" name="phone" class="form-control" value="${phoneValue}">
                <c:if test="${not empty errors['phone']}">
                    <div class="text-danger small">${errors['phone']}</div>
                </c:if>
            </div>

            <div class="mb-2">
                <label class="form-label">Địa chỉ</label>
                <input type="text" name="address" class="form-control" value="${addressValue}">
                <c:if test="${not empty errors['address']}">
                    <div class="text-danger small">${errors['address']}</div>
                </c:if>
            </div>

            <div class="mb-2">
                <label class="form-label">Nghề nghiệp</label>
                <input type="text" name="job_title" class="form-control" value="${jobTitleValue}">
                <c:if test="${not empty errors['job_title']}">
                    <div class="text-danger small">${errors['job_title']}</div>
                </c:if>
            </div>

            <div class="mb-2">
                <label class="form-label">Ngày sinh</label>
                <input type="date" name="dob" class="form-control" value="${dobValue}">
                <c:if test="${not empty errors['dob']}">
                    <div class="text-danger small">${errors['dob']}</div>
                </c:if>
            </div>

            <div class="mb-2">
                <label class="form-label">Giới thiệu</label>
                <textarea class="form-control" name="bio" rows="5">${bioValue}</textarea>
            </div>

            <div class="d-flex justify-content-between mt-3">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/OrganizationProfileDetail?id=${user.id}">Hủy</a>
                <button type="submit" class="btn btn-primary">Lưu</button>
            </div>
        </div>
    </form>
                </div>
            </div>
        </div>
        <script>
            document.querySelector("input[name='avatar']").addEventListener("change", function (e) {
                const f = e.target.files && e.target.files[0];
                if (!f)
                    return;
                const p = document.getElementById('avatarPreview');
                p.src = URL.createObjectURL(f);
            });
        </script>
    </body>
</html>