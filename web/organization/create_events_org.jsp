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

            <div class="main-content p-4">
                <div class="container-fluid">
                    <h2 class="fw-bold mb-4"><i class="bi bi-calendar-plus"></i> Tạo Sự Kiện Mới</h2>

                    <form method="post" action="CreateEventServlet" enctype="multipart/form-data">
                        <div class="card">
                            <div class="card-body">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">ID Sự Kiện <span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" name="eventId" required>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tiêu Đề <span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" name="title" required>
                                    </div>

                                    <div class="col-md-4">
                                        <label class="form-label fw-semibold">Ảnh Sự Kiện <span class="text-danger">*</span></label>
                                        <input type="file" class="form-control mb-2" name="eventImage" accept="image/*" required onchange="document.getElementById('preview').src = window.URL.createObjectURL(this.files[0])">
                                        <img id="preview" src="https://via.placeholder.com/400x250?text=Ch%E1%BB%8Dn+%E1%BA%A3nh" class="img-fluid border rounded" alt="Preview">
                                    </div>

                                    <div class="col-md-8">
                                        <div class="row g-3">
                                            <div class="col-12">
                                                <label class="form-label fw-semibold">Mô Tả <span class="text-danger">*</span></label>
                                                <textarea class="form-control" name="description" rows="8" required></textarea>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Ngày Bắt Đầu <span class="text-danger">*</span></label>
                                        <input type="datetime-local" class="form-control" name="startDate" required>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Ngày Kết Thúc <span class="text-danger">*</span></label>
                                        <input type="datetime-local" class="form-control" name="endDate" required>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Địa Điểm <span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" name="location" required>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Số Lượng Tình Nguyện Viên <span class="text-danger">*</span></label>
                                        <input type="number" class="form-control" name="volunteers" min="0" value="0" required>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Chế Độ <span class="text-danger">*</span></label>
                                        <select class="form-select" name="privacy" required>
                                            <option value="">-- Chọn --</option>
                                            <option value="public">Công khai</option>
                                            <option value="private">Riêng tư</option>
                                        </select>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Loại Sự Kiện <span class="text-danger">*</span></label>
                                        <select class="form-select" name="eventType" required>
                                            <option value="">-- Chọn --</option>
                                            <option value="health">Y tế</option>
                                            <option value="environment">Môi trường</option>
                                            <option value="education">Giáo dục</option>
                                            <option value="social">Xã hội</option>
                                        </select>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tổng Tiền Từ Thiện (VNĐ)</label>
                                        <input type="text" class="form-control" name="donationAmount" value="0">
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer text-end">
                                <button type="button" class="btn btn-secondary" onclick="history.back()">
                                    <i class="bi bi-x-circle"></i> Hủy
                                </button>
                                <button type="reset" class="btn btn-outline-warning">
                                    <i class="bi bi-arrow-clockwise"></i> Đặt lại
                                </button>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-circle"></i> Tạo Sự Kiện
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
