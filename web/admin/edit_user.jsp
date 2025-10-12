<%-- 
    Document   : edit_user
    Created on : 10 Oct 2025, 06:07:50
    Author     : Mirinesa
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body class="p-4">
        <h2>Edit User Information</h2>
        <form action="AdminUserEditServlet" method="post" class="mt-4">
            <input type="hidden" name="id" value="${user.id}" />

            <div class="mb-3">
                <label>Full Name:</label>
                <input type="text" name="full_name" value="${user.full_name}" class="form-control" required />
            </div>

            <div class="mb-3">
                <label>Gender:</label>
                <select name="gender" class="form-select">
                    <option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${user.gender == 'Female' ? 'selected' : ''}>Female</option>
                </select>
            </div>

            <div class="mb-3"><label>Phone:</label>
                <input type="text" name="phone" value="${user.phone}" class="form-control" />
            </div>

            <div class="mb-3"><label>Email:</label>
                <input type="email" name="email" value="${user.email}" class="form-control" />
            </div>

            <div class="mb-3"><label>Address:</label>
                <input type="text" name="address" value="${user.address}" class="form-control" />
            </div>

            <div class="mb-3"><label>Job Title:</label>
                <input type="text" name="job_title" value="${user.job_title}" class="form-control" />
            </div>

            <div class="mb-3"><label>Bio:</label>
                <textarea name="bio" class="form-control" rows="3">${user.bio}</textarea>
            </div>

            <div class="d-flex gap-3">
                <button type="submit" class="btn btn-success">Save Changes</button>
                <a href="AdminUserServlet" class="btn btn-secondary">Back to List</a>
            </div>
        </form>
    </body>
</html>
