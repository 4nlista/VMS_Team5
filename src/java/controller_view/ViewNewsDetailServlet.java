package controller_view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import model.New;
import service.AdminNewsService;

@WebServlet(name = "ViewNewsDetailServlet", urlPatterns = {"/ViewNewsDetailServlet"})

public class ViewNewsDetailServlet extends HttpServlet {

    private AdminNewsService newsService;

    @Override
    public void init() {
        newsService = new AdminNewsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/GuessNewServlet");
            return;
        }

        try {
            int newsId = Integer.parseInt(idParam);
            // Lấy chi tiết News
            New news = newsService.getNewsById(newsId);

            if (news == null) {
                response.sendRedirect(request.getContextPath() + "/GuessNewServlet");
                return;
            }
            // Chỉ hiển thị bài đã duyệt (published)
            if (!"published".equals(news.getStatus())) {
                response.sendRedirect(request.getContextPath() + "/GuessNewServlet");
                return;
            }
            // Set attribute
            request.setAttribute("news", news);
            // Forward to JSP
            request.getRequestDispatcher("/news_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/GuessNewServlet");
        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
