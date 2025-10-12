<%-- 
    Document   : user_detail
    Created on : 10 Oct 2025, 05:46:20
    Author     : Mirinesa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="p-4">

        <a href="AdminUserServlet" class="btn btn-secondary mb-3">← Back to User List</a>

        <div class="card mx-auto" style="max-width: 700px;">
            <div class="card-header bg-dark text-white">
                <h4>User Information</h4>
            </div>
            <div class="card-body">
                <div class="text-center mb-3">
                    <img src="${user.avatar}" class="rounded-circle" width="120" height="120" alt="Avatar">
                </div>
                <table class="table table-bordered">
                    <tr><th>ID</th><td>${user.id}</td></tr>
                    <tr><th>Username</th><td>${user.account.username}</td></tr>
                    <tr><th>Full Name</th><td>${user.full_name}</td></tr>
                    <tr><th>Gender</th><td>${user.gender}</td></tr>
                    <tr><th>Email</th><td>${user.email}</td></tr>
                    <tr><th>Phone</th><td>${user.phone}</td></tr>
                    <tr><th>Address</th><td>${user.address}</td></tr>
                    <tr><th>Job Title</th><td>${user.job_title}</td></tr>
                    <tr><th>Bio</th><td>${user.bio}</td></tr>
                    <tr><th>Date of Birth</th><td>${user.dob}</td></tr>
                </table>

                <div class="text-center">
                    <a href="AdminUserEditServlet?id=${user.id}" class="btn btn-warning">Edit</a>
                    <a href="AdminUserServlet" class="btn btn-secondary">Back</a>
                </div>
            </div>
        </div>

    </body>
</html>
