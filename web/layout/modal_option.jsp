<%-- 
    Document   : modal_option
    Created on : Sep 16, 2025, 2:10:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<section class="ftco-counter ftco-intro" id="section-counter">
    <div class="container">
        <div class="row no-gutters">
            <div class="col-md-5 d-flex justify-content-center counter-wrap ftco-animate">
                <div class="block-18 color-1 align-items-stretch">
                    <div class="text">
                        <span>Quỹ đóng góp từ thiện của tổ chức</span>
                        <strong class="number" data-number="${totalDonationSystem}"/></strong>
                        <span>Bảo vệ môi trường và giúp đỡ những hoàn cảnh khó khăn.</span>
                    </div>
                </div>
            </div>
            <div class="col-md d-flex justify-content-center counter-wrap ftco-animate">
                <div class="block-18 color-2 align-items-stretch">
                    <div class="text">
                        <h3 class="mb-4">Nhà tài trợ</h3>
                        <p>Trở thành nhà tài trợ, góp phần xây dựng cộng đồng hỗ trợ và lan tỏa yêu thương.</p>
                        <p><a href="${pageContext.request.contextPath}/ActionRedirectServlet?action=donate" class="btn btn-white px-3 py-2 mt-2">
                                Đăng kí tài trợ
                            </a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md d-flex justify-content-center counter-wrap ftco-animate">
                <div class="block-18 color-3 align-items-stretch">
                    <div class="text">
                        <h3 class="mb-4">Tình nguyện viên</h3>
                        <p>Trở thành tình nguyện viên, chung tay xây dựng, giúp đỡ, lan tỏa yêu thương.</p>
                        <p><a href="${pageContext.request.contextPath}/ActionRedirectServlet?action=volunteer" class="btn btn-white px-3 py-2 mt-2">
                                Đăng kí tham gia
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>