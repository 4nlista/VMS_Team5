package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Tự động chuyển status = 'closed' cho các sự kiện đã kết thúc Chạy mỗi 1 giờ
 * kiểm tra và update
 */
@WebListener
public class AutoCloseEventScheduler implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Chạy ngay khi khởi động và lặp lại mỗi 1 phút
        scheduler.scheduleAtFixedRate(() -> {
            try {
                closeExpiredEvents();
                System.out.println("[AutoCloseEventScheduler] Đã kiểm tra và đóng sự kiện hết hạn");
            } catch (Exception e) {
                System.err.println("[AutoCloseEventScheduler] Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES); // 0 = chạy ngay, 1 = mỗi 1 phút

        System.out.println("[AutoCloseEventScheduler] Đã khởi động - Chạy mỗi 1 phút");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("[AutoCloseEventScheduler] Đã dừng");
        }
    }

    /**
     * Update status = 'closed' cho các events có end_date < GETDATE()
     */
    private void closeExpiredEvents() {
        String sql = """
            UPDATE dbo.events
            SET status = 'closed'
            WHERE status IN ('active', 'inactive')
              AND end_date < GETDATE()
        """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("[AutoCloseEventScheduler] Đã đóng " + rowsUpdated + " sự kiện hết hạn");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
