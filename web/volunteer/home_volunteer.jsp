<%-- 
    Document   : home_volunteer
    Created on : Sep 7, 2025, 4:14:34 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ khách hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <%
            Object sessionId = session.getId();
            String fullname = (String) session.getAttribute("fullname");
            if (fullname == null) {
                fullname = "Khách";
            }
        %>


        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <!-- Background Images -->
        <jsp:include page="/layout/background.jsp" />


        <!-- Modal Option -->
        <jsp:include page="/layout/modal_option.jsp" />

        <!-- Text Modal -->
        <jsp:include page="/layout/text_modal.jsp" />


        <!-- Intro Organization -->
        <jsp:include page="/layout/intro.jsp" />


        <!-- Donors -->
        <jsp:include page="/layout/donors.jsp" />
        <!-- Images Child -->
        <jsp:include page="/layout/images.jsp" />

        <!-- Blog -->
        <jsp:include page="/layout/blog.jsp" />

        <!-- Events -->
        <jsp:include page="/layout/events.jsp" />
        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
    </body>
</html>
