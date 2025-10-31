<%-- 
    Document   : blog
    Created on : Sep 16, 2025, 2:47:49 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<section class="ftco-section">
    <div class="container">
        <div class="row justify-content-center mb-5 pb-3">
            <div class="col-md-7 heading-section ftco-animate text-center">
                <h2 class="mb-4">Các tin tức mới nhất</h2>
                <p>Những tin tức mới nhất về các sự kiện sẽ thường xuyên được cập nhật.</p>
            </div>
        </div>
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
                                        <fmt:formatDate value="${e.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                    <br/>
                                    &nbsp;•&nbsp;
                                    <strong class="text-primary">${e.organizationName}</strong>
                                </div>
<!--                                <div>
                                    <a href="#" class="meta-chat">
                                        <span class="icon-chat"></span> 3
                                    </a>
                                </div>-->
                            </div>

                            <h3 class="heading mt-3"><a href="#">${e.title}</a></h3>
                            <p>${e.content}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </div>
</section>

