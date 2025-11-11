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

            <!-- Thêm vào đầu trang history_event_volunteer.jsp, sau thẻ <h1> -->
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" id="errAlert">
                    ${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="errorMessage" scope="session"/>

                <script>
                    setTimeout(() => bootstrap.Alert.getOrCreateInstance('#errAlert').close(), 3000);
                </script>
            </c:if>

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
                                <!--                                <th scope="col">Số giờ</th>-->
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
                                            <td><fmt:formatDate value="${ev.applyDate}" pattern="dd/MM/yyyy" /></td>
<!--                                            <td>${ev.hours}</td>-->
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
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
