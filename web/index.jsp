<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
            <div class="alert alert-${messageType}" role="alert" 
                 style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; min-width: 400px; max-width: 600px;">
                <span style="color: #166534; font-weight: 600;"><c:out value="${message}"/></span>
            </div>
            <script>
                setTimeout(function () {
                    var alertEl = document.querySelector('.alert');
                    if (alertEl) {
                        alertEl.classList.add('fade');
                        alertEl.addEventListener('transitionend', function () {
                            alertEl.remove();
                        }, {once: true});
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
