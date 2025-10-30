<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <div class="d-flex gap-3 flex-wrap">

                            <!-- Lọc theo loại sự kiện -->
                            <div class="form-group">
                                <label class="form-label me-2 fw-semibold">Loại sự kiện:</label>
                                <select class="form-select form-select-sm" style="width: 160px;">
                                    <option>Tất cả</option>
                                    <option>Y tế</option>
                                    <option>Môi trường</option>
                                    <option>Xã hội</option>
                                    <option>Giáo dục</option>
                                </select>
                            </div>

                            <!-- Lọc theo trạng thái -->
                            <div class="form-group">
                                <label class="form-label me-2 fw-semibold">Trạng thái:</label>
                                <select class="form-select form-select-sm" style="width: 160px;">
                                    <option>Tất cả</option>
                                    <option>Đang diễn ra</option>
                                    <option>Chưa diễn ra</option>
                                    <option>Đã kết thúc</option>
                                </select>
                            </div>

                            <!-- Lọc theo chế độ -->
                            <div class="form-group">
                                <label class="form-label me-2 fw-semibold">Chế độ:</label>
                                <select class="form-select form-select-sm" style="width: 160px;">
                                    <option>Tất cả</option>
                                    <option>Công khai</option>
                                    <option>Riêng tư</option>
                                </select>
                            </div>
                        </div>

                        <!-- Nút tạo mới -->
                        <button class="btn btn-primary btn-sm">
                            <i class="bi bi-plus-lg"></i> Tạo mới sự kiện
                        </button>
                    </div>


                    <!-- Bảng dữ liệu -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="table-secondary">
                                <tr>
                                    <th>STT</th>
                                    <th>Tiêu đề</th>
                                    <th>Loại sự kiện</th>
                                    <th>Chế độ</th>
                                    <th>Trạng thái</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Sự kiện gây quỹ mùa hè</td>
                                    <td><span class="badge bg-success">Môi trường</span></td>
                                    <td>
                                        <select class="form-select form-select-sm w-auto">
                                            <option selected>Công khai</option>
                                            <option>Riêng tư</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-select form-select-sm w-auto">
                                            <option selected>Đang diễn ra</option>
                                            <option>Chưa diễn ra</option>
                                            <option>Đã kết thúc</option>
                                        </select>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/organization/detail_event_org.jsp" class="btn btn-primary btn-sm">
                                            <i class=""></i> Xem
                                        </a>
                                        <a href="<%= request.getContextPath() %>/organization/apply_org.jsp" class="btn btn-secondary btn-sm">
                                            <i class=""></i> Xử lý
                                        </a>
                                        <button class="btn btn-danger btn-sm">
                                            <i class=""></i> Xóa
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>Hội thảo công nghệ</td>
                                    <td><span class="badge bg-success">Y tế</span></td>
                                    <td>
                                        <select class="form-select form-select-sm w-auto">
                                            <option>Công khai</option>
                                            <option selected>Riêng tư</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-select form-select-sm w-auto">
                                            <option>Đang diễn ra</option>
                                            <option selected>Chưa diễn ra</option>
                                            <option>Đã kết thúc</option>
                                        </select>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/organization/detail_event_org.jsp" class="btn btn-primary btn-sm">
                                            <i class=""></i> Xem
                                        </a>
                                        <a href="<%= request.getContextPath() %>/organization/apply_org.jsp" class="btn btn-secondary btn-sm">
                                            <i class=""></i> Xử lý
                                        </a>
                                        <button class="btn btn-danger btn-sm">
                                            <i class=""></i> Xóa
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Ngày hội tuyển sinh 2025</td>
                                    <td><span class="badge bg-success">Giáo dục</span></td>
                                    <td>
                                        <select class="form-select form-select-sm w-auto">
                                            <option selected>Công khai</option>
                                            <option>Riêng tư</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-select form-select-sm w-auto">
                                            <option>Đang diễn ra</option>
                                            <option>Chưa diễn ra</option>
                                            <option selected>Đã kết thúc</option>
                                        </select>
                                    </td>
                                    <td>
                                        <a href="<%= request.getContextPath() %>/organization/detail_event_org.jsp" class="btn btn-primary btn-sm">
                                            <i class=""></i> Xem
                                        </a>
                                        <a href="<%= request.getContextPath() %>/organization/apply_org.jsp" class="btn btn-secondary btn-sm">
                                            <i class=""></i> Xử lý
                                        </a>
                                        <button class="btn btn-danger btn-sm">
                                            <i class=""></i> Xóa
                                        </button>
                                    </td>
                                </tr>
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
