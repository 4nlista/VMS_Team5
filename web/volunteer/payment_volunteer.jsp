<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thanh toán quyên góp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    <jsp:include page="/layout/header.jsp" />
</head>
<body>
<jsp:include page="/layout/navbar.jsp" />

<div class="page-content container mt-5 pt-5">
    <h1 class="mb-4 text-center">Trang Thanh Toán</h1>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow p-4">

                    <!-- Thông báo success/error -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <h5 class="mb-3">Thông tin người quyên góp</h5>
                    <form action="DonateServlet" method="post">
                        <!-- hidden field: event_id -->
                        <input type="hidden" name="eventId" value="${param.eventId}">

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label">Họ và tên</label>
                                <input type="text" name="fullname" class="form-control" 
                                       value="${volunteerName}" readonly>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" 
                                       value="${volunteerEmail}" readonly>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Số điện thoại</label>
                                <input type="text" name="phone" class="form-control" 
                                       value="${volunteerPhone}" readonly>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Địa chỉ</label>
                                <input type="text" name="address" class="form-control" 
                                       value="${volunteerAddress}" readonly>
                            </div>
                        </div>

                        <hr class="my-4">
                        <h5 class="mb-3">Phương thức thanh toán</h5>
                        <div class="mb-3">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod" value="QR" checked>
                                <label class="form-check-label">QR / Ví điện tử</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod" value="momo">
                                <label class="form-check-label">Ví MoMo</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod" value="bank">
                                <label class="form-check-label">Chuyển khoản ngân hàng</label>
                            </div>
                        </div>

                        <hr class="my-4">
                        <h5 class="mb-3">Thông tin giao dịch</h5>
                        <div class="mb-3">
                            <label class="form-label">Số tiền ủng hộ (VNĐ)</label>
                            <input type="number" class="form-control" name="amount" min="0" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Ghi chú (tùy chọn)</label>
                            <textarea class="form-control" name="note" rows="2"></textarea>
                        </div>

                        <div class="text-end">
                            <button type="submit" class="btn btn-success btn-lg">Xác nhận thanh toán</button>
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
