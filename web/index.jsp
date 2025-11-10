<%-- 
    Document   : index
    Created on : Sep 16, 2025, 12:43:17 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Welfare - Free Bootstrap 4 Template by Colorlib</title>
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>

        <!-- Hiển thị thông báo -->
        <c:if test="${not empty message}">
            <div class="alert alert-${messageType} alert-dismissible fade show" role="alert" 
                 style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; min-width: 400px; max-width: 600px;">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <script>
                // Tự động ẩn sau 3 giây
                setTimeout(function () {
                    var alert = document.querySelector('.alert');
                    if (alert) {
                        var bsAlert = new bootstrap.Alert(alert);
                        bsAlert.close();
                    }
                }, 3000);
            </script>
        </c:if>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />
        <!-- Background Images -->
        <jsp:include page="layout/background.jsp" />
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

        <!-- Offer -->
        <jsp:include page="/layout/offer.jsp" />


        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
    </body>
</html>
