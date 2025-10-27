<%-- 
    Document   : user_detail
    Created on : 10 Oct 2025, 05:46:20
    Author     : Mirinesa
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/user_admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content (centered) -->
            <div class="main-content">
                <div class="container-detail shadow-sm bg-white rounded">
                    <div class="row g-0">
                        <!-- LEFT: Avatar -->
                        <div class="col-md-3 profile-left p-4 text-center">
                            <img img src="${not empty user.avatar ? user.avatar : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}" alt="avatar" class="rounded-circle avatar-lg mb-3 border p-2"/>
                            <div class="fw-semibold">${fn:escapeXml(user.full_name)}</div>
                            <div class="text-muted small">${user.account.username}</div>
                        </div>

                        <!-- RIGHT: All fields  -->
                        <div class="col-md-9 p-4">
                            <div class="d-flex justify-content-between align-items-start mb-3">
                                <div>
                                    <h5 class="fw-bold mb-0">User Details</h5>
                                    <small class="text-muted">Viewing <strong>${user.account.username}</strong>'s account</small>
                                </div>
                                <div>
                                    <a href="AdminUserServlet" class="btn btn-sm btn-outline-secondary me-2">← Back</a>
                                    <a href="AdminUserEditServlet?id=${user.id}" class="btn btn-sm btn-warning">✎ Edit</a>
                                </div>
                            </div>

                            <form>
                                <div class="row g-2">
                                    <div class="col-md-6">
                                        <label class="form-label">ID</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.id}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Username</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.account.username}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Full Name</label>
                                        <input type="text" class="form-control form-control-sm" value="${fn:escapeXml(user.full_name)}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Job Title</label>
                                        <input type="text" class="form-control form-control-sm" value="${fn:escapeXml(user.job_title)}" readonly>
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
                                        <label class="form-label">Phone number</label>
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
                                        <label class="form-label">Account created at</label>
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

                                    <div class="col-12 mt-2">
                                        <label class="form-label">Bio</label>
                                        <textarea class="form-control form-control-sm" style="resize:none;"rows="6" readonly>${fn:escapeXml(user.bio)}</textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<%= request.getContextPath() %>/admin/js/detail_user_admin.js"></script>
    </body>
</html>