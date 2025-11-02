<%-- 
    Document   : history_transaction_volunteer
    Created on : Sep 29, 2025, 8:33:31 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang lịch sử thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    <jsp:include page="/layout/header.jsp" />
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />
        <div class="page-content container-fluid mt-5 pt-5 pb-5" style="max-width: 1400px;">
            <h1 class="mb-4 text-center">Lịch sử thanh toán</h1>

            <div class="card shadow-sm border">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover align-middle" style="table-layout: fixed; width: 100%;">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="col" style="width:4%;">STT</th>
                                    <th scope="col" style="width:13%;">Mã QR</th>
                                    <th scope="col" style="width:20%;">Sự kiện</th>
                                    <th scope="col" style="width:9%;">Số tiền</th>
                                    <th scope="col" style="width:11%;">Phương thức</th>
                                    <th scope="col" style="width:15%;">Ngày thanh toán</th>
                                    <th scope="col" style="width:15%; word-wrap: break-word; overflow-wrap: break-word;">Ghi chú</th>
                                    <th scope="col" style="width:8%;">Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>QR-20250901-V5-500K</td>
                                    <td>World Wide Donation</td>
                                    <td>500,000</td>
                                    <td>Momo</td>
                                    <td>2025-09-20 10:30</td>
                                    <td>Ủng hộ người nghèo, nội dung rất dài sẽ xuống dòng mà không kéo cột khác</td>
                                    <td><span class="badge bg-success">Thành công</span></td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>QR987654</td>
                                    <td>Clean the Beach</td>
                                    <td>200,000</td>
                                    <td>Ngân hàng</td>
                                    <td>2025-09-18 14:10</td>
                                    <td>Ủng hộ người đói khổ</td>
                                    <td><span class="badge bg-warning text-dark">Đang xử lý</span></td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>QR555888</td>
                                    <td>Charity Run</td>
                                    <td>300,000</td>
                                    <td>QR Pay</td>
                                    <td>2025-09-10 09:00</td>
                                    <td>Quyên góp từ thiện</td>
                                    <td><span class="badge bg-danger">Bị từ chối</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
