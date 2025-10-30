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

    // Lấy thông báo từ session
    String applyMessage = (String) session.getAttribute("applyMessage");
    String cancelMessage = (String) session.getAttribute("cancelMessage");
    Boolean justApplied = (Boolean) session.getAttribute("justApplied");

    boolean shouldShowApply = (applyMessage != null && justApplied != null && justApplied);
    boolean shouldShowCancel = (cancelMessage != null);

    // Xóa session sau khi lấy giá trị
    if (shouldShowApply) { 
        session.removeAttribute("applyMessage"); 
        session.removeAttribute("justApplied"); 
    }
    if (shouldShowCancel) { 
        session.removeAttribute("cancelMessage"); 
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tham gia sự kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
    <jsp:include page="/layout/header.jsp"/>
</head>
<body>
<jsp:include page="/layout/navbar.jsp"/>

<div class="container mt-5 pt-5 pb-5">
    <h1 class="mb-4 text-center">Đăng ký tham gia sự kiện</h1>

    <div class="card mx-auto border shadow-sm" style="max-width: 700px;">
        <div class="card-body">

            <!-- FORM ĐĂNG KÝ -->
            <form action="${pageContext.request.contextPath}/ApplyEventServlet" method="post">
                <div class="row">
                    <div class="col-md-6 mb-2">
                        <label class="form-label fw-bold">ID</label>
                        <input type="text" name="volunteerId" class="form-control"
                               value="<%= session.getAttribute("accountId") %>" readonly>
                    </div>
                    <div class="col-md-6 mb-2">
                        <label class="form-label fw-bold">Họ và tên</label>
                        <input type="text" name="fullName" class="form-control"
                               value="${user.fullName}" readonly>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-2">
                        <label class="form-label fw-bold">Email</label>
                        <input type="email" name="email" class="form-control"
                               value="${user.email}" readonly>
                    </div>
                    <div class="col-md-6 mb-2">
                        <label class="form-label fw-bold">Số điện thoại</label>
                        <input type="tel" name="phone" class="form-control"
                               value="${user.phone}" readonly>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-2">
                        <label class="form-label fw-bold">Số giờ đăng ký</label>
                        <input type="number" name="hours" class="form-control" placeholder="Nhập số giờ">
                    </div>
                    <div class="col-md-6 mb-2">
                        <label class="form-label fw-bold">Thời gian đăng ký</label>
                        <input type="text" name="applyDate" class="form-control"
                               value="<%= formattedDate %>" readonly>
                    </div>
                </div>

                <div class="mb-2">
                    <label class="form-label fw-bold">Ghi chú</label>
                    <textarea name="note" class="form-control" rows="2" placeholder="Ghi chú thêm..."></textarea>
                </div>

                <input type="hidden" name="eventId" value="<%= request.getParameter("eventId") %>">

                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary">Đăng ký tham gia</button>
                </div>
            </form>

            <!-- FORM HỦY ĐƠN -->
            <form action="${pageContext.request.contextPath}/CancelEventServlet" method="post" class="mt-3">
                <input type="hidden" name="eventId" value="<%= request.getParameter("eventId") %>">
                <input type="hidden" name="volunteerId" value="<%= session.getAttribute("accountId") %>">
                <button type="submit" class="btn btn-outline-danger"
                        onclick="return confirm('Bạn có chắc muốn hủy đơn đăng ký này không?');">
                    Hủy đơn đăng ký
                </button>
            </form>

        </div>
    </div>
</div>

<!-- Modal thông báo chung -->
<div class="modal fade" id="applyModal" tabindex="-1" aria-labelledby="applyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content shadow">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="applyModalLabel">Thông báo</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body text-center">
                <p id="applyMessage" class="fw-semibold"></p>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        var modalEl = document.getElementById('applyModal');
        var modal = bootstrap.Modal.getOrCreateInstance(modalEl);

        <% if (shouldShowApply) { %>
            document.getElementById("applyMessage").textContent = "<%= applyMessage.replace("\"","\\\"") %>";
            modal.show();
        <% } else if (shouldShowCancel) { %>
            document.getElementById("applyMessage").textContent = "<%= cancelMessage.replace("\"","\\\"") %>";
            modal.show();
        <% } %>

        modalEl.addEventListener('hidden.bs.modal', function () {
            document.getElementById("applyMessage").textContent = "";
        });
    });
</script>

<jsp:include page="/layout/footer.jsp"/>
<jsp:include page="/layout/loader.jsp"/>
</body>
</html>
