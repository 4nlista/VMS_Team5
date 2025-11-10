<%-- 
    Document   : home_org
    Created on : Sep 7, 2025, 4:14:28 PM
    Author     : Organization
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/home_org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <!-- Main Content -->
            <div class="main-content" style="background-color:#f8f9fa; padding:20px;">
                <!-- Thanh tiêu đề -->
                <div class="topbar d-flex justify-content-between align-items-center mb-4 border-bottom pb-2">
                    <h4 class="fw-bold mb-0 text-primary">
                        <i class="bi bi-speedometer2"></i> Bảng quản trị tổ chức
                    </h4>
                    <div class="notification position-relative me-3">
                        <i class="bi bi-bell-fill fs-4 text-secondary"></i>
                        <span class="badge bg-danger position-absolute top-0 start-100 translate-middle-x rounded-pill px-2 py-1" 
                              style="font-size: 0.75rem;">0</span>
                    </div>
                </div>

                <!-- Hàng thống kê 1 -->
                <div class="row g-3 mb-3">
                    <!-- Tổng sự kiện -->
                    <div class="col-md-3">
                        <div class="bg-primary rounded shadow p-3 text-start text-white position-relative">
                            <h2 class="fw-bold">${totalEvents}</h2>
                            <h5 class="">Tổng số sự kiện</h5>
                            <i class="bi bi-calendar-event"
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15;"></i>
                        </div>
                    </div>

                    <!-- Tổng tình nguyện viên -->
                    <div class="col-md-3">
                        <div class="bg-success rounded shadow p-3 text-start text-white position-relative">
                            <h2 class="fw-bold">${totalVolunteers}</h2>
                            <h5 class="">Tổng tình nguyện viên</h5>
                            <i class="bi bi-people-fill"
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15;"></i>
                        </div>
                    </div>

                    <!-- Tổng giờ tham gia -->
                    <div class="col-md-3">
                        <div class="bg-info rounded shadow p-3 text-start text-white position-relative">
                            <h2 class="fw-bold">${totalVolunteerHours}</h2>
                            <h5 class="">Tổng giờ tham gia</h5>
                            <i class="bi bi-clock-history"
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15;"></i>
                        </div>
                    </div>

                    <!-- Tổng tiền donate -->
                    <div class="col-md-3">
                        <div class="bg-warning rounded shadow p-3 text-start text-dark position-relative">
                            <h2 class="fw-bold">
                                <fmt:formatNumber value="${totalDonations}" type="number" pattern="#,###" /> ₫
                            </h2>
                            <h5 class="">Tổng tiền tài trợ</h5>
                            <i class="bi bi-cash-coin"
                               style="font-size: 5rem; position: absolute; bottom:5px; right: 10px; opacity: 0.15;"></i>
                        </div>
                    </div>
                </div>

                <!-- Biểu đồ tổng tiền -->
                <div class="card border rounded shadow-sm p-3 bg-white mb-4">
                    <h6 class="fw-bold text-primary mb-3">
                        <i class="bi bi-bar-chart-line"></i> Tổng số tiền tài trợ theo tháng (6 tháng gần nhất)
                    </h6>
                    <canvas id="donationChart" height="100"></canvas>
                </div>

                <!-- Hàng biểu đồ phụ -->
                <div class="row g-3">
                    <!-- Top 3 sự kiện donate -->
                    <div class="col-md-6">
                        <div class="card border rounded shadow-sm p-3 bg-white">
                            <h6 class="fw-bold mb-3"><i class="bi bi-trophy"></i> Top 3 sự kiện được tài trợ nhiều nhất</h6>
                            <c:choose>
                                <c:when test="${empty topEvents}">
                                    <div class="text-center text-muted py-4">
                                        <i class="bi bi-inbox fs-1"></i>
                                        <p class="mt-2">Chưa có dữ liệu tài trợ</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
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
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${status.index == 0}">
                                                                <span class="badge bg-warning text-dark">no1.</span>
                                                            </c:when>
                                                            <c:when test="${status.index == 1}">
                                                                <span class="badge bg-secondary">no2.</span>
                                                            </c:when>
                                                            <c:when test="${status.index == 2}">
                                                                <span class="badge bg-danger">no3.</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>${e.title}</td>
                                                    <td class="text-end fw-bold">
                                                        <fmt:formatNumber value="${e.totalDonation}" type="number" pattern="#,###" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- Top 3 nhà tài trợ -->
                    <div class="col-md-6">
                        <div class="card border rounded shadow-sm p-3 bg-white">
                            <h6 class="fw-bold mb-3"><i class="bi bi-award"></i> Top 3 nhà tài trợ</h6>
                            <c:choose>
                                <c:when test="${empty topDonors}">
                                    <div class="text-center text-muted py-4">
                                        <i class="bi bi-inbox fs-1"></i>
                                        <p class="mt-2">Chưa có dữ liệu nhà tài trợ</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <table class="table table-hover table-sm align-middle">
                                        <thead class="table-light">
                                            <tr>
                                                <th>STT</th>
                                                <th>Tên nhà tài trợ</th>
                                                <th class="text-end">Tổng tiền (₫)</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="d" items="${topDonors}" varStatus="status">
                                                <tr>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${status.index == 0}">
                                                                <span class="badge bg-warning text-dark">no1.</span>
                                                            </c:when>
                                                            <c:when test="${status.index == 1}">
                                                                <span class="badge bg-secondary">no2.</span>
                                                            </c:when>
                                                            <c:when test="${status.index == 2}">
                                                                <span class="badge bg-danger">no3.</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>${d.fullName}</td>
                                                    <td class="text-end fw-bold">
                                                        <fmt:formatNumber value="${d.totalDonated}" type="number" pattern="#,###" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <!-- Sự kiện sắp diễn ra -->
                <div class="card border rounded shadow-sm p-3 bg-white mt-4 mb-4">
                    <h6 class="fw-bold mb-3"><i class="bi bi-calendar-week"></i> Sự kiện sắp diễn ra</h6>
                    <c:choose>
                        <c:when test="${empty upcomingEvents}">
                            <div class="text-center text-muted py-4">
                                <i class="bi bi-calendar-x fs-1"></i>
                                <p class="mt-2">Không có sự kiện sắp diễn ra</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-striped table-sm table-bordered table-hover align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên sự kiện</th>
                                        <th>Ngày bắt đầu</th>
                                        <th>Ngày kết thúc</th>
                                        <th>Địa điểm</th>
                                        <th>Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="e" items="${upcomingEvents}" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td>${e.title}</td>
                                            <td><fmt:formatDate value="${e.startDate}" pattern="dd/MM/yyyy"/></td>
                                            <td><fmt:formatDate value="${e.endDate}" pattern="dd/MM/yyyy"/></td>
                                            <td>${e.location}</td>
                                            <td><span class="badge bg-success">Sắp diễn ra</span></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>

        <!-- Chart.js script -->
        <script>
            // Get monthly donations data from JSP
            const monthlyData = [
                <c:forEach var="amount" items="${monthlyDonations}" varStatus="status">
                    ${amount}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            // Generate month labels (last 6 months)
            const monthLabels = [];
            const currentDate = new Date();
            for (let i = 5; i >= 0; i--) {
                const d = new Date(currentDate.getFullYear(), currentDate.getMonth() - i, 1);
                monthLabels.push('Tháng ' + (d.getMonth() + 1));
            }

            // Biểu đồ tổng tiền donate theo tháng
            new Chart(document.getElementById("donationChart"), {
                type: 'bar',
                data: {
                    labels: monthLabels,
                    datasets: [{
                        label: 'Tổng tiền (₫)',
                        data: monthlyData,
                        backgroundColor: ['#0d6efd', '#198754', '#dc3545', '#ffc107', '#0dcaf0', '#6f42c1']
                    }]
                },
                options: {
                    responsive: true, 
                    plugins: {
                        legend: {display: false},
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return 'Tổng: ' + context.parsed.y.toLocaleString('vi-VN') + ' ₫';
                                }
                            }
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function(value) {
                                    return value.toLocaleString('vi-VN');
                                }
                            }
                        }
                    }
                }
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>