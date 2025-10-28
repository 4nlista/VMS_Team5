<%-- 
    Document   : accounts_admin
    Created on : Sep 21, 2025, 9:34:56 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Quản lí tài khoản</title>
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
            <div class="main-content p-4">
                <h1>Quản lí tài khoản</h1>               
                
                
                <!-- Yêu cầu 1: Thêm nút Add Account + Filter + Search -->
                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                    <!-- Filter (vai trò, trạng thái) -->
                    <form action="AdminAccountServlet" method="get" class="d-flex gap-3 flex-wrap" style="flex: 3;">
                        <select name="role" class="form-select w-25">
                            <option value="">-- Vai trò --</option>
                            <option value="admin">Admin</option>
                            <option value="user">Organization</option>
                            <option value="user">Volunteer</option>
                        </select>

                        <select name="status" class="form-select w-25">
                            <option value="">-- Trạng thái --</option>
                            <option value="active">Active</option>
                            <option value="inactive">Inactive</option>
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

                    <a href="add_account.jsp" class="btn btn-primary">
                        <i class="bi bi-plus-circle"></i> Tạo tài khoản
                    </a>
                </div>

                <!-- Bảng dữ liệu -->
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID
                                <a href="?sort=id_asc"><i class="bi bi-caret-up-fill text-white ms-1"></i></a>
                                <a href="?sort=id_desc"><i class="bi bi-caret-down-fill text-white"></i></a>
                            </th>
                            <th>Tài khoản</th>
                            <th>Mật khẩu</th>
                            <th>Vai trò</th>
                            <th>Trạng thái</th>
                            <th>Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="acc" items="${accounts}">
                            <tr>
                                <td>${acc.id}</td>
                                <td>${acc.username}</td>
                                <td>${acc.password}</td>
                                <td>${acc.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${acc.status}">
                                            <span class="badge bg-success">
                                                <i class="bi bi-circle-fill me-1"></i> Active
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">
                                                <i class="bi bi-circle-fill me-1"></i> Inactive
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="action-icons">
                                        <a href="#" class="btn btn-primary btn-sm" title="Xem chi tiết">
                                            <i class="bi bi-eye"></i>
                                        </a>
                                        <a href="#" class="btn btn-warning btn-sm" title="Chỉnh sửa">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                        <a href="#" class="btn btn-danger btn-sm" title="Xóa">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </div>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                
                <!--Kết thúc main -->
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
