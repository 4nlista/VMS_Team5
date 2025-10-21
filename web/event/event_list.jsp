<%-- 
    Document   : event_list
    Created on : 21 Oct 2025, 2:41:29 pm
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách sự kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Danh sách sự kiện</h2>

    <c:if test="${empty events}">
        <div class="alert alert-warning text-center">Hiện chưa có sự kiện nào!</div>
    </c:if>

    <div class="row">
        <c:forEach var="e" items="${events}">
            <div class="col-md-4 mb-4">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${e.title}</h5>
                        <p class="card-text">
                            <b>Địa điểm:</b> ${e.location}<br>
                            <b>Thời gian:</b> ${e.startDate} → ${e.endDate}
                        </p>
                        <a href="eventDetail?id=${e.id}" class="btn btn-primary">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
