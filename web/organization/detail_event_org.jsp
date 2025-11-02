<%-- 
    Document   : detail_event_org
    Created on : Oct 30, 2025, 9:45:25 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
        <style>
            body {
                background-color: #f8f9fa;
            }
            .event-image-container {
                border: 2px solid #e9ecef;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            }
            .event-image-container img {
                width: 100%;
                height: auto;
                display: block;
            }
            .info-table th {
                background-color: #f8f9fa;
                font-weight: 600;
                color: #495057;
            }
            .section-title {
                border-left: 4px solid #0d6efd;
                padding-left: 15px;
                margin-bottom: 20px;
            }
            .donor-table {
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            }
            .donor-table thead {
                background-color: #0d6efd;
                color: white;
            }
            .donor-table tbody tr:hover {
                background-color: #f8f9fa;
            }
            .amount-highlight {
                color: #198754;
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <!-- Page Header -->

                    <!-- Event Information Card -->
                    <div class="card shadow-sm border-0 mb-4">
                        <div class="card-body p-4">
                            <h5 class="section-title mb-4">
                                Thông tin sự kiện
                            </h5>

                            <!-- Header với tiêu đề nổi bật -->
                            <div class="alert alert-primary border-0 mb-4" role="alert">
                                <div class="d-flex align-items-center">
                                    <div>
                                        <h4 class="alert-heading mb-1">Lớp học tình thương</h4>
                                        <p >
                                             Mã sự kiện: <strong>EVT001</strong></small>
                                        </p>
                                    </div>
                                </div>
                            </div>

                            <!-- Grid Layout 2 cột -->
                            <div class="row g-4">
                                <!-- Cột trái -->
                                <div class="col-md-6">
                                    <div class="border rounded p-3 h-100 bg-light">
                                        <h6 class="text-primary fw-bold mb-3">
                                            <i class="bi bi-info-square me-2"></i>Thông tin cơ bản
                                        </h6>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-tag-fill text-info me-1"></i>Loại sự kiện
                                            </label>
                                            <div>
                                                <span class="badge bg-info fs-6 px-3 py-2">Giáo dục</span>
                                            </div>
                                        </div>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-person-badge-fill text-primary me-1"></i>Người tổ chức
                                            </label>
                                            <div class="fw-semibold">Org1</div>
                                        </div>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-geo-alt-fill text-danger me-1"></i>Địa điểm
                                            </label>
                                            <div class="fw-semibold">Trung tâm Hội nghị Thành phố</div>
                                        </div>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-people-fill text-success me-1"></i>Số lượng tình nguyện viên
                                            </label>
                                            <div class="fw-semibold">150 người</div>
                                        </div>

                                        <div>
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-calendar-plus-fill text-secondary me-1"></i>Ngày tạo
                                            </label>
                                            <div class="fw-semibold">25/10/2025</div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Cột phải -->
                                <div class="col-md-6">
                                    <div class="border rounded p-3 h-100 bg-light">
                                        <h6 class="text-primary fw-bold mb-3">
                                            <i class="bi bi-clock-history me-2"></i>Thời gian & Trạng thái
                                        </h6>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-calendar-check-fill text-success me-1"></i>Ngày bắt đầu
                                            </label>
                                            <div class="fw-semibold">01/11/2025</div>
                                        </div>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-calendar-x-fill text-danger me-1"></i>Ngày kết thúc
                                            </label>
                                            <div class="fw-semibold">05/11/2025</div>
                                        </div>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-hourglass-split text-warning me-1"></i>Trạng thái
                                            </label>
                                            <div>
                                                <span class="badge bg-warning text-dark fs-6 px-3 py-2">
                                                    <i class="bi bi-lightning-charge-fill me-1"></i>Đang diễn ra
                                                </span>
                                            </div>
                                        </div>

                                        <div class="mb-3">
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-eye-fill text-secondary me-1"></i>Chế độ
                                            </label>
                                            <div>
                                                <span class="badge bg-secondary fs-6 px-3 py-2">
                                                    <i class="bi bi-globe me-1"></i>Công khai
                                                </span>
                                            </div>
                                        </div>

                                        <div>
                                            <label class="text-muted small mb-1">
                                                <i class="bi bi-cash-stack text-success me-1"></i>Tổng tiền tài trợ
                                            </label>
                                            <div>
                                                <span class="badge bg-success fs-5 px-4 py-2">
                                                    <i class="bi bi-currency-dollar me-1"></i>50,000,000 VND
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Mô tả -->
                            <div class="mt-4">
                                <div class="border rounded p-3 bg-light">
                                    <h6 class="text-primary fw-bold mb-2">
                                        <i class="bi bi-file-text-fill me-2"></i>Mô tả sự kiện
                                    </h6>
                                    <p class="mb-0 text-dark">
                                        Sự kiện quy mô toàn cầu hỗ trợ các khu vực gặp khó khăn về kinh tế và y tế.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Donations List Card -->
                    <div class="card shadow-sm border-0">
                        <div class="card-body p-4">
                            <h5 class="section-title">
                                Đơn tài trợ
                            </h5>
                            <div class="table-responsive">
                                <table class="table table-hover donor-table mb-0">
                                    <thead>
                                        <tr>
                                            <th style="width: 5%;">STT</th>
                                            <th style="width: 15%;">Họ và Tên</th>
                                            <th style="width: 15%;">Số tiền</th>
                                            <th style="width: 20%;">Thời gian</th>                             
                                            <th style="width: 15%;">Phương thức</th>
                                            <th style="width: 10%;">Trạng thái</th>
                                            <th style="width: 20%;">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>Nguyễn Văn An</td>
                                            <td>5,000,000 VND</td>
                                            <td>
                                                01/11/2025 10:30
                                            </td>
                                            <td>
                                                <span class="badge bg-info">
                                                    <i class="bi bi-qr-code me-1"></i>QR Code
                                                </span>
                                            </td>
                                            <td>
                                                <span class="badge bg-warning text-dark">Chưa xử lí</span>
                                            </td>

                                            <td>
                                                <a href="#" class="btn btn-primary btn-sm">Chấp nhận</a>
                                                <a href="#" class="btn btn-danger btn-sm">Từ chối</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>Nguyễn Văn Nam</td>
                                            <td>4,000,000 VND</td>
                                            <td>
                                                01/11/2025 10:30
                                            </td>
                                            <td>
                                                <span class="badge bg-info">
                                                    <i class="bi bi-qr-code me-1"></i>QR Code
                                                </span>
                                            </td>
                                            <td>
                                                <span class="badge bg-success">Đã xử lý</span>
                                            </td>
                                            <td>
                                                <span class="badge bg-success">Chấp nhận</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>Nguyễn Văn An</td>
                                            <td>2,500,000 VND</td>
                                            <td>
                                                01/11/2025 10:30
                                            </td>
                                            <td>
                                                <span class="badge bg-info">
                                                    <i class="bi bi-qr-code me-1"></i>QR Code
                                                </span>
                                            </td>
                                            <td>
                                                <span class="badge bg-success">Đã xử lý</span>
                                            </td>
                                            <td>
                                                <span class="badge bg-danger">Từ chối</span>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Pagination -->
                            <div class="d-flex justify-content-between align-items-center mt-4">
                                <div class="text-muted">
                                    <i class="bi bi-info-circle me-1"></i>
                                    Hiển thị <strong>1-10</strong> trong tổng số <strong>10</strong> người đóng góp
                                </div>
                                <nav>
                                    <ul class="pagination mb-0">
                                        <li class="page-item disabled">
                                            <a class="page-link" href="#" tabindex="-1">
                                                <i class="bi bi-chevron-left"></i>
                                            </a>
                                        </li>
                                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                                        <li class="page-item">
                                            <a class="page-link" href="#">
                                                <i class="bi bi-chevron-right"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Chi Tiết Người Donate 1 -->
            <div class="modal fade" id="donorModal1" tabindex="-1" aria-labelledby="donorModal1Label" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header bg-primary text-white">
                            <h5 class="modal-title" id="donorModal1Label">
                                <i class="bi bi-person-circle me-2"></i>Thông Tin Chi Tiết Người Đóng Góp
                            </h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row g-3">
                                <div class="col-12">
                                    <div class="card border-0 bg-light">
                                        <div class="card-body">
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-hash me-1"></i>ID Người donate:
                                                </div>
                                                <div class="col-7 fw-semibold">DN001</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-person me-1"></i>Họ và Tên:
                                                </div>
                                                <div class="col-7 fw-semibold">Nguyễn Văn An</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-telephone me-1"></i>Số điện thoại:
                                                </div>
                                                <div class="col-7">0123456789</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-envelope me-1"></i>Email:
                                                </div>
                                                <div class="col-7">nguyenvanan@email.com</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-geo-alt me-1"></i>Địa chỉ:
                                                </div>
                                                <div class="col-7">Hà Nội</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-currency-dollar me-1"></i>Số tiền:
                                                </div>
                                                <div class="col-7">
                                                    <span class="badge bg-success fs-6">5,000,000 VND</span>
                                                </div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-clock me-1"></i>Thời gian:
                                                </div>
                                                <div class="col-7">01/11/2025 10:30</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-credit-card me-1"></i>Phương thức:
                                                </div>
                                                <div class="col-7">
                                                    <span class="badge bg-info">QR Code</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <i class="bi bi-x-circle me-1"></i>Đóng
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
