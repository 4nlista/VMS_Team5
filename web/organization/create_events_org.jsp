<%-- 
    Document   : create_events_org
    Created on : Nov 1, 2025, 11:16:37 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
        <style>
            .image-preview {
                width: 100%;
                height: 300px;
                border: 2px dashed #dee2e6;
                border-radius: 8px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f8f9fa;
                overflow: hidden;
                position: relative;
            }
            .image-preview img {
                max-width: 100%;
                max-height: 100%;
                object-fit: cover;
            }
            .image-preview .placeholder {
                text-align: center;
                color: #6c757d;
            }
            .form-label {
                font-weight: 600;
                color: #495057;
            }
            .required::after {
                content: " *";
                color: #dc3545;
            }
        </style>
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <div class="main-content p-8">
                <div class="container-fluid">
                    <h2 class="fw-bold mb-4"> Tạo Sự Kiện Mới</h2>
                    <c:if test="${not empty param.msg}">
                        <div class="container mt-3">
                            <c:choose>
                                <c:when test="${param.msg == 'success'}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <strong>Thành công!</strong> Sự kiện đã được tạo.
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                </c:when>
                                <c:when test="${param.msg == 'error'}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <strong>Lỗi!</strong> Không thể tạo sự kiện. Vui lòng thử lại.
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                    </c:if>
                    <div class="card mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Bước 1: Tải ảnh sự kiện</h5>

                            <form method="post" action="UploadImagesServlet" enctype="multipart/form-data">
                                <div class="row">

                                    <div class="col-md-6">
                                        <%-- Preview ảnh --%>
                                        <c:if test="${not empty sessionScope.uploadedFileName}">
                                            <div class="image-preview">
                                                <img src="UploadImagesServlet?file=${sessionScope.uploadedFileName}" alt="Preview">
                                            </div>
                                            <small class="text-success d-block mt-2">
                                                <i class="bi bi-check-circle"></i> Đã tải lên: ${sessionScope.uploadedFileName}
                                            </small>
                                        </c:if>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Ảnh Sự Kiện</label>
                                        <c:choose>
                                            <c:when test="${not empty uploadedFileName}">
                                                <input type="text" class="form-control" value="${sessionScope.uploadedFileName}" readonly>
                                                <input type="hidden" name="uploadedImage" value="${sessionScope.uploadedFileName}">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control" value="Chưa tải ảnh" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Chọn Ảnh</label>
                                        <input type="file" class="form-control" name="eventImage" accept="image/*" 
                                               onchange="this.form.submit()" required>
                                    </div>

                                </div>
                            </form>
                        </div>
                    </div>

                    <form method="post" action="OrganizationCreateEventServlet" enctype="multipart/form-data">
                        <%-- ✅ ĐOẠN VỪA THÊM --%>
                        <c:if test="${not empty sessionScope.uploadedFileName}">
                            <input type="hidden" name="uploadedImage" value="${sessionScope.uploadedFileName}">
                        </c:if>
                        <%-- KẾT THÚC ĐOẠN THÊM --%>
                        <div class="card">
                            <div class="card-body">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label class="form-label required">Tiêu Đề</label>
                                        <input type="text" class="form-control" name="title" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Loại Sự Kiện</label>
                                        <select class="form-select" name="categoryId" required>
                                            <option value="">-- Chọn --</option>
                                            <option value="1">Y tế</option>
                                            <option value="2">Môi trường</option>
                                            <option value="3">Giáo dục</option>
                                            <option value="4">Xã hội</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Ngày Bắt Đầu</label>
                                        <input type="date" class="form-control" name="startDate" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Ngày Kết Thúc</label>
                                        <input type="date" class="form-control" name="endDate" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Số Lượng Tình Nguyện Viên</label>
                                        <input type="number" class="form-control" name="neededVolunteers" min="1" value="1" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Chế Độ</label>
                                        <select class="form-select" name="visibility" required>
                                            <option value="public">Công khai</option>
                                            <option value="private">Riêng tư</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label required">Địa Điểm</label>
                                        <input type="text" class="form-control" name="location" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Tổng Tiền Từ Thiện (VNĐ)</label>
                                        <input type="text" class="form-control" name="totalDonation" value="0">
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label required">Mô Tả</label>
                                        <textarea class="form-control" name="description" rows="5" required></textarea>
                                    </div>

                                </div>
                            </div>
                            <div class="text-end mb-3">
                                <div class="text-end mb-3">
                                    <a href="<%= request.getContextPath() %>/OrganizationListServlet" class="btn btn-secondary">Quay lại</a>
                                    <button type="submit" class="btn btn-primary">Tạo Sự Kiện</button>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
