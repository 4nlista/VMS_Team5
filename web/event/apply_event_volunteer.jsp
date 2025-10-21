<%-- 
    Document   : apply_event_volunteer
    Created on : 21 Oct 2025, 2:42:32 pm
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Kết quả đăng ký sự kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5 text-center">
    <div class="card shadow p-4 mx-auto" style="max-width: 600px;">
        <h3>Kết quả đăng ký</h3>
        <hr>
        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>
        <a href="VolunteerEventListServlet" class="btn btn-primary mt-3">Quay lại danh sách sự kiện</a>
    </div>
</div>

</body>
</html>
