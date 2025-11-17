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

            <!-- Success Message -->
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill"></i> <strong>Thành công!</strong> ${sessionScope.successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>

            <!-- Error Message -->
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill"></i> <strong>Lỗi!</strong> ${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <div class="card shadow-sm border">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover align-middle" style="table-layout: fixed; width: 100%;">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="col" style="width:4%;">STT</th>
                                    <th scope="col" style="width:12%;">Mã giao dịch</th>
                                    <th scope="col" style="width:18%;">Sự kiện</th>
                                    <th scope="col" style="width:10%;">Số tiền</th>
                                    <th scope="col" style="width:10%;">Phương thức</th>
                                    <th scope="col" style="width:13%;">Ngày thanh toán</th>
                                    <th scope="col" style="width:13%; word-wrap: break-word; overflow-wrap: break-word;">Ghi chú</th>
                                    <th scope="col" style="width:8%;">Trạng thái</th>
                                    <th scope="col" style="width:7%;">Chi tiết</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:if test="${empty volunteerDonations}">
                                    <tr>
                                        <td colspan="9" class="text-center">Chưa có giao dịch nào.</td>
                                    </tr>
                                </c:if>

                                <c:forEach var="d" items="${volunteerDonations}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1 + (currentPage - 1) * pageSize}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${d.paymentTxnRef != null && !d.paymentTxnRef.isEmpty()}">
                                                    <small>${d.paymentTxnRef}</small>
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${d.eventTitle != null ? d.eventTitle : "-"}</td>
                                        <td>
                                            <fmt:formatNumber value="${d.amount}" type="number" groupingUsed="true"/> VNĐ
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${d.paymentMethod == 'VNPay'}">
                                                    <i class="bi bi-credit-card text-primary"></i> VNPay
                                                </c:when>
                                                <c:when test="${d.paymentMethod == 'QR'}">
                                                    <i class="bi bi-qr-code"></i> QR Code
                                                </c:when>
                                                <c:otherwise>${d.paymentMethod != null ? d.paymentMethod : "-"}</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${d.donateDate != null}">
                                                    <fmt:formatDate value="${d.donateDate}" pattern="dd/MM/yyyy HH:mm" />
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="max-width: 150px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                                            title="${d.note != null ? d.note : ''}">
                                            ${d.note != null ? d.note : ""}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${d.status == 'success'}">
                                                    <span class="badge bg-success text-white fw-bold">
                                                        <i class="bi bi-check-circle"></i> Thành công
                                                    </span>
                                                </c:when>
                                                <c:when test="${d.status == 'failed'}">
                                                    <span class="badge bg-danger text-white fw-bold">
                                                        <i class="bi bi-x-circle"></i> Thất bại
                                                    </span>
                                                </c:when>
                                                <c:when test="${d.status == 'pending'}">
                                                    <span class="badge bg-warning text-white fw-bold">
                                                        <i class="bi bi-clock"></i> Đang xử lý
                                                    </span>
                                                </c:when>
                                                <c:when test="${d.status == 'cancelled'}">
                                                    <span class="badge bg-secondary text-white fw-bold">
                                                        <i class="bi bi-slash-circle"></i> Đã hủy
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary text-white fw-bold">${d.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:url var="detailUrl" value="/VolunteerDonationDetailServlet">
                                                <c:param name="donationId" value="${d.id}"/>
                                                <c:param name="page" value="${currentPage}"/>
                                                <c:if test="${not empty startDate}">
                                                    <c:param name="startDate" value="${startDate}"/>
                                                </c:if>
                                                <c:if test="${not empty endDate}">
                                                    <c:param name="endDate" value="${endDate}"/>
                                                </c:if>
                                                <c:if test="${not empty statusFilter}">
                                                    <c:param name="status" value="${statusFilter}"/>
                                                </c:if>
                                            </c:url>
                                            <a href="${detailUrl}" class="btn btn-sm btn-info text-white fw-bold">
                                                <i class="bi bi-eye text-white"></i> Chi tiết
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