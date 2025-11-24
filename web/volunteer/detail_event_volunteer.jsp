<%-- 
    Document   : history_event_volunteer
    Created on : Nov 03, 2025, 5:20:00 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
        <jsp:include page="/layout/header.jsp"/>
        <style>
            .list-group-item strong {
                font-weight: 700;
                font-size: 1.05rem;
                color: #2c3e50;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/layout/navbar.jsp"/>

        <div class="container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center text-primary fw-bold">Chi tiết sự kiện</h1>

            <c:choose>
                <c:when test="${empty eventDetails}">
                    <p class="text-danger text-center">Không tìm thấy thông tin chi tiết sự kiện.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="ev" items="${eventDetails}">
                        <div class="row justify-content-center">
                            <!-- Cột 1: Thông tin sự kiện -->
                            <div class="col-md-6 mb-4">
                                <div class="card shadow-sm h-100">
                                    <div class="card-header bg-primary text-white fw-bold">Thông tin sự kiện</div>
                                    <div class="card-body">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item"><strong>Tiêu đề:</strong> ${ev.eventTitle}</li>
                                            <li class="list-group-item"><strong>Người tổ chức:</strong> ${ev.organizationName}</li>
                                            <li class="list-group-item"><strong>Email:</strong> ${ev.orgEmail}</li>
                                            <li class="list-group-item"><strong>Số điện thoại:</strong> ${ev.orgPhone}</li>
                                            <li class="list-group-item"><strong>Danh mục:</strong> ${ev.categoryName}</li>
                                            <li class="list-group-item">
                                                <strong>Ngày bắt đầu:</strong> 
                                                <fmt:formatDate value="${ev.startDateEvent}" pattern="dd/MM/yyyy HH:mm"/>
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Ngày kết thúc:</strong> 
                                                <fmt:formatDate value="${ev.endDateEvent}" pattern="dd/MM/yyyy HH:mm"/>
                                            </li>
                                            <li class="list-group-item"><strong>Địa điểm:</strong> ${ev.eventLocation}</li>
                                            <li class="list-group-item">
                                                <strong>Trạng thái sự kiện:</strong>
                                                <span class="badge
                                                      <c:choose>
                                                          <c:when test="${ev.eventStatus eq 'active'}">bg-success</c:when>
                                                          <c:when test="${ev.eventStatus eq 'inactive'}">bg-warning text-dark</c:when>
                                                          <c:otherwise>bg-secondary</c:otherwise>
                                                      </c:choose>">
                                                    <c:choose>
                                                        <c:when test="${ev.eventStatus eq 'active'}">Đang hoạt động</c:when>
                                                        <c:when test="${ev.eventStatus eq 'inactive'}">Tạm dừng</c:when>
                                                        <c:otherwise>Đã đóng</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Mô tả:</strong>
                                                <div id="shortDesc">
                                                    <c:choose>
                                                        <c:when test="${ev.eventDescription.length() > 150}">
                                                            ${ev.eventDescription.substring(0, 150)}...
                                                            <a href="#" id="showMoreBtn" class="text-primary">Xem thêm</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${ev.eventDescription}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div id="fullDesc" style="display: none;">
                                                    ${ev.eventDescription}
                                                    <a href="#" id="showLessBtn" class="text-primary">Thu gọn</a>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <!-- Cột 2: Thông tin đăng ký -->
                            <div class="col-md-6 mb-4">
                                <div class="card shadow-sm h-100">
                                    <div class="card-header bg-success text-white fw-bold">Thông tin đăng ký</div>
                                    <div class="card-body">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">
                                                <strong>Ngày đăng ký:</strong> 
                                                <fmt:formatDate value="${ev.applyDate}" pattern="dd/MM/yyyy HH:mm"/>
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Trạng thái:</strong>
                                                <span class="badge
                                                      <c:choose>
                                                          <c:when test="${ev.status eq 'approved'}">bg-success</c:when>
                                                          <c:when test="${ev.status eq 'pending'}">bg-warning text-dark</c:when>
                                                          <c:otherwise>bg-danger</c:otherwise>
                                                      </c:choose>">
                                                    <c:choose>
                                                        <c:when test="${ev.status eq 'approved'}">Tham gia</c:when>
                                                        <c:when test="${ev.status eq 'pending'}">Đang xử lý</c:when>
                                                        <c:otherwise>Hủy</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Tổng donate:</strong> 
                                                <fmt:formatNumber value="${ev.totalDonate}" type="number" groupingUsed="true" maxFractionDigits="0"/> đồng
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Điểm danh:</strong>
                                                <span class="badge
                                                      <c:choose>
                                                          <c:when test="${ev.attendanceReport eq 'present'}">bg-success</c:when>
                                                          <c:when test="${ev.attendanceReport eq 'absent'}">bg-danger</c:when>
                                                          <c:otherwise>bg-warning text-dark</c:otherwise>
                                                      </c:choose>">
                                                    <c:choose>
                                                        <c:when test="${ev.attendanceReport eq 'present'}">Đã có mặt</c:when>
                                                        <c:when test="${ev.attendanceReport eq 'absent'}">Đã vắng</c:when>
                                                        <c:otherwise>Chưa điểm danh</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Ghi chú:</strong> <c:out value="${ev.note != null ? ev.note : 'Không có'}"/>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/VolunteerEventServlet" class="btn btn-primary">← Quay lại</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <jsp:include page="/layout/footer.jsp"/>
        <jsp:include page="/layout/loader.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Toggle mô tả đầy đủ
            document.addEventListener('DOMContentLoaded', function() {
                const showMoreBtn = document.getElementById('showMoreBtn');
                const showLessBtn = document.getElementById('showLessBtn');
                const shortDesc = document.getElementById('shortDesc');
                const fullDesc = document.getElementById('fullDesc');
                
                if (showMoreBtn) {
                    showMoreBtn.addEventListener('click', function(e) {
                        e.preventDefault();
                        shortDesc.style.display = 'none';
                        fullDesc.style.display = 'block';
                    });
                }
                
                if (showLessBtn) {
                    showLessBtn.addEventListener('click', function(e) {
                        e.preventDefault();
                        shortDesc.style.display = 'block';
                        fullDesc.style.display = 'none';
                    });
                }
            });
        </script>
    </body>
</html>
