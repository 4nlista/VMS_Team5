<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="model.Event" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Events | Volunteer Platform</title>
        <%@ include file="layout/header.jsp" %>
    </head>
    <body>

        <%@ include file="layout/navbar.jsp" %>

        <!-- Hero section -->
        <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');" data-stellar-background-ratio="0.5">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text align-items-center justify-content-center" data-scrollax-parent="true">
                    <div class="col-md-7 ftco-animate text-center">
                        <p class="breadcrumbs">
                            <span class="mr-2"><a href="index.jsp">Home</a></span> 
                            <span>Events</span>
                        </p>
                        <h1 class="mb-3 bread">Upcoming Events</h1>
                    </div>
                </div>
            </div>
        </div>

        <!-- Event list section -->
        <section class="ftco-section">
            <div class="container">
                <div class="row">

                    <!-- Nếu có danh sách sự kiện -->
                    <c:if test="${not empty events}">
                        <c:forEach var="e" items="${events}">
                            <div class="col-md-4 d-flex ftco-animate">
                                <div class="blog-entry align-self-stretch">

                                    <!-- Ảnh đại diện sự kiện -->
                                    <a href="EventDetailServlet?id=${e.id}" class="block-20" 
                                       style="background-image: url('${pageContext.request.contextPath}/images/event-default.jpg');">
                                    </a>

                                    <div class="text p-4 d-block">
                                        <div class="meta mb-3">
                                            <div>
                                                <a href="#">${e.startDate} - ${e.endDate}</a>
                                            </div>
                                            <div>
                                                <a href="#">Organization ID: ${e.organizationId}</a>
                                            </div>
                                        </div>

                                        <!-- Tiêu đề sự kiện -->
                                        <h3 class="heading mb-4">
                                            <a href="eventDetail?id=${e.id}">${e.title}</a>
                                        </h3>

                                        <!-- Địa điểm -->
                                        <p class="time-loc">
                                            <span><i class="icon-map-o"></i> ${e.location}</span>
                                        </p>

                                        <!-- Mô tả (rút gọn 100 ký tự nếu quá dài) -->
                                        <p>
                                            <c:choose>
                                                <c:when test="${fn:length(e.description) > 100}">
                                                    ${fn:substring(e.description, 0, 100)}...
                                                </c:when>
                                                <c:otherwise>${e.description}</c:otherwise>
                                            </c:choose>
                                        </p>

                                        <!-- Nút hành động -->
                                        <div class="d-flex justify-content-between">
                                            <p class="mb-0">
                                                <a href="eventDetail?id=${e.id}">
                                                    View Details <i class="ion-ios-arrow-forward"></i>
                                                </a>
                                            </p>
                                            <p class="mb-0">
                                                <a href="${pageContext.request.contextPath}/volunteer/apply_event_volunteer.jsp?eventId=${e.id}" 
                                                   class="btn btn-link p-0 m-0 align-baseline">
                                                    Join <i class="ion-ios-person-add"></i>
                                                </a>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>

                    <!-- Nếu không có sự kiện -->
                    <c:if test="${empty events}">
                        <div class="col-md-12 text-center">
                            <h4>No active events found.</h4>
                        </div>
                    </c:if>

                </div>
            </div>
        </section>

        <%@ include file="layout/footer.jsp" %>
        <%@ include file="layout/loader.jsp" %>
    </body>
</html>
