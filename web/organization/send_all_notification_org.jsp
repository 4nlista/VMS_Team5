<%-- 
    Document   : send_all_notification_org
    Created on : Nov 11, 2025, 9:25:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gửi thông báo chung</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-8 offset-md-2">
                            <div class="card shadow">
                                <div class="card-header bg-success text-white">
                                    <h4 class="mb-0"><i class="bi bi-broadcast"></i> Gửi thông báo chung</h4>
                                </div>
                                <div class="card-body">
                                    <!-- Thông báo -->
                                    <c:if test="${not empty sessionScope.message}">
                                        <c:choose>
                                            <c:when test="${fn:contains(sessionScope.message, 'thành công')}">
                                                <div class="alert alert-success alert-dismissible fade show" role="alert" id="autoCloseAlert">
                                                    <i class="bi bi-check-circle"></i> ${sessionScope.message}
                                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="alert alert-warning alert-dismissible fade show" role="alert" id="autoCloseAlert">
                                                    <i class="bi bi-exclamation-triangle"></i> ${sessionScope.message}
                                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:remove var="message" scope="session"/>
                                    </c:if>
                                    
                                    <div class="alert alert-info">
                                        <i class="bi bi-info-circle"></i> Thông báo này sẽ được gửi đến <strong>tất cả tình nguyện viên</strong> đã được duyệt tham gia sự kiện.
                                    </div>

                                    <form action="<%= request.getContextPath() %>/OrganizationSendNotificationServlet" method="POST">
                                        <!-- Hidden inputs -->
                                        <input type="hidden" name="eventId" value="${eventId}">
                                        <input type="hidden" name="sendType" value="all">

                                        <!-- Loại thông báo -->
                                        <div class="mb-3">
                                            <label for="type" class="form-label">Loại thông báo <span class="text-danger">*</span></label>
                                            <select class="form-select" id="type" name="type" required>
                                                <option value="">-- Chọn loại --</option>
                                                <option value="reminder" selected>Nhắc nhở (Reminder)</option>
                                                <option value="system">Hệ thống (System)</option>
                                            </select>
                                        </div>

                                        <!-- Nội dung thông báo -->
                                        <div class="mb-3">
                                            <label for="message" class="form-label">Nội dung thông báo <span class="text-danger">*</span></label>
                                            <textarea class="form-control" id="message" name="message" rows="5" 
                                                      placeholder="Nhập nội dung thông báo chung cho tất cả tình nguyện viên..." required></textarea>
                                        </div>

                                        <!-- Buttons -->
                                        <div class="d-flex justify-content-between">
                                            <a href="<%= request.getContextPath() %>/OrganizationVolunteersServlet?eventId=${eventId}" 
                                               class="btn btn-secondary">
                                                <i class="bi bi-arrow-left"></i> Quay lại
                                            </a>
                                            <button type="submit" class="btn btn-success">
                                                <i class="bi bi-broadcast"></i> Gửi cho tất cả
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Auto close alert after 3 seconds
            window.onload = function() {
                var alert = document.getElementById('autoCloseAlert');
                if (alert) {
                    setTimeout(function() {
                        var bsAlert = new bootstrap.Alert(alert);
                        bsAlert.close();
                    }, 3000); // 3 seconds
                }
            };
        </script>
    </body>
</html>