<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-5 text-center text-primary fw-bold">Chi tiết sự kiện</h1>

            <div class="row justify-content-center">
                <div class="col-md-10 col-lg-8">
                    <div class="card shadow-lg border-0 rounded-3">
                        <!-- Header -->
                        <div class="card-header bg-gradient bg-primary text-white text-center fs-5 fw-bold">
                            ${event.title}
                        </div>

                        <!-- Body -->
                        <div class="card-body p-4">
                            <table class="table table-bordered align-middle">
                                <tbody>
                                    <tr>
                                        <th class="bg-light w-40">Người tổ chức</th>
                                        <td>${event.organizationId}</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Thời gian</th>
                                        <td>
                                            <fmt:formatDate value="${event.startDate}" pattern="dd/MM/yyyy HH:mm" /> -
                                            <fmt:formatDate value="${event.endDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Địa điểm</th>
                                        <td>${event.location}</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Số lượng tình nguyện viên cần</th>
                                        <td>${event.neededVolunteers}</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Trạng thái</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${event.status == 'active'}">
                                                    <span class="badge bg-success">Đang mở đăng ký</span>
                                                </c:when>
                                                <c:when test="${event.status == 'ended'}">
                                                    <span class="badge bg-danger">Đã kết thúc</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${event.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Mô tả sự kiện</th>
                                        <td>${event.description}</td>
                                    </tr>
                                    <tr>
                                        <th class="bg-light">Tổng số tiền quyên góp</th>
                                        <td><fmt:formatNumber value="${event.totalDonation}" type="currency" currencySymbol="₫"/></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <!-- Footer -->
                        <div class="card-footer text-center bg-white border-0 pb-4">
                            <a href="<%= request.getContextPath() %>/EventListServlet" class="btn btn-primary px-4">
                                ← Quay lại
                            </a>
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
