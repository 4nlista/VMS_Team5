<%-- 
    Document   : create_news_org
    Created on : Nov 2, 2025, 2:59:57 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <h3 class="fw-bold mb-4 text-center">Tạo bài đăng</h3>

                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">
                                <form action="<%= request.getContextPath() %>/organization/submit_report" method="post" class="row g-3">

                                    <!-- ID bình luận -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">ID bình luận</label>
                                        <input type="text" class="form-control" name="commentId" value="1" required>
                                    </div>

                                    <!-- Người gửi -->
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tên người gửi</label>
                                        <input type="text" class="form-control" name="senderName" value="OrganizationName" required>
                                    </div>

                                    <!-- Người bị báo cáo -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Người bình luận</label>
                                        <input type="text" class="form-control" name="commenterName" value="Nguyễn Bảo An" required>
                                    </div>

                                    <!-- Lý do chi tiết -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Lý do chi tiết</label>
                                        <textarea class="form-control" name="details" rows="4" placeholder="Nhập mô tả chi tiết về báo cáo..." required></textarea>
                                    </div>

                                    <!-- Nút thao tác -->
                                    <div class="col-12 mt-3">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <!-- Bên trái -->
                                            <a href="<%= request.getContextPath() %>/organization/manage_feedback_org.jsp" 
                                               class="btn btn-secondary">
                                                <i class="bi bi-arrow-left me-1"></i> Quay lại
                                            </a>

                                            <!-- Bên phải -->
                                            <div>
                                                <button type="submit" class="btn btn-primary me-2">
                                                    <i></i> Gửi báo cáo
                                                </button>
                                                <a href="<%= request.getContextPath() %>/organization/send_report_org.jsp" 
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
