/*
 * A friendly reminder to drink enough water
 */
package service;

import dao.OrganizationNewsManagementDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.New;

/**
 *
 * @author Mirinae
 */
public class OrganizationNewsManagementService {

	private final OrganizationNewsManagementDAO newsManDAO = new OrganizationNewsManagementDAO();
	private final int pageSize = 7; // configurable if needed

	public List<New> getNewsByPage(int page, int organizationId, String status, String search) throws SQLException {
		if (page < 1) {
			page = 1;
		}
		return newsManDAO.getNewsWithFiltersAndPagination(page, pageSize, organizationId, status, search);
	}

	public int getTotalPages(int organizationId, String status, String search) throws SQLException {
		int total = newsManDAO.getFilteredNewsCount(organizationId, status, search);
		return (int) Math.ceil((double) total / pageSize);
	}

	public void loadNewsList(HttpServletRequest request) throws SQLException, ServletException {
		Integer organizationId = getOrganizationIdFromSession(request);
		if (organizationId == null) {
			throw new ServletException("No logged-in organization found in session.");
		}

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

		List<New> news = getNewsByPage(page, organizationId, status, search);
		int totalPages = getTotalPages(organizationId, status, search);

		request.setAttribute("news", news);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentStatus", status == null ? "" : status);
		request.setAttribute("currentSearch", search == null ? "" : search);
	}

	private Integer getOrganizationIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		//  check "account" attribute
		Object acc = session.getAttribute("account");
		if (acc != null) {
			try {
				// attempt common methods via reflection to avoid compile dependency
				java.lang.reflect.Method m;
				try {
					m = acc.getClass().getMethod("getOrganizationId");
					Object val = m.invoke(acc);
					if (val instanceof Integer) {
						return (Integer) val;
					}
					if (val instanceof Number) {
						return ((Number) val).intValue();
					}
				} catch (NoSuchMethodException ignored) {
				}

				try {
					m = acc.getClass().getMethod("getOrgId");
					Object val = m.invoke(acc);
					if (val instanceof Integer) {
						return (Integer) val;
					}
					if (val instanceof Number) {
						return ((Number) val).intValue();
					}
				} catch (NoSuchMethodException ignored) {
				}

				try {
					m = acc.getClass().getMethod("getId");
					Object val = m.invoke(acc);
					if (val instanceof Integer) {
						return (Integer) val;
					}
					if (val instanceof Number) {
						return ((Number) val).intValue();
					}
				} catch (NoSuchMethodException ignored) {
				}
			} catch (Exception e) {
				return null;
			}
		}
		// nothing found
		return null;
	}

	// Load the details of news
	public New loadNewsDetail(HttpServletRequest request) throws Exception {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Integer organizationId = getOrganizationIdFromSession(request);
			if (organizationId == null) {
				return null;
			}
			return newsManDAO.getNewsDetailById(id, organizationId);
		} catch (Exception e) {
			return null;
		}
	}
}
