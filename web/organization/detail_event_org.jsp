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
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h3 class="fw-bold mb-0">
                            <i class="bi bi-eye text-primary me-2"></i>Xem chi tiết sự kiện
                        </h3>



                    </div>

                    <!-- Event Information Card -->
                    <div class="card shadow-sm border-0 mb-4">
                        <div class="card-body p-4">
                            <h5 class="section-title">
                                <i class="bi bi-info-circle me-2"></i>Thông tin sự kiện
                            </h5>

                            <div class="row g-4">
                                <!-- Cột ảnh -->
                                <div class="col-lg-4">
                                    <div class="event-image-container">
                                        <img src="https://viet-power.vn/wp-content/uploads/2025/06/backdrop-su-kien-3.jpg" 
                                             alt="Event Image" 
                                             class="img-fluid">
                                    </div>
                                    <div class="text-center mt-3">
                                        <span class="badge bg-light text-dark border px-3 py-2">
                                            <i class="bi bi-image me-1"></i>Hình ảnh sự kiện
                                        </span>
                                    </div>
                                </div>

                                <!-- Cột thông tin -->
                                <div class="col-lg-8">
                                    <div class="table-responsive">
                                        <table class="table table-bordered align-middle info-table mb-0">
                                            <tbody>
                                                <tr>
                                                    <th style="width: 30%">
                                                        <i class="bi bi-hash text-primary me-2"></i>Mã sự kiện
                                                    </th>
                                                    <td class="fw-semibold">EVT001</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-file-text text-primary me-2"></i>Tiêu đề
                                                    </th>
                                                    <td class="fs-5 fw-bold text-dark">Lớp học tình thương</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-tag text-primary me-2"></i>Loại sự kiện
                                                    </th>
                                                    <td>
                                                        <span class="badge bg-info">Giáo dục</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-person-badge text-primary me-2"></i>Người tổ chức
                                                    </th>
                                                    <td>Org1</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-calendar-check text-primary me-2"></i>Ngày bắt đầu
                                                    </th>
                                                    <td>01/11/2025</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-calendar-x text-primary me-2"></i>Ngày kết thúc
                                                    </th>
                                                    <td>05/11/2025</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-geo-alt text-primary me-2"></i>Địa điểm
                                                    </th>
                                                    <td>Trung tâm Hội nghị Thành phố</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-people text-primary me-2"></i>Số lượng tình nguyện viên
                                                    </th>
                                                    <td>150 người</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-currency-dollar text-primary me-2"></i>Tổng tiền tài trợ
                                                    </th>
                                                    <td>
                                                        <span class="badge bg-success fs-6 px-3 py-2">
                                                            50,000,000 VND
                                                        </span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-clock-history text-primary me-2"></i>Trạng thái
                                                    </th>
                                                    <td>
                                                        <span class="badge bg-warning text-dark px-3 py-2">
                                                            <i class="bi bi-hourglass-split me-1"></i>Đang diễn ra
                                                        </span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-eye text-primary me-2"></i>Chế độ
                                                    </th>
                                                    <td>
                                                        <span class="badge bg-secondary px-3 py-2">
                                                            <i class="bi bi-globe me-1"></i>Công khai
                                                        </span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-calendar-plus text-primary me-2"></i>Ngày tạo
                                                    </th>
                                                    <td>25/10/2025</td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        <i class="bi bi-card-text text-primary me-2"></i>Mô tả sự kiện
                                                    </th>
                                                    <td>Sự kiện quy mô toàn cầu hỗ trợ các khu vực gặp khó khăn về kinh tế và y tế.</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>


                            </div>
                        </div>
                    </div>

                    <!-- Donations List Card -->
                    <div class="card shadow-sm border-0">
                        <div class="card-body p-4">
                            <h5 class="section-title">
                                <i class="bi bi-heart-fill text-danger me-2"></i>Danh sách người đóng góp
                            </h5>

                            <div class="table-responsive">
                                <table class="table table-hover donor-table mb-0">
                                    <thead>
                                        <tr>
                                            <th style="width: 60px;" class="text-center">STT</th>
                                            <th style="width: 120px;">ID</th>
                                            <th>Họ và Tên</th>
                                            <th style="width: 150px;">Số tiền</th>
                                            <th style="width: 180px;">Thời gian</th>
                                            <th style="width: 120px;" class="text-center">Phương thức</th>
                                            <th style="width: 100px;" class="text-center">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="text-center fw-semibold">1</td>
                                            <td class="font-monospace">DN001</td>
                                            <td>
                                                <i class="bi bi-person-circle text-primary me-2"></i>
                                                <span class="fw-semibold">Nguyễn Văn An</span>
                                            </td>
                                            <td class="amount-highlight">5,000,000 VND</td>
                                            <td>
                                                <i class="bi bi-calendar3 me-1 text-muted"></i>01/11/2025 10:30
                                            </td>
                                            <td class="text-center">
                                                <span class="badge bg-info">
                                                    <i class="bi bi-qr-code me-1"></i>QR Code
                                                </span>
                                            </td>
                                            <td class="text-center">
                                                <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#donorModal1">
                                                    <i class="bi bi-info-circle"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="text-center fw-semibold">2</td>
                                            <td class="font-monospace">DN002</td>
                                            <td>
                                                <i class="bi bi-person-circle text-primary me-2"></i>
                                                <span class="fw-semibold">Trần Thị Bình</span>
                                            </td>
                                            <td class="amount-highlight">3,000,000 VND</td>
                                            <td>
                                                <i class="bi bi-calendar3 me-1 text-muted"></i>01/11/2025 14:15
                                            </td>
                                            <td class="text-center">
                                                <span class="badge bg-info">
                                                    <i class="bi bi-qr-code me-1"></i>QR Code
                                                </span>
                                            </td>
                                            <td class="text-center">
                                                <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#donorModal2">
                                                    <i class="bi bi-info-circle"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="text-center fw-semibold">3</td>
                                            <td class="font-monospace">DN003</td>
                                            <td>
                                                <i class="bi bi-person-circle text-primary me-2"></i>
                                                <span class="fw-semibold">Lê Minh Cường</span>
                                            </td>
                                            <td class="amount-highlight">10,000,000 VND</td>
                                            <td>
                                                <i class="bi bi-calendar3 me-1 text-muted"></i>02/11/2025 09:00
                                            </td>
                                            <td class="text-center">
                                                <span class="badge bg-info">
                                                    <i class="bi bi-qr-code me-1"></i>QR Code
                                                </span>
                                            </td>
                                            <td class="text-center">
                                                <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#donorModal3">
                                                    <i class="bi bi-info-circle"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="text-center fw-semibold">4</td>
                                            <td class="font-monospace">DN004</td>
                                            <td>
                                                <i class="bi bi-person-circle text-primary me-2"></i>
                                                <span class="fw-semibold">Phạm Thị Dung</span>
                                            </td>
                                            <td class="amount-highlight">2,000,000 VND</td>
                                            <td>
                                                <i class="bi bi-calendar3 me-1 text-muted"></i>02/11/2025 16:45
                                            </td>
                                            <td class="text-center">
                                                <span class="badge bg-info">
                                                    <i class="bi bi-qr-code me-1"></i>QR Code
                                                </span>
                                            </td>
                                            <td class="text-center">
                                                <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#donorModal4">
                                                    <i class="bi bi-info-circle"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Pagination -->
                            <div class="d-flex justify-content-between align-items-center mt-4">
                                <nav>
                                    <ul class="pagination mb-0">
                                        <li class="page-item disabled">
                                            <a class="page-link" href="#" tabindex="-1">
                                                <i class="bi bi-chevron-left"></i>Trước
                                            </a>
                                        </li>
                                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                                        <li class="page-item">
                                            <a class="page-link" href="#">
                                                <i class="bi bi-chevron-right"></i>Sau
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                                <div class="d-flex justify-content-end mt-3">
                                    <a href="${pageContext.request.contextPath}/OrganizationApplyServlet" class="btn btn-secondary">
                                        <i class="bi bi-arrow-left"></i> Quay lại
                                    </a>
                                </div>
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
