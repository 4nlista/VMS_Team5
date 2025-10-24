<%-- 
    Document   : edit_user
    Created on : 10 Oct 2025, 06:07:50
    Author     : Mirinesa
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/user_admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />
            <div class="card mx-auto shadow-sm border-0" style= "margin-top: 15px">
                <div class="card-header bg-dark text-white text-center py-1 rounded-top">
                    <h6 class="mb-0"><i class="bi bi-person-circle me-1"></i>Editing ${user.account.username}'s account</h6>
                </div>
                <form action="AdminUserEditServlet" method="post" class="mt-1">
                    <div class="card-body bg-light py-1">
                        <div class="container py-1">
                            <div class="profile-card bg-white rounded-3 shadow-sm p-2">
                                <div class="row align-items-start">
                                    <!-- Avatar -->
                                    <div class="col-md-3 text-center border-end pe-2">
                                        <img src="${not empty user.avatar ? user.avatar : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}" 
                                             name="avatar"
                                             class="img-fluid rounded-circle border border-2 border-secondary-subtle shadow-sm mb-1"
                                             style="width: 100px; height: 100px; object-fit: cover;"
                                             alt="Avatar" />
                                        <p class="fw-semibold mt-0 small">${user.full_name}</p>
                                    </div>

                                    <!-- Info -->
                                    <div class="col-md-9 ps-md-2 mt-1 mt-md-0">
                                        <h6 class="fw-bold mb-1 text-dark">Editing user detail</h6>

                                        <div class="row g-1">
                                            <!-- Hidden ensures the value is actually submitted -->
                                            <label class="form-label small text-muted mb-0"></label>
                                            <input type="hidden" name="id" class="form-control form-control-sm" value="${user.id}" readonly>
                                            <label class="form-label small text-muted mb-0"></label>
                                            <input type="hidden" name="username" class="form-control form-control-sm" value="${user.account.username}">

                                            <!-- Row 1 -->
                                            <div class="col-md-6 position-relative">
                                                <label class="fw-bold form-label small text-muted mb-0">Full Name</label>
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
                                                <label class="fw-bold form-label small text-muted mb-0">Occupation</label>
                                                <div class="error-container" style="height: 1rem; position: relative;">
                                                    <span class="field-error small text-danger"
                                                          style="${empty errors['job_title'] ? 'opacity: 0;' : ''}">
                                                        ${errors['job_title']}
                                                    </span>
                                                </div>
                                                <input type="text" name="job_title" class="form-control form-control-sm"
                                                       value="${not empty param.job_title ? param.job_title : user.job_title}">
                                            </div>

                                            <!-- Row 2 -->
                                            <div class="col-md-6">
                                                <label class="fw-bold form-label small text-muted mb-3">Gender</label>
                                                <select name="gender" class="form-select form-select-sm">
                                                    <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
                                                    <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="fw-bold form-label small text-muted mb-3">Date of Birth</label>
                                                <input type="date" name="dob" class="form-control form-control-sm"
                                                       value="${not empty param.dob ? param.dob : user.dob}">
                                                <c:if test="${not empty errors.dob}">
                                                    <div class="field-error">${errors.dob}</div>
                                                </c:if>
                                            </div>

                                                <!-- Row 3 -->
                                            <div class="col-md-6 position-relative">
                                                <label class="fw-bold form-label small text-muted mb-0">Address</label>
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
                                                <label class="fw-bold form-label small text-muted mb-0">Phone</label>
                                                <div class="error-container" style="height: 1rem; position: relative;">
                                                    <span class="field-error small text-danger"
                                                          style="${empty errors['phone'] ? 'opacity: 0;' : ''}">
                                                        ${errors['phone']}
                                                    </span>
                                                </div>
                                                <input type="text" name="phone" class="form-control form-control-sm"
                                                       value="${not empty param.phone ? param.phone : user.phone}">
                                            </div>

                                            <!-- Row 4 -->
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
                                            
                                            <!-- Row 5 -->
                                        <hr class="my-1">
                                        <h6 class="fw-bold text-dark mb-3 mt-3">Bio</h6>
                                        <textarea class="form-control form-control-sm" style="resize:none;"name="bio" rows="9">${user.bio}</textarea>
                                    </div>
                                </div>
                            </div>

                            <div class="text-center mt-1 mt-2">
                                <a href="AdminUserServlet" class="btn btn-secondary px-2 btn-sm rounded-pill shadow-sm">← Back to User List</a>
                                <a href="AdminUserDetailServlet?id=${user.id}" class="btn btn-warning btn-sm px-2 rounded-pill shadow-sm">
                                    ✕ Discard Changes
                                </a>
                                <button type="submit" class="btn btn-success btn-sm px-2 rounded-pill shadow-sm">✓ Save Changes</button>
                            </div>
                        </div>
                    </div>
            </div>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>