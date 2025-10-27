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
            <div class="main-content">
                <div class="topbar d-flex justify-content-between align-items-center mb-4">
                    <h1>Quản trị</h1>

                    <!-- Chuông thông báo -->
                    <div class="notification position-relative">
                        <i class="bi bi-bell-fill"></i>
                        <span class="badge">10</span> <!-- số thông báo -->
                    </div>
                </div>

                <!-- 6 khối thống kê -->
                <div class="row g-4 mb-4">
                    <div class="col-md-4"><div class="stat-card bg-warning text-black"><h2>120</h2><p>Tổng tài khoản</p></div></div>
                    <div class="col-md-4"><div class="stat-card bg-success text-white"><h2>80</h2><p>Tổng người dùng</p></div></div>
                    <div class="col-md-4"><div class="stat-card bg-danger text-white"><h2>35</h2><p>Tổng sự kiện</p></div></div>
                </div>

                <div class="row g-4 mb-4">
                    <div class="col-md-4"><div class="stat-card bg-primary text-white"><h2>125.000.000</h2><p>Tổng số tiền từ thiện</p></div></div>
                    <div class="col-md-4"><div class="stat-card bg-secondary text-white"><h2>200</h2><p>Tổng tình nguyện viên</p></div></div>
                    <div class="col-md-4"><div class="stat-card bg-info text-black"><h2>450</h2><p>Tổng giờ tham gia</p></div></div>
                </div>
               
                <!-- Biểu đồ cột tĩnh -->
                <div class="chart-container card p-3 shadow-sm">
                    <h5 class="mb-3"  >Tổng số tiền theo tháng</h5>
                    <div class="bar-chart">
                        <div class="bar" style="height:50%">Tháng 5</div>
                        <div class="bar" style="height:70%">Tháng 6</div>
                        <div class="bar" style="height:90%">Tháng 7</div>
                        <div class="bar" style="height:60%">Tháng 8</div>
                        <div class="bar" style="height:100%">Tháng 9</div>
                    </div>
                </div>          
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
