<%-- 
    Document   : apply_org
    Created on : Oct 30, 2025, 8:53:08 PM
    Author     : Admin
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lí sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <%
                     Object sessionId = session.getId();
                     String fullname = (String) session.getAttribute("fullname");
                     if (fullname == null) {
                         fullname = "Khách";
                     }
            %>

            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />


            <!-- Main Content -->
            <div class="main-content p-4">
                <div class="container-fluid">

                    <h2 class="fw-bold mb-4">Danh sách đơn đăng kí</h2>

                    <!-- Form lọc -->
                    <form class="row g-3 align-items-center mb-3">
                        <div class="col-auto">
                            <label for="statusFilter" class="col-form-label fw-semibold">Trạng thái:</label>
                        </div>
                        <div class="col-auto">
                            <select id="statusFilter" class="form-select form-select-sm">
                                <option value="all" selected>Tất cả</option>
                                <option value="pending">Đang chờ duyệt</option>
                                <option value="approved">Đã duyệt</option>
                                <option value="rejected">Đã từ chối</option>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <i class="bi bi-filter"></i> Lọc
                            </button>
                        </div>
                    </form>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead class="table-secondary">
                                <tr>
                                    <th>STT</th>
                                    <th>Tên</th>
                                    <th>Ngày nộp đơn</th>
                                    <th>Trạng thái</th>
                                    <th>Ghi chú</th>
                                    <th>Số giờ đăng ký</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Nguyễn Văn A</td>
                                    <td>25/10/2025</td>
                                    <td><span class="badge bg-warning text-dark">Đang chờ duyệt</span></td>
                                    <td>Có kinh nghiệm tổ chức sự kiện</td>
                                    <td>10</td>
                                    <td>
                                        <button class="btn btn-success btn-sm">
                                            <i class="bi bi-check-circle"></i> Duyệt đơn
                                        </button>
                                        <button class="btn btn-danger btn-sm">
                                            <i class="bi bi-x-circle"></i> Từ chối
                                        </button>
                                    </td>
                                </tr>

                                <tr>
                                    <td>2</td>
                                    <td>Trần Thị B</td>
                                    <td>26/10/2025</td>
                                    <td><span class="badge bg-success">Đã duyệt</span></td>
                                    <td>Tham gia nhiều hoạt động môi trường</td>
                                    <td>8</td>
                                    <td>
                                        <button class="btn btn-success btn-sm">
                                            <i class="bi bi-check-circle"></i> Duyệt đơn
                                        </button>
                                        <button class="btn btn-danger btn-sm">
                                            <i class="bi bi-x-circle"></i> Từ chối
                                        </button>
                                    </td>
                                </tr>

                                <tr>
                                    <td>3</td>
                                    <td>Lê Quốc C</td>
                                    <td>27/10/2025</td>
                                    <td><span class="badge bg-danger">Đã từ chối</span></td>
                                    <td>Thiếu thông tin xác minh</td>
                                    <td>5</td>
                                    <td>
                                        <button class="btn btn-success btn-sm">
                                            <i class="bi bi-check-circle"></i> Duyệt đơn
                                        </button>
                                        <button class="btn btn-danger btn-sm">
                                            <i class="bi bi-x-circle"></i> Từ chối
                                        </button>
                                    </td>
                                </tr>
                            </tbody>

                        </table>
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
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
