<%-- 
    Document   : edit_event
    Created on : Oct 20, 2025, 9:24:49 PM
    Author     : locng
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Chỉnh sửa sự kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/admin/css/admin.css" rel="stylesheet"/>
</head>
<body>
<div class="content-container">
    <jsp:include page="/admin/layout_admin/sidebar_admin.jsp"/>
    
    <div class="main-content">
        <h2 class="mb-4">Chỉnh sửa sự kiện</h2>
            ${event.title}

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/UpdateEvenServlet" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="id" value="${event.id}" />

            <div class="mb-3">
                <label class="form-label">Tiêu đề</label>
                <input type="text" name="title" class="form-control" value="${event.title}" required />
            </div>

            <div class="mb-3">
                <label class="form-label">Mô tả</label>
                <textarea name="description" class="form-control" rows="5" required>${event.description}</textarea>
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label">Ngày bắt đầu</label>
                    <input type="date" name="start_date" class="form-control"
                           value="<fmt:formatDate value='${event.startDate}' pattern='yyyy-MM-dd'/>" required />
                </div>
                <div class="col-md-6">
                    <label class="form-label">Ngày kết thúc</label>
                    <input type="date" name="end_date" class="form-control"
                           value="<fmt:formatDate value='${event.endDate}' pattern='yyyy-MM-dd'/>" required />
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Địa điểm</label>
                <input type="text" name="location" class="form-control" value="${event.location}" required />
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-4">
                    <label class="form-label">Số tình nguyện viên cần</label>
                    <input type="number" name="needed_volunteers" class="form-control" min="0"
                           value="${event.neededVolunteers}" />
                </div>

                <div class="col-md-4">
                    <label class="form-label">Category</label>
                    <select name="category_id" class="form-select">
                        <option value="0">-- Chọn category --</option>
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.id}" <c:if test="${c.id == event.categoryId}">selected</c:if>>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Trạng thái</label>
                    <select name="status" class="form-select">
                        <option value="DRAFT" <c:if test="${event.status == 'DRAFT'}">selected</c:if>>Draft</option>
                        <option value="PENDING" <c:if test="${event.status == 'PENDING'}">selected</c:if>>Pending</option>
                        <option value="ACTIVE" <c:if test="${event.status == 'ACTIVE'}">selected</c:if>>Active</option>
                        <option value="CLOSED" <c:if test="${event.status == 'CLOSED'}">selected</c:if>>Closed</option>
                    </select>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Tổng donate</label>
                <input type="number" name="total_donation" class="form-control" step="0.01" min="0"
                       value="${event.totalDonation}" />
            </div>

            <div class="text-end">
                <a href="${pageContext.request.contextPath}/ListEventsServlet" class="btn btn-secondary">Quay lại</a>
                <button type="submit" class="btn btn-primary">Cập nhật</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
