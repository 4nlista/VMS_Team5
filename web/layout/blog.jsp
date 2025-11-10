<%-- 
    Document   : blog
    Created on : Sep 16, 2025, 2:47:49 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

        </div>
    </div>
</section>

