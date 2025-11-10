<%-- 
    Document   : blog
    Created on : Sep 16, 2025, 2:53:15 PM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Trang tin tức</title>
        <%@ include file="layout/header.jsp" %>
    </head>
    <body>

        <!-- Navbar -->
        <%@ include file="layout/navbar.jsp" %>

        <div class="hero-wrap" style="background-image: url('images/bg_2.jpg');" data-stellar-background-ratio="0.5">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text align-items-center justify-content-center" data-scrollax-parent="true">
                    <div class="col-md-7 ftco-animate text-center" data-scrollax=" properties: { translateY: '70%' }">
                        <p class="breadcrumbs" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">
                            <span class="mr-2"><a href="<%= request.getContextPath() %>/">Home</a></span> 
                            <span>Blog</span>
                        </p>
                        <h1 class="mb-3 bread" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">Trang tin tức</h1>
                    </div>
                </div>
            </div>
        </div>

        <section class="ftco-section">
            <div class="container">
                <div class="row d-flex">
                    <c:choose>
                        <c:when test="${empty allNews}">
                            <div class="col-12 text-center py-5">
                                <p class="text-muted">Chưa có tin tức nào</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="e" items="${allNews}">
                                <div class="col-md-4 d-flex ftco-animate">
                                    <div class="blog-entry align-self-stretch w-100">
                                        <!-- Ảnh thumbnail -->
                                        <c:choose>
                                            <c:when test="${not empty e.images}">
                                                <a href="ViewNewsDetailServlet?id=${e.id}" class="block-20 d-block" 
                                                   style="background-image: url('<%= request.getContextPath() %>/viewImage?type=news&file=${e.images}'); min-height: 250px;">
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="ViewNewsDetailServlet?id=${e.id}" class="block-20 d-block" 
                                                   style="background-image: url('<%= request.getContextPath() %>/images/no-image.jpg'); min-height: 250px;">
                                                </a>
                                            </c:otherwise>
                                        </c:choose>

                                        <!-- Nội dung -->
                                        <div class="text p-4 d-flex flex-column">
                                            <div class="meta mb-3">
                                                <div class="text-truncate">
                                                    <strong class="text-success">Ngày đăng:</strong>
                                                    <fmt:formatDate value="${e.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                                    <br/>
                                                    <span class="text-primary">• ${e.organizationName}</span>
                                                </div>
                                            </div>

                                            <h3 class="heading mt-3 text-truncate" style="max-height: 3.6em; overflow: hidden;">
                                                <a href="ViewNewsDetailServlet?id=${e.id}" title="${e.title}">${e.title}</a>
                                            </h3>

                                            <!-- Nội dung giới hạn -->
                                            <p class="flex-grow-1 text-truncate" style="max-height: 4.5em; overflow: hidden;">
                                                <c:choose>
                                                    <c:when test="${fn:length(e.content) > 150}">
                                                        ${fn:substring(e.content, 0, 150)}...
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${e.content}
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>

                                            <a href="ViewNewsDetailServlet?id=${e.id}" class="btn btn-primary btn-sm mt-auto">
                                                Đọc thêm
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Phân trang -->
                <c:if test="${totalPages > 1}">
                    <div class="row mt-5">
                        <div class="col text-center">
                            <div class="block-27">
                                <ul>
                                    <!-- Nút Previous -->
                                    <c:if test="${currentPage > 1}">
                                        <li><a href="GuessNewServlet?page=${currentPage - 1}">&lt;</a></li>
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
                                                <li><a href="GuessNewServlet?page=${i}">${i}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>

                                    <!-- Nút Next -->
                                    <c:if test="${currentPage < totalPages}">
                                        <li><a href="GuessNewServlet?page=${currentPage + 1}">&gt;</a></li>
                                        </c:if>
                                        <c:if test="${currentPage == totalPages}">
                                        <li class="disabled"><span>&gt;</span></li>
                                        </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </section>

        <%@ include file="layout/footer.jsp" %>
        <%@ include file="layout/loader.jsp" %>

    </body>
</html>