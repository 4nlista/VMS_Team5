<%-- 
    Document   : profile_admin
    Created on : Sep 30, 2025, 1:26:44 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/profile_admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container d-flex">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content -->
            <div class="main-content flex-grow-1">
                <div class="container-detail shadow bg-white">
                    <div class="row g-0">
                        <!-- LEFT: Avatar -->
                        <div class="col-md-4 profile-left d-flex flex-column align-items-center justify-content-center text-center p-4">
                            <img src="${not empty user.avatar ? user.avatar : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}"
                                 alt="avatar" class="rounded-circle avatar-lg mb-3 border border-2 border-light shadow-sm"/>
                            <div class="fw-semibold fs-5">${fn:escapeXml(user.full_name)}</div>
                            <div class="text-muted small">@${user.account.username}</div>
                            <div class="mt-3 text-muted small fst-italic">${fn:escapeXml(user.job_title)}</div>
                        </div>

                        <!-- RIGHT: Fields -->
                        <div class="col-md-8 p-4">
                            <div class="d-flex justify-content-between align-items-start mb-4">
                                <div>
                                    <h5 class="mb-0">Admin Profile</h5>
                                    <small class="text-muted">Viewing <strong>${user.account.username}</strong>'s account</small>
                                </div>
                                <div>
                                    <a href="AdminProfileEditServlet?id=${user.id}" class="btn btn-sm btn-warning text-white">
                                        <i class="bi bi-pencil"></i> Edit
                                    </a>
                                </div>
                            </div>

                            <form>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label class="form-label">ID</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.id}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Username</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.account.username}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Gender</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.gender}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Date of Birth</label>
                                        <c:choose>
                                            <c:when test="${not empty user.dob}">
                                                <fmt:formatDate value="${user.dob}" pattern="yyyy-MM-dd" var="dobFmt" />
                                                <input type="text" class="form-control form-control-sm" value="${dobFmt}" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control form-control-sm" value="N/A" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Address</label>
                                        <input type="text" class="form-control form-control-sm" value="${fn:escapeXml(user.address)}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Phone</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.phone}" readonly>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label">Email</label>
                                        <input type="email" class="form-control form-control-sm" value="${user.email}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Role</label>
                                        <input type="text" class="form-control form-control-sm fw-bold text-danger" value="${user.account.role}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Account Created At</label>
                                        <c:choose>
                                            <c:when test="${not empty user.account.createdAt}">
                                                <fmt:formatDate value="${user.account.createdAt}" pattern="HH:mm:ss / dd-MM-yyyy" var="acctCreated"/>
                                                <input type="text" class="form-control form-control-sm" value="${acctCreated}" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control form-control-sm" value="N/A" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label">Bio</label>
                                        <textarea class="form-control form-control-sm" style="resize:none;" rows="5" readonly>${fn:escapeXml(user.bio)}</textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
