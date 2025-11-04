package service;

import dao.AttendanceDAO;
import java.util.List;
import model.Attendance;

public class AttendanceService {

    private AttendanceDAO attendanceDAO = new AttendanceDAO();

    public List<Attendance> getAttendanceHistory(int volunteerId) {
        return attendanceDAO.getAttendanceHistoryByVolunteerId(volunteerId);
    }
}
