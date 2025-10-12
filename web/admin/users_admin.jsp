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
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content -->
            <div class="main-content">
                <h1>Quản lí người dùng</h1> 
                <!-- Yêu cầu 1: Thêm nút Add Account + Filter + Search -->
                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                    <!-- Filter (vai trò, trạng thái) -->
                    <form action="AdminUserServlet" method="get" class="d-flex gap-3 flex-wrap" style="flex: 3;">
                        <select name="role" class="form-select w-25">
                            <option value="">-- Vai trò --</option>
                            <option value="admin">Admin</option>
                            <option value="user">Organization</option>
                            <option value="user">Volunteer</option>
                        </select>

                        <button type="submit" class="btn btn-danger">
                            <i class="bi bi-filter"></i> Filter
                        </button>
                        <button type="reset" class="btn btn-secondary">
                            <i class="bi bi-trash"></i> Reset
                        </button>
                    </form>

                    <form action="AdminAccountServlet" method="get" class="d-flex" style="flex: 1; justify-content: end;">
                        <input type="text" name="search" class="form-control w-100" placeholder="Tìm tài khoản..." />
                    </form>
                </div>
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID
                                <a href="?sort=id_asc"><i class="bi bi-caret-up-fill text-white ms-1"></i></a>
                                <a href="?sort=id_desc"><i class="bi bi-caret-down-fill text-white"></i></a>
                            </th>
                            <th>Ảnh đại diện</th>
                            <th>Tên tài khoản</th>
                            <th>Tên người dùng</th>
                            <th>Giới tính</th>
                            <th>Vai trò</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty users}">
                            <tr><td colspan="7" class="text-center text-danger">No users found</td></tr>
                        </c:if>

                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.id}</td>
                                <td>
                                    <img src="${user.avatar}" alt="avatar" class="rounded-circle" width="50" height="50">
                                </td>
                                <td>${user.account.username}</td>
                                <td>${user.full_name}</td>
                                <td>${user.gender}</td>
                                <td>${user.account.role}</td>
                                <td class="text-center">
                                    <form action="AdminUserDetailServlet" method="get" style="display:inline;">
                                        <input type="hidden" name="id" value="${user.id}">
                                        <button type="submit" class="btn btn-primary btn-sm" title="View details">
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
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
