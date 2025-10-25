<%-- 
    Document   : events
    Created on : Sep 16, 2025, 2:45:31 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<section class="ftco-section bg-light">
    <div class="container">
        <div class="row justify-content-center mb-5 pb-3">
            <div class="col-md-7 heading-section ftco-animate text-center">
                <h2 class="mb-4">Các sự kiện mới nhất</h2>
            </div>
        </div>
        <div class="row">
            <c:forEach var="e" items="${lastEvents}">
                <div class="col-md-4 d-flex align-items-stretch">
                    <div class="blog-entry align-self-stretch h-100 w-100">
                        <a href="#" class="block-20" style="background-image: url('images/event-1.jpg');"></a>
                        <div class="text p-4 d-block h-100">
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
                                    <a href="${pageContext.request.contextPath}/volunteer/apply_event_volunteer.jsp">
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
            <!--                <div class="col-md-4 d-flex ftco-animate">
                                <div class="blog-entry align-self-stretch">
                                    <a href="blog-single.html" class="block-20" style="background-image: url('images/event-2.jpg');">
                                    </a>
                                    <div class="text p-4 d-block">
                                        <div class="meta mb-3">
                                            <div><a href="#">Sep. 10, 2018</a></div>
                                            <div><a href="#">Tên của người tổ chức</a></div>
                                            <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 3</a></div>
                                        </div>
                                        <h3 class="heading mb-4"><a href="#">World Wide Donation</a></h3>
                                        <p class="time-loc"><span class="mr-2"><i class="icon-clock-o"></i> 10:30AM-03:30PM</span> <span><i class="icon-map-o"></i> Venue Main Campus</span></p>
                                        <p>A small river named Duden flows by their place and supplies it with the necessary regelialia.</p>
                                        <p><a href="<%=request.getContextPath()%>/volunteer/apply_event_volunteer.jsp">Join Event <i class="ion-ios-arrow-forward"></i></a></p>
                                    </div>
                                </div>
                            </div>-->
            <!--                <div class="col-md-4 d-flex ftco-animate">
                                <div class="blog-entry align-self-stretch">
                                    <a href="blog-single.html" class="block-20" style="background-image: url('images/event-3.jpg');">
                                    </a>
                                    <div class="text p-4 d-block">
                                        <div class="meta mb-3">
                                            <div><a href="#">Sep. 10, 2018</a></div>
                                            <div><a href="#">Tên của người tổ chức</a></div>
                                            <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 3</a></div>
                                        </div>
                                        <h3 class="heading mb-4"><a href="#">World Wide Donation</a></h3>
                                        <p class="time-loc"><span class="mr-2"><i class="icon-clock-o"></i> 10:30AM-03:30PM</span> <span><i class="icon-map-o"></i> Venue Main Campus</span></p>
                                        <p>A small river named Duden flows by their place and supplies it with the necessary regelialia.</p>
                                        <p><a href="<%=request.getContextPath()%>/volunteer/apply_event_volunteer.jsp">Join Event <i class="ion-ios-arrow-forward"></i></a></p>
                                    </div>
                                </div>
                            </div>-->
        </div>
    </div>
</section>
