<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
                    <!-- Tổng tài khoản -->
                    <div class="col-md-4">
                        <div class="bg-warning rounded shadow p-3 text-start text-dark position-relative">
                            <h2 class="fw-bold">${totalAccounts}</h2>
                            <h5 class="">Tổng số tài khoản</h5>
                            <i class="bi bi-person-plus-fill"
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15; color: black"></i>
                        </div>
                    </div>

                    <!-- Tổng sự kiện -->
                    <div class="col-md-4">
                        <div class="bg-primary rounded shadow p-3 text-start text-white position-relative">
                            <h2 class="fw-bold">240</h2>
                            <h5 class="">Tổng số sự kiện</h5>
                            <i class="bi bi-house-door"
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15; color: black"></i>
                        </div>
                    </div>

                    <!-- Tổng tiền donate -->
                    <div class="col-md-4">
                        <div class="bg-primary rounded shadow p-3 text-start text-white position-relative">
                            <h2 class="fw-bold">
                                <fmt:formatNumber value="${totalMoneyDonate}" type="number" pattern="#,###" /> VND
                            </h2>
                            <h5 class="">Tổng số tiền tài trợ</h5>
                            <i class="bi bi-cash"
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15; color: black"></i>
                        </div>
                    </div>
                </div>

                <!-- Hàng thống kê 2 -->
                <div class="row g-3 mb-4">
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

                <!-- Biểu đồ tổng tiền -->
                <div class="card border rounded shadow-sm p-3 bg-white mb-4">
                    <h6 class="fw-bold text-primary mb-3">
                        <i class="bi bi-bar-chart-line"></i> Tổng số tiền theo tháng
                    </h6>
                    <canvas id="donationChart" height="100"></canvas>
                </div>

                <!-- Hàng biểu đồ phụ -->
                <div class="row g-3">
                    <!-- Biểu đồ phân loại tài khoản -->
                    <div class="col-md-6">
                        <div class="card border rounded shadow-sm p-3 bg-white">
                            <h6 class="fw-bold mb-3"><i class="bi bi-pie-chart"></i> Phân loại tài khoản</h6>
                            <canvas id="accountChart" height="200"></canvas>
                        </div>
                    </div>

                    <!-- Top 5 sự kiện donate -->
                    <div class="col-md-6">
                        <div class="card border rounded shadow-sm p-3 bg-white">
                            <h6 class="fw-bold mb-3"><i class="bi bi-trophy"></i> Top 3 sự kiện được tài trợ nhiều nhất</h6>
                            <table class="table table-hover table-sm align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên sự kiện</th>
                                        <th class="text-end">Tổng tiền (₫)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="e" items="${topEvents}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td>${e.title}</td>
                                            <td class="text-end"><fmt:formatNumber value="${e.totalDonation}" type="number" pattern="#,###" /></td>
                                            
                                            
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>


                    </div>
                </div>

                <!-- Sự kiện sắp diễn ra -->
                <div class="card border rounded shadow-sm p-3 bg-white mt-4 mb-4">
                    <h6 class="fw-bold mb-3"><i class="bi bi-calendar-week"></i> Sự kiện sắp diễn ra</h6>
                    <table class="table table-striped table-sm table-bordered table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>STT</th>
                                <th>Tên sự kiện</th>
                                <th>Ngày bắt đầu</th>
                                <th>Địa điểm</th>
                                <th>Tổ chức</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="e" items="${eventsComing}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>${e.title}</td>
                                    <td><fmt:formatDate value="${e.startDate}" pattern="yyyy-MM-dd"/></td>
                                    <td>${e.location}</td>
                                    <td>${e.organizationName}</td>
                                    <td><span class="badge bg-success">Sắp diễn ra</span></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>


            </div>
        </div>



        <!-- Chart.js script -->
        <script>
            // Biểu đồ tổng tiền donate theo tháng
            new Chart(document.getElementById("donationChart"), {
                type: 'bar',
                data: {
                    labels: ["Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9"],
                    datasets: [{
                            label: 'Tổng tiền (₫)',
                            data: [50000000, 70000000, 90000000, 60000000, 100000000],
                            backgroundColor: ['#0d6efd', '#198754', '#dc3545', '#ffc107', '#0dcaf0']
                        }]
                },
                options: {responsive: true, plugins: {legend: {display: false}}}
            });

            // Biểu đồ phân loại tài khoản
            new Chart(document.getElementById("accountChart"), {
                type: 'pie',
                data: {
                    labels: ["Admin", "Tổ chức", "Tình nguyện viên"],
                    
                    
                    
                    datasets: [{
                            data: [5, 20, 120],
                            backgroundColor: ['#0d6efd', '#20c997', '#ffc107']
                        }]
                }
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
