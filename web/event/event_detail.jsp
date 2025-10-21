<%-- 
    Document   : event_detail
    Created on : 21 Oct 2025, 2:42:01 pm
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Chi tiết sự kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4">
        <h2 class="text-primary">${event.title}</h2>
        <hr>
        <p><b>Địa điểm:</b> ${event.location}</p>
        <p><b>Thời gian:</b> ${event.startDate} - ${event.endDate}</p>
        <p><b>Mô tả:</b></p>
        <p>${event.description}</p>
        <p><b>Trạng thái:</b> ${event.status}</p>

        <form action="ApplyEventServlet" method="post" class="mt-3">
            <input type="hidden" name="eventId" value="${event.id}">
            <button type="submit" class="btn btn-success">Đăng ký tham gia</button>
            <a href="VolunteerEventListServlet" class="btn btn-secondary">Quay lại</a>
        </form>

        <c:if test="${not empty message}">
            <div class="alert alert-info mt-3">${message}</div>
        </c:if>
    </div>
</div>

</body>
</html>
