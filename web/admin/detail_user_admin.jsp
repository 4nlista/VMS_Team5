<%-- 
    Document   : user_detail
    Created on : 10 Oct 2025, 05:46:20
    Author     : Mirinesa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="p-4">

        <a href="AdminUserServlet" class="btn btn-secondary mb-3">← Back to User List</a>

        <div class="card mx-auto shadow-lg border-0" style="max-width: 750px;">
            <div class="card-header bg-dark text-white text-center py-3 rounded-top">
                <h4 class="mb-0"><i class="bi bi-person-circle me-2"></i>Viewing ${user.account.username}s' account</h4>
            </div>

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
                                <form>
                                    <h5 class="fw-bold mb-3 text-dark">User Details</h5>

                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">ID</label>
                                            <input type="text" name="id" class="form-control form-control-sm" value="${user.id}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Username</label>
                                            <input type="text" name="username" class="form-control form-control-sm" value="${user.account.username}" readonly>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Full Name</label>
                                            <input type="text" name="full_name" class="form-control form-control-sm" value="${user.full_name}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Job Title</label>
                                            <input type="text" name="job_title" class="form-control form-control-sm" value="${user.job_title}" readonly>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Gender</label>
                                            <input type="text" name="gender" class="form-control form-control-sm" value="${user.gender}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Date of Birth</label>
                                            <input type="date" name="dob" class="form-control form-control-sm" value="${user.dob}" readonly>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Address</label>
                                            <input type="text" name="address" class="form-control form-control-sm" value="${user.address}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label small text-muted">Phone number</label>
                                            <input type="number" name="phone" class="form-control form-control-sm" value="${user.phone}" readonly>
                                        </div>

                                        <div class="col-md-12 mt-3">
                                            <label class="form-label small text-muted">Email</label>
                                            <input type="email" name="email" class="form-control form-control-sm" value="${user.email}" readonly>
                                        </div>
                                    </div>

                                    <hr class="my-4">

                                    <h5 class="fw-bold text-dark mb-2">Bio</h5>
                                    <textarea class="form-control form-control-sm" rows="4" readonly>${user.bio}</textarea>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <a href="AdminUserEditServlet?id=${user.id}" class="btn btn-warning px-4 rounded-pill shadow-sm">
                            <i class="bi bi-pencil-square me-1"></i> Edit
                        </a>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
