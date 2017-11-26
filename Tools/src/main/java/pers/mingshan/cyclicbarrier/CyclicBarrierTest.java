package pers.mingshan.cyclicbarrier;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *  必须所有线程都到了 才能运行
 *  
 *  CyclicBarrier常用方法：
 *  <ul>
 *    <li>getParties(): 返回要求启动此 barrier 的参与者数目</li>
 *    <li>await():当前线程等待，知道所有线程到达这个barrier</li>
 *    <li>await(long timeout, TimeUnit unit)：可以指定一个等待时间</li>
 *    <li>isBroken():查询此屏障是否处于损坏状态</li>
 *    <li>reset(): 将屏障重置为其初始状态</li>
 *    <li>getNumberWaiting(): 返回当前在屏障处等待的参与者数目，只能用于调试</li>
 *  </ul>
 * @author mingshan
 *
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CyclicBarrier cb = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep((long)(Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName()
                                + "即将到达集合地点1，当前已有" + (cb.getNumberWaiting() + 1) + "个已经到达，"
                                + (cb.getNumberWaiting() == 2 ? "都到齐了，继续走啊" : "正在等候"));
                        cb.await();

                        Thread.sleep((long)(Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName()
                                + "即将到达集合地点2，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，"
                                + (cb.getNumberWaiting() == 2 ? "都到齐了，继续走啊" : "正在等候"));
                        cb.await();

                        Thread.sleep((long)(Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName()
                                + "即将到达集合地点3，当前已有" + (cb.getNumberWaiting() + 1) + "个已经到达，"
                                + (cb.getNumberWaiting() == 2 ? "都到齐了，继续走啊" : "正在等候"));
                        cb.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            service.execute(runnable);
        }

        service.shutdown();
    }
}
