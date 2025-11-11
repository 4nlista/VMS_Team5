<%-- 
    Document   : apply_org
    Created on : Oct 30, 2025, 8:53:08 PM
    Author     : Admin
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lí sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <%
                     Object sessionId = session.getId();
                     String fullname = (String) session.getAttribute("fullname");
                     if (fullname == null) {
                         fullname = "Khách";
                     }
            %>

            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />


            <!-- Main Content -->
            <div class="main-content p-4">
                <div class="container-fluid">

                    <h2 class="fw-bold mb-4">Danh sách đơn đăng kí</h2>

                    <!-- Form lọc -->
                    <form class="row g-3 align-items-center mb-3" method="get" action="OrganizationApplyServlet">
                        <!-- giữ eventId -->
                        <input type="hidden" name="id" value="${eventId}" />

                        <div class="col-auto">
                            <label for="statusFilter" class="col-form-label fw-semibold">Trạng thái:</label>
                        </div>
                        <div class="col-auto">
                            <select id="statusFilter" name="statusFilter" class="form-select form-select-sm">
                                <option value="all" ${statusFilter == 'all' ? 'selected' : ''}>Tất cả</option>
                                <option value="pending" ${statusFilter == 'pending' ? 'selected' : ''}>Chưa xử lý</option>
                                <option value="approved" ${statusFilter == 'approved' ? 'selected' : ''}>Đã xử lý</option>
                                <option value="rejected" ${statusFilter == 'rejected' ? 'selected' : ''}>Đã từ chối</option>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <i class="bi bi-filter"></i> Lọc
                            </button>
                        </div>
                    </form>


                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead class="table-secondary">
                                <tr>
                                    <th style="width:5%;">STT</th>
                                    <th style="width:15%;">Tên</th>
                                    <th style="width:15%;">Ngày nộp đơn</th>
                                    <th style="width:15%;">Trạng thái</th>
                                    <th style="width:20%;">Ghi chú</th>
                                    <th style="width:15%;">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="v" items="${volunteers}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${v.volunteerName}</td>
                                        <td> <fmt:formatDate value="${v.applyDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${v.status == 'approved'}">
                                                    <span class="badge bg-success">Đã xử lý</span>
                                                </c:when>
                                                <c:when test="${v.status == 'pending'}">
                                                    <span class="badge bg-warning text-dark">Chưa xử lý</span>
                                                </c:when>
                                                <c:when test="${v.status == 'rejected'}">
                                                    <span class="badge bg-danger text-white">Từ chối</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-light text-dark">${v.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${v.note}</td>

                                        <!--                                        thao tác xử lý-->
                                        <td>
                                            <c:choose>
                                                <c:when test="${v.status == 'pending'}">
                                                    <!-- Form duyệt -->
                                                    <form method="post" action="OrganizationApplyServlet" class="d-inline">
                                                        <input type="hidden" name="action" value="approve"/>
                                                        <input type="hidden" name="id" value="${v.id}"/>
                                                        <input type="hidden" name="eventId" value="${v.eventId}"/>
                                                        <button type="submit" class="btn btn-success btn-sm">
                                                            Chấp nhận
                                                        </button>
                                                    </form>

                                                    <!-- Form từ chối -->
                                                    <form method="post" action="OrganizationApplyServlet" class="d-inline">
                                                        <input type="hidden" name="action" value="reject"/>
                                                        <input type="hidden" name="id" value="${v.id}"/>
                                                        <input type="hidden" name="eventId" value="${v.eventId}"/>
                                                        <button type="submit" class="btn btn-danger btn-sm">
                                                            Từ chối
                                                        </button>
                                                    </form>
                                                </c:when>

                                                <c:when test="${v.status == 'approved'}">
                                                    <span class="badge bg-success">Đã duyệt</span>
                                                </c:when>

                                                <c:when test="${v.status == 'rejected'}">
                                                    <span class="badge bg-danger">Đã từ chối</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <!--                                        hết thao tác xử lý-->
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        
                        <div class="d-flex justify-content-end mt-3">
                            <a href="${pageContext.request.contextPath}/OrganizationApplyServlet" class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> Quay lại
                            </a>
                        </div>
                        <!-- Phân trang -->
                        <div class="d-flex justify-content-between align-items-center mt-3">
                            <span>Hiển thị phân trang sự kiện</span>
                            <ul class="pagination pagination-sm mb-0">
                                <li class="page-item disabled"><a class="page-link" href="#">Trước</a></li>
                                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                <li class="page-item"><a class="page-link" href="#">Sau</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
