<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết giao dịch</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
        <style>
            .list-group-item strong {
                font-weight: 700;
                font-size: 1.05rem;
                color: #2c3e50;
            }
            .info-label {
                font-weight: 600;
                color: #495057;
                min-width: 150px;
                display: inline-block;
            }
        </style>
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <h1 class="mb-4 text-center text-primary fw-bold">Chi tiết giao dịch donate</h1>

                    <c:choose>
                        <c:when test="${empty donation}">
                            <div class="alert alert-danger text-center">
                                <i class="bi bi-exclamation-triangle"></i> Không tìm thấy thông tin giao dịch.
                            </div>
                            <div class="text-center mt-3">
                                <a href="${pageContext.request.contextPath}/OrganizationListServlet" class="btn btn-primary">
                                    <i class="bi bi-arrow-left"></i> Quay lại
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row justify-content-center">
                                <!-- Cột 1: Thông tin sự kiện -->
                                <div class="col-md-6 mb-4">
                                    <div class="card shadow-sm h-100">
                                        <div class="card-header bg-primary text-white fw-bold">
                                            Thông tin sự kiện
                                        </div>
                                        <div class="card-body">
                                            <ul class="list-group list-group-flush">
                                                <li class="list-group-item">
                                                    <strong>Tiêu đề:</strong> ${event.title}
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Danh mục:</strong> ${event.categoryName}
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Thời gian bắt đầu:</strong>
                                                    <fmt:formatDate value="${event.startDate}" pattern="dd/MM/yyyy HH:mm" />
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Thời gian kết thúc:</strong>
                                                    <fmt:formatDate value="${event.endDate}" pattern="dd/MM/yyyy HH:mm" />
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Địa điểm:</strong> ${event.location}
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Mô tả:</strong>
                                                    <p class="mt-2 mb-0">${event.description}</p>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <!-- Cột 2: Thông tin giao dịch -->
                                <div class="col-md-6 mb-4">
                                    <div class="card shadow-sm h-100">
                                        <div class="card-header bg-success text-white fw-bold">
                                            Chi tiết giao dịch
                                        </div>
                                        <div class="card-body">
                                            <ul class="list-group list-group-flush">
                                                <li class="list-group-item">
                                                    <strong>Mã giao dịch:</strong>
                                                    <span class="badge bg-info text-white">${donation.paymentTxnRef != null ? donation.paymentTxnRef : 'N/A'}</span>
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Đối tượng:</strong>
                                                    <c:choose>
                                                        <c:when test="${donation.donorType == 'guest'}">
                                                            Khách (Guest)
                                                        </c:when>
                                                        <c:otherwise>
                                                            Tình nguyện viên
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Tên người donate:</strong> ${donation.displayDonorName}
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Số điện thoại:</strong> ${donation.displayDonorPhone}
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Số tiền:</strong>
                                                    <span class="text-danger fw-bold fs-5">
                                                        <fmt:formatNumber value="${donation.amount}" pattern="#,###" /> đ
                                                    </span>
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Phương thức thanh toán:</strong>
                                                    <c:choose>
                                                        <c:when test="${donation.paymentMethod == 'VNPay'}">
                                                            <span class="badge bg-primary">
                                                                <i class="bi bi-credit-card"></i> VNPay
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${donation.paymentMethod == 'QR Code'}">
                                                            <span class="badge bg-info">
                                                                <i class="bi bi-qr-code"></i> QR Code
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${donation.paymentMethod}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Ngày thanh toán:</strong>
                                                    <c:choose>
                                                        <c:when test="${donation.donateDate != null}">
                                                            <fmt:formatDate value="${donation.donateDate}" pattern="dd/MM/yyyy HH:mm" />
                                                        </c:when>
                                                        <c:otherwise>-</c:otherwise>
                                                    </c:choose>
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Trạng thái:</strong>
                                                    <c:choose>
                                                        <c:when test="${donation.status == 'success'}">
                                                            <span class="badge bg-success">
                                                                <i class="bi bi-check-circle"></i> Thành công
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${donation.status == 'pending'}">
                                                            <span class="badge bg-warning text-dark">
                                                                <i class="bi bi-clock"></i> Đang xử lý
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${donation.status == 'cancelled'}">
                                                            <span class="badge bg-danger">
                                                                <i class="bi bi-x-circle"></i> Đã hủy
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${donation.status == 'failed'}">
                                                            <span class="badge bg-danger">
                                                                <i class="bi bi-x-circle"></i> Thất bại
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">${donation.status}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>
                                                <li class="list-group-item">
                                                    <strong>Ghi chú:</strong>
                                                    <p class="mt-2 mb-0 text-muted">
                                                        ${donation.note != null && !donation.note.isEmpty() ? donation.note : "(Không có ghi chú)"}
                                                    </p>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Nút quay lại -->
                            <div class="text-center mt-4">
                                <a href="${pageContext.request.contextPath}/OrganizationDetailEventServlet?eventId=${event.id}&page=${returnPage != null ? returnPage : '1'}"
                                   class="btn btn-primary">
                                    <i class="bi bi-arrow-left"></i> Quay lại chi tiết sự kiện
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
