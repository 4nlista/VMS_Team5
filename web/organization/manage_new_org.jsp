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
        <div class="content-container d-flex">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4 flex-grow-1">
                <div class="container" style="max-width: 1000px;">
                    <h3 class="fw-bold mb-4 text-center">Danh sách bài viết</h3>

                    <!-- Bộ lọc + nút tạo mới -->
                    <form method="get" action="OrganizationListServlet" 
                          class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">

                        <div class="d-flex gap-2 align-items-end flex-wrap">
                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái</label>
                                <select name="status" class="form-select form-select-sm" style="width: 140px;">
                                    <option value="">Tất cả</option>
                                    <option value="published">Hiển thị</option>
                                    <option value="hidden">Đã ẩn</option>
                                </select>
                            </div>

                            <!-- Nút Lọc -->
                            <button type="submit" class="btn btn-primary btn-sm" style="min-width:100px;">
                                <i class="bi bi-search"></i> Lọc
                            </button>

                            <!-- Nút Reset -->
                            <a href="OrganizationListServlet" class="btn btn-secondary btn-sm" style="min-width:100px;">
                                <i class="bi bi-arrow-counterclockwise"></i> Reset
                            </a>
                        </div>

                        <!-- Nút tạo mới bài viết -->
                        <a href="${pageContext.request.contextPath}/organization/create_news_org.jsp" 
                           class="btn btn-success btn-sm" style="min-width:130px;">
                            <i class="bi bi-plus-lg"></i> Tạo bài đăng mới
                        </a>
                    </form>

                    <!-- Bảng dữ liệu -->
                    <div class="table-responsive" style="max-height: 500px; overflow-y:auto;">
                        <table class="table table-bordered table-hover" style="table-layout: fixed; width: 100%;">
                            <thead class="table-secondary">
                                <tr>
                                    <th style="width:5%;">STT</th>
                                    <th style="width:50%;">Tên bài viết</th>
                                    <th style="width:15%;">Trạng thái</th>
                                    <th style="width:30%;">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="e" items="${eventsOrg}" varStatus="loop">
                                <tr>
                                    <td>1</td>
                                    <td class="text-truncate" title="">Trồng cây gây rừng</td>
                                    <td>
                                <c:choose>
                                    <c:when test="${e.status == 'published'}">
                                        <span class="badge bg-success">Hiển thị</span>
                                    </c:when>
<!--                                    <c:when test="${e.status == 'hidden'}">
                                        <span class="badge bg-warning text-dark">Ẩn</span>
                                    </c:when>-->
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${e.status}</span>
                                    </c:otherwise>
                                </c:choose>
                                </td>
                                <td>
                                    <a href="<%= request.getContextPath() %>/organization/detail_news_org.jsp?id=${e.id}" 
                                       class="btn btn-primary btn-sm">Chi tiết</a>
                                    <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                                </td>
                                </tr>

                                <tr>
                                    <td>2</td>
                                    <td class="text-truncate" title="">Chiến dịch bảo vệ rừng</td>
                                    <td>
                                <c:choose>
    <!--                                    <c:when test="${e.status == 'published'}">
                                            <span class="badge bg-success">Hiển thị</span>
                                        </c:when>-->
                                    <c:when test="${e.status == 'hidden'}">
                                        <span class="badge bg-warning text-dark">Đã ẩn</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${e.status}</span>
                                    </c:otherwise>
                                </c:choose>
                                </td>
                                <td>
                                    <a href="<%= request.getContextPath() %>/organization/detail_news_org.jsp?id=${e.id}" 
                                       class="btn btn-primary btn-sm">Chi tiết</a>
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

