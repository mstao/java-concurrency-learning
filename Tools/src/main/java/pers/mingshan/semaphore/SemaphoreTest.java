package pers.mingshan.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
/**
 * 信号量
 * 
 * Semaphore类是一个计数信号量，必须由获取它的线程释放，
 * 通常用于限制可以访问某些资源（物理或逻辑的）线程数目。
 * 通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 * 
 * @author mingshan
 *
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore sp = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        sp.acquire();

                        System.out.println("线程" + Thread.currentThread().getName() +
                                "进入，当前已有" + (3 - sp.availablePermits()) + "个并发");
                        Thread.sleep((long)(Math.random()*10000));
    
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "即将离开");
                        sp.release();

                        // 下面代码有时候执行不准确，因为主要用于调试
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "已离开，当前已有" + (3 - sp.availablePermits()) + "个并发");
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            };

            service.execute(runnable);
        }

        service.shutdown();
    }

}
