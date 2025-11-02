<%-- 
    Document   : manage_new_org
    Created on : Nov 2, 2025, 2:56:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Báo cáo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <h3 class="fw-bold mb-4">Danh sách bài viết</h3>

                    <!-- Bộ lọc + nút tạo mới -->
                    <form method="get" action="OrganizationListServlet" class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <!-- Nhóm dropdown + nút lọc/reset -->
                        <div class="d-flex gap-2 align-items-center flex-wrap">

                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái</label>
                                <select name="status" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="">Tất cả</option>
                                    <option value="published">Hiển thị</option>
                                    <option value="pending">Đang xử lý</option>
                                    <option value="hidden">Đã ẩn</option>
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
                        <a href="${pageContext.request.contextPath}/organization/create_news_org.jsp" 
                           class="btn btn-success btn-sm" style="min-width:130px;">
                            <i class="bi bi-plus-lg"></i> Tạo mới bài viết
                        </a>
                    </form>

                    <!-- Bảng dữ liệu -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                            <thead class="table-secondary">
                                <tr>
                                    <th style="width:4%;">STT</th>
                                    <th style="width:30%;">Tên sự kiện</th>
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
                                    <a href="<%= request.getContextPath() %>/organization/detail_news_org.jsp" class="btn btn-primary btn-sm">Chi tiết</a>
                                    <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                                </td>
                                </tr>
                            </c:forEach>

                            </tbody>

                        </table>
                    </div>

                    <!-- Phân trang -->
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <span>Hiển thị 1 - 3 trong tổng số bài viết</span>
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

