<%-- 
    Document   : notifications_volunteer
    Created on : Nov 13, 2025, 9:13:01 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xem thông báo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
        <style>
            .notification-item {
                border-left: 4px solid #0d6efd;
                transition: all 0.3s ease;
            }
            .notification-item:hover {
                background-color: #f8f9fa;
                transform: translateX(5px);
            }
            .notification-item.unread {
                background-color: #e7f3ff;
                border-left-color: #dc3545;
            }
            .notification-badge {
                font-size: 0.75rem;
                padding: 0.25rem 0.5rem;
            }
        </style>
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <h1 class="mb-4 text-center">Lịch sử sự kiện đã tham gia</h1>
                    <!-- Header -->
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2 class="mb-0">
                            <i class="bi bi-bell-fill text-primary"></i> Thông báo của bạn
                            <c:if test="${totalNotifications > 0}">
                                <span class="badge bg-secondary">${totalNotifications}</span>
                            </c:if>
                        </h2>
                        <div class="d-flex gap-2">
                            <!-- Lọc sắp xếp -->
                            <form method="GET" action="<%= request.getContextPath() %>/VolunteerNotificationServlet" class="d-inline">
                                <input type="hidden" name="page" value="1">
                                <select name="sort" class="form-select form-select-sm" onchange="this.form.submit()">
                                    <option value="newest" ${sortOrder == 'newest' ? 'selected' : ''}>Mới nhất</option>
                                    <option value="oldest" ${sortOrder == 'oldest' ? 'selected' : ''}>Cũ nhất</option>
                                </select>
                            </form>

                            <!-- Đánh dấu tất cả -->
                            <c:if test="${not empty notifications}">
                                <form method="POST" action="<%= request.getContextPath() %>/VolunteerNotificationServlet" style="display: inline;">
                                    <input type="hidden" name="action" value="markAllRead">
                                    <button type="submit" class="btn btn-outline-primary btn-sm">
                                        <i class="bi bi-check-all"></i> Đọc tất cả
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </div>

                    <!-- Message alert -->
                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-success alert-dismissible fade show">
                            ${sessionScope.message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                        <c:remove var="message" scope="session"/>
                    </c:if>

                    <!-- Danh sách thông báo -->
                    <c:choose>
                        <c:when test="${empty notifications}">
                            <div class="text-center py-5">
                                <i class="bi bi-bell-slash" style="font-size: 4rem; color: #ccc;"></i>
                                <p class="mt-3 text-muted fs-5">Bạn chưa có thông báo nào</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="list-group">
                                <c:forEach var="noti" items="${notifications}">
                                    <div class="list-group-item notification-item ${noti.isRead ? '' : 'unread'} mb-3 rounded shadow-sm">
                                        <div class="d-flex w-100 justify-content-between align-items-start">
                                            <div class="flex-grow-1">
                                                <!-- Icon theo type -->
                                                <c:choose>
                                                    <c:when test="${noti.type == 'reminder'}">
                                                        <span class="badge bg-warning notification-badge">
                                                            <i class="bi bi-clock"></i> Nhắc nhở
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${noti.type == 'apply'}">
                                                        <span class="badge bg-info notification-badge">
                                                            <i class="bi bi-file-earmark-check"></i> Duyệt đơn
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${noti.type == 'donation'}">
                                                        <span class="badge bg-success notification-badge">
                                                            <i class="bi bi-cash-coin"></i> Quyên góp
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${noti.type == 'system'}">
                                                        <span class="badge bg-secondary notification-badge">
                                                            <i class="bi bi-gear"></i> Hệ thống
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${noti.type == 'report'}">
                                                        <span class="badge bg-danger notification-badge">
                                                            <i class="bi bi-flag"></i> Báo cáo
                                                        </span>
                                                    </c:when>
                                                </c:choose>

                                                <!-- Người gửi -->
                                                <p class="mb-2 mt-2">
                                                    <strong>Người gửi :  </strong>
                                                    <c:choose>
                                                        <c:when test="${not empty noti.senderName}">
                                                            ${noti.senderName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">Hệ thống</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>

                                                <!-- Nội dung -->
                                                <p class="mb-2"><strong>[Nội dung] : </strong> ${noti.message}</p>

                                                <!-- Sự kiện liên quan -->
                                                <c:if test="${not empty noti.eventTitle}">
                                                    <p class="mb-2 text-primary">
                                                        <i class="bi bi-calendar-event"></i> 
                                                        <strong>Sự kiện : </strong> ${noti.eventTitle}
                                                    </p>
                                                </c:if>

                                                <!-- Thời gian -->
                                                <small class="text-muted">
                                                    <i class="bi bi-clock"><strong> Ngày gửi:</strong></i>
                                                    <fmt:formatDate value="${noti.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                                </small>
                                            </div>

                                            <!-- Nút đánh dấu đã đọc -->
                                            <c:if test="${!noti.isRead}">
                                                <form method="POST" action="<%= request.getContextPath() %>/VolunteerNotificationServlet" class="ms-3">
                                                    <input type="hidden" name="action" value="markRead">
                                                    <input type="hidden" name="notificationId" value="${noti.id}">
                                                    <button type="submit" class="btn btn-sm btn-outline-success" title="Đánh dấu đã đọc">
                                                        <i class="bi bi-check2"></i>
                                                    </button>
                                                </form>
                                            </c:if>
                                            <c:if test="${noti.isRead}">
                                                <span class="badge bg-success ms-3">
                                                    <i class="bi bi-check-circle"></i> Đã đọc
                                                </span>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Phân trang -->
                            <!-- Phân trang -->
                            <c:if test="${totalPages > 1}">
                                <nav class="mt-4">
                                    <ul class="pagination justify-content-center">
                                        <!-- Nút Previous -->
                                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="<%= request.getContextPath() %>/VolunteerNotificationServlet?page=${currentPage - 1}&sort=${sortOrder}">
                                                Trước
                                            </a>
                                        </li>

                                        <!-- Các số trang -->
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <c:if test="${i == 1 || i == totalPages || (i >= currentPage - 2 && i <= currentPage + 2)}">
                                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                    <a class="page-link" href="<%= request.getContextPath() %>/VolunteerNotificationServlet?page=${i}&sort=${sortOrder}">
                                                        ${i}
                                                    </a>
                                                </li>
                                            </c:if>
                                            <c:if test="${i == 2 && currentPage > 4}">
                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                </c:if>
                                                <c:if test="${i == totalPages - 1 && currentPage < totalPages - 3}">
                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                </c:if>
                                            </c:forEach>

                                        <!-- Nút Next -->
                                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                            <a class="page-link" href="<%= request.getContextPath() %>/VolunteerNotificationServlet?page=${currentPage + 1}&sort=${sortOrder}">
                                                Sau
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
