package pers.mingshan.countdownlatch;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使一个线程等待其他线程完成各自的工作后再执行<br>
 * 
 * CountdownLatch提供的方法
 * <ul>
 *   <li>CountDownLatch(int count) ：构造函数，需要传入一个初始值，即定义必须等待的先行完成的操作的数目</li>
 *   <li>await()：当调用await时候，调用线程处于等待挂起状态，直至计数器变成0再继续</li>
 *   <li>await(long timeout, TimeUnit unit)：可以指定等待时间，否则线程被中断</li>
 *   <li>countDown()：每个被等待的事件在完成的时候调用，计数器减一</li>
 * </ul>
 * @author mingshan
 *
 */
public class CountdownLatchTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "正准备接受命令");
                        cdOrder.await();
                        System.out.println("线程" + Thread.currentThread().getName() +
                        "已接受命令");
                        Thread.sleep((long)(Math.random()*10000));
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "回应命令处理结果");
                        cdAnswer.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            service.execute(runnable);
        }

        try {
            Thread.sleep((long)(Math.random() * 10000));

            System.out.println("线程" + Thread.currentThread().getName() +
                    "即将发布命令");
            cdOrder.countDown();
            System.out.println("线程" + Thread.currentThread().getName() +
            "已发送命令，正在等待结果");
            cdAnswer.await();
            System.out.println("线程" + Thread.currentThread().getName() +
            "已收到所有响应结果");
        } catch (Exception e) {
            e.printStackTrace();
        }

        service.shutdown();
    }
}
