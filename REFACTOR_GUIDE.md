# 🔧 HƯỚNG DẪN REFACTOR - THỐNG NHẤT UPLOAD ẢNH

## 📌 TÓM TẮT:
Đã tạo **UnifiedImageUploadService** để xử lý TẤT CẢ upload ảnh (avatar, news, events) với validation thống nhất (1MB cho avatar, 5MB cho news/event).

---

## ❌ FILES CẦN **XÓA HOÀN TOÀN**:

### 1. `src/java/service/AvatarUploadService.java`
**LÝ DO:** Đã thay thế bằng `UnifiedImageUploadService`
```
❌ XÓA TOÀN BỘ FILE NÀY
```

### 2. `src/java/controller_organization/UploadImagesServlet.java`
**LÝ DO:** 
- Dùng đường dẫn cứng: `C:\Users\Admin\Downloads\uploads\background`
- Không có validation
- Không tuân thủ chuẩn VMS_Uploads
```
❌ XÓA TOÀN BỘ FILE NÀY
```

---

## ⚠️ FILES CẦN **REFACTOR** (Giữ file, sửa code):

### 3. `src/java/service/FileStorageService.java`
**TRẠNG THÁI:** Đã comment `@Deprecated` các method cũ
**CẦN LÀM:**
- ✅ Giữ method `uploadImage()` (method CHÍNH)
- ✅ Giữ `getAvatarFile()`, `getNewsFile()`, `getEventFile()` (để serve ảnh)
- ✅ Giữ `detectContentType()`, `openAvatarStream()`
- ⏳ **SAU KHI REFACTOR HẾT CÁC SERVLET:** Xóa 2 method `@Deprecated`:
  - `saveAvatar()` ❌
  - `saveNewsImage()` ❌

---

### 4. `src/java/service/AdminProfileService.java`
**CẦN XÓA:** Method `handleAvatarUpload()`
**THAY BẰNG:** Dùng `UnifiedImageUploadService.uploadAvatar()`

**CODE CŨ (XÓA):**
```java
public boolean handleAvatarUpload(HttpServletRequest request, int userId) {
    // ❌ Xóa toàn bộ method này
}
```

**CODE MỚI (THÊM VÀO AdminProfileEditServlet):**
```java
UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
Map<String, Object> result = uploadService.uploadAvatar(request, userId, "avatar");

if ((boolean) result.get("success")) {
    String fileName = (String) result.get("fileName");
    userDAO.updateAvatar(userId, fileName);
    response.sendRedirect("AdminProfileServlet?id=1");
} else {
    String error = (String) result.get("error");
    request.setAttribute("errors", Map.of("avatar", error));
    // forward về JSP
}
```

---

### 5. `src/java/service/OrganizationProfileService.java`
**CẦN XÓA:** Method `handleAvatarUpload()`
**GIỐNG NHU TRÊN** - Dùng `UnifiedImageUploadService.uploadAvatar()`

---

### 6. `src/java/service/VolunteerProfileService.java`
**CẦN SỬA:** Method `processUpdate()` - phần xử lý avatar upload
**DÒNG CẦN THAY:**
```java
// ❌ CODE CŨ (tìm và xóa):
Part avatarPart = request.getPart("avatar");
if (avatarPart != null && avatarPart.getSize() > 0) {
    // validation thủ công...
    String saved = fileStorageService.saveAvatar(...);
}

// ✅ CODE MỚI (thay thế):
UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
Map<String, Object> uploadResult = uploadService.uploadAvatar(request, existingProfile.getAccountId(), "avatar");
if ((boolean) uploadResult.get("success")) {
    String fileName = (String) uploadResult.get("fileName");
    profileVolunteer.setImages(fileName);
} else {
    errors.put("avatar", (String) uploadResult.get("error"));
}
```

---

## 🔄 SERVLETS CẦN CẬP NHẬT:

### 7. `src/java/controller_admin/AdminProfileEditServlet.java`
**DÒNG 48-61:** Thay `profileService.handleAvatarUpload()` → `UnifiedImageUploadService`

```java
// ❌ XÓA:
boolean avatarOk = profileService.handleAvatarUpload(request, userId);

// ✅ THAY BẰNG:
UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
Map<String, Object> result = uploadService.uploadAvatar(request, userId, "avatar");
if (!(boolean) result.get("success")) {
    request.setAttribute("errors", Map.of("avatar", result.get("error")));
    // forward lại JSP
}
```

