package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import model.Account;
import service.AdminAccountService;

@WebServlet(name = "AdminAccountServlet", urlPatterns = {"/AdminAccountServlet"})

public class AdminAccountServlet extends HttpServlet {

    private AdminAccountService adminAccountService;

    @Override
    public void init() {
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
                if (pageParam != null) page = Integer.parseInt(pageParam);
                if (page <= 0) page = 1;
            } catch (NumberFormatException ignored) {}

            Boolean status = null;
            if (statusParam != null && !statusParam.isEmpty()) {
                // expecting "active" or "inactive"
                if ("active".equalsIgnoreCase(statusParam)) status = true;
                else if ("inactive".equalsIgnoreCase(statusParam)) status = false;
            }

            // pagination always applied (filters optional)
            int totalItems = adminAccountService.countAccounts(role, status, search);
            int totalPages = (int) Math.ceil(totalItems / (double) pageSize);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;
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
                try {
                    int id = Integer.parseInt(idRaw);
                    adminAccountService.toggleAccountStatus(id);
                } catch (NumberFormatException ignored) {
                }
                response.sendRedirect(request.getContextPath() + "/AdminAccountServlet");
                break;
            }
            case "delete": {
                String idRaw = request.getParameter("id");
                try {
                    int id = Integer.parseInt(idRaw);
                    boolean ok = adminAccountService.deleteAccount(id);
                    if (!ok) {
                        response.sendRedirect(request.getContextPath() + "/AdminAccountServlet?msg=delete_failed");
                        break;
                    }
                } catch (NumberFormatException ignored) {
                    response.sendRedirect(request.getContextPath() + "/AdminAccountServlet?msg=delete_failed");
                    break;
                }
                response.sendRedirect(request.getContextPath() + "/AdminAccountServlet?msg=deleted");
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
