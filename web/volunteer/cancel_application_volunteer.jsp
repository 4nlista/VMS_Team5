<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hủy đăng ký sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center text-danger">Hủy đăng ký sự kiện</h1>

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
                            <h5 class="card-title fw-bold mb-3">Thông tin sự kiện</h5>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <strong>Tiêu đề:</strong> ${event.title}
                                </li>
                                <li class="list-group-item">
                                    <strong>Người tổ chức:</strong> ${event.organizationName}
                                </li>
                                <li class="list-group-item">
                                    <strong>Thời gian:</strong>
                                    <fmt:formatDate value="${event.startDate}" pattern="yyyy-MM-dd HH:mm" /> 
                                    đến 
                                    <fmt:formatDate value="${event.endDate}" pattern="yyyy-MM-dd HH:mm" />
                                </li>
                                <li class="list-group-item">
                                    <strong>Địa điểm:</strong> ${event.location}
                                </li>
                                <li class="list-group-item">
                                    <strong>Danh mục:</strong> ${event.categoryName}
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Cột phải: Thông tin đơn đăng ký -->
                <div class="col-md-6">
                    <div class="card border shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title fw-bold mb-3 text-danger">Xác nhận hủy đơn đăng ký</h5>

                            <ul class="list-group list-group-flush mb-3">
                                <li class="list-group-item">
                                    <strong>Họ tên:</strong> ${registration.volunteerName}
                                </li>
                                <li class="list-group-item">
                                    <strong>Email:</strong> ${registration.volunteerEmail}
                                </li>
                                <li class="list-group-item">
                                    <strong>Ghi chú:</strong> ${registration.note}
                                </li>
                                <li class="list-group-item">
                                    <strong>Ngày nộp đơn:</strong> 
                                    <fmt:formatDate value="${registration.applyDate}" pattern="dd/MM/yyyy HH:mm" />
                                </li>
                                <li class="list-group-item">
                                    <strong>Trạng thái hiện tại:</strong> 
                                    <span class="badge bg-warning text-dark">${registration.status}</span>
                                </li>
                            </ul>

                            <div class="alert alert-warning">
                                <i class="bi bi-exclamation-triangle-fill"></i>
                                <strong>Lưu ý:</strong> Sau khi hủy, bạn sẽ không thể khôi phục lại đơn đăng ký này.
                            </div>

                            <form action="${pageContext.request.contextPath}/CancelApplicationServlet" method="post">
                                <input type="hidden" name="eventId" value="${registration.eventId}">
                                <input type="hidden" name="volunteerId" value="${registration.volunteerId}">

                                <div class="d-flex justify-content-between">
                                    <a href="${pageContext.request.contextPath}/volunteer/history_event.jsp" class="btn btn-secondary">
                                        <i class="bi bi-arrow-left"></i> Quay lại
                                    </a>
                                    <button type="submit" class="btn btn-danger">
                                        <i class="bi bi-trash"></i> Xác nhận hủy đăng ký
                                    </button>
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
