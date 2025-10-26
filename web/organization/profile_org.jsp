<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Organization"%>
<%
    Organization organization = (Organization) request.getAttribute("organization");
    if (organization == null) {
        organization = new Organization();
        organization.setFullName("Tên tổ chức chưa có");
    }
    String message = request.getAttribute("message") != null ? (String) request.getAttribute("message") : request.getParameter("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Hồ sơ tổ chức</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />

        <style>
            body {
                background-color: #181a1b;
                color: #f8f9fa;
                display: flex;
                margin: 0;
                font-family: 'Segoe UI', sans-serif;
            }

            /* KHÔNG định nghĩa màu nền cho sidebar ở đây — để giữ giao diện gốc */
            .sidebar-container {
                width: 260px;
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                padding: 0;
                z-index: 10;
            }

            /* Giữ layout của main content — đẩy sang phải 260px */
            .main-content {
                margin-left: 260px;
                padding: 30px;
                flex: 1;
                min-height: 100vh;
                background-color: #181a1b;
                transition: all 0.3s;
            }

            /* Card thông tin tổ chức */
            .profile-card {
                max-width: 850px;
                margin: 0 auto;
                background-color: #1f1f1f;
                color: #f8f9fa;
                border: 1px solid #333;
                border-radius: 12px;
                padding: 25px;
                box-shadow: 0 0 10px rgba(255,255,255,0.05);
            }

            .form-control, .form-select, textarea {
                background-color: #222;
                border: 1px solid #444;
                color: #f1f1f1;
            }
            .form-control:focus, .form-select:focus, textarea:focus {
                background-color: #2a2a2a;
                color: #fff;
                border-color: #0d6efd;
                box-shadow: none;
            }

            img.rounded-circle {
                border: 2px solid #444;
                object-fit: cover;
            }

            .btn-primary {
                background-color: #0d6efd;
                border: none;
            }
            .btn-primary:hover {
                background-color: #0b5ed7;
            }

            h1 {
                font-size: 1.8rem;
                font-weight: 600;
            }

        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <div class="sidebar-container">
            <jsp:include page="layout_org/sidebar_org.jsp" />
        </div>

        <!-- Nội dung chính -->
        <div class="main-content">
            <div class="container-fluid">
                <h1 class="mb-4">Hồ sơ tổ chức</h1>

                <% if (message != null) { %>
                <div id="msg" class="alert <%= message.startsWith("✅") ? "alert-success" : "alert-danger" %> alert-dismissible fade show">
                    <%= message %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <% } %>

                <div class="profile-card">
                    <form method="post" action="<%= request.getContextPath() %>/organization/profile" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="<%= organization.getId() %>"/>

                        <div class="row">
                            <!-- Cột trái -->
                            <div class="col-md-4 text-center border-end">
                                <img id="avatarPreview" 
                                     src="<%= organization.getAvatar()!=null && !organization.getAvatar().isEmpty() ? organization.getAvatar() : "https://via.placeholder.com/180x180.png?text=Logo" %>"
                                     alt="Logo" class="rounded-circle mb-3" width="160" height="160"/>
                                <div class="mb-2">
                                    <label class="form-label">Upload logo (ảnh)</label>
                                    <input type="file" name="avatarFile" accept="image/*" class="form-control" onchange="previewAvatar(event)">
                                </div>
                                <div class="mb-2">
                                    <label class="form-label">Hoặc URL ảnh</label>
                                    <input type="text" name="avatarUrl" class="form-control" value="<%= organization.getAvatar()!=null?organization.getAvatar():"" %>" oninput="previewUrl(this.value)">
                                </div>
                            </div>

                            <!-- Cột phải -->
                            <div class="col-md-8">
                                <div class="mb-3">
                                    <label class="form-label">Tên tổ chức</label>
                                    <input type="text" name="fullName" class="form-control" value="<%= organization.getFullName()!=null?organization.getFullName():"" %>" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Người đại diện</label>
                                    <input type="text" name="jobTitle" class="form-control" value="<%= organization.getJobTitle()!=null?organization.getJobTitle():"" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Email</label>
                                    <input type="email" name="email" class="form-control" value="<%= organization.getEmail()!=null?organization.getEmail():"" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Số điện thoại</label>
                                    <input type="text" name="phone" class="form-control" value="<%= organization.getPhone()!=null?organization.getPhone():"" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Địa chỉ</label>
                                    <input type="text" name="address" class="form-control" value="<%= organization.getAddress()!=null?organization.getAddress():"" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Ngày thành lập</label>
                                    <input type="date" name="dob" class="form-control" value="<%= organization.getDob()!=null? new java.text.SimpleDateFormat("yyyy-MM-dd").format(organization.getDob()):"" %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Giới tính</label>
                                    <select name="gender" class="form-select">
                                        <option value="" <%= organization.getGender()==null?"selected":"" %>>-- Chọn --</option>
                                        <option value="Nam" <%= "Nam".equals(organization.getGender())?"selected":"" %>>Nam</option>
                                        <option value="Nữ" <%= "Nữ".equals(organization.getGender())?"selected":"" %>>Nữ</option>
                                        <option value="Khác" <%= "Khác".equals(organization.getGender())?"selected":"" %>>Khác</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Mô tả</label>
                                    <textarea name="bio" class="form-control" rows="4"><%= organization.getBio()!=null?organization.getBio():"" %></textarea>
                                </div>

                                <div class="mt-3">
                                    <button type="submit" class="btn btn-primary me-2">Lưu hồ sơ</button>
                                    <a href="<%= request.getContextPath() %>/OrganizationHomeServlet" class="btn btn-secondary">Trang chủ</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                        function previewAvatar(evt) {
                                            const [file] = evt.target.files;
                                            if (file) {
                                                document.getElementById('avatarPreview').src = URL.createObjectURL(file);
                                            }
                                        }
                                        function previewUrl(val) {
                                            if (val && val.trim() !== '') {
                                                document.getElementById('avatarPreview').src = val;
                                            }
                                        }
                                        // auto hide message
                                        const msg = document.getElementById('msg');
                                        if (msg)
                                            setTimeout(() => {
                                                const bs = bootstrap.Alert.getOrCreateInstance(msg);
                                                bs.close();
                                            }, 3000);
        </script>
    </body>
</html>
