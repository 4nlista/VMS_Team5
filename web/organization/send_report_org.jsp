<%-- 
    Document   : send_report_org
    Created on : Nov 2, 2025, 1:34:07 PM
    Author     : Admin
--%>

<%-- 
    Document   : manage_feedback_org
    Created on : Nov 2, 2025, 12:55:14 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gửi đơn báo cáo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container d-flex justify-content-center">
                    <div class="w-100" style="max-width: 800px;">
                        <h3 class="fw-bold mb-4 text-center">Gửi đơn báo cáo</h3>
                        <c:if test="${param.success == '1'}">
                            <div id="reportSuccessAlert" class="alert alert-success" role="alert">
                                Gửi đơn báo cáo thành công. Trạng thái đã được ghi nhận là pending.
                            </div>
                            <script>
                                setTimeout(function(){
                                    var el = document.getElementById('reportSuccessAlert');
                                    if(el){ el.style.display = 'none'; }
                                }, 3000);
                            </script>
                        </c:if>

                            
                            
                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">
                                <form action="<%= request.getContextPath() %>/organization/send_report_org" method="post" class="row g-3">

                                    <!-- ID bình luận -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">ID bình luận</label>
                                        <input type="text" class="form-control" name="feedbackId" value="${feedback != null ? feedback.id : param.feedbackId}" readonly>
                                    </div>

                                    <!-- Người gửi -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tên người gửi</label>
                                        <input type="text" class="form-control" value="${feedback != null ? feedback.organizationName : ''}" readonly>
                                    </div>

                                    <!-- Người bị báo cáo -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Người bình luận</label>
                                        <input type="text" class="form-control" value="${feedback != null ? feedback.volunteerName : ''}" readonly>
                                    </div>

                                    <!-- Lý do chi tiết -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Lý do chi tiết</label>
                                        <textarea class="form-control" name="reason" rows="4" placeholder="Nhập mô tả chi tiết về báo cáo..." required></textarea>
                                    </div>

                                    <!-- Nút thao tác -->
                                    <div class="col-12 mt-3">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <!-- Bên trái -->
                                            <a href="<%= request.getContextPath() %>/OrganizationManageFeedbackServlet" 
                                               class="btn btn-secondary">
                                                <i class="bi bi-arrow-left me-1"></i> Quay lại
                                            </a>

                                            <!-- Bên phải -->
                                            <div>
                                                <button type="submit" class="btn btn-primary me-2">
                                                    <i></i> Gửi báo cáo
                                                </button>
                                                <a href="<%= request.getContextPath() %>/OrganizationManageFeedbackServlet" 
                                                   class="btn btn-outline-danger">
                                                    <i class="bi bi-x-circle me-1"></i> Hủy
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
