<%-- 
    Document   : register
    Created on : Sep 7, 2025, 4:12:43 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css" rel="stylesheet"/>
        <title>Đăng kí</title>
    </head>
    <body>
        <form action="<%= request.getContextPath() %>/RegisterServlet" method="post">
            <section style="background-color: #9a616d; min-height: 100vh;">
                <% 
                    String error = (String) request.getAttribute("error");
                    model.Account acc = (model.Account) request.getAttribute("acc");
                    model.User user = (model.User) request.getAttribute("user");
                %>

                <% if (error != null) { %>
                <div class="error-message text-danger text-center mt-3">
                    <script>
                        alert("<%= error %>");
                    </script>
                </div>
                <% } %>

                <div class="container py-5">
                    <div class="row justify-content-center align-items-center">
                        <div class="col-xl-9">
                            <div class="card border-0 shadow" style="border-radius: 1rem;">
                                <div class="row g-0">
                                    <!-- Ảnh bên trái -->
                                    <div class="col-md-5 d-none d-md-block">
                                        <img
                                            src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img1.webp"
                                            alt="register form"
                                            class="img-fluid h-100"
                                            style="border-radius: 1rem 0 0 1rem; object-fit: cover;"/>
                                    </div>

                                    <!-- Form bên phải -->
                                    <div class="col-md-7 d-flex align-items-center">
                                        <div class="card-body p-4 text-black">
                                            <div class="d-flex align-items-center mb-4">
                                                <i class="bi bi-person-plus-fill fs-2 me-2 text-danger"></i>
                                                <span class="h3 fw-bold mb-0">Đăng kí tài khoản</span>
                                            </div>

                                            <!-- Row 1 -->
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <label class="form-label">Tài khoản</label>
                                                    <input type="text" name="username" class="form-control" required
                                                           value="<%= acc != null ? acc.getUsername() : "" %>"/>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Email</label>
                                                    <input type="email" name="email" class="form-control" required
                                                           value="<%= user != null ? user.getEmail() : "" %>"/>
                                                </div>
                                            </div>

                                            <!-- Row 2 -->
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <label class="form-label">Mật khẩu</label>
                                                    <input type="password" name="password" class="form-control" required/>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Xác nhận mật khẩu</label>
                                                    <input type="password" name="confirmPassword" class="form-control" required/>
                                                </div>
                                            </div>

                                            <!-- Row 3 -->
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <label class="form-label">Họ tên</label>
                                                    <input type="text" name="fullName" class="form-control" required
                                                           value="<%= user != null ? user.getFull_name() : "" %>"/>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Số điện thoại</label>
                                                    <input type="text" name="phone" class="form-control"
                                                           value="<%= user != null ? user.getPhone() : "" %>"/>
                                                </div>
                                            </div>

                                            <!-- Row 4 -->
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <label class="form-label">Ngày sinh</label>
                                                    <input type="date" name="dob" class="form-control"
                                                           value="<%= user != null && user.getDob() != null ? user.getDob().toString() : "" %>"/>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Địa chỉ</label>
                                                    <input type="text" name="address" class="form-control"
                                                           value="<%= user != null ? user.getAddress() : "" %>"/>
                                                </div>
                                            </div>

                                            <!-- Row 5 -->
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <label class="form-label d-block">Giới tính</label>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="gender" value="male"
                                                               <%= user != null && "male".equals(user.getGender()) ? "checked" : "" %>/>
                                                        <label class="form-check-label">Nam</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="gender" value="female"
                                                               <%= user != null && "female".equals(user.getGender()) ? "checked" : "" %>/>
                                                        <label class="form-check-label">Nữ</label>
                                                    </div>
                                                </div>

                                                <div class="col-md-6">
                                                    <div class="row mb-3">
                                                        <label class="form-label col-12">Vai trò:</label>
                                                        <div class="col-6">
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" name="role" id="volunteer" value="volunteer"
                                                                       <%= acc != null && "volunteer".equals(acc.getRole()) ? "checked" : "" %>>
                                                                <label class="form-check-label" for="volunteer">Volunteer</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-6">
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" name="role" id="organization" value="organization"
                                                                       <%= acc != null && "organization".equals(acc.getRole()) ? "checked" : "" %>>
                                                                <label class="form-check-label" for="organization">Organization</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Submit -->
                                            <div class="text-center mb-3">
                                                <button class="btn btn-danger btn-lg btn-block" type="submit">
                                                    Đăng kí tài khoản
                                                </button>
                                            </div>

                                            <p class="text-center mb-2">
                                                <a class="small" href="<%=request.getContextPath()%>/auth/reset_password.jsp">Quên mật khẩu?</a>
                                            </p>
                                            <p class="text-center" style="color: #393f81;">
                                                Bạn đã có tài khoản?
                                                <a class="small text-danger" href="<%= request.getContextPath() %>/LoginServlet">Đăng nhập</a>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </form>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
