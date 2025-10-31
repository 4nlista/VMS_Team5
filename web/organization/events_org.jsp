<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <h3 class="fw-bold mb-4">Danh sách sự kiện</h3>

                    <!-- Bộ lọc + nút tạo mới -->
                    <form method="get" action="OrganizationListServlet" class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <!-- Nhóm dropdown + nút lọc/reset -->
                        <div class="d-flex gap-2 align-items-center flex-wrap">
                            <!-- Loại sự kiện -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Loại sự kiện:</label>
                                <select name="category" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="" <c:if test="${param.category == null || param.category == ''}">selected</c:if>>Tất cả</option>
                                    <option value="Y tế" <c:if test="${param.category == 'Y tế'}">selected</c:if>>Y tế</option>
                                    <option value="Môi trường" <c:if test="${param.category == 'Môi trường'}">selected</c:if>>Môi trường</option>
                                    <option value="Xã hội" <c:if test="${param.category == 'Xã hội'}">selected</c:if>>Xã hội</option>
                                    <option value="Giáo dục" <c:if test="${param.category == 'Giáo dục'}">selected</c:if>>Giáo dục</option>
                                    </select>
                                </div>

                                <!-- Trạng thái -->
                                <div class="form-group d-flex flex-column">
                                    <label class="form-label fw-semibold">Trạng thái:</label>
                                    <select name="status" class="form-select form-select-sm" style="width: 160px;">
                                        <option value="" <c:if test="${param.status == null || param.status == ''}">selected</c:if>>Tất cả</option>
                                    <option value="active" <c:if test="${param.status == 'active'}">selected</c:if>>Đang diễn ra</option>
                                    <option value="pending" <c:if test="${param.status == 'pending'}">selected</c:if>>Chưa diễn ra</option>
                                    <option value="inactive" <c:if test="${param.status == 'inactive'}">selected</c:if>>Tạm dừng</option>
                                    <option value="closed" <c:if test="${param.status == 'closed'}">selected</c:if>>Đã kết thúc</option>
                                    </select>
                                </div>

                                <!-- Chế độ -->
                                <div class="form-group d-flex flex-column">
                                    <label class="form-label fw-semibold">Chế độ:</label>
                                    <select name="visibility" class="form-select form-select-sm" style="width: 160px;">
                                        <option value="" <c:if test="${param.visibility == null || param.visibility == ''}">selected</c:if>>Tất cả</option>
                                    <option value="public" <c:if test="${param.visibility == 'public'}">selected</c:if>>Công khai</option>
                                    <option value="private" <c:if test="${param.visibility == 'private'}">selected</c:if>>Riêng tư</option>
                                    </select>
                                </div>

                                <!-- Nút Lọc -->
                                <button type="submit" class="btn btn-primary btn-sm" style="min-width:110px; align-self:end;">
                                    <i class="bi bi-search"></i> Lọc
                                </button>

                                <!-- Nút Reset -->
                                <a href="OrganizationListServlet" class="btn btn-secondary btn-sm" style="min-width:110px; align-self:end;">
                                    <i class="bi bi-arrow-counterclockwise"></i> Reset
                                </a>
                            </div>

                            <!-- Nút tạo mới sự kiện -->
                            <a href="create_event_org.jsp" class="btn btn-success btn-sm" style="min-width:130px;">
                                <i class="bi bi-plus-lg"></i> Tạo mới sự kiện
                            </a>
                        </form>




                        <!-- Bảng dữ liệu -->
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                                <thead class="table-secondary">
                                    <tr>
                                        <th style="width:4%;">STT</th>
                                        <th style="width:30%;">Tiêu đề</th>
                                        <th style="width:15%;">Loại sự kiện</th>
                                        <th style="width:15%;">Chế độ</th>
                                        <th style="width:16%;">Trạng thái</th>
                                        <th style="width:20%;">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="e" items="${eventsOrg}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${e.title}</td>
                                        <td><span class="badge bg-success">${e.categoryName}</span></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${e.visibility == 'public'}">
                                                    <span class="badge bg-secondary">Công khai</span>
                                                </c:when>
                                                <c:when test="${e.visibility == 'private'}">
                                                    <span class="badge bg-warning text-dark">Riêng tư</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-light text-dark">${e.visibility}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${e.status == 'active'}">
                                                    <span class="badge bg-success">Đang diễn ra</span>
                                                </c:when>
                                                <c:when test="${e.status == 'pending'}">
                                                    <span class="badge bg-warning text-dark">Chưa diễn ra</span>
                                                </c:when>
                                                <c:when test="${e.status == 'closed'}">
                                                    <span class="badge bg-danger">Đã kết thúc</span>
                                                </c:when>
                                                <c:when test="${e.status == 'inactive'}">
                                                    <span class="badge bg-dark">Tạm dừng</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${e.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td>
                                            <a href="organization/detail_event_org.jsp?id=${e.id}" class="btn btn-primary btn-sm">Chi tiết</a>
                                            <a href="OrganizationApplyServlet?id=${e.id}" class="btn btn-secondary btn-sm">Xử lý</a>
                                            <a href="organization/delete_event?id=${e.id}" class="btn btn-danger btn-sm">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>

                        </table>
                    </div>

                    <!-- Phân trang -->
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <span>Hiển thị 1 - 3 trong tổng 3 sự kiện</span>
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
