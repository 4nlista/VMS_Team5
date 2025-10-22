<%-- 
    Document   : create_event
    Created on : Oct 17, 2025
    Author     : locng
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo sự kiện mới</title>

        <!-- Bootstrap & icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />

        <!-- CSS admin -->
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="/admin/layout_admin/sidebar_admin.jsp" />

            <!-- Nội dung chính -->
            <div class="main-content p-4">
                <h2 class="mb-4">Thêm sự kiện mới</h2>

                <!-- Hiển thị lỗi nếu có -->
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>

                <form action="CreateEventServlet" method="post" class="needs-validation" novalidate>
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">Tiêu đề sự kiện</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" required>
                            <div class="invalid-feedback">Vui lòng nhập tiêu đề.</div>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">Mô tả</label>
                        <div class="col-sm-10">
                            <textarea name="description" class="form-control" rows="4" required></textarea>
                            <div class="invalid-feedback">Vui lòng nhập mô tả.</div>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">Ngày bắt đầu</label>
                        <div class="col-sm-4">
                            <input type="date" name="start_date" class="form-control" required>
                        </div>

                        <label class="col-sm-2 col-form-label">Ngày kết thúc</label>
                        <div class="col-sm-4">
                            <input type="date" name="end_date" class="form-control" required>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">Địa điểm</label>
                        <div class="col-sm-10">
                            <input type="text" name="location" class="form-control" required>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">Số tình nguyện viên cần</label>
                        <div class="col-sm-4">
                            <input type="number" name="needed_volunteers" class="form-control" min="0" value="0" required>
                        </div>

                        <label class="col-sm-2 col-form-label">Phân loại (Category)</label>
                        <div class="col-sm-4">
                            <select name="category_id" class="form-select">
                                <option value="0">-- Chọn category --</option>
                                <c:forEach var="c" items="${categories}">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label">Trạng thái</label>
                        <div class="col-sm-4">
                            <select name="status" class="form-select">
                                <option value="INACTIVE">Inactive</option>
                                <option value="ACTIVE">Active</option>
                            </select>
                        </div>

                        <label class="col-sm-2 col-form-label">Tổng donate ban đầu</label>
                        <div class="col-sm-4">
                            <input type="number" name="total_donation" class="form-control" value="0" step="0.01" min="0">
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-sm-10 offset-sm-2">
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-save"></i> Lưu sự kiện
                            </button>
                            <a href="${pageContext.request.contextPath}/ListEventsServlet" class="btn btn-secondary">
                                <i class="bi bi-arrow-left-circle"></i> Quay lại
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
