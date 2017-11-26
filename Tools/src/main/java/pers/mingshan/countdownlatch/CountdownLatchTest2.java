package pers.mingshan.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主线程等待其他线程执行完再执行
 * @author mingshan
 *
 */
public class CountdownLatchTest2 {

    public static void main(String[] args) throws InterruptedException {
        // 设置数量
        int count = 4;
        final CountDownLatch latch = new CountDownLatch(count);
        ExecutorService executor = Executors.newFixedThreadPool(count);

        for (int i = 0; i < count; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "任务开始工作！");

                    int workTime = new Random().nextInt(10 - 3 + 1) + 3;
                    try {
                        Thread.sleep(workTime * 1000);
                        System.out.println(Thread.currentThread().getName() + "完成工作，耗时" + workTime + "秒!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            }; 

            executor.execute(runnable);
        }

        // 主线程被阻塞，等待其他线程执行完毕
        latch.await();
        executor.shutdown();
        System.out.println("所有任务执行完毕！");
    }
}
