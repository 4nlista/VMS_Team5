<%-- 
    Document   : view_event
    Created on : Oct 15, 2025
    Author     : locng
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Chi tiết sự kiện</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body class="p-4">

        <div class="container mt-4">
            <h2>Chi tiết sự kiện</h2>
            <hr>

            <c:if test="${not empty event}">
                <table class="table table-bordered">
                    <tr><th>ID</th><td>${event.id}</td></tr>
                    <tr><th>Tiêu đề</th><td>${event.title}</td></tr>
                    <tr><th>Mô tả</th><td>${event.description}</td></tr>
                    <tr><th>Ngày bắt đầu</th><td><fmt:formatDate value="${event.startDate}" pattern="dd/MM/yyyy HH:mm"/></td></tr>
                    <tr><th>Ngày kết thúc</th><td><fmt:formatDate value="${event.endDate}" pattern="dd/MM/yyyy HH:mm"/></td></tr>
                    <tr><th>Địa điểm</th><td>${event.location}</td></tr>
                    <tr><th>Tình trạng</th><td>${event.status}</td></tr>
                    <tr><th>Tổng donate</th><td><fmt:formatNumber value="${event.totalDonation}" type="number" groupingUsed="true"/></td></tr>
                    <tr><th>Số lượng tình nguyện viên cần</th><td>${event.neededVolunteers}</td></tr>
                </table>
            </c:if>

            <a href="listEvent" class="btn btn-secondary">← Quay lại danh sách</a>
            <a href="edit_event.jsp?id=${event.id}" class="btn btn-warning">Chỉnh sửa</a>
            <a href="DeleteEventServlet?id=${event.id}" class="btn btn-danger"
               onclick="return confirm('Bạn có chắc muốn xóa sự kiện này?');">Xóa</a>
        </div>

    </body>
</html>
