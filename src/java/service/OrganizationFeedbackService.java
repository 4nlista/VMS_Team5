package service;

import dao.OrganizationFeedbackDAO;
import java.util.List;
import model.Feedback;

public class OrganizationFeedbackService {

    private final OrganizationFeedbackDAO feedbackDAO;

    public OrganizationFeedbackService() {
        this.feedbackDAO = new OrganizationFeedbackDAO();
    }

    public List<Feedback> listFeedbacksForOrganization(int organizationAccountId, Integer rating, String status, String eventTitleQuery) {
        // Business rules could be added here (e.g., normalize inputs)
        String normalizedStatus = status == null ? null : status.trim();
        String trimmedQuery = eventTitleQuery == null ? null : eventTitleQuery.trim();
        return feedbackDAO.findByOrganization(organizationAccountId, rating, normalizedStatus, trimmedQuery);
    }
}


