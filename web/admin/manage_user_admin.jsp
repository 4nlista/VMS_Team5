<%-- 
    Document   : users_admin
    Created on : Sep 16, 2025, 9:30:33 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <div class="main-content">
                <h1><i class="bi bi-people-fill me-2"></i>User Management</h1>

                <!-- Filter + Search -->
                <div class="filter-bar mb-4 d-flex flex-wrap justify-content-between align-items-center gap-3">

                    <!-- Compact Filter -->
                    <div class="dropdown" style="flex: 3;">
                        <button class="btn btn-danger dropdown-toggle" type="button" id="filterDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-filter me-1"></i>Filter
                        </button>

                        <div class="dropdown-menu filter-dropdown shadow" aria-labelledby="filterDropdown">
                            <form action="AdminUserServlet" method="get" class="d-flex flex-column gap-2">
                                <select name="role" class="form-select form-select-sm">
                                    <option value="">-- Role --</option>
                                    <option value="admin" ${currentRole == 'admin' ? 'selected' : ''}>Admin</option>
                                    <option value="organization" ${currentRole == 'organization' ? 'selected' : ''}>Organization</option>
                                    <option value="volunteer" ${currentRole == 'volunteer' ? 'selected' : ''}>Volunteer</option>
                                </select>

                                <select name="gender" class="form-select form-select-sm">
                                    <option value="">-- Gender --</option>
                                    <option value="male" ${currentGender == 'male' ? 'selected' : ''}>Male</option>
                                    <option value="female" ${currentGender == 'female' ? 'selected' : ''}>Female</option>
                                </select>

                                <input type="hidden" name="search" value="${fn:escapeXml(currentSearch)}" />
                                <input type="hidden" name="sort" value="${fn:escapeXml(currentSort)}" />

                                <div class="d-flex justify-content-end gap-2 mt-2">
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="bi bi-search me-1"></i>Apply
                                    </button>
                                    <a href="AdminUserServlet" class="btn btn-secondary btn-sm">
                                        <i class="bi bi-trash me-1"></i>Reset
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Search -->
                    <form action="AdminUserServlet" method="get" class="d-flex" style="flex: 1; justify-content: end;">
                        <input type="text" name="search" class="form-control" placeholder="Search by name" value="${fn:escapeXml(currentSearch)}" />
                        <input type="hidden" name="role" value="${fn:escapeXml(currentRole)}" />
                        <input type="hidden" name="sort" value="${fn:escapeXml(currentSort)}" />
                        <input type="hidden" name="gender" value="${fn:escapeXml(currentGender)}" />
                        <button type="submit" class="btn btn-primary ms-2">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>
                </div>

                <!-- User Table -->
                <div class="table-responsive shadow-sm mb-4" style="min-height: 450px;">
                    <table class="table table-striped table-hover align-middle mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Avatar</th>
                                <th>Username</th>
                                <th>Full Name</th>
                                <th>Gender</th>
                                <th>Role</th>
                                <th class="text-center">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty users}">
                                <tr><td colspan="7" class="text-center text-danger py-4">No users found</td></tr>
                            </c:if>

                            <c:forEach var="user" items="${users}">
                                <tr class="user-row bg-white">
                                    <td>${user.id}</td>
                                    <td>
                                        <img src="${not empty user.avatar ? user.avatar : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}"
                                             alt="avatar"
                                             class="rounded-circle shadow-sm"
                                             width="50"
                                             height="50">
                                    </td>
                                    <td>${user.account.username}</td>
                                    <td>${user.full_name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.gender == 'male'}">Male</c:when>
                                            <c:when test="${user.gender == 'female'}">Female</c:when>
                                            <c:otherwise>Unknown</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><span class="badge bg-secondary text-capitalize">${user.account.role}</span></td>
                                    <td class="text-center">
                                        <form action="AdminUserDetailServlet" method="get" style="display:inline;">
                                            <input type="hidden" name="id" value="${user.id}">
                                            <button type="submit" class="btn btn-primary btn-sm me-1" title="View details">
                                                <i class="bi bi-eye"></i>
                                            </button>
                                        </form>
                                        <form action="AdminUserEditServlet" method="get" style="display:inline;">
                                            <input type="hidden" name="id" value="${user.id}">
                                            <button type="submit" class="btn btn-warning btn-sm" title="Edit">
                                                <i class="bi bi-pencil-square"></i>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Pagination Section -->
                <div class="card shadow-sm border-0 p-3">
                    <div class="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">

                        <!-- Left: Page info -->
                        <div class="text-muted small">
                            Page <strong>${currentPage}</strong> of <strong>${totalPages}</strong>
                        </div>

                        <!-- Center: Pagination Controls -->
                        <nav aria-label="User list pagination">
                            <ul class="pagination mb-0 flex-wrap justify-content-center">
                                <!-- Previous -->
                                <c:url var="prevUrl" value="AdminUserServlet">
                                    <c:param name="page" value="${currentPage - 1}" />
                                    <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                    <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                    <c:param name="sort" value="${fn:escapeXml(currentSort)}" />
                                    <c:param name="gender" value="${fn:escapeXml(currentGender)}" />
                                </c:url>
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="${prevUrl}">Previous</a>
                                </li>

                                <!-- Page numbers -->
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <c:url var="pageUrl" value="AdminUserServlet">
                                        <c:param name="page" value="${i}" />
                                        <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                        <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                        <c:param name="sort" value="${fn:escapeXml(currentSort)}" />
                                        <c:param name="gender" value="${fn:escapeXml(currentGender)}" />
                                    </c:url>
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="${pageUrl}">${i}</a>
                                    </li>
                                </c:forEach>

                                <!-- Next -->
                                <c:url var="nextUrl" value="AdminUserServlet">
                                    <c:param name="page" value="${currentPage + 1}" />
                                    <c:param name="role" value="${fn:escapeXml(currentRole)}" />
                                    <c:param name="search" value="${fn:escapeXml(currentSearch)}" />
                                    <c:param name="sort" value="${fn:escapeXml(currentSort)}" />
                                    <c:param name="gender" value="${fn:escapeXml(currentGender)}" />
                                </c:url>
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="${nextUrl}">Next</a>
                                </li>
                            </ul>
                        </nav>

                        <!-- Right: Go to page -->
                        <form action="AdminUserServlet" method="get" class="d-flex align-items-center gap-2">
                            <label for="gotoPage" class="form-label mb-0 small text-muted">Go to:</label>
                            <input type="number" id="gotoPage" name="page" min="1" max="${totalPages}" value="${currentPage}"
                                   class="form-control form-control-sm" style="width: 80px;">
                            <input type="hidden" name="role" value="${fn:escapeXml(currentRole)}" />
                            <input type="hidden" name="search" value="${fn:escapeXml(currentSearch)}" />
                            <input type="hidden" name="sort" value="${fn:escapeXml(currentSort)}" />
                            <input type="hidden" name="gender" value="${fn:escapeXml(currentGender)}" />
                            <button type="submit" class="btn btn-primary btn-sm">Go</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
