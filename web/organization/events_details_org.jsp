<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Event"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/organization/css/org.css" rel="stylesheet" />
    </head>
    <body>
        <div class="content-container">
            <%
                String fullname = (String) session.getAttribute("fullname");
                if (fullname == null) fullname = "Khách";
                Event event = (Event) request.getAttribute("event");
            %>

            <!-- Sidebar -->
            <jsp:include page="layout_org/sidebar_org.jsp" />

            <!-- Main Content -->
            <div class="main-content container mt-4">
                <h1>Chỉnh sửa sự kiện</h1>
                <h4>Chào <%= fullname %></h4>

                <!-- Quay lại danh sách -->
                <a href="<%= request.getContextPath() %>/OrganizationEventServlet?action=list" class="btn btn-secondary mb-3">
                    <i class="bi bi-arrow-left"></i> Quay lại danh sách
                </a>

                <!-- Form chỉnh sửa event -->
                <form action="<%= request.getContextPath() %>/OrganizationEventEditServlet" method="post">
                    <input type="hidden" name="id" value="<%= event.getId() %>" />

                    <div class="mb-3">
                        <label class="form-label">Tiêu đề</label>
                        <input type="text" name="title" class="form-control" value="<%= event.getTitle() %>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mô tả</label>
                        <textarea name="description" class="form-control" rows="4" required><%= event.getDescription() %></textarea>
                    </div>



                    <div class="row mb-3">
                        <div class="col">
                            <label class="form-label">Ngày bắt đầu</label>
                            <input type="date" name="start_date" class="form-control" 
                                   value="<%= event != null && event.getStartDate() != null ? sdf.format(event.getStartDate()) : "" %>" required>
                        </div>
                        <div class="col">
                            <label class="form-label">Ngày kết thúc</label>
                            <input type="date" name="end_date" class="form-control" 
                                   value="<%= event != null && event.getEndDate() != null ? sdf.format(event.getEndDate()) : "" %>" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Địa điểm</label>
                        <input type="text" name="location" class="form-control" value="<%= event.getLocation() %>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Số tình nguyện viên cần</label>
                        <input type="number" name="needed_volunteers" class="form-control" value="<%= event.getNeededVolunteers() %>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Trạng thái</label>
                        <select name="status" class="form-select" required>
                            <option value="active" <%= "active".equals(event.getStatus()) ? "selected" : "" %>>Active</option>
                            <option value="inactive" <%= "inactive".equals(event.getStatus()) ? "selected" : "" %>>Inactive</option>
                        </select>
                    </div>

                    <select name="category_id" class="form-select" required>
                        <option value="">-- Chọn danh mục --</option>
                        <% 
                            List<Category> categories = (List<Category>) request.getAttribute("categories");
                            if (categories != null) {
                                for (Category c : categories) {
                        %>
                        <option value="<%= c.getCategoryId() %>" 
                                <%= (event.getCategoryId() == c.getCategoryId()) ? "selected" : "" %>>
                            <%= c.getName() %>
                        </option>
                        <% 
                                }
                            }
                        %>
                    </select>


                    <div class="mb-3">
                        <label class="form-label">Tổng donation</label>
                        <input type="number" step="0.01" name="total_donation" class="form-control" value="<%= event.getTotalDonation() %>" required>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Lưu thay đổi
                    </button>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
