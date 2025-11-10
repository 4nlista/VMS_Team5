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
                <h1 class="mb-4">Hồ sơ tổ chức</h1>

                <div class="container py-4">
                    <div class="card shadow-sm p-4">
                        <div class="row">
                            <!-- Cột trái: Logo -->
                            <div class="col-md-4 text-center border-end">
                                <img src="${pageContext.request.contextPath}/OrganizationAvatar?file=${profile.avatar}&t=${now}" 
                                     style="max-width:250px;max-height:250px;object-fit:contain;" />

                                <h4 class="fw-bold">${profile.full_name}</h4>
                                <p class="text-muted">Mã tổ chức: ORG${profile.id}</p>
                            </div>

                            <!-- Cột phải: Thông tin -->
                            <div class="col-md-8">
                                <h5 class="mb-3">Thông tin chi tiết</h5>

                                <div class="row mb-2">
                                    <label class="col-sm-4 fw-bold">Họ tên:</label>
                                    <div class="col-sm-8">${profile.full_name}</div>
                                </div>

                                <div class="row mb-2">
                                    <label class="col-sm-4 fw-bold">Email:</label>
                                    <div class="col-sm-8">${profile.email}</div>
                                </div>

                                <div class="row mb-2">
                                    <label class="col-sm-4 fw-bold">Số điện thoại:</label>
                                    <div class="col-sm-8">${profile.phone}</div>
                                </div>

                                <div class="row mb-2">
                                    <label class="col-sm-4 fw-bold">Địa chỉ:</label>
                                    <div class="col-sm-8">${profile.address}</div>
                                </div>

                                <div class="row mb-2">
                                    <label class="col-sm-4 fw-bold">Nghề nghiệp:</label>
                                    <div class="col-sm-8">${profile.job_title}</div>
                                </div>

                                <div class="row mb-2">
                                    <label class="col-sm-4 fw-bold">Ngày sinh:</label>
                                    <div class="col-sm-8">
                                        <c:choose>
                                            <c:when test="${not empty profile.dob}">
                                                <fmt:formatDate value="${profile.dob}" pattern="dd/MM/yyyy" />
                                            </c:when>
                                            <c:otherwise>
                                                N/A
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <div class="row mb-2">
                                    <label class="col-sm-4 fw-bold">Mô tả:</label>
                                    <div class="col-sm-8">${profile.bio}</div>
                                </div>

                                <!-- Buttons -->
                                <div class="mt-4">
                                    <a href="${pageContext.request.contextPath}/OrganizationProfileEdit?id=${profile.id}" class="btn btn-primary">Chỉnh sửa</a>
                                    <a href="home_org.jsp" class="btn btn-secondary">Quay lại Dashboard</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
