<%-- 
    Document   : create_news_org
    Created on : Nov 2, 2025, 2:59:57 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo bài viết tin tức</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />
            <div class="main-content p-4">
                <div class="container d-flex justify-content-center">
                    <div class="w-100" style="max-width: 800px;">
                        <h3 class="fw-bold mb-4 text-center">Tạo bài viết tin tức</h3>
                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">

                                <!-- show generic errorMessage if any -->
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger">${errorMessage}</div>
                                </c:if>

                                <form action="${pageContext.request.contextPath}/OrganizationNewsCreate"
                                      method="post" enctype="multipart/form-data" class="row g-3">

                                    <input type="hidden" name="organizationId" value="${orgId}" />

                                    <!-- Title -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Tiêu đề</label>
                                        <input type="text" class="form-control" name="title"
                                               value="${newsInput.title}" placeholder="Nhập tiêu đề bài viết">
                                        <c:if test="${fieldErrors.title != null}">
                                            <div class="text-danger small">${fieldErrors.title}</div>
                                        </c:if>
                                    </div>

                                    <!-- Image preview + upload -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Ảnh tin tức</label>
                                        
                                        <div class="col-12 text-center mb-3">
                                            <img id="newsImagePreview" 
                                                 src="https://cdn-icons-png.flaticon.com/512/3342/3342137.png"
                                                 style="max-width:300px; max-height:300px; object-fit:contain; border:2px dashed #dee2e6; border-radius:8px; padding:20px; background-color:#f8f9fa;" />
                                        </div>
                                        
                                        <input type="file" class="form-control" name="newsImage" id="newsImage" accept="image/*" required>
                                        <small class="text-muted">Kích thước tối đa: 2MB. Định dạng: JPG, PNG, GIF, WebP</small>
                                        <div id="newsFileError" class="text-danger mt-2" style="display: none;"></div>
                                        
                                        <c:if test="${fieldErrors.image != null}">
                                            <div class="text-danger small">${fieldErrors.image}</div>
                                        </c:if>
                                    </div>

                                    <!-- Content -->
                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Nội dung</label>
                                        <textarea class="form-control" name="content" rows="6" placeholder="Nhập nội dung bài viết...">${newsInput.content}</textarea>
                                        <c:if test="${fieldErrors.content != null}">
                                            <div class="text-danger small">${fieldErrors.content}</div>
                                        </c:if>
                                    </div>

                                    <!-- Buttons -->
                                    <div class="col-12 mt-3">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <a href="${pageContext.request.contextPath}/OrganizationManageNews" class="btn btn-secondary">
                                                <i class="bi bi-arrow-left me-1"></i> Quay lại
                                            </a>
                                            <div>
                                                <button type="submit" class="btn btn-primary me-2">
                                                    <i class="bi bi-upload me-1"></i> Tạo bài viết
                                                </button>
                                                <a href="${pageContext.request.contextPath}/OrganizationManageNews" class="btn btn-outline-danger">
                                                    <i class="bi bi-x-circle me-1"></i> Hủy
                                                </a>
                                            </div>
                                        </div>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Preview ảnh và validate kích thước
            const fileInput = document.getElementById('newsImage');
            const imagePreview = document.getElementById('newsImagePreview');
            const fileError = document.getElementById('newsFileError');
            const MAX_SIZE = 2 * 1024 * 1024; // 2MB

            fileInput.addEventListener('change', function(e) {
                const file = e.target.files[0];
                fileError.style.display = 'none';
                
                if (!file) {
                    imagePreview.src = 'https://cdn-icons-png.flaticon.com/512/3342/3342137.png';
                    return;
                }

                // Validate file type
                if (!file.type.startsWith('image/')) {
                    fileError.textContent = '⚠️ File phải là ảnh (JPG, PNG, GIF, WebP)';
                    fileError.style.display = 'block';
                    fileInput.value = '';
                    imagePreview.src = 'https://cdn-icons-png.flaticon.com/512/3342/3342137.png';
                    return;
                }

                // Validate file size
                if (file.size > MAX_SIZE) {
                    const sizeMB = (file.size / (1024 * 1024)).toFixed(2);
                    fileError.textContent = `⚠️ Kích thước file (${sizeMB}MB) vượt quá giới hạn 2MB!`;
                    fileError.style.display = 'block';
                    fileInput.value = '';
                    imagePreview.src = 'https://cdn-icons-png.flaticon.com/512/3342/3342137.png';
                    return;
                }

                // Preview ảnh
                const reader = new FileReader();
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                };
                reader.readAsDataURL(file);
            });
        </script>
    </body>
</html>