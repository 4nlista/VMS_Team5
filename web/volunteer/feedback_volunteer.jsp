<%-- 
    Document   : feedback_volunteer
    Created on : Nov 9, 2025, 9:25:53 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đánh giá sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5">
            <h1 class="mb-4 text-center">Trang đánh giá sự kiện</h1>

            <!-- Thông tin sự kiện -->
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="alert alert-info">
                        <h5 class="mb-3"><i class="bi bi-calendar-event"></i> Thông tin sự kiện</h5>
                        <p><strong>Tên sự kiện:</strong> ${feedback.eventTitle}</p>
                        <p><strong>Người tổ chức:</strong> ${feedback.organizationName}</p>
                        <p class="mb-0"><strong>Người đánh giá:</strong> ${feedback.volunteerName}</p>
                    </div>

                    <!-- Form đánh giá -->
                    <div class="card shadow">
                        <div class="card-body">
                            <c:choose>
                                <%-- CASE 1: Chưa có feedback -> Form tạo mới --%>
                                <c:when test="${!hasFeedback}">
                                    <h5 class="card-title mb-4"><i class="bi bi-star"></i> Đánh giá của bạn</h5>
                                    <form method="post" action="VolunteerFeedbackServlet">
                                        <input type="hidden" name="action" value="create">
                                        <input type="hidden" name="eventId" value="${feedback.eventId}">

                                        <!-- Chọn điểm -->
                                        <div class="mb-4">
                                            <label class="form-label"><strong>Đánh giá:</strong></label>
                                            <select class="form-select" name="rating" required>
                                                <option value="5" selected>⭐⭐⭐⭐⭐ (5 sao - Xuất sắc)</option>
                                                <option value="4">⭐⭐⭐⭐ (4 sao - Tốt)</option>
                                                <option value="3">⭐⭐⭐ (3 sao - Trung bình)</option>
                                                <option value="2">⭐⭐ (2 sao - Kém)</option>
                                                <option value="1">⭐ (1 sao - Rất kém)</option>
                                            </select>
                                        </div>

                                        <!-- Nhập comment -->
                                        <div class="mb-4">
                                            <label for="comment" class="form-label"><strong>Nhận xét:</strong></label>
                                            <textarea class="form-control" id="comment" name="comment" rows="5" 
                                                      placeholder="Chia sẻ trải nghiệm của bạn về sự kiện này..." 
                                                      required></textarea>
                                        </div>

                                        <!-- Nút submit -->
                                        <div class="d-flex justify-content-between">
                                            <a href="VolunteerEventServlet" class="btn btn-secondary">
                                                <i class="bi bi-arrow-left"></i> Quay lại
                                            </a>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="bi bi-send"></i> Gửi đánh giá
                                            </button>
                                        </div>
                                    </form>
                                </c:when>

                                <%-- CASE 2: Đã có feedback -> Form xem/sửa --%>
                                <c:otherwise>
                                    <h5 class="card-title mb-4"><i class="bi bi-check-circle text-success"></i> Đánh giá của bạn</h5>

                                    <!-- Hiển thị ngày đánh giá -->
                                    <div class="alert alert-success mb-4">
                                        <i class="bi bi-info-circle"></i> Bạn đã đánh giá sự kiện này vào ngày: 
                                        <strong>${feedback.feedbackDate}</strong>
                                    </div>

                                    <form method="post" action="VolunteerFeedbackServlet">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="eventId" value="${feedback.eventId}">

                                        <!-- Chọn điểm -->
                                        <div class="mb-4">
                                            <label class="form-label"><strong>Đánh giá:</strong></label>
                                            <select class="form-select" name="rating" required>
                                                <option value="5" ${feedback.rating == 5 ? 'selected' : ''}>⭐⭐⭐⭐⭐ (5 sao - Xuất sắc)</option>
                                                <option value="4" ${feedback.rating == 4 ? 'selected' : ''}>⭐⭐⭐⭐ (4 sao - Tốt)</option>
                                                <option value="3" ${feedback.rating == 3 ? 'selected' : ''}>⭐⭐⭐ (3 sao - Trung bình)</option>
                                                <option value="2" ${feedback.rating == 2 ? 'selected' : ''}>⭐⭐ (2 sao - Kém)</option>
                                                <option value="1" ${feedback.rating == 1 ? 'selected' : ''}>⭐ (1 sao - Rất kém)</option>
                                            </select>
                                        </div>

                                        <!-- Nhập comment -->
                                        <div class="mb-4">
                                            <label for="comment" class="form-label"><strong>Nhận xét:</strong></label>
                                            <textarea class="form-control" id="comment" name="comment" rows="5" 
                                                      required>${feedback.comment}</textarea>
                                        </div>

                                        <!-- Nút hành động -->
                                        <div class="d-flex justify-content-between">
                                            <a href="VolunteerEventServlet" class="btn btn-secondary">
                                                <i class="bi bi-arrow-left"></i> Quay lại
                                            </a>
                                            <div>
                                                <button type="submit" class="btn btn-success">
                                                    <i class="bi bi-check-lg"></i> Lưu thay đổi
                                                </button>    
                                            </div>
                                        </div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
