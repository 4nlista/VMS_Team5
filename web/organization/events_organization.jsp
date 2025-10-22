<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>Quản lý sự kiện</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
        <!-- optional: nếu jQuery đã dùng ở project, không cần; chúng ta dùng fetch (vanilla JS) -->
    </head>
    <body>

        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="/admin/layout_admin/sidebar_admin.jsp" />

            <!-- Nội dung chính -->
            <div class="main-content p-4">
                <h2 class="mb-4">Danh sách sự kiện </h2>

                <a href="CreateEventServlet" class="btn btn-primary mb-3">
                    <i class="bi bi-plus-circle"></i> Thêm sự kiện mới
                </a>

                <c:if test="${not empty message}">
                    <p class="text-success">${message}</p>
                </c:if>

                <table class="table table-bordered table-striped text-center align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tiêu đề</th>
                            <th>Ngày bắt đầu</th>
                            <th>Trạng thái</th>
                            <th>Tổng donate</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="event" items="${events}">
                            <tr>
                                <td>${event.id}</td>
                                <td>${event.title}</td>
                                <td><fmt:formatDate value="${event.startDate}" pattern="dd/MM/yyyy" /></td>
                                <td>${event.status}</td>
                                <td><fmt:formatNumber value="${event.totalDonation}" pattern="#,###" /> VND</td>
                                <td>
                                    <!-- nút view sẽ gọi JS -->
                                    <button type="button" class="btn btn-info btn-sm"
                                            onclick="openEventModal(${event.id});">
                                        <i class="bi bi-eye"></i> Xem
                                    </button>

                                    <a href="UpdateEvenServlet?id=${event.id}" class="btn btn-warning btn-sm">
                                        <i class="bi bi-pencil"></i> Sửa
                                    </a>
                                    <a href="DeleteEventServlet?id=${event.id}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa sự kiện này không?');">
                                        <i class="bi bi-trash"></i> Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal Bootstrap: chứa nội dung chi tiết event (được load động) -->
        <div class="modal fade" id="eventModal" tabindex="-1" aria-labelledby="eventModalLabel" aria-hidden="true">
          <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="eventModalLabel">Chi tiết sự kiện</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body" id="eventModalBody">
                <!-- Nội dung sẽ được insert ở đây bởi JS -->
                <div class="text-center py-4" id="eventModalLoading">Đang tải...</div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
              </div>
            </div>
          </div>
        </div>

        <!-- Bootstrap JS + Popper -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

        <!-- JS: fetch nội dung fragment từ servlet và show modal -->
        <script>
           // Tham chiếu tới modal bootstrap
    const eventModalEl = document.getElementById('eventModal');
    const eventModal = new bootstrap.Modal(eventModalEl, { keyboard: true });

    async function openEventModal(eventId) {
        const bodyEl = document.getElementById('eventModalBody');
        bodyEl.innerHTML = '<div class="text-center py-4">Đang tải...</div>';
        eventModal.show();

        try {
        
            const url = '<%= request.getContextPath() %>/organization/ViewEventServlet?id=' + eventId + '&ajax=true';

            const resp = await fetch(url, {
                method: 'GET',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            });

            if (!resp.ok) {
                bodyEl.innerHTML = `<div class="alert alert-danger">Lỗi khi tải dữ liệu: ${resp.status}</div>`;
                return;
            }

            // server trả về HTML fragment
            const html = await resp.text();
            bodyEl.innerHTML = html;
        } catch (err) {
            console.error(err);
            bodyEl.innerHTML = '<div class="alert alert-danger">Không thể kết nối tới server.</div>';
        }
    }
        </script>

    </body>
</html>
