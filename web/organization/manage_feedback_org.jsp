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
                            <i class="bi bi-arrow-left"></i> Quay về quản lí sự kiện
                        </a>
                    </div>

                    <!-- Bộ lọc + nút tạo mới -->
                    <form method="get" action="<%= request.getContextPath() %>/OrganizationManageFeedbackServlet" class="d-flex align-items-end mb-3 flex-wrap gap-3">
                        <input type="hidden" name="eventId" value="${eventId}" />
                        <div class="d-flex align-items-end flex-wrap gap-3">
                            <!-- Loại sự kiện -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Điểm đánh giá:</label>
                                <select name="rating" class="form-select" style="width: 160px;">
                                    <option value="" ${empty rating ? 'selected' : ''}>Tất cả</option>
                                    <option value="1" ${rating == '1' ? 'selected' : ''}>1</option>
                                    <option value="2" ${rating == '2' ? 'selected' : ''}>2</option>
                                    <option value="3" ${rating == '3' ? 'selected' : ''}>3</option>
                                    <option value="4" ${rating == '4' ? 'selected' : ''}>4</option>
                                    <option value="5" ${rating == '5' ? 'selected' : ''}>5</option>
                                </select>
                            </div>

                            <!-- Trạng thái -->
                            <div class="form-group d-flex flex-column">
                                <label class="form-label fw-semibold">Trạng thái:</label>
                                <select name="status" class="form-select" style="width: 160px;">
                                    <option value="" ${empty status ? 'selected' : ''}>Tất cả</option>
                                    <option value="valid" ${status == 'valid' ? 'selected' : ''}>Hợp lệ</option>
                                    <option value="invalid" ${status == 'invalid' ? 'selected' : ''}>Không hợp lệ</option>
                                </select>
                            </div>

                            <!-- Tên sự kiện -->
                            <div class="form-group">
                                <label class="form-label fw-semibold">Tên sự kiện</label>
                                <div class="d-flex align-items-center gap-2 mt-1">
                                    <input class="form-control" style="min-width:320px" name="q" value="${q}" placeholder="Nhập tên sự kiện..." />
                                </div>
                            </div>

                            <!-- Buttons placed right after search fields -->
                            <div class="d-flex align-items-end gap-2">
                                <button type="submit" class="btn btn-primary" style="min-width:140px;">
                                    <i class="bi bi-search"></i> Lọc và tìm kiếm
                                </button>
                                <a href="<%= request.getContextPath() %>/OrganizationManageFeedbackServlet" class="btn btn-secondary" style="min-width:110px;">
                                    <i class="bi bi-arrow-counterclockwise"></i> Làm mới
                                </a>
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

                        <!-- Bảng dữ liệu -->
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle" style="table-layout: fixed; width: 100%;">
                                <thead class="table-secondary">
                                    <tr>
                                        <th style="width:5%;">STT</th>
                                        <th style="width:20%;">Tình nguyện viên</th>
                                        <th style="width:40%;">Bình luận</th>
                                        <th style="width:8%;">Điểm</th>
                                        <th style="width:12%;">Trạng thái</th>
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
                                                    <c:when test="${f.status == 'valid' || f.status == 'Valid'}"><span class="badge bg-success">Hợp lệ</span></c:when>
                                                    <c:otherwise><span class="badge bg-danger">Không hợp lệ</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <a href="<%= request.getContextPath() %>/organization/send_report_org?feedbackId=${f.id}" class="btn btn-sm btn-warning text-white">Báo cáo</a>
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
                        <span>Hiển thị ${empty feedbacks ? 0 : feedbacks.size()} đánh giá</span>
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
