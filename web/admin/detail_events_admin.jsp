<%-- 
    Document   : detail_events_admin
    Created on : Sep 16, 2025, 9:25:20 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Chi tiết sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content -->
            <div class="main-content">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h1><i class="bi bi-calendar-event me-2"></i>Chi tiết sự kiện</h1>
                    <a href="AdminEventsServlet" class="btn btn-secondary">
                        <i class="bi bi-arrow-left me-2"></i>Quay lại
                    </a>
                </div>

                <c:if test="${empty event}">
                    <div class="alert alert-warning" role="alert">
                        <i class="bi bi-exclamation-triangle me-2"></i>Không tìm thấy sự kiện!
                    </div>
                </c:if>

                <c:if test="${not empty event}">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="bi bi-info-circle me-2"></i>Thông tin sự kiện</h5>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">ID Sự kiện:</label>
                                    <p class="form-control-plaintext">${event.id}</p>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Tiêu đề:</label>
                                    <p class="form-control-plaintext">${fn:escapeXml(event.title)}</p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Trạng thái:</label>
                                    <p class="form-control-plaintext">
                                        <c:choose>
                                            <c:when test="${event.status == 'active'}">
                                                <span class="badge bg-success">Active</span>
                                            </c:when>
                                            <c:when test="${event.status == 'inactive'}">
                                                <span class="badge bg-secondary">Inactive</span>
                                            </c:when>
                                            <c:when test="${event.status == 'closed'}">
                                                <span class="badge bg-dark">Closed</span>
                                            </c:when>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Chế độ:</label>
                                    <p class="form-control-plaintext">
                                        <c:choose>
                                            <c:when test="${event.visibility == 'public'}">
                                                <span class="badge bg-info">Public</span>
                                            </c:when>
                                            <c:when test="${event.visibility == 'private'}">
                                                <span class="badge bg-warning text-dark">Private</span>
                                            </c:when>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Ngày diễn ra:</label>
                                    <p class="form-control-plaintext">
                                        <fmt:formatDate value="${event.startDate}" pattern="dd/MM/yyyy HH:mm" />
                                    </p>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Ngày kết thúc:</label>
                                    <p class="form-control-plaintext">
                                        <fmt:formatDate value="${event.endDate}" pattern="dd/MM/yyyy HH:mm" />
                                    </p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Địa điểm:</label>
                                    <p class="form-control-plaintext">${fn:escapeXml(event.location)}</p>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Số lượng tình nguyện viên đã tham gia:</label>
                                    <p class="form-control-plaintext">
                                        <span class="badge bg-primary">${approvedVolunteersCount}</span>
                                    </p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Tên người tổ chức:</label>
                                    <p class="form-control-plaintext">${fn:escapeXml(event.organizationName)}</p>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Tổng số tiền donate:</label>
                                    <p class="form-control-plaintext">
                                        <strong class="text-success">
                                            <fmt:formatNumber value="${event.totalDonation}" type="number" maxFractionDigits="0" /> VNĐ
                                        </strong>
                                    </p>
                                </div>
                            </div>

                            <c:if test="${not empty event.description}">
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Mô tả:</label>
                                    <div class="form-control-plaintext border rounded p-3 bg-light">
                                        ${fn:escapeXml(event.description)}
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty event.images}">
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Hình ảnh:</label>
                                    <div>
                                        <img src="${event.images}" alt="Event Image" class="img-thumbnail" style="max-width: 300px; max-height: 300px;" />
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

