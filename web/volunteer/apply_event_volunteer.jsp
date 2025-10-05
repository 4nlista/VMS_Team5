<%-- 
    Document   : apply_event_volunteer
    Created on : Sep 29, 2025, 4:37:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang đăng kí tham gia sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />


        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center">Đăng ký tham gia sự kiện</h1>

            <div class="row">
                <!-- Cột trái: Thông tin sự kiện -->
                <div class="col-md-6">
                    <div class="card border shadow-sm mb-4">
                        <div class="card-body">
                            <h5 class="card-title d-flex justify-content-between align-items-center">
                                <span class="fw-bold">Thông tin sự kiện</span>
                                <span class="badge bg-success">Active</span>
                            </h5>
                            <ul class="list-group list-group-flush mt-3">
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Tiêu đề:</span>
                                    <span class="text-secondary ms-2">World Wide Donation</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Thời gian:</span>
                                    <span class="text-secondary ms-2">Sep, 10, 2018 10:30AM - 03:30PM</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Địa điểm:</span>
                                    <span class="text-secondary ms-2">Venue Main Campus</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Người tổ chức:</span>
                                    <span class="text-secondary ms-2">Org1</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Số lượng tình nguyện viên cần:</span>
                                    <span class="text-secondary ms-2">50</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Mô tả sự kiện:</span>
                                    <span class="text-secondary ms-2">Sự kiện gây quỹ quy mô toàn cầu...</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Cột phải: Form đăng ký -->
                <div class="col-md-6">
                    <div class="card border shadow-sm">
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">ID</label>
                                        <input type="text" class="form-control" value="V1" readonly>
                                    </div>
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Họ và tên</label>
                                        <input type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Email</label>
                                        <input type="email" class="form-control" placeholder="Emai của bạn....">
                                    </div>
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Số điện thoại</label>
                                        <input type="tel" class="form-control" placeholder="0123 456 789">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Số giờ đăng ký</label>
                                    <input type="number" class="form-control" placeholder="Nhập số giờ">
                                    </div>
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Ngày đăng ký</label>
                                        <input type="date" class="form-control">
                                    </div>
                                </div>
                                <div class="mb-2">
                                    <label class="form-label fw-bold">Ghi chú</label>
                                    <textarea class="form-control" rows="2" placeholder="Ghi chú thêm..."></textarea>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-primary">Đăng ký tham gia</button>
                                    <button type="reset" class="btn btn-outline-secondary">Hủy</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>








        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
