<%-- 
    Document   : edit_event_org
    Created on : 17 Nov 2025, 23:52:21
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
              rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
              rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
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
                            <c:remove var="successMessage" scope="session" />
                        </c:if>

                        <c:if test="${not empty sessionScope.errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${sessionScope.errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                            <c:remove var="errorMessage" scope="session" />
                        </c:if>
                    </div>
                    <!-- Kiểm tra điều kiện disable form để hiển thị cảnh bảo -->
                    <jsp:useBean id="now" class="java.util.Date" />
                    <c:set var="msUntilStart" value="${event.startDate.time - now.time}" />
                    <c:set var="isEventEnded" value="${event.endDate.time < now.time}" />
                    <c:set var="isWithin24Hours" value="${msUntilStart <= 24*60*60*1000 && msUntilStart > 0}" />
                    <c:set var="canUpdate" value="${!isEventEnded && !isWithin24Hours}" />



                    <!-- Hiển thị cảnh báo nếu không thể cập nhật -->
                    <c:if test="${isEventEnded}">
                        <div class="alert alert-danger" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>
                            <strong>Sự kiện đã kết thúc!</strong> Không thể thực hiện cập nhật.
                        </div>
                    </c:if>
                    <c:if test="${!isEventEnded && isWithin24Hours}">
                        <div class="alert alert-warning" role="alert">
                            <i class="bi bi-clock-fill me-2"></i>
                            <strong>Sự kiện sắp diễn ra trong vòng 24h!</strong> Không thể thực hiện cập
                            nhật.
                        </div>
                    </c:if>

                    <!-- Form Cập nhật Sự kiện -->
                    <form action="<%= request.getContextPath() %>/OrganizationDetailEventServlet"
                          method="post" id="updateEventForm" onsubmit="return validateEventDates()">
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
                                           value="${event.title}" required ${!canUpdate ? 'disabled' : '' }>
                                </div>

                                <div class="row">
                                    <!-- Cột trái -->
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label text-muted">Mã sự kiện</label>
                                            <input type="text" class="form-control" value="${event.id}"
                                                   disabled>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label text-muted">Danh mục sự kiện</label>
                                            <select name="categoryId" class="form-select" required>
                                                <option value="">-- Chọn loại sự kiện --</option>
                                                <c:forEach var="category" items="${categories}">
                                                    <option value="${category.categoryId}"
                                                            ${category.categoryId==event.categoryId ? 'selected'
                                                              : '' }>
                                                                ${category.categoryName}
                                                            </option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label text-muted">Người tổ chức</label>
                                                <input type="text" class="form-control"
                                                       value="${event.organizationName}" disabled>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label text-muted">Địa điểm</label>
                                                <input type="text" name="location" class="form-control"
                                                       value="${event.location}" required ${!canUpdate ? 'disabled'
                                                                : '' }>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label text-muted">Số lượng tình nguyện viên
                                                    cần</label>
                                                <input type="number" name="neededVolunteers"
                                                       class="form-control" value="${event.neededVolunteers}"
                                                       min="1" required ${!canUpdate ? 'disabled' : '' }>
                                            </div>
                                        </div>

                                        <!-- Cột phải -->
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label class="form-label text-muted">Ngày bắt đầu</label>
                                                <input type="datetime-local" name="startDate"
                                                       class="form-control"
                                                       value="<fmt:formatDate value='${event.startDate}' pattern='yyyy-MM-dd\'T\'HH:mm' />"
                                                       required ${!canUpdate ? 'disabled' : '' }>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label text-muted">Ngày kết thúc</label>
                                                <input type="datetime-local" name="endDate" class="form-control"
                                                       value="<fmt:formatDate value='${event.endDate}' pattern='yyyy-MM-dd\'T\'HH:mm' />"
                                                       required ${!canUpdate ? 'disabled' : '' }>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label text-muted">Trạng thái</label>
                                                <select name="status" class="form-select" required ${!canUpdate
                                                                                                     ? 'disabled' : '' }>
                                                    <option value="active" ${event.status=='active' ? 'selected'
                                                                             : '' }>Đang hoạt động</option>
                                                    <option value="inactive" ${event.status=='inactive'
                                                                               ? 'selected' : '' }>Tạm dừng</option>
                                                    <option value="closed" ${event.status=='closed' ? 'selected'
                                                                             : '' }>Đã đóng</option>
                                                </select>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label text-muted">Chế độ</label>
                                                <select name="visibility" class="form-select" required
                                                        ${!canUpdate ? 'disabled' : '' }>
                                                    <option value="public" ${event.visibility=='public'
                                                                             ? 'selected' : '' }>Công khai</option>
                                                    <option value="private" ${event.visibility=='private'
                                                                              ? 'selected' : '' }>Riêng tư</option>
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
                                        <textarea name="description" class="form-control" rows="4" required
                                                  ${!canUpdate ? 'disabled' : '' }>${event.description}</textarea>
                                    </div>

                                    <!-- Action Buttons -->
                                    <div class="text-end">
                                        <a href="<%= request.getContextPath() %>/OrganizationListServlet"
                                           class="btn btn-secondary me-2">
                                            <i class="bi bi-x-circle me-1"></i>Hủy
                                        </a>
                                        <c:if test="${canUpdate}">
                                            <button type="submit" name="action" value="update"
                                                    class="btn btn-primary me-2">
                                                <i class="bi bi-check-circle me-1"></i>Cập nhật
                                            </button>
                                        </c:if>
                                    </div>

                                    <a href="<%= request.getContextPath() %>/OrganizationListServlet"
                                       class="btn btn-primary">
                                        <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách
                                    </a>
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
                                                        <fmt:formatNumber value="${donation.amount}"
                                                                          type="number" groupingUsed="true" /> VND
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${donation.donateDate}"
                                                                        pattern="dd/MM/yyyy HH:mm:ss" />
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-info">
                                                            <i
                                                                class="bi bi-qr-code me-1"></i>${donation.paymentMethod}
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${donation.status == 'pending'}">
                                                                <span class="badge bg-warning text-dark">Chưa xử
                                                                    lý</span>
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
                                                                <form
                                                                    action="<%= request.getContextPath() %>/ProcessDonationServlet"
                                                                    method="post" style="display:inline;">
                                                                    <input type="hidden" name="donationId"
                                                                           value="${donation.id}">
                                                                    <input type="hidden" name="eventId"
                                                                           value="${event.id}">
                                                                    <input type="hidden" name="volunteerId"
                                                                           value="${donation.volunteerId}">
                                                                    <button type="submit" name="action"
                                                                            value="approve"
                                                                            class="btn btn-primary btn-sm">Chấp
                                                                        nhận</button>
                                                                    <button type="submit" name="action"
                                                                            value="reject"
                                                                            class="btn btn-danger btn-sm"
                                                                            onclick="return confirm('Bạn có chắc muốn từ chối đơn này?')">Từ
                                                                        chối</button>
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
                                                    <td colspan="7" class="text-center text-muted">Chưa có đơn
                                                        donate nào</td>
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
                                                <li
                                                    class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                    <a class="page-link"
                                                       href="${pageContext.request.contextPath}/OrganizationDetailEventServlet?eventId=${event.id}&page=${currentPage + 1}">
                                                        Sau
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>

                                        <!-- Hiển thị thông tin -->
                                        <div class="text-center text-muted">
                                            Trang ${currentPage} / ${totalPages} (Tổng ${totalDonations} đơn
                                            donate)
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <script
                src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

    </html>