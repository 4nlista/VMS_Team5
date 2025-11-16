package utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
                int rejectedCount = service.autoRejectAllPendingApplications();

                if (rejectedCount > 0) {
                    System.out.println("[AutoRejectScheduler] Đã tự động từ chối "
                            + rejectedCount + " đơn do còn < 24h trước sự kiện");
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
