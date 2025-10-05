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
        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center">Lịch sử thanh toán</h1>

            <div class="card shadow-sm border">
                <div class="card-body">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Mã giao dịch</th>
                                <th scope="col">Sự kiện</th>
                                <th scope="col">Số tiền (VNĐ)</th>
                                <th scope="col">Phương thức</th>
                                <th scope="col">Ngày thanh toán</th>
                                <th scope="col">Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Ví dụ dữ liệu mẫu -->
                            <tr>
                                <td>1</td>
                                <td>QR123456</td>
                                <td>World Wide Donation</td>
                                <td>500,000</td>
                                <td>Momo</td>
                                <td>2025-09-20 10:30</td>
                                <td><span class="badge bg-success">Thành công</span></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>QR987654</td>
                                <td>Clean the Beach</td>
                                <td>200,000</td>
                                <td>Ngân hàng</td>
                                <td>2025-09-18 14:10</td>
                                <td><span class="badge bg-warning text-dark">Đang xử lý</span></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>QR555888</td>
                                <td>Charity Run</td>
                                <td>300,000</td>
                                <td>QR Pay</td>
                                <td>2025-09-10 09:00</td>
                                <td><span class="badge bg-danger">Đã hủy</span></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
