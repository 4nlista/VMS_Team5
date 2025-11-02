<%-- 
    Document   : history_attendance_volunteer
    Created on : Nov 2, 2025, 4:35:34 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang lịch sử điểm danh</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <!-- Navbar -->
        <jsp:include page="/layout/navbar.jsp" />

        <div class="page-content container mt-5 pt-5 pb-5">
            <h1 class="mb-4 text-center">Lịch sử sự kiện đã tham gia</h1>

            <div class="card shadow-sm border">
                <div class="card-body">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Tên sự kiện</th>
                                <th scope="col">Tên tổ chức</th>
                                <th scope="col">Ngày bắt đầu</th>
                                <th scope="col">Ngày kết thúc</th>
                                <th scope="col">Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Tên sự kiện 1</td>
                                <td>OrganizationName1</td>
                                <td>2025/11/20 13:00</td>
                                <td>2025/11/21 17:00</td>
                                <td><span class="badge bg-success">Đã tham gia</span></td>                              
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Tên sự kiện 2</td>
                                <td>OrganizationName2</td>
                                <td>2025/11/10 12:00</td>
                                <td>2025/11/21 16:30</td>
                                <td><span class="badge bg-danger text-white">Đã vắng</span></td>   
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Tên sự kiện 3</td>
                                <td>OrganizationName3</td>
                                <td>2025/09/20 11:00</td>
                                <td>2025/09/25 20:30</td>
                                <td><span class="badge bg-warning text-white">Chưa điểm danh</span></td>   
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
