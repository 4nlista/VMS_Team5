<%-- 
    Document   : payment_volunteer
    Created on : Sep 28, 2025, 8:25:21 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ khách hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />
        <div class="page-content container mt-5 pt-5">
            <h1 class="mb-4 text-center">Trang Thanh Toán</h1>

            <div class="container py-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card shadow p-4">
                            <h5 class="mb-3">Thông tin người thanh toán</h5>
                            <form action="PaymentServlet" method="post">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label class="form-label">Họ và tên</label>
                                        <input type="text" name="fullname" class="form-control" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Số điện thoại</label>
                                        <input type="text" name="phone" class="form-control" required>
                                    </div>
                                </div>

                                <hr class="my-4">

                                <h5 class="mb-3">Phương thức thanh toán</h5>
                                <div class="mb-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="paymentMethod" value="credit" checked>
                                        <label class="form-check-label">
                                            Thẻ tín dụng / Ghi nợ
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="paymentMethod" value="momo">
                                        <label class="form-check-label">
                                            Ví MoMo
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="paymentMethod" value="bank">
                                        <label class="form-check-label">
                                            Chuyển khoản ngân hàng
                                        </label>
                                    </div>
                                </div>

                                <div class="row g-3 mt-3">
                                    <div class="col-md-6">
                                        <label class="form-label">Số thẻ</label>
                                        <input type="text" class="form-control" name="cardNumber" placeholder="xxxx-xxxx-xxxx-xxxx">
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label">Ngày hết hạn</label>
                                        <input type="text" class="form-control" name="expiryDate" placeholder="MM/YY">
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label">CVV</label>
                                        <input type="password" class="form-control" name="cvv" maxlength="3">
                                    </div>
                                </div>

                                <hr class="my-4">

                                <h5 class="mb-3">Thông tin giao dịch</h5>
                                <div class="mb-3">
                                    <label class="form-label">Số tiền ủng hộ (VNĐ)</label>
                                    <input type="number" class="form-control" name="amount" min="10000" required>
                                </div>

                                <h5 class="mb-3">Ghi chú</h5>
                                <div class="mb-3">
                                    <input type="text" class="form-control" name="note" required>
                                </div>

                                <div class="text-end">
                                    <button type="submit" class="btn btn-success btn-lg mr-2">Xác nhận thanh toán</button>
                                    <a href="<%= request.getContextPath() %>/VolunteerHomeServlet" class="btn btn-secondary btn-lg">Hủy</a>
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
