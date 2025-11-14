<%-- 
    Document   : history_event_volunteer
    Created on : Nov 02, 2025, 3:18:00 PM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang lịch sử sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center">Lịch sử sự kiện đã tham gia</h1>

            <!-- Alert thông báo hủy đơn hoặc lỗi/success -->
            <c:if test="${not empty sessionScope.message}">
                <div class="alert
                     ${sessionScope.messageType == 'success' ? 'alert-success' :
                       sessionScope.messageType == 'warning' ? 'alert-warning' :
                       'alert-danger'}
                     alert-dismissible fade show" role="alert" id="autoHideAlert">
                    ${sessionScope.message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="message" scope="session"/>
                <c:remove var="messageType" scope="session"/>

                <script>
                    // Tự động ẩn sau 2 giây
                    setTimeout(function () {
                        var alertBox = document.getElementById("autoHideAlert");
                        if (alertBox) {
                            var alert = bootstrap.Alert.getOrCreateInstance(alertBox);
                            alert.close();
                        }
                    }, 2000);
                </script>
            </c:if>

            <!-- Alert thông báo lỗi feedback -->
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" id="autoHideAlert">
                    <i class="bi bi-exclamation-triangle-fill"></i> ${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="errorMessage" scope="session"/>

                <script>
                    // Tự động ẩn sau 3 giây
                    setTimeout(function () {
                        var alertBox = document.getElementById("autoHideAlert");
                        if (alertBox) {
                            var alert = bootstrap.Alert.getOrCreateInstance(alertBox);
                            alert.close();
                        }
                    }, 3000);
                </script>
            </c:if>

            <!-- Form filter và sắp xếp -->
            <div class="card shadow-sm border mb-3">
                <div class="card-body">
                    <form method="get" action="VolunteerEventServlet" class="row g-3">
                        <div class="col-md-4">
                            <label class="form-label fw-semibold">Trạng thái:</label>
                            <select name="status" class="form-select">
                                <option value="all" ${statusFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                                <option value="approved" ${statusFilter == 'approved' ? 'selected' : ''}>Được duyệt</option>
                                <option value="pending" ${statusFilter == 'pending' ? 'selected' : ''}>Chờ duyệt</option>
                                <option value="rejected" ${statusFilter == 'rejected' ? 'selected' : ''}>Từ chối</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-semibold">Sắp xếp:</label>
                            <select name="sort" class="form-select">
                                <option value="desc" ${sortOrder == 'desc' ? 'selected' : ''}>Mới nhất</option>
                                <option value="asc" ${sortOrder == 'asc' ? 'selected' : ''}>Cũ nhất</option>
                            </select>
                        </div>
                        <div class="col-md-4 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary me-2">
                                <i class="bi bi-filter"></i> Lọc
                            </button>
                            <a href="VolunteerEventServlet" class="btn btn-secondary">
                                <i class="bi bi-arrow-counterclockwise"></i> Reset
                            </a>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card shadow-sm border">
                <div class="card-body">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Tên sự kiện</th>
                                <th scope="col">Tổ chức</th>
                                <th scope="col">Danh mục</th>
                                <th scope="col">Ngày đăng ký</th>
                                <th scope="col">Trạng thái</th>
                                <th scope="col">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty eventRegistrations}">
                                    <c:forEach var="ev" items="${eventRegistrations}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td>${ev.eventTitle}</td>
                                            <td>${ev.organizationName}</td>
                                            <td>${ev.categoryName}</td>
                                            <td><fmt:formatDate value="${ev.applyDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${ev.status == 'approved'}">
                                                        <span class="badge bg-success">Được duyệt</span>
                                                    </c:when>
                                                    <c:when test="${ev.status == 'pending'}">
                                                        <span class="badge bg-warning text-dark">Chờ duyệt</span>
                                                    </c:when>
                                                    <c:when test="${ev.status == 'rejected'}">
                                                        <span class="badge bg-danger">Từ chối</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">${ev.status}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/VolunteerEventDetailServlet?eventId=${ev.eventId}&volunteerId=${sessionScope.account.id}" 
                                                   class="btn btn-sm btn-outline-warning me-2">
                                                    Xem chi tiết
                                                </a>

                                                <!-- Hiển thị nút hủy nếu đơn đang pending -->
                                                <c:if test="${ev.status == 'pending'}">
                                                    <button type="button" 
                                                            class="btn btn-sm btn-outline-danger"
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#cancelModal${ev.eventId}">
                                                        Hủy đăng ký
                                                    </button>

                                                    <!-- Modal xác nhận -->
                                                    <div class="modal fade" id="cancelModal${ev.eventId}" tabindex="-1" aria-labelledby="cancelModalLabel${ev.eventId}" aria-hidden="true">
                                                        <div class="modal-dialog modal-dialog-centered">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="cancelModalLabel${ev.eventId}">Xác nhận hủy đăng ký</h5>
                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    Bạn có chắc chắn muốn hủy đơn đăng ký sự kiện 
                                                                    <strong>${ev.eventTitle}</strong> không?
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <form action="${pageContext.request.contextPath}/CancelApplicationServlet" method="post">
                                                                        <input type="hidden" name="eventId" value="${ev.eventId}">
                                                                        <input type="hidden" name="volunteerId" value="${sessionScope.account.id}">
                                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                                                        <button type="submit" class="btn btn-danger">Xác nhận hủy</button>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>

                                                <!-- Hiển thị nút bình luận nếu không phải pending hoặc reject -->
                                                <c:if test="${ev.status == 'approved'}">
                                                    <a href="${pageContext.request.contextPath}/VolunteerFeedbackServlet?eventId=${ev.eventId}" 
                                                       class="btn btn-sm btn-outline-info">
                                                        Bình luận
                                                    </a>
                                                </c:if>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="8" class="text-center">Chưa đăng ký sự kiện nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Phân trang - LUÔN HIỆN -->
                    <c:if test="${totalPages >= 1}">
                        <nav aria-label="Page navigation" class="mt-4">
                            <ul class="pagination justify-content-center">
                                <!-- Previous -->
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="VolunteerEventServlet?page=${currentPage - 1}&status=${statusFilter}&sort=${sortOrder}">
                                        &lt; Trước
                                    </a>
                                </li>

                                <!-- Số trang -->
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="VolunteerEventServlet?page=${i}&status=${statusFilter}&sort=${sortOrder}">
                                            ${i}
                                        </a>
                                    </li>
                                </c:forEach>

                                <!-- Next -->
                                <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="VolunteerEventServlet?page=${currentPage + 1}&status=${statusFilter}&sort=${sortOrder}">
                                        Sau &gt;
                                    </a>
                                </li>
                            </ul>
                        </nav>

                        <!-- Thông tin phân trang -->
                        <div class="text-center text-muted">
                            Trang ${currentPage} / ${totalPages} (Tổng ${totalRecords} sự kiện)
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
