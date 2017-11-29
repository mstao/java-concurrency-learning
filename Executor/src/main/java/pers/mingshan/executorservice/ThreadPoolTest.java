package pers.mingshan.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 1. ExecutorService为接口 : 使用工厂类 Executors生成 三种不同的线程池
 * <ul>
 *   <li>newSingleThreadExecutor
 *      : 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *   </li>
 *   <li>newFixedThreadPool
 *      : 创建具有指定线程数目的线程池，如果任务数量大于线程数目，那么必须等待
 *   </li>
 *   <li>newCachedThreadPool
 *      : 根据用户任务数量来创建具有一定数量的线程池
 *   </li>
 * </ul>
 * 
 * 2. ScheduledExecutorService 为接口 , 可用来执行周期或定时任务
 * <ul>
 *   <li>newScheduledThreadPool</li>
 * </ul>
 * @author mingshan
 *
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 单线程化的线程池
        ExecutorService service2 = Executors.newSingleThreadExecutor();
        service2.execute(() -> {
            System.out.println("newSingleThreadExecutor test--");
        });

        //不固定线程数目的线程池
        ExecutorService threadPool2 = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool2.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ThreadName-"+ Thread.currentThread().getName());
                }
            });
        }

        // 利用线程池使用定时器
        Executors.newScheduledThreadPool(2).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("scheduleAtFixedRate test-- ");
            }
        }, 6, 2, TimeUnit.MINUTES);

        // ---------------------
        //  固定数目的线程池  
        //  newFixedThreadPool
        // ---------------------
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println("固定线程池 test");
            }
        });

        // JDK8 +
        /*   service.execute(() -> {
            System.out.println("固定线程池 test");
        });*/

        Future<String> future = service.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "我是小明";
            }
        });

        // JDK8 +
        /*  Future<String> future = service.submit(() -> {
            return "我是小明";
        });*/

        System.out.println("获得结果  - " + future.get());

        Future<String> future2 = service.submit(new Runnable() {
            
            @Override
            public void run() {
                // do something
            }
        }, "ccccc");

        System.out.println("2获取结果 - " + future2.get());
        service.shutdown();
    }
}
