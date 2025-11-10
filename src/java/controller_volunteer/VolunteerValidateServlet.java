package controller_volunteer;

import dao.ProfileVolunteerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "VolunteerValidateServlet", urlPatterns = {"/VolunteerValidateServlet"})
public class VolunteerValidateServlet extends HttpServlet {

    private ProfileVolunteerDAO profileVolunteerDAO;

    @Override
    public void init() throws ServletException {
        profileVolunteerDAO = new ProfileVolunteerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        String type = request.getParameter("type"); // email | phone
        String value = request.getParameter("value");
        String idStr = request.getParameter("accountId");

        boolean exists = false;
        int accountId = -1;
        try {
            accountId = Integer.parseInt(idStr);
        } catch (Exception ignored) { }

        if (value != null && accountId > -1) {
            if ("email".equalsIgnoreCase(type)) {
                exists = profileVolunteerDAO.emailExistsForOther(value.trim(), accountId);
            } else if ("phone".equalsIgnoreCase(type)) {
                exists = profileVolunteerDAO.phoneExistsForOther(value.trim(), accountId);
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.write("{\"type\":\"" + (type == null ? "" : type) + "\",\"exists\":" + exists + "}");
        }
    }
}


