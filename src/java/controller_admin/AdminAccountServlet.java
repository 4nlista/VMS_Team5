package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import model.Account;
import service.AccountService;
import service.AdminAccountService;

@WebServlet(name = "AdminAccountServlet", urlPatterns = {"/AdminAccountServlet"})

public class AdminAccountServlet extends HttpServlet {

    private AccountService accountService;
    private AdminAccountService adminAccountService;

    @Override
    public void init() {
        accountService = new AccountService();
        adminAccountService = new AdminAccountService();
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            // parse filter params + pagination
            String role = request.getParameter("role");
            String statusParam = request.getParameter("status");
            String search = request.getParameter("search");
            String pageParam = request.getParameter("page");
            int page = 1;
            int pageSize = 5; // yêu cầu: 5 item/trang
            try {
                if (pageParam != null) {
                    page = Integer.parseInt(pageParam);
                }
                if (page <= 0) {
                    page = 1;
                }
            } catch (NumberFormatException ignored) {
            }

            Boolean status = null;
            if (statusParam != null && !statusParam.isEmpty()) {
                // expecting "active" or "inactive"
                if ("active".equalsIgnoreCase(statusParam)) {
                    status = true;
                } else if ("inactive".equalsIgnoreCase(statusParam)) {
                    status = false;
                }
            }

            // pagination always applied (filters optional)
            int totalItems = adminAccountService.countAccounts(role, status, search);
            int totalPages = (int) Math.ceil(totalItems / (double) pageSize);
            if (totalPages == 0) {
                totalPages = 1;
            }
            if (page > totalPages) {
                page = totalPages;
            }
            List<Account> accounts = adminAccountService.findAccountsPaged(role, status, search, page, pageSize);

            // keep selections
            request.setAttribute("selectedRole", role);
            request.setAttribute("selectedStatus", statusParam);
            request.setAttribute("searchText", search);

            request.setAttribute("accounts", accounts);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalItems", totalItems);
            request.getRequestDispatcher("/admin/accounts_admin.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "toggle": {
                String idRaw = request.getParameter("id");
                // Lấy các tham số filter và page để giữ lại
                String role = request.getParameter("role");
                String statusParam = request.getParameter("status");
                String search = request.getParameter("search");
                String pageParam = request.getParameter("page");
                
                try {
                    int id = Integer.parseInt(idRaw);
                    boolean success = adminAccountService.toggleAccountStatus(id);
                    if (!success) {
                        // Kiểm tra lý do thất bại để hiển thị thông báo phù hợp
                        Account acc = adminAccountService.getAccountById(id);
                        StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/AdminAccountServlet?");
                        if (acc != null && "organization".equalsIgnoreCase(acc.getRole()) && acc.isStatus()) {
                            // Đang cố khóa Organization nhưng có sự kiện trong 48h
                            redirectUrl.append("msg=lock_org_48h_error");
                        } else {
                            // Lỗi khác (có thể là admin hoặc lỗi khác)
                            redirectUrl.append("msg=lock_error");
                        }
                        // Giữ lại các tham số filter và page
                        if (role != null && !role.isEmpty()) {
                            redirectUrl.append("&role=").append(role);
                        }
                        if (statusParam != null && !statusParam.isEmpty()) {
                            redirectUrl.append("&status=").append(statusParam);
                        }
                        if (search != null && !search.isEmpty()) {
                            redirectUrl.append("&search=").append(java.net.URLEncoder.encode(search, "UTF-8"));
                        }
                        if (pageParam != null && !pageParam.isEmpty()) {
                            redirectUrl.append("&page=").append(pageParam);
                        }
                        response.sendRedirect(redirectUrl.toString());
                        break;
                    }
                } catch (NumberFormatException ignored) {
                }
                
                // Build redirect URL với các tham số filter và page
                StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/AdminAccountServlet");
                boolean hasParams = false;
                if (role != null && !role.isEmpty()) {
                    redirectUrl.append(hasParams ? "&" : "?").append("role=").append(role);
                    hasParams = true;
                }
                if (statusParam != null && !statusParam.isEmpty()) {
                    redirectUrl.append(hasParams ? "&" : "?").append("status=").append(statusParam);
                    hasParams = true;
                }
                if (search != null && !search.isEmpty()) {
                    redirectUrl.append(hasParams ? "&" : "?").append("search=").append(java.net.URLEncoder.encode(search, "UTF-8"));
                    hasParams = true;
                }
                if (pageParam != null && !pageParam.isEmpty()) {
                    redirectUrl.append(hasParams ? "&" : "?").append("page=").append(pageParam);
                }
                response.sendRedirect(redirectUrl.toString());
                break;
            }
            case "delete": {
                String idRaw = request.getParameter("id");
                // Lấy các tham số filter và page để giữ lại
                String role = request.getParameter("role");
                String statusParam = request.getParameter("status");
                String search = request.getParameter("search");
                String pageParam = request.getParameter("page");
                
                try {
                    int id = Integer.parseInt(idRaw);
                    boolean ok = adminAccountService.deleteAccount(id);
                    if (!ok) {
                        // Build redirect URL với thông báo lỗi và các tham số filter và page
                        StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/AdminAccountServlet?msg=delete_failed");
                        if (role != null && !role.isEmpty()) {
                            redirectUrl.append("&role=").append(role);
                        }
                        if (statusParam != null && !statusParam.isEmpty()) {
                            redirectUrl.append("&status=").append(statusParam);
                        }
                        if (search != null && !search.isEmpty()) {
                            redirectUrl.append("&search=").append(java.net.URLEncoder.encode(search, "UTF-8"));
                        }
                        if (pageParam != null && !pageParam.isEmpty()) {
                            redirectUrl.append("&page=").append(pageParam);
                        }
                        response.sendRedirect(redirectUrl.toString());
                        break;
                    }
                } catch (NumberFormatException ignored) {
                    // Build redirect URL với thông báo lỗi và các tham số filter và page
                    StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/AdminAccountServlet?msg=delete_failed");
                    if (role != null && !role.isEmpty()) {
                        redirectUrl.append("&role=").append(role);
                    }
                    if (statusParam != null && !statusParam.isEmpty()) {
                        redirectUrl.append("&status=").append(statusParam);
                    }
                    if (search != null && !search.isEmpty()) {
                        redirectUrl.append("&search=").append(java.net.URLEncoder.encode(search, "UTF-8"));
                    }
                    if (pageParam != null && !pageParam.isEmpty()) {
                        redirectUrl.append("&page=").append(pageParam);
                    }
                    response.sendRedirect(redirectUrl.toString());
                    break;
                }
                
                // Build redirect URL với thông báo thành công và các tham số filter và page
                StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/AdminAccountServlet?msg=deleted");
                if (role != null && !role.isEmpty()) {
                    redirectUrl.append("&role=").append(role);
                }
                if (statusParam != null && !statusParam.isEmpty()) {
                    redirectUrl.append("&status=").append(statusParam);
                }
                if (search != null && !search.isEmpty()) {
                    redirectUrl.append("&search=").append(java.net.URLEncoder.encode(search, "UTF-8"));
                }
                if (pageParam != null && !pageParam.isEmpty()) {
                    redirectUrl.append("&page=").append(pageParam);
                }
                response.sendRedirect(redirectUrl.toString());
                break;
            }
            default: {
                response.sendRedirect(request.getContextPath() + "/AdminAccountServlet");
            }
        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Tạm thời chưa xử lý, sau thêm các chức năng thêm, sửa, xóa
        doGet(request, response);
    }
}
