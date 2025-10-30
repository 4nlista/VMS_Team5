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
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <h3 class="fw-bold mb-4">Xem chi tiết 1 sự kiện</h3>


                    <div class="card shadow-sm border-0 p-4">
        <div class="row g-4">
            <!-- Cột ảnh -->
            <div class="col-md-4">
                <div class="border rounded p-2 text-center bg-light">
                    <img src="https://viet-power.vn/wp-content/uploads/2025/06/backdrop-su-kien-3.jpg" 
                         alt="Event Image" 
                         class="img-fluid rounded mb-2 shadow-sm">
                    <div class="fw-semibold text-secondary">Hình ảnh sự kiện</div>
                </div>
            </div>

            <!-- Cột thông tin -->
            <div class="col-md-8">
                <table class="table table-bordered align-middle">
                    <tbody class="fw-semibold">
                        <tr>
                            <th style="width: 30%" class="bg-light">Mã sự kiện</th>
                            <td>EVT001</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Tiêu đề</th>
                            <td class="fs-5 fw-bold text-dark">Lớp học tình thương</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Loại sự kiện</th>
                            <td>Giáo dục</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Người tổ chức</th>
                            <td>Org1</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Ngày bắt đầu</th>
                            <td>01/11/2025</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Ngày kết thúc</th>
                            <td>05/11/2025</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Địa điểm</th>
                            <td>Trung tâm Hội nghị Thành phố</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Số lượng tình nguyện viên</th>
                            <td>150</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Tổng tiền tài trợ</th>
                            <td><span class="badge bg-info text-dark fs-6">50,000,000 VND</span></td>
                        </tr>
                        <tr>
                            <th class="bg-light">Trạng thái</th>
                            <td><span class="badge bg-warning text-dark">Đang diễn ra</span></td>
                        </tr>
                        <tr>
                            <th class="bg-light">Chế độ</th>
                            <td><span class="badge bg-secondary">Công khai</span></td>
                        </tr>
                        <tr>
                            <th class="bg-light">Ngày tạo</th>
                            <td>25/10/2025</td>
                        </tr>
                        <tr>
                            <th class="bg-light">Mô tả sự kiện</th>
                            <td>Sự kiện quy mô toàn cầu hỗ trợ các khu vực gặp khó khăn về kinh tế và y tế.</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/organization/events_org.jsp" class="btn btn-outline-primary">
                <i class="bi bi-arrow-left"></i> Quay lại danh sách
            </a>
        </div>


                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
