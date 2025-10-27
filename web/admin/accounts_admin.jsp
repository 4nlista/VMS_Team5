<%-- 
    Document   : accounts_admin
    Created on : Sep 21, 2025, 9:34:56 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Quản lí tài khoản</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content -->
            <div class="main-content p-4">
                <h1>Quản lí tài khoản</h1>               
                <c:if test="${param.msg == 'deleted'}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert" id="delete-success-alert">
                        Đã xóa tài khoản thành công.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${param.msg == 'delete_failed'}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        Không thể xóa tài khoản. Có thể là tài khoản quyền admin hoặc đang có dữ liệu liên quan.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <!-- Yêu cầu 1: Thêm nút Add Account + Filter + Search -->
                <div class="d-flex justify-content-between align-items-center mb-3 gap-2 flex-nowrap">
                    <!-- Filter + Search (cùng một form) -->
                    <form action="AdminAccountServlet" method="get" class="d-flex align-items-center gap-3 flex-nowrap flex-grow-1" style="flex: 1;">
                        <select name="role" class="form-select" style="min-width: 180px;">
                            <option value="">-- Vai trò --</option>
                            <option value="admin" ${'admin' == selectedRole ? 'selected' : ''}>Admin</option>
                            <option value="organization" ${'organization' == selectedRole ? 'selected' : ''}>Organization</option>
                            <option value="volunteer" ${'volunteer' == selectedRole ? 'selected' : ''}>Volunteer</option>
                        </select>

                        <select name="status" class="form-select" style="min-width: 180px;">
                            <option value="">-- Trạng thái --</option>
                            <option value="active" ${'active' == selectedStatus ? 'selected' : ''}>Active</option>
                            <option value="inactive" ${'inactive' == selectedStatus ? 'selected' : ''}>Inactive</option>
                        </select>

                        <input type="text" name="search" class="form-control flex-grow-1" placeholder="Tìm tài khoản..." value="${fn:escapeXml(searchText)}" />

                        <button type="submit" class="btn btn-danger d-inline-flex align-items-center gap-2 text-nowrap px-3" style="height: 40px;">
                            <i class="bi bi-filter"></i> Lọc
                        </button>
                        <a href="<%= request.getContextPath() %>/AdminAccountServlet" class="btn btn-secondary d-inline-flex align-items-center justify-content-center gap-2 text-nowrap px-4" style="min-width: 140px; height: 40px;">
                            <i class="bi bi-trash"></i> Khôi phục
                        </a>
                    </form>

                    <a href="add_account.jsp" class="btn btn-primary">
                        <i class="bi bi-plus-circle"></i> Tạo tài khoản
                    </a>
                </div>

                <!-- Bảng dữ liệu -->
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID
                                <a href="?sort=id_asc"><i class="bi bi-caret-up-fill text-white ms-1"></i></a>
                                <a href="?sort=id_desc"><i class="bi bi-caret-down-fill text-white"></i></a>
                            </th>
                            <th>Tài khoản</th>
                            <th>Vai trò</th>
                            <th>Trạng thái</th>
                            <th>Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="acc" items="${accounts}">
                            <tr>
                                <td>${acc.id}</td>
                                <td>${acc.username}</td>
                                
                                <td>${acc.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${acc.status}">
                                            <span class="badge bg-success">
                                                <i class="bi bi-circle-fill me-1"></i> Active
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">
                                                <i class="bi bi-circle-fill me-1"></i> Inactive
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="action-icons">
                                        <a class="btn btn-primary btn-sm btn-icon" title="Xem chi tiết"
                                           href="<%= request.getContextPath() %>/admin/detail_accounts_admin.jsp?id=${acc.id}">
                                            <i class="bi bi-eye"></i>
                                        </a>
                                        <c:choose>
                                            <c:when test="${acc.role == 'admin'}">
                                                <span class="d-inline-block" tabindex="0" data-bs-toggle="tooltip" data-bs-title="Không thể khóa tài khoản quyền admin">
                                                    <button type="button" class="btn btn-secondary btn-sm btn-icon opacity-50" style="pointer-events: none;" disabled aria-disabled="true">
                                                        <i class="bi bi-lock"></i>
                                                    </button>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<%= request.getContextPath() %>/AdminAccountServlet?action=toggle&id=${acc.id}"
                                                   class="btn ${acc.status ? 'btn-info' : 'btn-success'} btn-sm btn-icon" 
                                                   title="${acc.status ? 'Khóa tài khoản' : 'Mở khóa tài khoản'}">
                                                    <i class="bi ${acc.status ? 'bi-lock' : 'bi-unlock'}"></i>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>

                                        <!-- Edit action (always enabled) -->
                                        <a href="edit_account.jsp?id=${acc.id}" class="btn btn-warning btn-sm btn-icon" title="Chỉnh sửa">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>

                                        <!-- Delete action -->
                                        <c:choose>
                                            <c:when test="${acc.role == 'admin'}">
                                                <span class="d-inline-block" tabindex="0" data-bs-toggle="tooltip" data-bs-title="Không thể xóa tài khoản quyền admin">
                                                    <button type="button" class="btn btn-secondary btn-sm btn-icon opacity-50" style="pointer-events: none;" disabled aria-disabled="true">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" class="btn btn-danger btn-sm btn-icon" title="Xóa tài khoản"
                                                        data-bs-toggle="modal" data-bs-target="#confirmDeleteModal"
                                                        data-delete-url="<%= request.getContextPath() %>/AdminAccountServlet?action=delete&id=${acc.id}"
                                                        data-username="${acc.username}" data-id="${acc.id}">
                                                    <i class="bi bi-trash"></i>
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Pagination -->
                <div class="d-flex justify-content-between align-items-center">
                    <div class="text-muted small">
                        Trang ${currentPage} / ${totalPages} · Tổng: ${totalItems}
                    </div>
                    <nav aria-label="Account pagination">
                        <ul class="pagination mb-0">
                            <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                <c:url var="prevUrl" value="AdminAccountServlet">
                                    <c:param name="role" value="${selectedRole}" />
                                    <c:param name="status" value="${selectedStatus}" />
                                    <c:param name="search" value="${searchText}" />
                                    <c:param name="page" value="${currentPage - 1}" />
                                </c:url>
                                <a class="page-link" href="${prevUrl}">Trước</a>
                            </li>

                            <c:forEach var="p" begin="1" end="${totalPages}">
                                <c:url var="pUrl" value="AdminAccountServlet">
                                    <c:param name="role" value="${selectedRole}" />
                                    <c:param name="status" value="${selectedStatus}" />
                                    <c:param name="search" value="${searchText}" />
                                    <c:param name="page" value="${p}" />
                                </c:url>
                                <li class="page-item ${p == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pUrl}">${p}</a>
                                </li>
                            </c:forEach>

                            <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                <c:url var="nextUrl" value="AdminAccountServlet">
                                    <c:param name="role" value="${selectedRole}" />
                                    <c:param name="status" value="${selectedStatus}" />
                                    <c:param name="search" value="${searchText}" />
                                    <c:param name="page" value="${currentPage + 1}" />
                                </c:url>
                                <a class="page-link" href="${nextUrl}">Sau</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>

            <!-- Detail Modal -->
            <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="detailModalLabel">Chi tiết tài khoản</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-2"><strong>ID:</strong> <span id="detail-id"></span></div>
                            <div class="mb-2"><strong>Username:</strong> <span id="detail-username"></span></div>
                            <div class="mb-2"><strong>Role:</strong> <span id="detail-role"></span></div>
                            <div class="mb-2"><strong>Status:</strong> <span id="detail-status"></span></div>
                            <div class="text-muted small">Lưu ý: Email hiển thị tại trang hồ sơ người dùng.</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Confirm Delete Modal -->
            <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title w-100 text-center" id="confirmDeleteLabel">Xác nhận xóa tài khoản</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body text-center">
                            Bạn có chắc muốn xóa tài khoản <strong id="delete-username"></strong> (ID: <span id="delete-id"></span>)?<br/>
                            Hành động này không thể hoàn tác.
                        </div>
                        <div class="modal-footer justify-content-center">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <a id="confirm-delete-btn" class="btn btn-danger" href="#">Xóa</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            const detailModal = document.getElementById('detailModal');
            if (detailModal) {
                detailModal.addEventListener('show.bs.modal', event => {
                    const button = event.relatedTarget;
                    const id = button.getAttribute('data-id');
                    const username = button.getAttribute('data-username');
                    const role = button.getAttribute('data-role');
                    const status = button.getAttribute('data-status') === 'true';

                    document.getElementById('detail-id').textContent = id;
                    document.getElementById('detail-username').textContent = username;
                    document.getElementById('detail-role').textContent = role;
                    document.getElementById('detail-status').innerHTML = status
                            ? '<span class="badge bg-success"><i class="bi bi-circle-fill me-1"></i> Active</span>'
                            : '<span class="badge bg-danger"><i class="bi bi-circle-fill me-1"></i> Inactive</span>';
                });
            }

            // Khởi tạo Bootstrap tooltip cho các phần tử có data-bs-toggle="tooltip"
            const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            tooltipTriggerList.forEach(function (tooltipTriggerEl) {
                new bootstrap.Tooltip(tooltipTriggerEl);
            });

            // Bind confirm delete modal
            const confirmDeleteModalEl = document.getElementById('confirmDeleteModal');
            if (confirmDeleteModalEl) {
                confirmDeleteModalEl.addEventListener('show.bs.modal', event => {
                    const button = event.relatedTarget;
                    const url = button.getAttribute('data-delete-url');
                    const username = button.getAttribute('data-username');
                    const id = button.getAttribute('data-id');
                    document.getElementById('delete-username').textContent = username || '';
                    document.getElementById('delete-id').textContent = id || '';
                    const confirmBtn = document.getElementById('confirm-delete-btn');
                    confirmBtn.setAttribute('href', url);
                });
            }

            // Tự động ẩn alert thành công sau 3 giây
            const successAlert = document.getElementById('delete-success-alert');
            if (successAlert) {
                setTimeout(() => {
                    const alert = new bootstrap.Alert(successAlert);
                    alert.close();
                }, 3000);
            }
        </script>
    </body>
</html>
