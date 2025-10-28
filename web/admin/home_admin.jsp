<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/home_admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content -->
            <div class="main-content" style="background-color:#f8f9fa; padding:20px;">

                <!-- Thanh tiêu đề -->
                <div class="topbar d-flex justify-content-between align-items-center mb-4 border-bottom pb-2">
                    <h4 class="fw-bold mb-0 text-primary">
                        <i class="bi bi-speedometer2"></i> Bảng quản trị
                    </h4>
                    <div class="notification position-relative me-3">
                        <i class="bi bi-bell-fill fs-4 text-secondary"></i>
                        <span class="badge bg-danger position-absolute top-0 start-100 translate-middle-x rounded-pill px-2 py-1" 
                              style="font-size: 0.75rem;">10</span>
                    </div>

                </div>

                <!-- Hàng thống kê 1 -->
                <div class="row g-3 mb-3">
                    <!-- Box 1 -->
                    <div class="col-md-4">
                        <div class="bg-warning rounded shadow p-3 text-start text-dark position-relative">
                            <h2 class="fw-bold">${totalAccounts}</h2>
                            <h5 class="">Tổng số tài khoản</h5>
                            <i class="bi bi-person-plus-fill" 
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15; pointer-events: none; color: black"></i>
                        </div>
                    </div>


                    <div class="col-md-4">
                        <div class="bg-primary rounded shadow p-3 text-start text-white position-relative">
                            <h2 class="fw-bold">240</h2>
                            <h5 class="">Tổng số sự kiện</h5>
                            <i class="bi bi-house-door" 
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15; pointer-events: none; color: black"></i>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="bg-primary rounded shadow p-3 text-start text-white position-relative">
                            <h2 class="fw-bold">150</h2>
                            <h5 class="">Tổng số tiền tài trợ</h5>
                            <i class="bi bi-calendar-event" 
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15; pointer-events: none; color: black"></i>
                        </div>
                    </div>


                </div>

                <!-- Hàng thống kê 2 -->
                <div class="row g-3 mb-3">
                    <div class="col-md-4">
                        <div class="p-3 border rounded bg-white text-center shadow-sm">
                            <h2 class="text-primary fw-bold mb-1">125.000.000</h2>
                            <p class="text-muted mb-0">Tổng số tiền từ thiện</p>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="p-3 border rounded bg-white text-center shadow-sm">
                            <h2 class="text-secondary fw-bold mb-1">200</h2>
                            <p class="text-muted mb-0">Tổng tình nguyện viên</p>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="p-3 border rounded bg-white text-center shadow-sm">
                            <h2 class="text-info fw-bold mb-1">450</h2>
                            <p class="text-muted mb-0">Tổng giờ tham gia</p>
                        </div>
                    </div>
                </div>

                <!-- Biểu đồ -->
                <div class="card border rounded shadow-sm p-3 bg-white">
                    <h6 class="fw-bold text-primary mb-3">
                        <i class="bi bi-bar-chart-line"></i> Tổng số tiền theo tháng
                    </h6>
                    <div class="d-flex justify-content-between align-items-end" style="height:200px;">
                        <div class="bg-primary text-white text-center" style="width:18%;height:50%;">Tháng 5</div>
                        <div class="bg-success text-white text-center" style="width:18%;height:70%;">Tháng 6</div>
                        <div class="bg-danger text-white text-center" style="width:18%;height:90%;">Tháng 7</div>
                        <div class="bg-warning text-dark text-center" style="width:18%;height:60%;">Tháng 8</div>
                        <div class="bg-info text-dark text-center" style="width:18%;height:100%;">Tháng 9</div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
