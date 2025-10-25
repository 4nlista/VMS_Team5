<%-- 
    Document   : donors
    Created on : Sep 16, 2025, 2:24:42 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<section class="ftco-section">
    <div class="container">
        <div class="row justify-content-center mb-5 pb-3">
            <div class="col-md-7 heading-section ftco-animate text-center">
                <h2 class="mb-4">Nhà tài trợ tiêu biểu</h2>
                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
            </div>
        </div>
        <div class="row">
            <c:forEach var="e" items="${topDonates}">
                <div class="col-lg-4 d-flex mb-sm-4 ftco-animate">
                    <div class="staff">
                        <div class="d-flex mb-4">
                            <div class="img" style="background-image: url(images/person_1.jpg);"></div>
                            <div class="info ml-4">
                                <h3><a href="teacher-single.html">${e.volunteerFullName}</a></h3>
                                <span class="position">Đã tài trợ ${e.numberOfEventsDonated} sự kiện</span>
                                <div class="text">
                                    <p>Đã tài trợ <span>${e.totalAmountDonated}</span> Donate <a href="#">${e.numberOfEventsDonated}</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>