<%-- 
    Document   : event
    Created on : Sep 16, 2025, 2:53:41 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Trang danh sách sự kiện</title>
        <%@ include file="layout/header.jsp" %>
    </head>
    <body>
        <!-- Navbar -->
        <%@ include file="layout/navbar.jsp" %>

        <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');" data-stellar-background-ratio="0.5">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text align-items-center justify-content-center" data-scrollax-parent="true">
                    <div class="col-md-7 ftco-animate text-center" data-scrollax=" properties: { translateY: '70%' }">
                        <p class="breadcrumbs" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }"><span class="mr-2"><a href="<%= request.getContextPath() %>/VolunteerHomeServlet">Home</a></span> <span>Event</span></p>
                        <h1 class="mb-3 bread" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">Danh sách các sự kiện đang diễn ra</h1>
                    </div>
                </div>
            </div>
        </div>

        <section class="ftco-section">
            <div class="container">
                <div class="row">
                    <c:forEach var="e" items="${events}">
                        <div class="col-md-4 d-flex align-items-stretch">
                            <div class="blog-entry align-self-stretch h-100 w-100">
                                <a href="#" class="block-20" style="background-image: url('${e.images}');"></a>
                                <div class="text p-4 d-block h-100">
                                    <div class="meta d-flex justify-content-end">
                                        <a href="#" class="meta-chat">
                                            <span class="icon-star text-warning"></span> 3
                                        </a>
                                    </div>
                                    <div class="meta mb-3">
                                        <div><a href="#">Người tổ chức: <b><i>${e.organizationName}</i></b></a></div>

                                    </div>

                                    <h3 class="heading mb-1"><a href="#">${e.title}</a></h3>
                                    <p class="text-muted mb-1"><i>Loại sự kiện: ${e.categoryName}</i></p>
                                    <p class="time-loc">
                                        <span class="mr-2"><i class="icon-clock-o"></i> Bắt đầu: ${e.startDate}</span><br/>
                                        <span class="mr-2"><i class="icon-clock-o"></i> Kết thúc: ${e.endDate}</span><br/>
                                        <span><i class="icon-map-o"></i> Địa điểm : ${e.location}</span>
                                    </p>
                                    <p>${e.description}</p>
                                    <div class="d-flex justify-content-between mt-auto">
                                        <p class="mb-0">
                                            <a href="${pageContext.request.contextPath}/VolunteerApplyEventServlet?eventId=${e.id}">
                                                Tham gia sự kiện <i class="ion-ios-arrow-forward"></i>
                                            </a>
                                        </p>
                                        <p class="mb-0">
                                            <a href="${pageContext.request.contextPath}/volunteer/payment_volunteer.jsp">
                                                Ủng hộ <i class="ion-ios-add-circle"></i>
                                            </a>
                                        </p>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="row mt-5">
                    <div class="col text-center">
                        <div class="block-27">
                            <ul>
                                <!-- Nút Previous -->
                                <c:if test="${currentPage > 1}">
                                    <li><a href="GuessEventServlet?page=${currentPage - 1}">&lt;</a></li>
                                    </c:if>
                                    <c:if test="${currentPage == 1}">
                                    <li class="disabled"><span>&lt;</span></li>
                                    </c:if>

                                <!-- Danh sách số trang -->
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <c:choose>
                                        <c:when test="${i == currentPage}">
                                            <li class="active"><span>${i}</span></li>
                                                </c:when>
                                                <c:otherwise>
                                            <li><a href="GuessEventServlet?page=${i}">${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>

                                <!-- Nút Next -->
                                <c:if test="${currentPage < totalPages}">
                                    <li><a href="GuessEventServlet?page=${currentPage + 1}">&gt;</a></li>
                                    </c:if>
                                    <c:if test="${currentPage == totalPages}">
                                    <li class="disabled"><span>&gt;</span></li>
                                    </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <%@ include file="layout/footer.jsp" %>
        <%@ include file="layout/loader.jsp" %>

    </body>
</html>
