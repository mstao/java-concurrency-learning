package pers.mingshan.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * 
 * @author mingshan
 *
 */
public class PhaserTest {

    public static void main(String[] args) {
        // 创建时就需要参与的parties的数量
        int parties = 12;
        Phaser phaser = new Phaser();
        // 主线程先注册
        phaser.register();
        System.out.println("已注册的parties数量  = " + phaser.getRegisteredParties());
        ExecutorService executor = Executors.newFixedThreadPool(parties);
        for (int i = 0; i < parties; i++) {
            // 每创建一个task，我们就注册一个party
            phaser.register();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        int i = 0;
                        while (i < 3 && !phaser.isTerminated()) {
                            System.out.println("Generation:" + phaser.getPhase());
                            Thread.sleep(3000);
                            // 等待同一周期内，其他Task到达 
                            // 然后进入新的周期，并继续同步进行 
                            phaser.arriveAndAwaitAdvance();
                            i++;// 我们假定，运行三个周期即可  
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 任务完成，取消自己的注册
                        phaser.arriveAndDeregister();
                    }
                }
            };

            executor.execute(runnable);
        }

        // 取消主线程的注册
        phaser.arriveAndDeregister();
        executor.shutdown();
    }
}
