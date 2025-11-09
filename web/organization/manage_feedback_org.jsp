<%-- 
    Document   : manage_feedback_org
    Created on : Nov 2, 2025, 12:55:14 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý đánh giá</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h3 class="fw-bold mb-0">Danh sách đánh giá</h3>
                        <a href="<%= request.getContextPath() %>/OrganizationListServlet" class="btn btn-secondary btn-sm">
                            <i class="bi bi-arrow-left"></i> Về quản lí sự kiện
                        </a>
                    </div>
                    <!-- Bộ lọc + nút tạo mới -->
                    <form method="get" action="OrganizationListServlet" class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <!-- Nhóm dropdown + nút lọc/reset -->
                        <div class="d-flex gap-2 align-items-center flex-wrap">
                            <!-- Loại sự kiện -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Điểm đánh giá:</label>
                                <select name="rating" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="">Tất cả</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </div>

                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái:</label>
                                <select name="status" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="" <c:if test="${param.status == null || param.status == ''}">selected</c:if>>Tất cả</option>
                                        <option value="valid">Hợp lệ</option>
                                        <option value="invalid">Không hợp lệ</option>
                                    </select>
                                </div>
                                <!-- Nút Lọc -->
                                <button type="submit" class="btn btn-primary btn-sm" style="min-width:110px; align-self:end;">
                                    <i class="bi bi-search"></i> Lọc
                                </button>

                                <!-- Nút Reset -->
                                <a href="#" class="btn btn-secondary btn-sm" style="min-width:110px; align-self:end;">
                                    <i class="bi bi-arrow-counterclockwise"></i> Reset
                                </a>
                            </div>
                            <div class="form-group">
                                <label class="form-label fw-semibold">Tên sự kiện:</label>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <input class="form-control w-auto" placeholder="Nhập tên sự kiện" />
                                    <button class="btn btn-danger">Tìm kiếm</button>
                                </div>
                            </div>
                        </form>

                        <!-- Bảng dữ liệu -->
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle" style="table-layout: fixed; width: 100%;">
                                <thead class="table-secondary">
                                    <tr>
                                        <th style="width:4%;">STT</th>
                                        <th style="width:25%;">Tên sự kiện</th>
                                        <th style="width:16%;">Tình nguyện viên</th>
                                        <th style="width:25%;">Bình luận</th>
                                        <th style="width:5%;">Điểm</th>
                                        <th style="width:10%;">Trạng thái</th>
                                        <th style="width:15%;">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Hàng 1: Đang Hiện -->
                                    <tr>
                                        <td>1</td>
                                        <td>Dọn rác bãi biển</td>
                                        <td>Cao Văn Huy</td>
                                        <td>Sự kiện diễn ra rất hay và ý nghĩa</td>
                                        <td>4</td>
                                        <td><span class="badge bg-success">Hiện</span></td>
                                        <td>
                                            <select class="form-select form-select-sm w-auto d-inline">
                                                <option selected>Hiện</option>
                                                <option>Ẩn</option>
                                            </select>
                                            <a href="<%= request.getContextPath() %>/organization/send_report_org.jsp" class="btn btn-sm btn-secondary">Báo cáo</a>
                                    </td>
                                </tr>

                                <!-- Hàng 2: Đang Ẩn -->
                                <tr>
                                    <td>2</td>
                                    <td>Hỗ trợ người nghèo</td>
                                    <td>Nguyễn Bảo An</td>
                                    <td>Sự kiện rất tệ, tôi không muốn thấy nó</td>
                                    <td>1</td>
                                    <td><span class="badge bg-danger">Ẩn</span></td>
                                    <td>
                                        <select class="form-select form-select-sm w-auto d-inline">
                                            <option>Hiện</option>
                                            <option selected>Ẩn</option>
                                        </select>
                                        <a href="<%= request.getContextPath() %>/organization/send_report_org.jsp" class="btn btn-sm btn-secondary">Báo cáo</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>



                    <!-- Phân trang -->
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <span>Hiển thị 1 - 3 đánh giá</span>
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