---

### 8. `src/java/controller_organization/OrganizationProfileEditServlet.java`
**DÒNG 55-82:** Giống AdminProfileEditServlet

---

### 9. `src/java/controller_volunteer/VolunteerProfileServlet.java`
**DÒNG 53-82:** Dùng `volunteerProfileService.processUpdate()` đã refactor

---

### 10. `src/java/controller_organization/OrganizationCreateEventServlet.java`
**CẦN REFACTOR:** Phần upload ảnh sự kiện trong `doPost()`

```java
// ❌ XÓA CODE UPLOAD THỦ CÔNG

// ✅ THAY BẰNG:
UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
Map<String, Object> result = uploadService.uploadEventImage(request, 0, "eventImage");

if ((boolean) result.get("success")) {
    String fileName = (String) result.get("fileName");
    event.setImages(fileName);
} else {
    session.setAttribute("message", result.get("error"));
    session.setAttribute("messageType", "error");
    // redirect lại form
}
```

---

### 11. `src/java/controller_organization/OrganizationNewsCreateServlet.java`
**CẦN REFACTOR:** Phần upload ảnh news trong `doPost()`

```java
// ❌ XÓA CODE CŨ (FileStorageService.saveNewsImage())

// ✅ THAY BẰNG:
UnifiedImageUploadService uploadService = new UnifiedImageUploadService();
Map<String, Object> result = uploadService.uploadNewsImage(request, 0, "newsImage");

if ((boolean) result.get("success")) {
    String fileName = (String) result.get("fileName");
    news.setImages(fileName);
} else {
    request.setAttribute("fieldErrors", Map.of("image", result.get("error")));
    // forward lại form
}
```

---

## ✅ FILES GIỮ NGUYÊN (KHÔNG SỬA):

### 12. `src/java/service/UnifiedImageUploadService.java`
✅ **GIỮ NGUYÊN** - Đây là service CHÍNH mới

### 13. `src/java/controller_view/ViewImageServlet.java`
✅ **GIỮ NGUYÊN** - Servlet serve ảnh ra view (avatar/news/event)

### 14. `src/java/controller_admin/AdminAvatarServlet.java`
✅ **GIỮ NGUYÊN** - Servlet serve avatar `/avatar/*`

### 15. `src/java/controller_organization/OrganizationAvatarServlet.java`
✅ **GIỮ NGUYÊN** - Servlet serve avatar organization

### 16. `src/java/controller_organization/NewsImageServlet.java`
✅ **GIỮ NGUYÊN** - Servlet serve ảnh news

---

## 📝 CHECKLIST THỰC HIỆN:

### BƯỚC 1: XÓA FILES
- [ ] Xóa `AvatarUploadService.java`
- [ ] Xóa `UploadImagesServlet.java`

### BƯỚC 2: REFACTOR SERVICES
- [ ] Xóa `AdminProfileService.handleAvatarUpload()`
- [ ] Xóa `OrganizationProfileService.handleAvatarUpload()`
- [ ] Sửa `VolunteerProfileService.processUpdate()`

### BƯỚC 3: CẬP NHẬT SERVLETS
- [ ] Sửa `AdminProfileEditServlet`
- [ ] Sửa `OrganizationProfileEditServlet`
- [ ] Sửa `VolunteerProfileServlet` (dùng service đã refactor)
- [ ] Sửa `OrganizationCreateEventServlet`
- [ ] Sửa `OrganizationNewsCreateServlet`

### BƯỚC 4: DỌN DẸP CUỐI CÙNG
- [ ] Xóa 2 method `@Deprecated` trong `FileStorageService.java`:
  - `saveAvatar()`
  - `saveNewsImage()`

---

## 🎯 KẾT QUẢ SAU KHI REFACTOR:

1. ✅ **1 service duy nhất** xử lý upload: `UnifiedImageUploadService`
2. ✅ **Validation thống nhất**:
   - Avatar: 1MB
   - News/Event: 5MB
3. ✅ **1 đường dẫn chung**: `user.home/VMS_Uploads/{avatars|news|events}`
4. ✅ **Dễ maintain**: Sửa 1 chỗ, ảnh hưởng toàn bộ hệ thống

---

**LƯU Ý:** Refactor từng bước, test sau mỗi thay đổi để đảm bảo không bị lỗi!
