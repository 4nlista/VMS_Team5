<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm sự kiện mới</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body>
        <div class="container mt-4">
            <h3>Thêm sự kiện mới</h3>
            <form action="<%= request.getContextPath() %>/OrganizationEventServlet" method="post">
                <input type="hidden" name="action" value="create" />

                <div class="mb-3">
                    <label class="form-label">Tiêu đề</label>
                    <input type="text" name="title" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô tả</label>
                    <textarea name="description" class="form-control" rows="3" required></textarea>
                </div>

                <div class="row mb-3">
                    <div class="col">
                        <label class="form-label">Ngày bắt đầu</label>
                        <input type="date" name="start_date" class="form-control" required>
                    </div>
                    <div class="col">
                        <label class="form-label">Ngày kết thúc</label>
                        <input type="date" name="end_date" class="form-control" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Địa điểm</label>
                    <input type="text" name="location" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Số tình nguyện viên cần</label>
                    <input type="number" name="needed_volunteers" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Category</label>
                    <select name="category_id" class="form-select" required>
                        <option value="">-- Chọn danh mục --</option>
                        <% 
                            List<model.Category> categories = (List<model.Category>) request.getAttribute("categories");
                            if (categories != null) {
                                for (model.Category c : categories) {
                        %>
                        <option value="<%= c.getCategoryId() %>"><%= c.getName() %></option>
                        <% 
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tổng donation</label>
                    <input type="number" step="0.01" name="total_donation" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-success">Thêm sự kiện</button>
                <a href="<%= request.getContextPath() %>/OrganizationEventServlet?action=list" class="btn btn-secondary">Hủy</a>
            </form>
        </div>
    </body>
</html>
