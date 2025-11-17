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
        <style>
            .form-control + div small {
                display: inline-block;
                margin-right: 10px;
            }
            .form-control + div small.text-danger {
                margin-left: 10px;
            }
        </style>
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

            <form action="<%= request.getContextPath() %>/volunteer-payment-donation" method="post">
                <!-- Hidden fields -->
                <input type="hidden" name="eventId" value="${event.id}">

                <div class="container py-5">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card shadow p-4">
                                <h5 class="mb-3">Thông tin thanh toán</h5>

                                <div class="row g-3">
                                    <!-- Tên sự kiện (readonly) -->
                                    <div class="col-md-12">
                                        <label class="form-label">Tên sự kiện</label>
                                        <input type="text" class="form-control" value="${event.title}" readonly>
                                    </div>

                                    <!-- Tên tổ chức (readonly) -->
                                    <div class="col-md-12">
                                        <label class="form-label">Tổ chức</label>
                                        <input type="text" class="form-control" value="${event.organizationName}" readonly>
                                    </div>
                                </div>

                                <hr class="my-4">

                                <!-- Thông tin volunteer (readonly - lấy từ User và Account) -->
                                <div class="mb-3">
                                    <h5 class="mb-3">Thông tin của bạn</h5>
                                    <div class="alert alert-info">
                                        <i class="bi bi-info-circle"></i> Thông tin dưới đây được lấy từ tài khoản của bạn. 
                                        Nếu cần cập nhật, vui lòng chỉnh sửa trong phần hồ sơ cá nhân.
                                    </div>
                                </div>

                                <div class="row g-3">
                                    <!-- Họ và tên volunteer (readonly) -->
                                    <div class="col-md-6">
                                        <label class="form-label">Họ và tên</label>
                                        <input type="text" class="form-control" value="${volunteerName}" readonly>
                                    </div>

                                    <!-- Email volunteer (readonly) -->
                                    <div class="col-md-6">
                                        <label class="form-label">Email</label>
                                        <input type="email" class="form-control" value="${volunteerEmail != null ? volunteerEmail : 'Chưa cập nhật'}" readonly>
                                    </div>

                                    <!-- Số điện thoại volunteer (readonly) -->
                                    <div class="col-md-6">
                                        <label class="form-label">Số điện thoại</label>
                                        <input type="text" class="form-control" value="${volunteerPhone != null ? volunteerPhone : 'Chưa cập nhật'}" readonly>
                                    </div>
                                </div>

                                <hr class="my-4">

                                <div class="row">
                                    <div class="col-md-6">
                                        <h5 class="mb-3">Phương thức thanh toán</h5>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="paymentMethod" value="VNPay" checked readonly>
                                            <label class="form-check-label">
                                                <i class="bi bi-credit-card"></i> Thanh toán
                                            </label>
                                        </div>
                                        <small class="text-muted">Hỗ trợ thanh toán qua thẻ ATM, thẻ tín dụng, ví điện tử</small>
                                    </div>

                                    <!-- Số tiền donate -->
                                    <div class="col-md-6">
                                        <h5 class="mb-3">Thông tin giao dịch</h5>
                                        <label class="form-label">Số tiền ủng hộ (VNĐ) <span class="text-danger">*</span></label>
                                        <input type="number" class="form-control" name="amount" id="amount" min="10000" step="1000" placeholder="Ví dụ: 100000" required>
                                        <div>
                                            <small class="text-muted" id="amount-note">Tối thiểu 10,000 VNĐ</small>
                                            <small class="text-danger fst-italic" id="amount-error"></small>
                                        </div>
                                    </div>
                                </div>

                                <div class="mb-3 mt-3">
                                    <label class="form-label">Ghi chú (tùy chọn)</label>
                                    <textarea class="form-control" name="note" rows="3" placeholder="Nhập lời nhắn của bạn..."></textarea>
                                </div>

                                <div class="d-flex justify-content-between">
                                    <a href="<%= request.getContextPath() %>/VolunteerExploreEventServlet" class="btn btn-secondary btn-lg">
                                        <i class="bi bi-arrow-left"></i> Quay lại
                                    </a>
                                    <button type="submit" class="btn btn-success btn-lg" id="submitBtn">
                                        <i class="bi bi-credit-card"></i> Thanh toán
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
        <script>
            // Get form elements
            const form = document.querySelector('form');
            const amountInput = document.getElementById('amount');
            const submitBtn = document.getElementById('submitBtn');
            const amountError = document.getElementById('amount-error');
            const amountNote = document.getElementById('amount-note');

            // Track if field has been touched
            let touchedAmount = false;

            // Validate amount
            function validateAmount(showError = true) {
                const amount = parseFloat(amountInput.value);
                
                if (!amountInput.value || amountInput.value.trim() === '') {
                    if (showError && touchedAmount) {
                        amountInput.classList.add('is-invalid');
                        if (amountError) amountError.textContent = 'Trường này là bắt buộc';
                        if (amountNote) amountNote.style.display = 'none';
                    }
                    return false;
                }
                
                if (isNaN(amount) || amount <= 0) {
                    if (showError && touchedAmount) {
                        amountInput.classList.add('is-invalid');
                        if (amountError) amountError.textContent = 'Số tiền phải lớn hơn 0';
                        if (amountNote) amountNote.style.display = 'none';
                    }
                    return false;
                }
                
                if (amount < 10000) {
                    if (showError && touchedAmount) {
                        amountInput.classList.add('is-invalid');
                        if (amountError) amountError.textContent = 'Số tiền tối thiểu là 10,000 VND';
                        if (amountNote) amountNote.style.display = 'none';
                    }
                    return false;
                }
                
                amountInput.classList.remove('is-invalid');
                if (amountError) amountError.textContent = '';
                if (amountNote) amountNote.style.display = 'inline-block';
                return true;
            }

            // Real-time validation for amount
            amountInput.addEventListener('input', () => {
                touchedAmount = true;
                validateAmount(true);
            });

            amountInput.addEventListener('blur', () => {
                touchedAmount = true;
                validateAmount(true);
            });

            // Validate on form submit
            form.addEventListener('submit', (e) => {
                touchedAmount = true;
                if (!validateAmount(true)) {
                    e.preventDefault();
                    return false;
                }
            });

            // Initial validation (no error shown on load)
            validateAmount(false);
        </script>
    </body>
</html>
