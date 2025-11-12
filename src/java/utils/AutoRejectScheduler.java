package utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;
import service.OrganizationApplyService;

/**
 * Scheduler để tự động reject các pending applications 
 * khi còn 24h trước sự kiện
 * 
 * @author Admin
 */
@WebListener
public class AutoRejectScheduler implements ServletContextListener {
    
    private Timer timer;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Khởi tạo timer khi webapp start
        timer = new Timer(true); // daemon thread
        
        // Chạy mỗi 1 giờ (3600000 milliseconds)
        long interval = 3600000; // 1 hour
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    OrganizationApplyService service = new OrganizationApplyService();
                    int rejectedCount = service.autoRejectAllPendingApplications();
                    
                    if (rejectedCount > 0) {
                        System.out.println("[AutoRejectScheduler] Đã tự động từ chối " + rejectedCount 
                            + " đơn đăng ký do không được xử lý trong 24h trước sự kiện.");
                    }
                } catch (Exception e) {
                    System.err.println("[AutoRejectScheduler] Lỗi khi tự động reject: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }, 0, interval); // Chạy ngay lập tức, sau đó mỗi 1 giờ
        
        System.out.println("[AutoRejectScheduler] Đã khởi động - Chạy mỗi 1 giờ");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Dừng timer khi webapp stop
        if (timer != null) {
            timer.cancel();
            System.out.println("[AutoRejectScheduler] Đã dừng");
        }
    }
}
