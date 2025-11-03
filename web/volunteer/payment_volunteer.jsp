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
        <title>Trang thanh toán donate</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5">
            <h1 class="mb-4 text-center">Trang Thanh Toán Donate</h1>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <form action="<%= request.getContextPath() %>/VolunteerPaymentServlet" method="post">
                <!-- Hidden field: eventId -->
                <input type="hidden" name="eventId" value="${event.id}">

                <div class="container py-5">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card shadow p-4">
                                <h5 class="mb-3">Thông tin thanh toán</h5>

                                <div class="row g-3">
                                    <!-- Tên sự kiện (readonly) -->
                                    <div class="col-md-6">
                                        <label class="form-label">Tên sự kiện</label>
                                        <input type="text" class="form-control" value="${event.title}" readonly>
                                    </div>

                                    <!-- Họ và tên volunteer (readonly) -->
                                    <div class="col-md-6">
                                        <label class="form-label">Họ và tên</label>
                                        <input type="text" class="form-control" value="${volunteerName}" readonly>
                                    </div>

                                    <!-- Tên tổ chức (readonly) -->
                                    <div class="col-md-12">
                                        <label class="form-label">Tổ chức</label>
                                        <input type="text" class="form-control" value="${event.organizationName}" readonly>
                                    </div>
                                </div>

                                <hr class="my-4">

                                <div class="row">
                                    <!-- Phương thức thanh toán (cứng: QR Code) -->
                                    <div class="col-md-6">
                                        <h5 class="mb-3">Phương thức thanh toán</h5>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="paymentMethod" value="QR Code" checked disabled>
                                            <label class="form-check-label">
                                                <i class="bi bi-qr-code"></i> Thanh toán bằng QR Code
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Số tiền donate -->
                                    <div class="col-md-6">
                                        <h5 class="mb-3">Thông tin giao dịch</h5>
                                        <label class="form-label">Số tiền ủng hộ (VNĐ) <span class="text-danger">*</span></label>
                                        <input type="number" class="form-control" name="amount" min="10000" step="1000" placeholder="Ví dụ: 100000" required>
                                        <small class="text-muted">Tối thiểu 10,000 VNĐ</small>
                                    </div>
                                </div>

                                <!-- Ghi chú -->
                                <div class="mb-3 mt-3">
                                    <label class="form-label">Ghi chú (tùy chọn)</label>
                                    <textarea class="form-control" name="note" rows="3" placeholder="Nhập lời nhắn của bạn..."></textarea>
                                </div>

                                <hr class="my-4">

                                <!-- Hiển thị QR Code -->
                                <div class="text-center mb-4">
                                    <h5 class="mb-3">Mã QR Code sẽ được tạo sau khi xác nhận</h5>
                                    <div id="qr-placeholder" class="border rounded p-4 bg-light">
                                        <i class="bi bi-qr-code"></i>
                                        <p class="text-muted mt-2">Nhấn "Xác nhận thanh toán" để tạo mã QR</p>
                                    </div>
                                </div>

                                <!-- Nút action -->
                                <div class="d-flex justify-content-between">
                                    <!-- Nút Quay lại -->
                                    <a href="<%= request.getContextPath() %>/GuessEventServlet" class="btn btn-secondary btn-lg">
                                        <i class="bi bi-arrow-left"></i> Quay lại
                                    </a>

                                    <!-- Nút Xác nhận -->
                                    <button type="submit" class="btn btn-success btn-lg">
                                        <i class="bi bi-check-circle"></i> Xác nhận thanh toán
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
