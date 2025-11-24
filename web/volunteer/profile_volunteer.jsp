<%-- 
    Document   : profile_volunteer
    Created on : Sep 22, 2025, 8:12:47 PM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hồ sơ tình nguyện viên</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/volunteer/css/profile_volunteer.css" rel="stylesheet" />
        <jsp:include page="/layout/header.jsp" />
    </head>
    <body>
        <jsp:include page="/layout/navbar.jsp" />

        <c:set var="displayName" value="${sessionScope.username != null ? sessionScope.username : 'Tình nguyện viên'}" />
        <c:set var="dobFormatted" value="" />
        <c:if test="${not empty profile.dob}">
            <fmt:formatDate value="${profile.dob}" pattern="yyyy-MM-dd" var="dobFormatted" />
        </c:if>

        <c:set var="placeholderData" value="data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='280' height='280' viewBox='0 0 280 280'><rect width='100%' height='100%' rx='16' ry='16' fill='%23ffffff' stroke='%23e5e7eb'/></svg>" />
        <c:set var="avatarUrl" value="${placeholderData}" />
        <c:if test="${not empty profile.images}">
            <c:choose>
                <c:when test="${fn:startsWith(profile.images, 'http')}">
                    <c:set var="avatarUrl" value="${profile.images}" />
                </c:when>
                <c:when test="${fn:startsWith(profile.images, '/')}">
                    <c:set var="avatarUrl" value="${profile.images}" />
                </c:when>
                <c:otherwise>
                    <c:set var="avatarUrl" value="${pageContext.request.contextPath}/avatar/${profile.images}" />
                </c:otherwise>
            </c:choose>
        </c:if>

        <fmt:formatNumber value="${profile.totalDonated}" type="number" minFractionDigits="0" maxFractionDigits="0" var="totalDonatedFormatted" />

        <div class="page-content container mt-5 pt-5 pb-5">
            <div class="row justify-content-center">
                <div class="col-lg-11">
                    <div class="d-flex flex-column flex-md-row align-items-md-center justify-content-between mb-4">
                        <div>
                            <h1 class="mb-1">Hồ sơ tình nguyện viên</h1>
                        </div>
                    </div>

                    <c:if test="${not empty message}">
                        <div class="alert ${messageType eq 'success' ? 'alert-success' : 'alert-danger'}" role="alert">
                            <span><c:out value="${message}"/></span>
                        </div>
                    </c:if>

                    <c:if test="${not empty errors['general']}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${errors['general']}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <div class="row g-4">
                        <div class="col-lg-8">
                            <div class="profile-card h-100">
                                <form action="${pageContext.request.contextPath}/VolunteerProfileServlet" method="post" enctype="multipart/form-data" 
                                      id="volunteerProfileForm" data-account-id="${profile.id}">
                                    <div class="row g-4">
                                        <div class="col-md-4 text-center">
                                            <img src="${avatarUrl}" class="profile-avatar border mb-3" alt="Avatar tình nguyện viên" id="avatarPreview" data-original-src="${avatarUrl}" onerror="this.onerror=null;this.src='${placeholderData}';">
                                            <div class="mb-3 text-start">
                                                <label class="form-label fw-semibold">Ảnh đại diện</label>
                                                <input type="file" class="form-control${not empty errors['avatar'] ? ' is-invalid' : ''}" name="avatar" id="avatar" accept="image/*">
                                                <small class="text-muted d-block mt-1">Hỗ trợ JPG, PNG, GIF (≤2MB).</small>
                                                <c:if test="${not empty errors['avatar']}">
                                                    <div class="invalid-feedback d-block">${errors['avatar']}</div>
                                                </c:if>
                                            </div>
                                        </div>

                                        <div class="col-md-8">
                                            <h5 class="mb-3">1. Thông tin cá nhân</h5>
                                            <div class="row g-3">
                                                <div class="col-sm-12">
                                                    <label class="form-label fw-semibold">Họ và tên<span class="text-danger">*</span></label>
                                                    <input type="text" id="fullName" class="form-control${not empty errors['fullName'] ? ' is-invalid' : ''}" name="fullName" value="${profile.fullName != null ? profile.fullName : ''}" placeholder="Nhập họ và tên" required>
                                                    <c:if test="${not empty errors['fullName']}">
                                                        <div class="invalid-feedback">${errors['fullName']}</div>
                                                    </c:if>
                                                </div>
                                                <div class="col-sm-6">
                                                    <label class="form-label fw-semibold">Ngày sinh<span class="text-danger">*</span></label>
                                                    <input type="date" id="dob" class="form-control${not empty errors['dob'] ? ' is-invalid' : ''}" name="dob" value="${dobFormatted}" required>
                                                    <c:if test="${not empty errors['dob']}">
                                                        <div class="invalid-feedback">${errors['dob']}</div>
                                                    </c:if>
                                                </div>
                                                <div class="col-sm-6">
                                                    <label class="form-label fw-semibold">Giới tính<span class="text-danger">*</span></label>
                                                    <select id="gender" class="form-select${not empty errors['gender'] ? ' is-invalid' : ''}" name="gender" required>
                                                        <option value="" ${empty profile.gender ? 'selected' : ''}>-- Chọn giới tính --</option>
                                                        <option value="male"${profile.gender == 'male' ? ' selected' : ''}>Nam</option>
                                                        <option value="female"${profile.gender == 'female' ? ' selected' : ''}>Nữ</option>
                                                        <option value="other"${profile.gender == 'other' ? ' selected' : ''}>Khác</option>
                                                    </select>
                                                    <c:if test="${not empty errors['gender']}">
                                                        <div class="invalid-feedback">${errors['gender']}</div>
                                                    </c:if>
                                                </div>
                                                <div class="col-12">
                                                    <label class="form-label fw-semibold">Địa chỉ<span class="text-danger">*</span></label>
                                                    <input type="text" id="address" class="form-control${not empty errors['address'] ? ' is-invalid' : ''}" name="address" value="${profile.address != null ? profile.address : ''}" placeholder="Nhập địa chỉ hiện tại" required>
                                                    <c:if test="${not empty errors['address']}">
                                                        <div class="invalid-feedback">${errors['address']}</div>
                                                    </c:if>
                                                </div>
                                                <div class="col-12">
                                                    <label class="form-label fw-semibold">Nghề nghiệp<span class="text-danger">*</span></label>
                                                    <input type="text" id="jobTitle" class="form-control${not empty errors['jobTitle'] ? ' is-invalid' : ''}" name="jobTitle" value="${profile.jobTitle != null ? profile.jobTitle : ''}" placeholder="Ví dụ: Sinh viên, Nhân viên..." required maxlength="100">
                                                    <c:if test="${not empty errors['jobTitle']}">
                                                        <div class="invalid-feedback">${errors['jobTitle']}</div>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <hr class="my-4">

                                            <h5 class="mb-3">Thông tin liên hệ</h5>
                                            <div class="row g-3">
                                                <div class="col-sm-6">
                                                    <label class="form-label fw-semibold">Email<span class="text-danger">*</span></label>
                                                    <input type="email" id="email" class="form-control${not empty errors['email'] ? ' is-invalid' : ''}" name="email" value="${profile.email != null ? profile.email : ''}" placeholder="example@gmail.com" required>
                                                    <c:if test="${not empty errors['email']}">
                                                        <div class="invalid-feedback">${errors['email']}</div>
                                                    </c:if>
                                                </div>
                                                <div class="col-sm-6">
                                                    <label class="form-label fw-semibold">Số điện thoại<span class="text-danger">*</span></label>
                                                    <input type="text" id="phone" class="form-control${not empty errors['phone'] ? ' is-invalid' : ''}" name="phone" value="${profile.phone != null ? profile.phone : ''}" placeholder="Bắt đầu bằng 0, 10-11 chữ số" required pattern="0[0-9]{9,10}">
                                                    <c:if test="${not empty errors['phone']}">
                                                        <div class="invalid-feedback">${errors['phone']}</div>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <hr class="my-4">
                                            <h5 class="mb-3">Giới thiệu bản thân<span class="text-danger">*</span></h5>
                                            <div class="mb-3">
                                                <textarea id="bio" class="form-control${not empty errors['bio'] ? ' is-invalid' : ''}" name="bio" rows="4" placeholder="Chia sẻ đôi nét về bản thân bạn..." maxlength="1000" required>${profile.bio != null ? profile.bio : ''}</textarea>
                                                <small class="text-muted d-block mt-1">Tối đa 1000 ký tự.</small>
                                                <c:if test="${not empty errors['bio']}">
                                                    <div class="invalid-feedback">${errors['bio']}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="d-flex justify-content-end gap-2 mt-4">
                                        <button type="submit" name="action" value="cancel" class="btn btn-outline-secondary" formnovalidate>Hủy</button>
                                        <button type="submit" name="action" value="save" class="btn btn-primary">Lưu cập nhật</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="col-lg-4">
                            <div class="profile-card mb-4">
                                <h5 class="mb-3">2. Thành tích đóng góp</h5>
                                <div class="stat-item">
                                    <div class="stat-label">Tổng sự kiện đã tham gia</div>
                                    <div class="stat-value text-primary">${profile.totalEvents}</div>
                                </div>
                                <div class="stat-item">
                                    <div class="stat-label">Tổng số tiền đã quyên góp</div>
                                    <div class="stat-value text-warning">${totalDonatedFormatted} VNĐ</div>
                                </div>
                            </div>

                            <div class="profile-card">
                                <h5 class="mb-3">3. Sự kiện gần nhất</h5>
                                <p class="mb-1"><strong>Sự kiện:</strong>
                                    <c:choose>
                                        <c:when test="${not empty profile.eventName}">
                                            ${profile.eventName}
                                        </c:when>
                                        <c:otherwise>
                                            Chưa có dữ liệu.
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p class="mb-0"><strong>Tổ chức:</strong>
                                    <c:choose>
                                        <c:when test="${not empty profile.organizationName}">
                                            ${profile.organizationName}
                                        </c:when>
                                        <c:otherwise>
                                            Chưa có dữ liệu.
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="/layout/footer.jsp" />
        <jsp:include page="/layout/loader.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            (function () {
                const form = document.getElementById('volunteerProfileForm');
                if (!form)
                    return;
                const accountId = form.getAttribute('data-account-id');
                var ctx = '<%= request.getContextPath() %>';

                const fields = {
                    fullName: document.getElementById('fullName'),
                    dob: document.getElementById('dob'),
                    gender: document.getElementById('gender'),
                    address: document.getElementById('address'),
                    jobTitle: document.getElementById('jobTitle'),
                    email: document.getElementById('email'),
                    phone: document.getElementById('phone'),
                    bio: document.getElementById('bio'),
                    avatar: document.getElementById('avatar')
                };
                const avatarPreview = document.getElementById('avatarPreview');
                let avatarObjectURL = null;

                function ensureFeedback(el) {
                    let fb = el.parentElement.querySelector('.invalid-feedback');
                    if (!fb) {
                        fb = document.createElement('div');
                        fb.className = 'invalid-feedback';
                        el.parentElement.appendChild(fb);
                    }
                    return fb;
                }

                function setError(el, msg) {
                    const fb = ensureFeedback(el);
                    el.classList.add('is-invalid');
                    fb.textContent = msg || 'Trường này không hợp lệ.';
                }

                function clearError(el) {
                    const fb = el.parentElement.querySelector('.invalid-feedback');
                    el.classList.remove('is-invalid');
                    if (fb && !fb.hasAttribute('data-server')) {
                        fb.textContent = '';
                    }
                }

                // Basic required and format checks
                function validateRequired(el, label) {
                    const v = (el.value || '').trim();
                    if (!v) {
                        setError(el, label + ' không được để trống.');
                        return false;
                    }
                    clearError(el);
                    return true;
                }

                function validateDob() {
                    const el = fields.dob;
                    const v = (el.value || '').trim();
                    if (!v) {
                        setError(el, 'Ngày sinh không được để trống.');
                        return false;
                    }
                    const today = new Date();
                    const d = new Date(v);
                    if (isNaN(d.getTime())) {
                        setError(el, 'Ngày sinh không hợp lệ.');
                        return false;
                    }
                    // Không được ở tương lai
                    const todayOnly = new Date(today.getFullYear(), today.getMonth(), today.getDate());
                    const dateOnly = new Date(d.getFullYear(), d.getMonth(), d.getDate());
                    if (dateOnly > todayOnly) {
                        setError(el, 'Ngày sinh không được ở tương lai.');
                        return false;
                    }
                    clearError(el);
                    return true;
                }

                function validateEmailFormat() {
                    const el = fields.email;
                    const v = (el.value || '').trim();
                    if (!v) {
                        setError(el, 'Email không được để trống.');
                        return false;
                    }
                    const re = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
                    if (!re.test(v)) {
                        setError(el, 'Định dạng email không hợp lệ.');
                        return false;
                    }
                    clearError(el);
                    return true;
                }

                function validatePhoneFormat() {
                    const el = fields.phone;
                    const v = (el.value || '').trim();
                    if (!v) {
                        setError(el, 'Số điện thoại không được để trống.');
                        return false;
                    }
                    if (!/^0\d{9,10}$/.test(v)) {
                        setError(el, 'Số điện thoại phải bắt đầu bằng 0 và gồm 10-11 chữ số.');
                        return false;
                    }
                    clearError(el);
                    return true;
                }

                function validateJobTitle() {
                    const el = fields.jobTitle;
                    const v = (el.value || '').trim();
                    if (!v) {
                        setError(el, 'Nghề nghiệp không được để trống.');
                        return false;
                    }
                    if (v.length > 100) {
                        setError(el, 'Nghề nghiệp không được vượt quá 100 ký tự.');
                        return false;
                    }
                    clearError(el);
                    return true;
                }

                function validateBio() {
                    const el = fields.bio;
                    const v = (el.value || '').trim();
                    if (!v) {
                        setError(el, 'Giới thiệu bản thân không được để trống.');
                        return false;
                    }
                    if (v.length > 1000) {
                        setError(el, 'Giới thiệu bản thân không được vượt quá 1000 ký tự.');
                        return false;
                    }
                    clearError(el);
                    return true;
                }

                // Debounce helper
                function debounce(fn, ms) {
                    let t;
                    return function () {
                        const ctx = this, args = arguments;
                        clearTimeout(t);
                        t = setTimeout(() => fn.apply(ctx, args), ms);
                    };
                }

                // Uniqueness checks
                const checkEmailUnique = debounce(async function () {
                    if (!validateEmailFormat())
                        return;
                    const val = fields.email.value.trim();
                    if (!val)
                        return;
                    try {
                        const base = window.location.origin + ctx;
                        const url = base + '/VolunteerValidateServlet?type=email&value=' + encodeURIComponent(val) + '&accountId=' + encodeURIComponent(accountId);
                        const res = await fetch(url);
                        const data = await res.json();
                        if (data && data.exists) {
                            setError(fields.email, 'Email đã tồn tại trong hệ thống.');
                        } else {
                            clearError(fields.email);
                        }
                    } catch (e) { /* ignore */
                    }
                }, 400);

                const checkPhoneUnique = debounce(async function () {
                    if (!validatePhoneFormat())
                        return;
                    const val = fields.phone.value.trim();
                    if (!val)
                        return;
                    try {
                        const base = window.location.origin + ctx;
                        const url = base + '/VolunteerValidateServlet?type=phone&value=' + encodeURIComponent(val) + '&accountId=' + encodeURIComponent(accountId);
                        const res = await fetch(url);
                        const data = await res.json();
                        if (data && data.exists) {
                            setError(fields.phone, 'Số điện thoại đã tồn tại trong hệ thống.');
                        } else {
                            clearError(fields.phone);
                        }
                    } catch (e) { /* ignore */
                    }
                }, 400);    //Đó là số mili-giây (ms) mà hệ thống chờ sau khi bạn dừng nhập, rồi mới gọi servlet để kiểm tra trùng email/phone.

                // Bind events
                fields.fullName.addEventListener('input', () => validateRequired(fields.fullName, 'Họ và tên'));
                fields.address.addEventListener('input', () => validateRequired(fields.address, 'Địa chỉ'));
                fields.jobTitle.addEventListener('input', validateJobTitle);
                fields.gender.addEventListener('change', () => validateRequired(fields.gender, 'Giới tính'));
                fields.dob.addEventListener('change', validateDob);
                fields.email.addEventListener('input', validateEmailFormat);
                fields.email.addEventListener('blur', checkEmailUnique);
                fields.phone.addEventListener('input', validatePhoneFormat);
                fields.phone.addEventListener('blur', checkPhoneUnique);
                fields.bio.addEventListener('input', validateBio);

                // Avatar preview & validation
                if (fields.avatar && avatarPreview) {
                    fields.avatar.addEventListener('change', function () {
                        const file = fields.avatar.files && fields.avatar.files[0];
                        if (!file) {
                            clearError(fields.avatar);
                            if (avatarObjectURL) {
                                URL.revokeObjectURL(avatarObjectURL);
                                avatarObjectURL = null;
                            }
                            avatarPreview.src = avatarPreview.getAttribute('data-original-src');
                            return;
                        }
                        if (!file.type || !file.type.startsWith('image/')) {
                            if (avatarObjectURL) {
                                URL.revokeObjectURL(avatarObjectURL);
                                avatarObjectURL = null;
                            }
                            avatarPreview.src = avatarPreview.getAttribute('data-original-src');
                            setError(fields.avatar, 'Tệp tải lên phải là hình ảnh.');
                            return;
                        }
                        const MAX_SIZE = 2 * 1024 * 1024;
                        if (file.size > MAX_SIZE) {
                            if (avatarObjectURL) {
                                URL.revokeObjectURL(avatarObjectURL);
                                avatarObjectURL = null;
                            }
                            avatarPreview.src = avatarPreview.getAttribute('data-original-src');
                            setError(fields.avatar, 'Ảnh đại diện phải nhỏ hơn hoặc bằng 2MB.');
                            return;
                        }
                        clearError(fields.avatar);
                        if (avatarObjectURL) {
                            URL.revokeObjectURL(avatarObjectURL);
                        }
                        avatarObjectURL = URL.createObjectURL(file);
                        avatarPreview.src = avatarObjectURL;
                    });
                }

                // On submit, run all validations
                form.addEventListener('submit', function (e) {
                    if (e.submitter && e.submitter.name === 'action' && e.submitter.value === 'cancel') {
                        return; // skip validations when cancelling
                    }
                    let ok = true;
                    ok = validateRequired(fields.fullName, 'Họ và tên') && ok;
                    ok = validateDob() && ok;
                    ok = validateRequired(fields.gender, 'Giới tính') && ok;
                    ok = validateRequired(fields.address, 'Địa chỉ') && ok;
                    ok = validateJobTitle() && ok;
                    ok = validateEmailFormat() && ok;
                    ok = validatePhoneFormat() && ok;
                    ok = validateBio() && ok;
                    if (fields.avatar && fields.avatar.classList.contains('is-invalid')) {
                        ok = false;
                    }
                    if (!ok) {
                        e.preventDefault();
                        e.stopPropagation();
                    }
                });
            })();
        </script>
    </body>
</html>

