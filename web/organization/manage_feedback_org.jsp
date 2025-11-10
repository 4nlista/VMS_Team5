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
        <title>Quản lý đánh giá</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-4">
                <div class="container-fluid">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h3 class="fw-bold mb-0">Danh sách đánh giá</h3>
                        <a href="<%= request.getContextPath() %>/OrganizationListServlet" class="btn btn-secondary btn-sm">
                            <i class="bi bi-arrow-left"></i> Về quản lí sự kiện
                        </a>
                    </div>
                    <!-- Bộ lọc + nút tạo mới -->
                    <form method="get" action="OrganizationListServlet" class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <!-- Nhóm dropdown + nút lọc/reset -->
                        <div class="d-flex gap-2 align-items-center flex-wrap">
                            <!-- Loại sự kiện -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Điểm đánh giá:</label>
                                <select name="rating" class="form-select form-select-sm" style="width: 160px;">
                                    <option value="">Tất cả</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </div>

                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái:</label>
                                <select name="status" class="form-select" style="width: 160px;">
                                    <option value="" ${empty status ? 'selected' : ''}>Tất cả</option>
                                    <option value="valid" ${status == 'valid' ? 'selected' : ''}>Hiện</option>
                                    <option value="invalid" ${status == 'invalid' ? 'selected' : ''}>Ẩn</option>
                                </select>
                            </div>

                            <!-- Tên sự kiện -->
                            <div class="form-group">
                                <label class="form-label fw-semibold">Tên sự kiện</label>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <input class="form-control" style="min-width:320px" name="q" value="${q}" placeholder="Nhập tên sự kiện..." />

                                </div>
                                <!-- Nút Lọc -->
                                <button type="submit" class="btn btn-primary btn-sm" style="min-width:110px; align-self:end;">
                                    <i class="bi bi-search"></i> Lọc
                                </button>

                                <!-- Nút Reset -->
                                <a href="#" class="btn btn-secondary btn-sm" style="min-width:110px; align-self:end;">
                                    <i class="bi bi-arrow-counterclockwise"></i> Reset
                                </a>
                            </div>
                            <div class="form-group">
                                <label class="form-label fw-semibold">Tên sự kiện:</label>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <input class="form-control w-auto" placeholder="Nhập tên sự kiện" />
                                    <button class="btn btn-danger">Tìm kiếm</button>
                                </div>
                            </div>
                        </form>

                    <!-- Thông báo thành công -->
                    <c:if test="${param.reported == '1'}">
                        <div id="reportSuccessAlert" class="alert alert-success mb-3" role="alert">
                            Gửi báo cáo thành công. Trạng thái đã được ghi nhận là pending.
                        </div>
                        <script>
                            setTimeout(function(){
                                var el = document.getElementById('reportSuccessAlert');
                                if(el){ el.style.display = 'none'; }
                            }, 5000);
                        </script>
                    </c:if>

                    <!-- Thông báo cập nhật trạng thái thành công -->
                    <c:if test="${param.statusUpdated == '1'}">
                        <div id="statusUpdateSuccessAlert" class="alert alert-success mb-3" role="alert">
                            Cập nhật trạng thái thành công.
                        </div>
                        <script>
                            setTimeout(function(){
                                var el = document.getElementById('statusUpdateSuccessAlert');
                                if(el){ el.style.display = 'none'; }
                            }, 5000);
                        </script>
                    </c:if>

                        <!-- Bảng dữ liệu -->
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle" style="table-layout: fixed; width: 100%;">
                                <thead class="table-secondary">
                                    <tr>
                                        <th style="width:4%;">STT</th>
                                        <th style="width:25%;">Tên sự kiện</th>
                                        <th style="width:16%;">Tình nguyện viên</th>
                                        <th style="width:25%;">Bình luận</th>
                                        <th style="width:5%;">Điểm</th>
                                        <th style="width:10%;">Trạng thái</th>
                                        <th style="width:15%;">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="f" items="${feedbacks}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>${f.volunteerName}</td>
                                            <td style="word-wrap: break-word; white-space: normal;">${f.comment}</td>
                                            <td>${f.rating}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${f.status == 'valid' || f.status == 'Valid'}">
                                                        <span class="badge bg-success rounded-pill px-3 py-2">Hiện</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger rounded-pill px-3 py-2">Ẩn</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="d-flex align-items-center gap-2">
                                                    <form method="post" action="<%= request.getContextPath() %>/OrganizationUpdateFeedbackStatusServlet" class="d-inline">
                                                        <input type="hidden" name="feedbackId" value="${f.id}" />
                                                        <input type="hidden" name="eventId" value="${eventId}" />
                                                        <select name="status" class="form-select form-select-sm" style="width: auto; min-width: 100px;" onchange="this.form.submit()">
                                                            <option value="Hiện" ${(f.status == 'valid' || f.status == 'Valid') ? 'selected' : ''}>Hiện</option>
                                                            <option value="Ẩn" ${(f.status != 'valid' && f.status != 'Valid') ? 'selected' : ''}>Ẩn</option>
                                                        </select>
                                                    </form>
                                                    <a href="<%= request.getContextPath() %>/organization/send_report_org?feedbackId=${f.id}" class="btn btn-sm btn-secondary text-white">Báo cáo</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty feedbacks}">
                                        <tr>
                                            <td colspan="6" class="text-center">Không có đánh giá phù hợp</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                        </table>
                    </div>



                    <!-- Phân trang -->
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <span>Hiển thị 1 - 3 đánh giá</span>
                        <ul class="pagination pagination-sm mb-0">
                            <li class="page-item disabled"><a class="page-link" href="#">Trước</a></li>
                            <li class="page-item active"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">Sau</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
