<%-- 
    Document   : save_events
    Created on : Oct 15, 2025, 2:57:00 AM
    Author     : locng
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${param.id != null ? 'Chỉnh sửa' : 'Thêm mới'} sự kiện</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
<body>
    <%@ include file="layout_organization/sidebar_organization.jsp" %>
    <div class="container">
        <h2>${param.id != null ? 'Chỉnh sửa' : 'Thêm mới'} sự kiện</h2>
        <form action="saveEvent" method="post">
            <input type="hidden" name="id" value="${param.id}">
            <div class="form-group">
                <label>Tiêu đề:</label>
                <input type="text" name="title" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Mô tả:</label>
                <textarea name="description" class="form-control" required></textarea>
            </div>
            <div class="form-group">
                <label>Ngày bắt đầu:</label>
                <input type="datetime-local" name="start_date" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Ngày kết thúc:</label>
                <input type="datetime-local" name="end_date" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Địa điểm:</label>
                <input type="text" name="location" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Số lượng volunteer cần:</label>
                <input type="number" name="needed_volunteers" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Trạng thái:</label>
                <select name="status" class="form-control">
                    <option value="active">Active</option>
                    <option value="inactive">Inactive</option>
                    <option value="close">Close</option>
                </select>
            </div>
            <div class="form-group">
                <label>Danh mục:</label>
                <select name="category_id" class="form-control">
                    <option value="1">Môi trường</option>
                    <option value="2">Giáo dục</option>
                    <option value="3">Y tế</option>
                    <option value="4">Xã hội</option>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Lưu</button>
        </form>
    </div>
</body>
</html>
