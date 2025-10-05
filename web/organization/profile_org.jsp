<%-- 
    Document   : profile_org
    Created on : Sep 30, 2025, 1:21:45 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                                <img src="https://via.placeholder.com/180x180.png?text=Logo" 
                                     alt="Logo tổ chức" class="rounded-circle mb-3" />
                                <h4 class="fw-bold">Tên tổ chức</h4>
                                <p class="text-muted">Mã tổ chức: ORG001</p>
                            </div>

                            <!-- Cột phải: Thông tin -->
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

                                <!-- Buttons -->
                                <div class="mt-4">
                                    <a href="edit_org_profile.jsp" class="btn btn-primary me-2">Chỉnh sửa hồ sơ</a>
                                    <a href="dashboard_org.jsp" class="btn btn-secondary">Quay lại Dashboard</a>
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
