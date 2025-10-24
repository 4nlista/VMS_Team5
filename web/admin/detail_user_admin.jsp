<%-- 
    Document   : user_detail
    Created on : 10 Oct 2025, 05:46:20
    Author     : Mirinesa
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/admin/css/admin.css" rel="stylesheet" />
        <style>
            /* Force page to never scroll; we will auto-scale the main card to fit the viewport */
            html, body { height: 100%; margin: 0; }
            body { overflow: hidden; }
            .content-container { height: 100vh; display: flex; }
            /* Sidebar include will occupy its normal width; main content should fill the remaining area */
            .main-content { flex: 1 1 auto; overflow: hidden; display: flex; align-items: center; justify-content: center; }

            /* Card style kept as your desired layout */
            .profile-card { width: 92%; max-width: 1000px; transform-origin: top left; }

            /* Small visual tweaks to reduce default spacing if scaling needed */
            .profile-card .row { margin: 0; }
            .profile-card .col-md-3, .profile-card .col-md-9 { padding: .75rem; }
            .avatar-lg { width: 140px; height: 140px; object-fit: cover; }
            .form-control[readonly] { background-color: #fff; }
            .bio-compact { display: -webkit-box; -webkit-line-clamp: 4; -webkit-box-orient: vertical; overflow: hidden; }
        </style>
    </head>
    <body>
        <div class="content-container">
            <!-- Sidebar -->
            <jsp:include page="layout_admin/sidebar_admin.jsp" />

            <!-- Main Content (centered) -->
            <div class="main-content">
                <div class="profile-card shadow-sm bg-white rounded">
                    <div class="row g-0">
                        <!-- LEFT: Avatar only (password removed as requested) -->
                        <div class="col-md-3 profile-left p-4 text-center">
                            <img src="${not empty user.avatar ? user.avatar : 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'}"
                                 class="rounded-circle avatar-lg mb-3 border p-2" alt="Avatar" />
                            <div class="fw-semibold">${fn:escapeXml(user.full_name)}</div>
                            <div class="text-muted small">${user.account.username}</div>
                        </div>

                        <!-- RIGHT: All fields (keeps original logic + createdAt) -->
                        <div class="col-md-9 p-4">
                            <div class="d-flex justify-content-between align-items-start mb-3">
                                <div>
                                    <h5 class="fw-bold mb-0">User Details</h5>
                                    <small class="text-muted">Viewing <strong>${user.account.username}</strong>'s account</small>
                                </div>
                                <div>
                                    <a href="AdminUserServlet" class="btn btn-sm btn-outline-secondary me-2">← Back</a>
                                    <a href="AdminUserEditServlet?id=${user.id}" class="btn btn-sm btn-warning">Edit</a>
                                </div>
                            </div>

                            <form>
                                <div class="row g-2">
                                    <div class="col-md-6">
                                        <label class="form-label">ID</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.id}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Username</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.account.username}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Full Name</label>
                                        <input type="text" class="form-control form-control-sm" value="${fn:escapeXml(user.full_name)}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Job Title</label>
                                        <input type="text" class="form-control form-control-sm" value="${fn:escapeXml(user.job_title)}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Gender</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.gender}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Date of Birth</label>
                                        <c:choose>
                                            <c:when test="${not empty user.dob}">
                                                <fmt:formatDate value="${user.dob}" pattern="yyyy-MM-dd" var="dobFmt" />
                                                <input type="text" class="form-control form-control-sm" value="${dobFmt}" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control form-control-sm" value="N/A" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Address</label>
                                        <input type="text" class="form-control form-control-sm" value="${fn:escapeXml(user.address)}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Phone number</label>
                                        <input type="text" class="form-control form-control-sm" value="${user.phone}" readonly>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label">Email</label>
                                        <input type="email" class="form-control form-control-sm" value="${user.email}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Role</label>
                                        <input type="text" class="form-control form-control-sm fw-bold text-danger" value="${user.account.role}" readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label class="form-label">Account created at</label>
                                        <c:choose>
                                            <c:when test="${not empty user.account.createdAt}">
                                                <fmt:formatDate value="${user.account.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" var="acctCreated"/>
                                                <input type="text" class="form-control form-control-sm" value="${acctCreated}" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control form-control-sm" value="N/A" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-12 mt-2">
                                        <label class="form-label">Bio</label>
                                        <textarea class="form-control form-control-sm" rows="4" readonly>${fn:escapeXml(user.bio)}</textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            (function(){
                // Auto-scale the .profile-card so the page never scrolls. This will shrink the card if needed.
                var card = document.querySelector('.profile-card');
                var container = document.querySelector('.main-content');
                if(!card || !container) return;

                function fitCard(){
                    // Ensure no page scroll
                    document.documentElement.style.overflow = 'hidden';
                    document.body.style.overflow = 'hidden';

                    // available size inside main-content (leave small margin)
                    var availW = container.clientWidth - 20;
                    var availH = container.clientHeight - 20;

                    // reset transform to measure natural size
                    card.style.transform = '';
                    var rect = card.getBoundingClientRect();
                    var cardW = rect.width;
                    var cardH = rect.height;

                    // compute scale (never upscale, only shrink)
                    var scaleX = availW / cardW;
                    var scaleY = availH / cardH;
                    var scale = Math.min(scaleX, scaleY, 1);

                    card.style.transformOrigin = 'top left';
                    card.style.transform = 'scale(' + scale + ')';

                    // center the scaled card inside container
                    var offsetX = (container.clientWidth - (cardW * scale)) / 2;
                    var offsetY = (container.clientHeight - (cardH * scale)) / 2;
                    card.style.position = 'relative';
                    card.style.left = offsetX + 'px';
                    card.style.top = offsetY + 'px';
                }

                // run on load and resize
                window.addEventListener('load', fitCard);
                window.addEventListener('resize', fitCard);

                // If content changes dynamically, re-fit after slight delay
                var mo = new MutationObserver(function(){ setTimeout(fitCard, 80); });
                mo.observe(card, { childList: true, subtree: true });
            })();
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>