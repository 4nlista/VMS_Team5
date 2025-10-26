<%-- 
    Document   : donors
    Created on : Sep 16, 2025, 2:24:42 PM
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
                <h2 class="mb-4">Nhà tài trợ tiêu biểu của chúng tôi</h2>
            </div>
        </div>
        <div class="row">
            <c:forEach var="e" items="${topDonates}"  >
                <div class="col-lg-4 d-flex mb-sm-4 ftco-animate">
                    <div class="staff">
                        <div class="d-flex mb-4">
                            <div class="img" style="background-image: url(images/person_1.jpg);"></div>
                            <div class="info ml-5">
                                <h3><a href="teacher-single.html">${e.volunteerFullName}</a></h3>
                                <span class="position" style="color: black">
                                    Mã ID:  ${e.volunteerId}
                                </span>
                                <div class="text">
                                    <p>
                                        Đã tài trợ 
                                        <span>
                                            <fmt:formatNumber value="${e.totalAmountDonated}" type="number" groupingUsed="true"/>
                                        </span>VNĐ 
                                        <br/>
                                        Cho <a href="#">${e.numberOfEventsDonated}</a> sự kiện
                                    </p>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>