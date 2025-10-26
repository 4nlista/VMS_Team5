/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_organization;

import dao.OrganizationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Organization;

@WebServlet("/organization/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 50,   // 50KB
                 maxFileSize = 1024 * 1024 * 5,    // 5MB
                 maxRequestSize = 1024 * 1024 * 10)// 10MB
public class OrganizationProfileServlet extends HttpServlet {

    private final OrganizationDAO organizationDAO = new OrganizationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect(request.getContextPath() + "/LoginService");
            return;
        }

        Organization org = organizationDAO.getOrganizationById(accountId);
        request.setAttribute("organization", org);
        request.getRequestDispatcher("/organization/profile_org.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");
        if (accountId == null) {
            response.sendRedirect(request.getContextPath() + "/LoginService");
            return;
        }

        // Read fields
        String fullName = request.getParameter("fullName");
        String jobTitle = request.getParameter("jobTitle");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String dobStr = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String bio = request.getParameter("bio");

        // Get existing org
        Organization org = organizationDAO.getOrganizationById(accountId);
        if (org == null) {
            response.sendRedirect(request.getContextPath() + "/organization/profile");
            return;
        }

        org.setFullName(fullName);
        org.setJobTitle(jobTitle);
        org.setEmail(email);
        org.setPhone(phone);
        org.setAddress(address);
        org.setGender(gender);
        org.setBio(bio);

        // parse dob
        try {
            if (dobStr != null && !dobStr.trim().isEmpty()) {
                java.util.Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
                org.setDob(parsed);
            } else {
                org.setDob(null);
            }
        } catch (Exception ex) {
            org.setDob(null);
        }

        // Handle file upload
        Part filePart = request.getPart("avatarFile"); // form field name
        if (filePart != null && filePart.getSize() > 0) {
            // Build path webapp/uploads/org/{id}/
            String uploadsDir = "/uploads/org/" + accountId + "/";
            String realPath = request.getServletContext().getRealPath(uploadsDir);
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // derive filename: avatar_timestamp.ext
            String submitted = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String ext = "";
            int i = submitted.lastIndexOf('.');
            if (i > 0) ext = submitted.substring(i);
            String filename = "avatar_" + System.currentTimeMillis() + ext;

            File file = new File(uploadDir, filename);
            try (InputStream is = filePart.getInputStream();
                 FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Save relative URL to DB (use context path + uploadsDir + filename)
            String avatarUrl = request.getContextPath() + uploadsDir + filename;
            org.setAvatar(avatarUrl);
        } else {
            // optionally allow text input avatarUrl (field "avatarUrl")
            String avatarUrl = request.getParameter("avatarUrl");
            if (avatarUrl != null && !avatarUrl.isBlank()) org.setAvatar(avatarUrl.trim());
        }

        boolean success = organizationDAO.updateOrganization(org);
        request.setAttribute("organization", org);
        request.setAttribute("message", success ? "Cập nhật hồ sơ thành công!" : "Cập nhật thất bại!");
        request.getRequestDispatcher("/organization/profile_org.jsp").forward(request, response);
    }
}
