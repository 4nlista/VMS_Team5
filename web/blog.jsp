<%-- 
    Document   : blog
    Created on : Sep 16, 2025, 2:53:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                        <p class="breadcrumbs" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }"><span class="mr-2"><a href="index.html">Home</a></span> <span>Blog</span></p>
                        <h1 class="mb-3 bread" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">Trang tin tức</h1>
                    </div>
                </div>
            </div>
        </div>


        <section class="ftco-section">
            <div class="container">
                <div class="row d-flex">
                    <c:forEach var="e" items="${allNews}">
                        <div class="col-md-4 d-flex ftco-animate">
                            <div class="blog-entry align-self-stretch">
                                <a href="blog-single.html" class="block-20" 
                                   style="background-image: url('images/event-1.jpg');"> 

                                </a>
                                <div class="text p-4 d-block">
                                    <div class="meta mb-3">
                                        <div>
                                            <strong class="text-success">Ngày đăng:</strong>
                                            <a href="#">

                                                <fmt:formatDate value="${e.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                            </a>
                                            <br/>
                                            &nbsp;•&nbsp;
                                            <strong class="text-primary">${e.organizationName}</strong>
                                        </div>
                                        <div>
                                            <a href="#" class="meta-chat">
                                                <span class="icon-chat"></span> 3
                                            </a>
                                        </div>
                                    </div>

                                    <h3 class="heading mt-3"><a href="#">${e.title}</a></h3>
                                    <p>${e.content}</p>
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

            </div>
        </section>
        <%@ include file="layout/footer.jsp" %>
        <%@ include file="layout/loader.jsp" %>

    </body>
</html>
