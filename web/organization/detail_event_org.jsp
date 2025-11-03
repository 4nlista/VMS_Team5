<%-- 
    Document   : detail_event_org
    Created on : Oct 30, 2025, 9:45:25 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
        <style>
            body {
                background-color: #f8f9fa;
            }
            .event-image-container {
                border: 2px solid #e9ecef;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            }
            .event-image-container img {
                width: 100%;
                height: auto;
                display: block;
            }
            .info-table th {
                background-color: #f8f9fa;
                font-weight: 600;
                color: #495057;
            }
            .section-title {
                border-left: 4px solid #0d6efd;
                padding-left: 15px;
                margin-bottom: 20px;
            }
            .donor-table {
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            }
            .donor-table thead {
                background-color: #0d6efd;
                color: white;
            }
            .donor-table tbody tr:hover {
                background-color: #f8f9fa;
            }
            .amount-highlight {
                color: #198754;
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <!-- Page Header -->
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h3 class="fw-bold mb-0">
                            <i class="bi bi-eye text-primary me-2"></i>Xem chi tiết sự kiện
                        </h3>
                        <!-- Thông báo success/error -->
                        <c:if test="${not empty sessionScope.successMessage}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${sessionScope.successMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                            <c:remove var="successMessage" scope="session"/>
                        </c:if>

                        <c:if test="${not empty sessionScope.errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${sessionScope.errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                            <c:remove var="errorMessage" scope="session"/>
                        </c:if>
                        <a href="<%= request.getContextPath() %>/OrganizationListServlet" class="btn btn-primary">
                            <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách
                        </a>
                    </div>
                    <!-- Form Cập nhật Sự kiện -->
                    <form action="<%= request.getContextPath() %>/OrganizationDetailEventServlet" method="post">
                        <input type="hidden" name="eventId" value="${event.id}">

                        <div class="card shadow-sm border mb-4">
                            <div class="card-header bg-primary text-white">
                                <h5 class="mb-0">Thông tin sự kiện</h5>
                            </div>
                            <div class="card-body p-4">
                                <!-- Tiêu đề sự kiện -->
                                <div class="mb-4">
                                    <label class="form-label text-muted fw-semibold">Tiêu đề sự kiện</label>
                                    <input type="text" name="title" class="form-control form-control-lg" 
                                           value="${event.title}" required>
                                </div>

                                <div class="row">
                                    <!-- Cột trái -->
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label text-muted">Mã sự kiện</label>
                                            <input type="text" class="form-control" value="${event.id}" disabled>
                                        </div>

                                        <div class="mb-3">
                                            <select name="categoryId" class="form-select" required>
                                                <option value="">-- Chọn loại sự kiện --</option>
                                                <c:forEach var="category" items="${categories}">
                                                    <option value="${category.categoryId}" 
                                                            ${category.categoryId == event.categoryId ? 'selected' : ''}>
                                                        ${category.categoryName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Người tổ chức</label>
                                            <input type="text" class="form-control" value="${event.organizationName}" disabled>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Địa điểm</label>
                                            <input type="text" name="location" class="form-control" 
                                                   value="${event.location}" required>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Số lượng tình nguyện viên cần</label>
                                            <input type="number" name="neededVolunteers" class="form-control" 
                                                   value="${event.neededVolunteers}" min="1" required>
                                        </div>
                                    </div>

                                    <!-- Cột phải -->
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label text-muted">Ngày bắt đầu</label>
                                            <input type="date" name="startDate" class="form-control" 
                                                   value="${event.startDate}" required>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Ngày kết thúc</label>
                                            <input type="date" name="endDate" class="form-control" 
                                                   value="${event.endDate}" required>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Trạng thái</label>
                                            <select name="status" class="form-select" required>
                                                <option value="active" ${event.status == 'active' ? 'selected' : ''}>Đang hoạt động</option>
                                                <option value="inactive" ${event.status == 'inactive' ? 'selected' : ''}>Tạm dừng</option>
                                                <option value="closed" ${event.status == 'closed' ? 'selected' : ''}>Đã đóng</option>
                                            </select>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Chế độ</label>
                                            <select name="visibility" class="form-select" required>
                                                <option value="public" ${event.visibility == 'public' ? 'selected' : ''}>Công khai</option>
                                                <option value="private" ${event.visibility == 'private' ? 'selected' : ''}>Riêng tư</option>
                                            </select>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Tổng tiền tài trợ</label>
                                            <input type="text" class="form-control fw-bold text-success" 
                                                   value="${event.totalDonation} VND" disabled>
                                        </div>
                                    </div>
                                </div>

                                <!-- Mô tả -->
                                <div class="mb-4">
                                    <label class="form-label text-muted">Mô tả sự kiện</label>
                                    <textarea name="description" class="form-control" rows="4" required>${event.description}</textarea>
                                </div>

                                <!-- Action Buttons -->
                                <div class="text-end">
                                    <a href="<%= request.getContextPath() %>/OrganizationListServlet" 
                                       class="btn btn-secondary me-2">
                                        <i></i>Hủy
                                    </a>
                                    <button type="submit" name="action" value="update" class="btn btn-primary me-2">
                                        <i></i>Cập nhật
                                    </button>
                                    <button type="submit" name="action" value="delete" class="btn btn-danger"
                                            onclick="return confirm('Bạn có chắc chắn muốn xóa sự kiện này?')">
                                        <i></i>Xóa
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>


                    <!-- Danh sách đơn tài trợ cho sự kiện này -->
                    <div class="card shadow-sm border-0">
                        <div class="card-body p-4">
                            <h5 class="section-title">
                                Đơn tài trợ
                            </h5>
                            <div class="table-responsive">
                                <table class="table table-hover donor-table mb-0">
                                    <thead>
                                        <tr>
                                            <th style="width: 7%;">STT</th>
                                            <th style="width: 15%;">Họ và Tên</th>
                                            <th style="width: 15%;">Số tiền</th>
                                            <th style="width: 19%;">Thời gian</th>                             
                                            <th style="width: 13%;">Phương thức</th>
                                            <th style="width: 15%;">Trạng thái</th>
                                            <th style="width: 15%;">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <!-- Thay thế phần <tbody> cũ bằng code này -->
                                    <tbody>
                                        <c:forEach var="donation" items="${donations}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td>${donation.volunteerFullName}</td>
                                                <td class="amount-highlight">
                                                    <fmt:formatNumber value="${donation.amount}" type="number" groupingUsed="true"/> VND
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${donation.donateDate}" pattern="dd/MM/yyyy HH:mm"/>
                                                </td>
                                                <td>
                                                    <span class="badge bg-info">
                                                        <i class="bi bi-qr-code me-1"></i>${donation.paymentMethod}
                                                    </span>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${donation.status == 'pending'}">
                                                            <span class="badge bg-warning text-dark">Chưa xử lý</span>
                                                        </c:when>
                                                        <c:when test="${donation.status == 'success'}">
                                                            <span class="badge bg-success">Thành công</span>
                                                        </c:when>
                                                        <c:when test="${donation.status == 'cancelled'}">
                                                            <span class="badge bg-danger">Từ chối</span>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${donation.status == 'pending'}">
                                                            <form action="<%= request.getContextPath() %>/ProcessDonationServlet" 
                                                                  method="post" style="display:inline;">
                                                                <input type="hidden" name="donationId" value="${donation.id}">
                                                                <input type="hidden" name="eventId" value="${event.id}">
                                                                <button type="submit" name="action" value="approve" 
                                                                        class="btn btn-primary btn-sm">Chấp nhận</button>
                                                                <button type="submit" name="action" value="reject" 
                                                                        class="btn btn-danger btn-sm"
                                                                        onclick="return confirm('Bạn có chắc muốn từ chối đơn này?')">Từ chối</button>
                                                            </form>
                                                        </c:when>
                                                        <c:when test="${donation.status == 'success'}">
                                                            <span class="badge bg-success">Đã xử lí</span>
                                                        </c:when>
                                                        <c:when test="${donation.status == 'cancelled'}">
                                                            <span class="badge bg-danger">Đã từ chối</span>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        <c:if test="${empty donations}">
                                            <tr>
                                                <td colspan="7" class="text-center text-muted">Chưa có đơn donate nào</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                                <!-- PHÂN TRANG -->
                                <c:if test="${totalPages > 1}">
                                    <nav aria-label="Donation pagination">
                                        <ul class="pagination justify-content-center">
                                            <!-- Nút Previous -->
                                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                <a class="page-link" 
                                                   href="${pageContext.request.contextPath}/OrganizationDetailEventServlet?eventId=${event.id}&page=${currentPage - 1}">
                                                    Trước
                                                </a>
                                            </li>

                                            <!-- Các số trang -->
                                            <c:forEach var="i" begin="1" end="${totalPages}">
                                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                    <a class="page-link" 
                                                       href="${pageContext.request.contextPath}/OrganizationDetailEventServlet?eventId=${event.id}&page=${i}">
                                                        ${i}
                                                    </a>
                                                </li>
                                            </c:forEach>

                                            <!-- Nút Next -->
                                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                <a class="page-link" 
                                                   href="${pageContext.request.contextPath}/OrganizationDetailEventServlet?eventId=${event.id}&page=${currentPage + 1}">
                                                    Sau
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>

                                    <!-- Hiển thị thông tin -->
                                    <div class="text-center text-muted">
                                        Trang ${currentPage} / ${totalPages} (Tổng ${totalDonations} đơn donate)
                                    </div>
                                </c:if>
                            </div>



                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Chi Tiết Người Donate 1 -->
            <div class="modal fade" id="donorModal1" tabindex="-1" aria-labelledby="donorModal1Label" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header bg-primary text-white">
                            <h5 class="modal-title" id="donorModal1Label">
                                <i class="bi bi-person-circle me-2"></i>Thông Tin Chi Tiết Người Đóng Góp
                            </h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row g-3">
                                <div class="col-12">
                                    <div class="card border-0 bg-light">
                                        <div class="card-body">
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-hash me-1"></i>ID Người donate:
                                                </div>
                                                <div class="col-7 fw-semibold">DN001</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-person me-1"></i>Họ và Tên:
                                                </div>
                                                <div class="col-7 fw-semibold">Nguyễn Văn An</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-telephone me-1"></i>Số điện thoại:
                                                </div>
                                                <div class="col-7">0123456789</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-envelope me-1"></i>Email:
                                                </div>
                                                <div class="col-7">nguyenvanan@email.com</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-geo-alt me-1"></i>Địa chỉ:
                                                </div>
                                                <div class="col-7">Hà Nội</div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-currency-dollar me-1"></i>Số tiền:
                                                </div>
                                                <div class="col-7">
                                                    <span class="badge bg-success fs-6">5,000,000 VND</span>
                                                </div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-clock me-1"></i>Thời gian:
                                                </div>
                                                <div class="col-7">01/11/2025 10:30</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-5 fw-semibold text-secondary">
                                                    <i class="bi bi-credit-card me-1"></i>Phương thức:
                                                </div>
                                                <div class="col-7">
                                                    <span class="badge bg-info">QR Code</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <i class="bi bi-x-circle me-1"></i>Đóng
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
