package utils;

import dao.NotificationDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import model.EventVolunteer;
import model.Notification;
import service.OrganizationApplyService;

/**
 * Scheduler để tự động reject các pending applications khi còn 24h trước sự
 * kiện
 *
 * @author Admin
 */
@WebListener
public class AutoRejectScheduler implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Chạy ngay và lặp mỗi 1 phút (giống AutoCloseEventScheduler)
        scheduler.scheduleAtFixedRate(() -> {
            try {
                OrganizationApplyService service = new OrganizationApplyService();
                List<EventVolunteer> rejectedVolunteers = service.autoRejectAllPendingApplications();

                if (!rejectedVolunteers.isEmpty()) {
                    System.out.println("[AutoRejectScheduler] Đã tự động từ chối "
                            + rejectedVolunteers.size() + " đơn do còn < 24h trước sự kiện");
                    
                    // Gửi thông báo cho từng volunteer bị reject
                    NotificationDAO notiDAO = new NotificationDAO();
                    for (EventVolunteer volunteer : rejectedVolunteers) {
                        try {
                            Notification noti = new Notification();
                            noti.setSenderId(volunteer.getOrganizationId()); // Organization gửi
                            noti.setReceiverId(volunteer.getVolunteerId());  // Volunteer nhận
                            noti.setMessage("Đơn đăng ký sự kiện \"" + volunteer.getEventTitle()
                                    + "\" của bạn đã bị tự động từ chối do không được xử lý trong 24h trước sự kiện.");
                            noti.setType("apply");
                            noti.setEventId(volunteer.getEventId());
                            notiDAO.insertNotification(noti);
                        } catch (Exception e) {
                            System.err.println("[AutoRejectScheduler] Lỗi gửi thông báo cho volunteer "
                                    + volunteer.getVolunteerId() + ": " + e.getMessage());
                        }
                    }
                    System.out.println("[AutoRejectScheduler] Đã gửi " + rejectedVolunteers.size()
                            + " thông báo cho volunteers");
                }
            } catch (Exception e) {
                System.err.println("[AutoRejectScheduler] Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES); // 0 = chạy ngay, 1 = mỗi 1 phút

        System.out.println("[AutoRejectScheduler] Đã khởi động - Chạy mỗi 1 phút");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("[AutoRejectScheduler] Đã dừng");
        }
    }
}
