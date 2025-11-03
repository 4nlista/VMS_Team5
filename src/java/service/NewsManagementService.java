/*
 * A friendly reminder to drink enough water
 */
package service;

import dao.NewsManagementDAO;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import model.New;

/**
 *
 * @author Mirinae
 */
public class NewsManagementService {

	private final NewsManagementDAO newsManDAO = new NewsManagementDAO();
	private final int pageSize = 7; // configurable if needed

	public List<New> getNewsByPage(int page, String status, String search) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return newsManDAO.getNewsWithFiltersAndPagination(page, pageSize, status, search);
	}

	public int getTotalPages(String status, String search) throws SQLException {
		int totalUsers = newsManDAO.getFilteredNewsCount(status, search);
		return (int) Math.ceil((double) totalUsers / pageSize);
	}

	public void loadNewsList(HttpServletRequest request) throws SQLException {
		int page = 1;
		String pageParam = request.getParameter("page");
		if (pageParam != null) {
			try {
				page = Integer.parseInt(pageParam);
			} catch (NumberFormatException ignored) {
			}
		}

		String status = request.getParameter("status");
		String search = request.getParameter("search");

		List<New> news = getNewsByPage(page, status, search);
		int totalPages = getTotalPages(status, search);

		request.setAttribute("news", news);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentStatus", status == null ? "" : status);
		request.setAttribute("currentSearch", search == null ? "" : search);
	}
}
