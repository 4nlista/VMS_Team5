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
        <style>
            .field-error {
                font-size: 0.9rem;
                color: #b02a37;
                margin-top: .25rem;
            }
            body {
                margin: 0;
            }
        </style>
    </head>
    <body class="p-1">

        <a href="AdminUserServlet" class="btn btn-secondary mb-1 btn-sm">← Back to User List</a>

        <div class="card mx-auto shadow-sm border-0" style="max-width: 900px;">
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
                                    <img src="${user.avatar}" 
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

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted mb-0">Full Name</label>
                                            <input type="text" name="full_name" class="form-control form-control-sm"
                                                   value="${not empty param.full_name ? param.full_name : user.full_name}">
                                            <c:if test="${not empty errors.full_name}">
                                                <div class="field-error">${errors.full_name}</div>
                                            </c:if>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted mb-0">Job Title</label>
                                            <input type="text" name="job_title" class="form-control form-control-sm"
                                                   value="${not empty param.job_title ? param.job_title : user.job_title}">
                                            <c:if test="${not empty errors['job_title']}">
                                                <div class="field-error">${errors['job_title']}</div>
                                            </c:if>
                                        </div>


                                        <div class="col-md-6">
                                            <label class="form-label small text-muted mb-0">Gender</label>
                                            <select name="gender" class="form-select form-select-sm">
                                                <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
                                                <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted mb-0">Date of Birth</label>
                                            <input type="date" name="dob" class="form-control form-control-sm"
                                                   value="${not empty param.dob ? param.dob : user.dob}">
                                            <c:if test="${not empty errors.dob}">
                                                <div class="field-error">${errors.dob}</div>
                                            </c:if>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted mb-0">Address</label>
                                            <input type="text" name="address" class="form-control form-control-sm"
                                                   value="${not empty param.address ? param.address : user.address}">
                                            <c:if test="${not empty errors['address']}">
                                                <div class="field-error">${errors['address']}</div>
                                            </c:if>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted mb-0">Phone</label>
                                            <input type="text" name="phone" class="form-control form-control-sm"
                                                   value="${not empty param.phone ? param.phone : user.phone}">
                                            <c:if test="${not empty errors.phone}">
                                                <div class="field-error">${errors.phone}</div>
                                            </c:if>
                                        </div>

                                        <div class="col-md-12 mt-1">
                                            <label class="form-label small text-muted mb-0">Email</label>
                                            <input type="email" name="email" class="form-control form-control-sm"
                                                   value="${not empty param.email ? param.email : user.email}">
                                            <c:if test="${not empty errors.email}">
                                                <div class="field-error">${errors.email}</div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <hr class="my-1">
                                    <h6 class="fw-bold text-dark mb-0">Bio</h6>
                                    <textarea class="form-control form-control-sm" name="bio" rows="2">${user.bio}</textarea>
                                </div>
                            </div>
                        </div>

                        <div class="text-center mt-1">
                            <a href="AdminUserDetailServlet?id=${user.id}" class="btn btn-warning btn-sm px-2 rounded-pill shadow-sm">
                                Discard Changes
                            </a>
                            <button type="submit" class="btn btn-success btn-sm px-2 rounded-pill shadow-sm">Save Changes</button>
                        </div>
                    </div>
                </div>
        </div>
    </form>
</body>
</html>