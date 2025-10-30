<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dao.UserDAO, model.User" %>

<%
Integer accountId = (Integer) session.getAttribute("accountId");
User user = null;
if (accountId != null) {
    UserDAO userDAO = new UserDAO();
    user = userDAO.getUserByAccountId(accountId);
    request.setAttribute("user", user);
}

java.util.Date now = new java.util.Date();
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
String formattedDate = sdf.format(now);

String applyMessage = (String) session.getAttribute("applyMessage");
String cancelMessage = (String) session.getAttribute("cancelMessage");
Boolean justApplied = (Boolean) session.getAttribute("justApplied");
String savedHours = (String) session.getAttribute("hoursValue");
String savedNote = (String) session.getAttribute("noteValue");

String message = null;
String messageType = "success";

if (cancelMessage != null) {
    message = cancelMessage;
    messageType = "danger";
    session.removeAttribute("cancelMessage");
    session.removeAttribute("applyMessage");
    session.removeAttribute("justApplied");
} else if (applyMessage != null && justApplied != null && justApplied) {
    message = applyMessage;
    messageType = "success";
    session.removeAttribute("applyMessage");
    session.removeAttribute("justApplied");
    session.removeAttribute("cancelMessage");
}

boolean showModal = (message != null);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tham gia sự kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>

    <style>
        .custom-btn {
            height: 55px;
            font-size: 16px;
            font-weight: 600;
            border-radius: 10px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
        }

        .custom-btn:hover {
            transform: translateY(-2px);
            filter: brightness(1.1);
            box-shadow: 0 4px 10px rgba(255, 255, 255, 0.2);
        }

        .back-btn {
            height: 50px;
            font-size: 16px;
            border-radius: 10px;
            transition: all 0.3s ease;
        }

        .back-btn:hover {
            background-color: #ffb84d !important;
            color: #000 !important;
            transform: translateY(-2px);
            box-shadow: 0 4px 10px rgba(255, 255, 255, 0.2);
        }
    </style>
</head>

<body class="bg-dark text-light">
<div class="container mt-5 pt-5 pb-5">
    <h1 class="mb-4 text-center text-light">Đăng ký tham gia sự kiện</h1>

    <div class="card mx-auto border-0 shadow-lg bg-dark-subtle" style="max-width: 700px;">
        <div class="card-body">

            <!-- FORM ĐĂNG KÝ -->
            <form action="${pageContext.request.contextPath}/ApplyEventServlet" method="post">
                <input type="hidden" name="eventId" value="<%= request.getParameter("eventId") %>">

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label fw-bold">ID</label>
                        <input type="text" name="volunteerId" class="form-control"
                               value="<%= session.getAttribute("accountId") %>" readonly>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Họ và tên</label>
                        <input type="text" name="fullName" class="form-control" value="${user.fullName}" readonly>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Email</label>
                        <input type="email" name="email" class="form-control" value="${user.email}" readonly>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Số điện thoại</label>
                        <input type="tel" name="phone" class="form-control" value="${user.phone}" readonly>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Số giờ đăng ký</label>
                        <input type="number" name="hours" class="form-control" placeholder="Nhập số giờ"
                               value="<%= savedHours != null ? savedHours : "" %>">
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Thời gian đăng ký</label>
                        <input type="text" name="applyDate" class="form-control" value="<%= formattedDate %>" readonly>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Ghi chú</label>
                    <textarea name="note" class="form-control" rows="2"
                              placeholder="Ghi chú thêm..."><%= savedNote != null ? savedNote : "" %></textarea>
                </div>

                <!-- HÀNG 3 NÚT -->
                <div class="d-flex justify-content-between mt-3 gap-3">
                    <!-- Nút Đăng ký -->
                    <div class="flex-fill">
                        <button type="submit" class="btn btn-success w-100 custom-btn">
                            Đăng ký tham gia
                        </button>
                    </div>
                </div>
            </form>

            <!-- Nút Quyên góp -->
            <form action="${pageContext.request.contextPath}/DonateServlet" method="post" class="flex-fill mt-3">
                <input type="hidden" name="eventId" value="<%= request.getParameter("eventId") %>">
                <input type="hidden" name="volunteerId" value="<%= session.getAttribute("accountId") %>">
                <button type="submit" class="btn btn-primary w-100 custom-btn text-white">
                    Quyên góp
                </button>
            </form>

            <!-- Nút Hủy đơn -->
            <form action="${pageContext.request.contextPath}/CancelEventServlet" method="post" class="flex-fill mt-3">
                <input type="hidden" name="eventId" value="<%= request.getParameter("eventId") %>">
                <input type="hidden" name="volunteerId" value="<%= session.getAttribute("accountId") %>">
                <button type="submit" class="btn btn-danger w-100 custom-btn"
                        onclick="return confirm('Bạn có chắc muốn hủy đơn đăng ký này không?');">
                    Hủy đơn đăng ký
                </button>
            </form>

            <!-- Nút Quay lại -->
            <div class="mt-3">
                <a href="${pageContext.request.contextPath}/EventListServlet"
                   class="btn btn-warning w-100 back-btn text-dark fw-semibold">
                     Quay lại
                </a>
            </div>

        </div>
    </div>
</div>

<!-- MODAL THÔNG BÁO -->
<div class="modal fade" id="messageModal" tabindex="-1" aria-labelledby="messageModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content <%= messageType.equals("danger") ? "border-danger" : "border-success" %>">
            <div class="modal-header <%= messageType.equals("danger") ? "bg-danger" : "bg-success" %> text-white">
                <h5 class="modal-title" id="messageModalLabel">
                    <%= messageType.equals("danger") ? "Thông báo lỗi" : "Thành công" %>
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-dark fw-semibold text-center">
                <%= message %>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<% if (showModal) { %>
<script>
    const modalEl = document.getElementById('messageModal');
    const modal = new bootstrap.Modal(modalEl);
    modal.show();
</script>
<% } %>
</body>
</html>
