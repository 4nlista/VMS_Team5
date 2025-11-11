<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.EventVolunteer"%>
<%@page import="java.util.List"%>
<%
    // Lấy danh sách EventVolunteer, có thể chỉ 1 phần tử
    List<EventVolunteer> eventDetails = (List<EventVolunteer>) request.getAttribute("eventDetails");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
        <jsp:include page="/layout/header.jsp"/>
    </head>
    <body>
        <jsp:include page="/layout/navbar.jsp"/>

        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center text-primary fw-bold">Chi tiết sự kiện</h1>

            <%
                if (eventDetails == null || eventDetails.isEmpty()) {
            %>
            <p class="text-danger text-center">Không tìm thấy thông tin chi tiết sự kiện.</p>
            <%
                } else {
                    for (EventVolunteer ev : eventDetails) {
            %>
            <div class="row">
                <!-- Cột trái: Thông tin sự kiện -->
                <div class="col-md-6">
                    <div class="card border shadow-sm mb-4">
                        <div class="card-body">
                            <h5 class="card-title d-flex justify-content-between align-items-center">
                                <span class="fw-bold">Thông tin sự kiện</span>
                                <span class="badge
                                      <%= "approved".equalsIgnoreCase(ev.getStatus()) ? "bg-success" : 
                                "pending".equalsIgnoreCase(ev.getStatus()) ? "bg-warning text-dark" : "bg-danger" %>">
                                    <%= ev.getStatus() %>
                                </span>
                            </h5>
                            <ul class="list-group list-group-flush mt-3">
                                <li class="list-group-item"><span class="fw-bold">Tiêu đề:</span> <span class="ms-2"><%= ev.getEventTitle() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Người tổ chức:</span> <span class="ms-2"><%= ev.getOrganizationName() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Danh mục:</span> <span class="ms-2"><%= ev.getCategoryName() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Thời gian:</span> <span class="ms-2"><%= ev.getStartDate() %> [ đến ] <%= ev.getEndDate() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Địa điểm:</span> <span class="ms-2"><%= ev.getLocation() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Số lượng tình nguyện viên cần:</span> <span class="ms-2"><%= ev.getRequiredVolunteers() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Mô tả:</span> <span class="ms-2"><%= ev.getDescription() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Số giờ tích lũy:</span> <span class="ms-2"><%= ev.getHours() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Ghi chú:</span> <span class="ms-2"><%= ev.getNote() != null ? ev.getNote() : "Không có" %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Số tiền đã ủng hộ:</span> <span class="badge bg-info text-dark px-2 py-1"><%= ev.getDonationAmount() != null ? ev.getDonationAmount() : 0 %> VND</span></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Cột phải: Thông tin volunteer -->
                <div class="col-md-6">
                    <div class="card border shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">Thông tin tình nguyện viên</h5>
                            <ul class="list-group list-group-flush mt-3">
                                <li class="list-group-item"><span class="fw-bold">Họ tên:</span> <span class="ms-2"><%= ev.getVolunteerName() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Email:</span> <span class="ms-2"><%= ev.getVolunteerEmail() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Trạng thái đơn:</span> <span class="ms-2"><%= ev.getStatus() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Số giờ đăng ký:</span> <span class="ms-2"><%= ev.getHours() %></span></li>
                                <li class="list-group-item"><span class="fw-bold">Ghi chú:</span> <span class="ms-2"><%= ev.getNote() != null ? ev.getNote() : "Không có" %></span></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="text-center mt-4">
                <a href="<%= request.getContextPath() %>/VolunteerEventServlet" class="btn btn-primary">← Quay lại</a>
            </div>

            <%
                    } // end for
                } // end if
            %>
        </div>

        <jsp:include page="/layout/footer.jsp"/>
        <jsp:include page="/layout/loader.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
