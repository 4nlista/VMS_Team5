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
    </head>
    <body class="p-4">

        <a href="AdminUserServlet" class="btn btn-secondary mb-3">← Back to User List</a>

        <div class="card mx-auto shadow-lg border-0" style="max-width: 750px;">
            <div class="card-header bg-dark text-white text-center py-3 rounded-top">
                <h4 class="mb-0"><i class="bi bi-person-circle me-2"></i>Editing ${user.account.username}'s account</h4>
            </div>
            <form action="AdminUserEditServlet" method="post" class="mt-4">
                <div class="card-body bg-light">
                    <div class="container py-3">
                        <div class="profile-card bg-white rounded-4 shadow-sm p-4">
                            <div class="row align-items-center">
                                <!-- Avatar -->
                                <div class="col-md-4 text-center border-end">
                                    <img src="${user.avatar}" 
                                         name="avatar"
                                         class="img-fluid rounded-circle border border-3 border-secondary-subtle shadow-sm mb-3"
                                         style="width: 150px; height: 150px; object-fit: cover;"
                                         alt="Avatar" />
                                    <p class="fw-semibold mt-2">${user.full_name}</p>
                                </div>

                                <!-- Info -->
                                <div class="col-md-8 ps-md-4 mt-4 mt-md-0">
                                    <h5 class="fw-bold mb-3 text-dark">Editing user detail</h5>

                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">ID</label>
                                            <input type="text" class="form-control form-control-sm" value="${user.id}" readonly>
                                            <!-- Hidden duplicate ensures the value is actually submitted -->
                                            <input type="hidden" name="id" value="${user.id}">
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Username</label>
                                            <input type="text" name="username" class="form-control form-control-sm" value="${user.account.username}">
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Full Name</label>
                                            <input type="text" name="full_name" class="form-control form-control-sm" value="${user.full_name}">
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Job Title</label>
                                            <input type="text" name="job_title" class="form-control form-control-sm" value="${user.job_title}">
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Gender</label>
                                            <select name="gender" class="form-select">
                                                <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
                                                <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Date of Birth</label>
                                            <input type="date" name="dob" class="form-control form-control-sm" value="${user.dob}">
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Address</label>
                                            <input type="text" name="address" class="form-control form-control-sm" value="${user.address}">
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Email</label>
                                            <input type="email" name="email" class="form-control form-control-sm" value="${user.email}" readonly>
                                        </div>
                                    </div>

                                    <hr class="my-4">

                                    <h5 class="fw-bold text-dark mb-2">Bio</h5>
                                    <textarea class="form-control form-control-sm" name="bio" rows="4">${user.bio}</textarea>
                                </div>
                            </div>
                        </div>

                        <div class="text-center mt-4">
                            <a href="AdminUserDetailServlet?id=${user.id}" class="btn btn-warning px-4 rounded-pill shadow-sm">
                                Discard Changes
                            </a>
                            <button type="submit" class="btn btn-success px-4 rounded-pill shadow-sm">Save Changes</button>
                        </div>
                    </div>
                </div>
        </div>
    </form>
</body>
</html>
