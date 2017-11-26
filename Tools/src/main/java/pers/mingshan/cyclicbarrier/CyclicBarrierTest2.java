package pers.mingshan.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试CyclicBarrier， 其中在所有线程到达屏障点时，调用Runnable，
 * 此Runnable任务在CyclicBarrier的数目达到后，所有其它线程被唤醒前被执行
 * 
 * @author mingshan
 *
 */
public class CyclicBarrierTest2 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有线程到达屏障点");
            }
        });

        for (int i = 0; i < 3; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep((long)(Math.random() * 10000));
                        System.out.println("线程" + no + "到达屏障点，阻塞...");
                        // 线程在这里等待，直到所有线程都到达barrier。
                        cb.await();

                        Thread.sleep((long)(Math.random() * 10000));
                        System.out.println("线程" + no + "通过屏障点，开始工作");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

            service.execute(runnable);
        }

        service.shutdown();
    }
}
