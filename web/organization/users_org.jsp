<%-- 
    Document   : users_org
    Created on : Sep 17, 2025, 7:34:33 PM
    Author     : Organization
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lí người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">


            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <!-- Main Content -->
            <div class="main-content p-4">
                <div class="container-fluid">
                    <h3 class="fw-bold mb-4">Danh sách tình nguyện viên</h3>

                    <!-- Bộ lọc + nút tạo mới (nếu cần) -->
                    <form method="get" action="UserListServlet" class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <div class="d-flex gap-2 align-items-center flex-wrap">
                            <!-- Giới tính -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Giới tính:</label>
                                <select name="gender" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="">Tất cả</option>
                                    <option value="Nam">Nam</option>
                                    <option value="Nữ">Nữ</option>
                                </select>
                            </div>

                            <!-- Nút Lọc -->
                            <button type="submit" class="btn btn-primary btn-sm" style="min-width:110px; align-self:end;">
                                <i class="bi bi-search"></i> Lọc
                            </button>

                            <!-- Nút Reset -->
                            <a href="UserListServlet" class="btn btn-secondary btn-sm" style="min-width:110px; align-self:end;">
                                <i class="bi bi-arrow-counterclockwise"></i> Reset
                            </a>
                        </div>
                        <div class="form-group">
                            <label class="form-label fw-semibold">Tên sự kiện</label>
                            <div class="d-flex align-items-center gap-2 mt-1">
                                <input class="form-control w-auto" placeholder="Nhập tên sự kiện..." />
                                <button class="btn btn-danger">Tìm kiếm</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label fw-semibold">Tên tình nguyện viên</label>
                            <div class="d-flex align-items-center gap-2 mt-1">
                                <input class="form-control w-auto" placeholder="Nhập tên..." />
                                <button class="btn btn-danger">Tìm kiếm</button>
                            </div>
                        </div>
                    </form>

                    <!-- Bảng dữ liệu -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                            <thead class="table-secondary">
                                <tr>
                                    <th style="width:5%;">STT</th>
                                    <th style="width:25%;">Sự kiện</th>
                                    <th style="width:20%;">Tên</th>
                                    <th style="width:10%;">Ngày sinh</th>
                                    <th style="width:10%;">Giới tính</th>
                                    <th style="width:10%;">Địa chỉ</th>
                                    <th style="width:20%;">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td title="">Trồng cây gây rừng</td>
                                    <td>Nguyễn Văn An</td>
                                    <td>2004/09/20</td>
                                    <td>Nam</td>
                                    <td>Hà Nội</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/organization/detail_users_org.jsp" class="btn btn-primary btn-sm me-1">Chi tiết</a>
                                        <a href="#" class="btn btn-danger btn-sm me-1">Xóa</a>
                                        <a href="<%= request.getContextPath() %>/organization/send_notification_org.jsp" class="btn btn-info btn-sm">Thông báo</a>
                                    </td>
                                </tr>

                                <tr>
                                    <td>2</td>
                                    <td title="">Trồng cây gây rừng</td>
                                    <td>Nguyễn Thị Linh</td>
                                    <td>1994/11/16</td>
                                    <td>Nữ</td>
                                    <td>Hà Nam</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/organization/detail_users_org.jsp" class="btn btn-primary btn-sm me-1">Chi tiết</a>
                                        <a href="#" class="btn btn-danger btn-sm me-1">Xóa</a>
                                        <a href="<%= request.getContextPath() %>/organization/send_notification_org.jsp" class="btn btn-info btn-sm">Thông báo</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- Phân trang -->                            
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <span>Hiển thị 1 - 3 người dùng</span>
                        <ul class="pagination pagination-sm mb-0">
                            <li class="page-item disabled"><a class="page-link" href="#">Trước</a></li>
                            <li class="page-item active"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">Sau</a></li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>


