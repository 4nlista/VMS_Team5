<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang lịch sử thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    <jsp:include page="/layout/header.jsp" />
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container-fluid mt-5 pt-5 pb-5" style="max-width: 1400px;">
            <h1 class="mb-4 text-center">Lịch sử thanh toán</h1>

            <!-- Form lọc theo ngày -->
            <div class="card mb-4 shadow-sm">
                <div class="card-body">
                    <form method="GET" action="<%= request.getContextPath() %>/VolunteerDonateServlet" class="row g-3 align-items-end">
                        <div class="col-md-3">
                            <label for="startDate" class="form-label fw-bold">
                                <i class="bi bi-calendar-event"></i> Từ ngày
                            </label>
                            <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                        </div>
                        <div class="col-md-3">
                            <label for="endDate" class="form-label fw-bold">
                                <i class="bi bi-calendar-check"></i> Đến ngày
                            </label>
                            <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                        </div>
                        <div class="col-md-3">
                            <label for="status" class="form-label fw-bold">
                                <i class="bi bi-check-circle"></i> Trạng thái
                            </label>
                            <select class="form-select" id="status" name="status">
                                <option value="all" ${statusFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                                <option value="success" ${statusFilter == 'success' ? 'selected' : ''}>Thành công</option>
                                <option value="pending" ${statusFilter == 'pending' ? 'selected' : ''}>Đang xử lý</option>
                                <option value="cancelled" ${statusFilter == 'cancelled' ? 'selected' : ''}>Bị từ chối</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <input type="hidden" name="page" value="1">
                            <button type="submit" class="btn btn-primary me-2">
                                <i class="bi bi-funnel"></i> Lọc
                            </button>
                            <a href="<%= request.getContextPath() %>/VolunteerDonateServlet" class="btn btn-secondary">
                                <i class="bi bi-x-circle"></i> Xóa lọc
                            </a>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card shadow-sm border">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover align-middle" style="table-layout: fixed; width: 100%;">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="col" style="width:5%;">STT</th>
                                    <!--                                    <th scope="col" style="width:13%;">Mã QR</th>-->
                                    <th scope="col" style="width:25%;">Sự kiện</th>
                                    <th scope="col" style="width:10%;">Số tiền</th>
                                    <th scope="col" style="width:15%;">Phương thức</th>
                                    <th scope="col" style="width:15%;">Ngày thanh toán</th>
                                    <!--                                    <th scope="col" style="width:15%; word-wrap: break-word; overflow-wrap: break-word;">Ghi chú</th>-->
                                    <th scope="col" style="width:15%;">Trạng thái</th>
                                    <!--tôi thêm button này nhé -->
                                    <th scope="col" style="width:15%;">Xem chi tiết</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:if test="${empty volunteerDonations}">
                                    <tr>
                                        <td colspan="8" class="text-center">Chưa có giao dịch nào.</td>
                                    </tr>
                                </c:if>

                                <c:forEach var="d" items="${volunteerDonations}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1 + (currentPage - 1) * pageSize}</td>
<!--                                        <td>${d.qrCode != null ? d.qrCode : "-"}</td>-->
                                        <td>${d.eventTitle != null ? d.eventTitle : "-"}</td>
                                        <td><fmt:formatNumber value="${d.amount}" pattern="#,###" /> </td>
                                        <td>${d.paymentMethod != null ? d.paymentMethod : "-"}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${d.donateDate != null}">
                                                    <fmt:formatDate value="${d.donateDate}" pattern="dd/MM/yyyy HH:mm" />
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
<!--                                        <td>${d.note != null ? d.note : ""}</td>-->
                                        <td>
                                            <c:choose>
                                                <c:when test="${d.status == 'success'}">
                                                    <span class="badge bg-success">Thành công</span>
                                                </c:when>
                                                <c:when test="${d.status == 'pending'}">
                                                    <span class="badge bg-warning text-dark">Đang xử lý</span>
                                                </c:when>
                                                <c:when test="${d.status == 'cancelled'}">
                                                    <span class="badge bg-danger">Bị từ chối</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${d.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/VolunteerDetailPaymentServlet?donationId=${d.id}" 
                                               class="btn btn-sm btn-info">
                                                <i class="bi bi-eye"></i> Xem chi tiết
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>

                        <!-- LUÔN HIỂN THỊ PHÂN TRANG -->
                        <c:if test="${totalPages >= 1}">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center mt-3">

                                    <!-- Nút Trước (disabled nếu ở trang 1) -->
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" 
                                           href="?page=${currentPage - 1}&startDate=${startDate}&endDate=${endDate}&status=${statusFilter}">
                                            &lt; Trước
                                        </a>
                                    </li>

                                    <!-- Các nút số trang -->
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="?page=${i}&startDate=${startDate}&endDate=${endDate}&status=${statusFilter}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <!-- Nút Sau (disabled nếu ở trang cuối) -->
                                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                        <a class="page-link" 
                                           href="?page=${currentPage + 1}&startDate=${startDate}&endDate=${endDate}&status=${statusFilter}">
                                            Sau &gt;
                                        </a>
                                    </li>

                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>