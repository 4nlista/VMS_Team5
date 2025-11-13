<%-- 
    Document   : event
    Created on : Sep 16, 2025, 2:53:41 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Trang danh sách sự kiện</title>
        <%@ include file="layout/header.jsp" %>
    </head>
    <body>
        <!-- Navbar -->
        <%@ include file="layout/navbar.jsp" %>

        <div class="hero-wrap" style="background-image: url('images/background.jpg');" data-stellar-background-ratio="0.5">
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
            <h1 class="text-center mb-4">Danh sách các sự kiện đang diễn ra</h1>
            <div class="container">
                <!-- Form lọc -->
                <div class="row mb-4" id="filter-section">
                    <div class="col-12">
                        <form method="get" action="GuessEventServlet#filter-section" class="bg-light p-4 rounded">
                            <div class="row g-3">
                                <!-- Lọc theo danh mục -->
                                <div class="col-md-2">
                                    <label class="form-label fw-bold">Danh mục</label>
                                    <select name="category" class="form-select">
                                        <option value="all" ${selectedCategory == 'all' ? 'selected' : ''}>Tất cả</option>
                                        <c:forEach var="cat" items="${categories}">
                                            <option value="${cat.categoryId}" ${selectedCategory == cat.categoryId.toString() ? 'selected' : ''}>
                                                ${cat.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Sắp xếp -->
                                <div class="col-md-2">
                                    <label class="form-label fw-bold">Sắp xếp theo</label>
                                    <select name="sort" class="form-select">
                                        <option value="desc" ${sortOrder == 'desc' ? 'selected' : ''}>Mới nhất</option>
                                        <option value="asc" ${sortOrder == 'asc' ? 'selected' : ''}>Cũ nhất</option>
                                    </select>
                                </div>

                                <!-- Từ ngày -->
                                <div class="col-md-3">
                                    <label class="form-label fw-bold">Từ ngày</label>
                                    <input type="date" name="startDate" class="form-control" value="${startDate}">
                                </div>

                                <!-- Đến ngày -->
                                <div class="col-md-3">
                                    <label class="form-label fw-bold">Đến ngày</label>
                                    <input type="date" name="endDate" class="form-control" value="${endDate}">
                                </div>

                                <!-- Nút lọc -->
                                <div class="col-md-2 d-flex align-items-end">
                                    <button type="submit" class="btn btn-primary w-100">
                                        <i class="icon-search"></i> Lọc
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="row">
                    <c:forEach var="e" items="${events}">
                        <div class="col-md-4 d-flex align-items-stretch mb-5">
                            <div class="blog-entry align-self-stretch h-100 w-100">
                                <a class="block-20" style="background-image: url('${pageContext.request.contextPath}/UploadImagesServlet?file=${e.images}');"></a>                           
                                <div class="text p-4 d-block h-100">
                                    <div class="meta d-flex justify-content-end">
                                        <a href="${pageContext.request.contextPath}/ViewFeedbackEventsServlet?eventId=${e.id}&page=${currentPage}" class="meta-chat">
                                            <span class="icon-comment text-warning"></span> Bình luận
                                        </a>
                                    </div>
                                    <div class="meta mb-3">
                                        <div><strong>Người tổ chức:</strong> <b><i>${e.organizationName}</i></b></div>
                                    </div>

                                    <h3 class="heading mb-1"><a href="${pageContext.request.contextPath}/VolunteerApplyEventServlet?eventId=${e.id}">${e.title}</a></h3>
                                    <p class="text-muted mb-1"><i>Loại sự kiện: ${e.categoryName}</i></p>
                                    <p class="time-loc">
                                        <span class="mr-2"><i class="icon-clock-o"></i> Bắt đầu: 
                                            <fmt:formatDate value="${e.startDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </span><br/>
                                        <span class="mr-2"><i class="icon-clock-o"></i> Kết thúc: 
                                            <fmt:formatDate value="${e.endDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </span><br/>
                                        <span><i class="icon-map-o"></i> Địa điểm : ${e.location}</span>
                                    </p>
                                    <p>${e.description}</p>

                                    <div class="d-flex justify-content-between mt-auto">
                                        <!-- Nút Tham gia sự kiện -->
                                        <p class="mb-0">
                                            <c:choose>
                                                <%-- 1. Full slot (ưu tiên cao nhất) --%>
                                                <c:when test="${e.isFull}">
                                                    <span class="text-secondary">
                                                        <i class="icon-users"></i> Đã đủ slot
                                                    </span>
                                                </c:when>

                                                <%-- 2. Đã đăng ký (pending hoặc approved) --%>
                                                <c:when test="${e.hasApplied}">
                                                    <span class="text-success">
                                                        <i class="icon-check"></i> Đã đăng ký
                                                    </span>
                                                </c:when>

                                                <%-- 3. Bị từ chối >= 3 lần --%>
                                                <c:when test="${e.rejectedCount >= 3}">
                                                    <span class="text-danger">
                                                        <i class="icon-ban"></i> Đã bị từ chối ${e.rejectedCount} lần
                                                    </span>
                                                </c:when>

                                                <%-- 4. Bị từ chối < 3 lần hoặc chưa đăng ký --%>
                                                <c:otherwise>
                                                    <c:if test="${e.rejectedCount > 0}">
                                                        <small class="text-warning d-block">
                                                            ⚠️ Đơn trước bị từ chối (${e.rejectedCount}/3)
                                                        </small>
                                                    </c:if>
                                                    <a href="${pageContext.request.contextPath}/VolunteerApplyEventServlet?eventId=${e.id}">
                                                        ${e.rejectedCount > 0 ? 'Đăng ký lại' : 'Tham gia sự kiện'} <i class="ion-ios-arrow-forward"></i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </p>

                                        <!-- Nút Ủng hộ / Donate -->
                                        <p class="mb-0">
                                            <c:choose>
                                                <c:when test="${e.hasDonated}">
                                                    <span class="text-success">
                                                        <i class="icon-check"></i> Đã donate
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="${pageContext.request.contextPath}/VolunteerPaymentServlet?eventId=${e.id}">
                                                        Ủng hộ <i class="ion-ios-add-circle"></i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Phân trang -->
                <div class="row mt-5">
                    <div class="col text-center">
                        <!-- Hiển thị thông tin -->
                        <p class="text-muted mb-3">
                            Trang ${currentPage} / ${totalPages} 
                            <c:if test="${not empty events}">
                                (Tổng: ${totalPages * 6 > 6 ? (totalPages - 1) * 6 : 0} - ${currentPage * 6 > (totalPages * 6) ? totalPages * 6 : currentPage * 6} sự kiện)
                            </c:if>
                        </p>

                        <c:if test="${totalPages > 1}">
                            <div class="block-27">
                                <ul>
                                    <!-- Nút Previous -->
                                    <c:if test="${currentPage > 1}">
                                        <li><a href="GuessEventServlet?page=${currentPage - 1}&category=${selectedCategory}&startDate=${startDate}&endDate=${endDate}&sort=${sortOrder}#filter-section">&lt;</a></li>
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
                                                <li><a href="GuessEventServlet?page=${i}&category=${selectedCategory}&startDate=${startDate}&endDate=${endDate}&sort=${sortOrder}#filter-section">${i}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>

                                    <!-- Nút Next -->
                                    <c:if test="${currentPage < totalPages}">
                                        <li><a href="GuessEventServlet?page=${currentPage + 1}&category=${selectedCategory}&startDate=${startDate}&endDate=${endDate}&sort=${sortOrder}#filter-section">&gt;</a></li>
                                        </c:if>
                                        <c:if test="${currentPage == totalPages}">
                                        <li class="disabled"><span>&gt;</span></li>
                                        </c:if>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </section>


        <%@ include file="layout/footer.jsp" %>
        <%@ include file="layout/loader.jsp" %>

    </body>
</html>
