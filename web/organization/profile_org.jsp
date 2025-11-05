<%-- 
    Document   : profile_org
    Created on : Sep 30, 2025, 1:21:45 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang hồ sơ cá nhân</title>
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
            <div class="main-content">
                <c:choose>
                    <c:when test="${not empty profile}">
                        <h1 class="mb-4">Hồ sơ tình nguyện viên</h1>
                        <div class="container py-4">
                            <div class="card shadow-sm p-4">
                                <div class="row g-0">
                                    <!-- LEFT: Avatar + name -->
                                    <div class="col-md-4 d-flex flex-column align-items-center justify-content-center text-center p-4">
                                        <img src="${not empty profile.images ? profile.images : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}"
                                             alt="avatar" class="rounded-circle mb-3 border" style="width:160px;height:160px;object-fit:cover;"/>
                                        <div class="fw-semibold fs-5">${profile.fullName}</div>
                                        <div class="mt-2 text-muted small">${profile.organizationName}</div>
                                    </div>

                                    <!-- RIGHT: Fields -->
                                    <div class="col-md-8 p-4">
                                        <div class="row g-3">
                                            <div class="col-md-6">
                                                <label class="form-label">ID</label>
                                                <input type="text" class="form-control form-control-sm" value="${profile.id}" readonly>
                                            </div>
                                            <!-- Bỏ hiển thị sự kiện gần nhất -->

                                            <div class="col-md-6">
                                                <label class="form-label">Giới tính</label>
                                                <input type="text" class="form-control form-control-sm" value="${profile.gender}" readonly>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Ngày sinh</label>
                                                <c:choose>
                                                    <c:when test="${not empty profile.dob}">
                                                        <fmt:formatDate value="${profile.dob}" pattern="dd-MM-yyyy" var="dobFmt" />
                                                        <input type="text" class="form-control form-control-sm" value="${dobFmt}" readonly>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="text" class="form-control form-control-sm" value="N/A" readonly>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                            <div class="col-md-6">
                                                <label class="form-label">Email</label>
                                                <input type="email" class="form-control form-control-sm" value="${profile.email}" readonly>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Địa chỉ</label>
                                                <input type="text" class="form-control form-control-sm" value="${profile.address}" readonly>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Số điện thoại</label>
                                                <input type="text" class="form-control form-control-sm" value="${profile.phone}" readonly>
                                            </div>

                                            <div class="col-md-4">
                                                <label class="form-label">Tổng sự kiện</label>
                                                <input type="text" class="form-control form-control-sm" value="${profile.totalEvents}" readonly>
                                            </div>
                                            <div class="col-md-4">
                                                <label class="form-label">Tổng giờ</label>
                                                <input type="text" class="form-control form-control-sm" value="${profile.totalHours}" readonly>
                                            </div>
                                            <div class="col-md-4">
                                                <label class="form-label">Tổng quyên góp</label>
                                                <input type="text" class="form-control form-control-sm" value="${profile.totalDonated}" readonly>
                                            </div>
                                        </div>

                                        <!-- Danh sách sự kiện đã tham gia (thuộc tổ chức hiện tại) - hiển thị bên phải -->
                                        <div class="mt-4">
                                            <h5 class="mb-3">Sự kiện đã tham gia</h5>
                                            <c:choose>
                                                <c:when test="${not empty eventsTitles}">
                                                    <ul class="list-group list-group-flush">
                                                        <c:forEach var="t" items="${eventsTitles}">
                                                            <li class="list-group-item px-0">${t}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-muted">Chưa có sự kiện nào.</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <div class="mt-3 text-end">
                                    <a href="javascript:history.back()" class="btn btn-secondary btn-sm">Quay lại</a>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h1 class="mb-4">Hồ sơ tổ chức</h1>
                        <div class="container py-4">
                            <div class="card shadow-sm p-4">
                                <div class="row">
                                    <div class="col-md-4 text-center border-end">
                                        <img src="https://via.placeholder.com/180x180.png?text=Logo" 
                                             alt="Logo tổ chức" class="rounded-circle mb-3" />
                                        <h4 class="fw-bold">Tên tổ chức</h4>
                                        <p class="text-muted">Mã tổ chức: ORG001</p>
                                    </div>
                                    <div class="col-md-8">
                                        <h5 class="mb-3">Thông tin chi tiết</h5>
                                        <div class="row mb-2">
                                            <label class="col-sm-4 fw-bold">Người đại diện:</label>
                                            <div class="col-sm-8">Nguyễn Văn A</div>
                                        </div>
                                        <div class="row mb-2">
                                            <label class="col-sm-4 fw-bold">Email:</label>
                                            <div class="col-sm-8">org@example.com</div>
                                        </div>
                                        <div class="row mb-2">
                                            <label class="col-sm-4 fw-bold">Số điện thoại:</label>
                                            <div class="col-sm-8">0123 456 789</div>
                                        </div>
                                        <div class="row mb-2">
                                            <label class="col-sm-4 fw-bold">Địa chỉ:</label>
                                            <div class="col-sm-8">Tầng 5, Tòa nhà ABC, Hà Nội</div>
                                        </div>
                                        <div class="row mb-2">
                                            <label class="col-sm-4 fw-bold">Mô tả:</label>
                                            <div class="col-sm-8">Tổ chức phi lợi nhuận chuyên hỗ trợ trẻ em vùng cao.</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
