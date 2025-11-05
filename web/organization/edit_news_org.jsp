<%-- 
    Document   : edit_news_org
    Created on : 4 Nov 2025, 13:31:21
    Author     : Mirinae
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết bài viết</title>
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
                        <h3 class="fw-bold mb-4 text-center">Chỉnh sửa bài viết</h3>

                        <div class="card shadow-sm border-0">
                            <div class="card-body p-4">
                                <form action="${pageContext.request.contextPath}/OrganizationNewsEdit"
                                      method="post" enctype="multipart/form-data"
                                      class="row g-3">
                                    <input type="hidden" name="id" value="${news.id}" />
                                    <input type="hidden" name="existingImage" value="${news.images}" />

                                    <div class="text-center">
                                        <!-- Serve image via /uploads/news/<filename> or an ImageServlet -->
                                        <img src="${pageContext.request.contextPath}/NewsImage?file=${news.images}"
     style="max-width:250px;max-height:250px;object-fit:contain;" />
                                    </div>

                                    <label class="form-label fw-semibold">Thay đổi ảnh</label>
                                    <input type="file" class="form-control" name="newsImage" accept="image/*" enctype="multipart/form-data">

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Tiêu đề</label>
                                        <input type="text" class="form-control" name="title" value="${news.title}">
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label fw-semibold">Trạng thái</label>
                                        <select class="form-select" name="status">
                                            <option value="published" ${news.status == 'published' ? 'selected' : ''}>Hiển thị</option>
                                            <option value="hidden" ${news.status == 'hidden' ? 'selected' : ''}>Ẩn</option>
                                        </select>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label fw-semibold">Nội dung</label>
                                        <textarea class="form-control" name="content" rows="12">${news.content}</textarea>
                                    </div>

                                    <div class="col-12 d-flex justify-content-between">
                                        <a href="${pageContext.request.contextPath}/OrganizationManageNews" class="btn btn-secondary">Quay lại</a>
                                        <div>
                                            <button type="submit" class="btn btn-primary"><i class="bi bi-save"></i> Lưu</button>
                                            <a href="${pageContext.request.contextPath}/OrganizationNewsDetail?id=${news.id}" class="btn btn-danger"><i class="bi bi-x-circle"></i> Hủy</a>
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
    </body>
</html>
