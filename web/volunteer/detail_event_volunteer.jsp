<%-- 
    Document   : detail_event_volunteer
    Created on : Sep 29, 2025, 9:22:06 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chi tiết lịch sử sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-5 text-center text-primary fw-bold">Chi tiết sự kiện</h1>

            <div class="row justify-content-center">
                <div class="col-md-10 col-lg-8">
                    <div class="card shadow-lg border-0 rounded-3">
                        <!-- Header -->
                        <div class="card-header bg-gradient bg-primary text-white text-center fs-5 fw-bold">
                            World Wide Donation
                        </div>

                        <!-- Body -->
                        <div class="card-body p-4">
                            <table class="table table-bordered align-middle">
                                <tbody>
                                    <tr>
                                        <th class="w-40 bg-light">Số giờ tích lũy</th>
                                        <td>3 giờ</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Người tổ chức</th>
                                        <td>Org1</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Thời gian</th>
                                        <td>Sep, 10, 2018 10:30AM - 03:30PM</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Địa điểm</th>
                                        <td>Venue Main Campus</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Số lượng tình nguyện viên cần</th>
                                        <td>50</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Tình trạng sự kiện</th>
                                        <td><span class="badge bg-danger">Đã kết thúc</span></td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Mô tả sự kiện</th>
                                        <td>Sự kiện gây quỹ quy mô toàn cầu với mục đích hỗ trợ các khu vực gặp khó khăn...</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Số tiền bạn đã ủng hộ</th>
                                        <td><span class="badge bg-info text-dark fs-6 px-3 py-2">2,000,000 VND</span></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <!-- Footer với nút quay lại -->
                        <div class="card-footer text-center bg-white border-0 pb-4">
                            <a href="<%= request.getContextPath() %>/EventListServlet" class="btn btn-primary px-4">
                                ← Quay lại
                            </a>
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
