package pers.mingshan.executorservice.threadpoolexecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 利用 ThreadPoolExecutor 创建线程池
 * 
 * ThreadPoolExecutor类的构造函数如下：
 * <code>
 * public ThreadPoolExecutor(int corePoolSize,
                             int maximumPoolSize,
                             long keepAliveTime,
                             TimeUnit unit,
                             BlockingQueue<Runnable> workQueue,
                             ThreadFactory threadFactory,
                             RejectedExecutionHandler handler)
 * </code><br>
 * 
 * 参数意义：
 * <ul>
 *   <li>corePoolSize: 核心线程数</li>
 *   <li>maximumPoolSize: 最大线程数， 当线程数 >= corePoolSize的时候，
 *       会把runnable放入workQueue中。线程总数 = 核心线程数 + 非核心线程数</li>
 *   <li>keepAliveTime: 线程池中超过corePoolSize数目的空闲线程最大存活时间,
 *       超过这个时间会使得那么核心线程之外的空闲线程被杀死，
 *       如果想把这个时间也作用在核心线程上需要设置allowCoreThreadTimeOut(boolean)为true</li>
 *   <li>unit: 时间单位</li>
 *   <li>workQueue: 保存任务的阻塞队列</li>
 *   <li>handler: 拒绝策略</li>
 * </ul>
 * 
 * <br>
 * ThreadPoolExecutor类的执行过程：
 * 1. 当线程数小于corePoolSize时，创建一个线程执行任务
 * 2. 如果线程数大于corePoolSize且workQueue还没有满时，放入到workQueue中
 * 3. 线程数大于等于corePoolSize并且当workQueue满时，新任务新建线程(非核心线程)运行，线程总数要小于maximumPoolSiz
 * 4. 当线程总数等于maximumPoolSize并且workQueue满了的时候执行handler的rejectedExecution。也就是拒绝策略
 *
 * <br>
 * 拒绝策略有四种： 
 * <ul>
 *   <li>ThreadPoolExecutor.AbortPolicy(): 直接抛出异常RejectedExecutionException(默认策略)</li>
 *   <li>ThreadPoolExecutor.CallerRunsPolicy(): 直接调用run方法并且阻塞执行</li>
 *   <li>ThreadPoolExecutor.DiscardPolicy(): 不处理，直接丢弃提交的任务</li>
 *   <li>ThreadPoolExecutor.DiscardOldestPolicy(): 如果Executor还未shutdown的话，则丢弃工作队列的最近的一个任务，然后执行当前任务。 </li>
 * </ul>
 * @author mingshan
 *
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        int corePoolSize = 1;
        int maximumPoolSize = 2;
        int keepAliveTime = 10;
//              BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // 线程池和队列满了之后的处理方式
        // 1.抛出异常
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        // 2. 直接调用run方法并且阻塞执行
        RejectedExecutionHandler handler2 = new ThreadPoolExecutor.CallerRunsPolicy();
        // 3. 不处理，直接丢弃提交的任务
        RejectedExecutionHandler handler3 = new ThreadPoolExecutor.DiscardPolicy();
        // 4. 如果Executor还未shutdown的话，则丢弃工作队列的最近的一个任务，然后执行当前任务。
        RejectedExecutionHandler handler4 = new ThreadPoolExecutor.DiscardOldestPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, 
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue,
                threadFactory,
                handler);

        for (int j = 1; j < 15; j++) {
            threadPoolExecutor.execute(new Runnable() {

                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        System.out.println(threadPoolExecutor);
    }

}
