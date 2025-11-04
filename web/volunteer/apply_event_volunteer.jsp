<%-- 
    Document   : apply_event_volunteer
    Created on : Sep 29, 2025, 4:37:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang đăng kí tham gia sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />


        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center">Đăng ký tham gia sự kiện</h1>
            <c:if test="${not empty message}">
                <div class="alert
                     ${messageType == 'success' ? 'alert-success' : 
                       messageType == 'warning' ? 'alert-warning' : 
                       'alert-danger'}
                     alert-dismissible fade show" role="alert">
                    ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <div class="row">
                <!-- Cột trái: Thông tin sự kiện -->
                <div class="col-md-6">
                    <div class="card border shadow-sm mb-4">
                        <div class="card-body">
                            <h5 class="card-title d-flex justify-content-between align-items-center">
                                <span class="fw-bold">Thông tin sự kiện</span>
                                <span class="badge bg-success">Active</span>
                            </h5>
                            <ul class="list-group list-group-flush mt-3">
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Tiêu đề:</span>
                                    <span class="text-secondary ms-2">${event.title}</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Người tổ chức:</span>
                                    <span class="text-secondary ms-2">${event.organizationName}</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Thời gian:</span>
                                    <span class="text-secondary ms-2">
                                        <fmt:formatDate value="${event.startDate}" pattern="yyyy-MM-dd  HH:mm" /> 
                                        [ đến ]
                                        <fmt:formatDate value="${event.endDate}" pattern="yyyy-MM-dd HH:mm" />
                                    </span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Địa điểm:</span>
                                    <span class="text-secondary ms-2">${event.location}</span>
                                </li>

                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Số lượng tình nguyện viên cần:</span>
                                    <span class="text-secondary ms-2">${event.neededVolunteers}</span>
                                </li>
                                <li class="list-group-item">
                                    <span class="fw-bold text-dark">Mô tả sự kiện:</span>
                                    <span class="text-secondary ms-2">${event.description}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Cột phải: Form đăng ký -->
                <div class="col-md-6">
                    <div class="card border shadow-sm">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/VolunteerApplyEventServlet" method="post">
                                <input type="hidden" name="volunteerId" value="${volunteerId}" />
                                <input type="hidden" name="eventId" value="${event.id}" />

                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Họ tên</label>
                                        <input type="text" class="form-control" value="${user.full_name}" readonly>
                                    </div>
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Email</label>
                                        <input type="email" class="form-control" value="${user.email}" readonly>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <label class="form-label fw-bold">Số giờ đăng ký</label>
                                        <input type="number" class="form-control" name="hours" placeholder="Nhập số giờ" required>
                                    </div>
                                </div>

                                <div class="mb-2">
                                    <label class="form-label fw-bold">Ghi chú</label>
                                    <textarea class="form-control" rows="2" name="note" placeholder="Ghi chú thêm..."></textarea>
                                </div>

                                <div class="d-flex justify-content-between">
                                    <c:if test="${isFull}">
                                        <div class="alert alert-warning">
                                            <i class="icon-info"></i> Sự kiện đã đủ slot. Không thể đăng ký thêm.
                                        </div>
                                    </c:if>

                                    <button type="submit" class="btn btn-primary" ${isFull ? 'disabled' : ''}>
                                        Đăng ký
                                    </button>
                                    <button type="reset" class="btn btn-outline-secondary">Hủy</button>
                                </div>
                            </form>
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
