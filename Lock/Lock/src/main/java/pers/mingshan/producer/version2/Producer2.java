package pers.mingshan.producer.version2;

import java.util.List;
import java.util.Random;

public class Producer2 implements Runnable {
    private List<PCData> queue;
    private int length;
    
    public Producer2(List<PCData> queue, int length) {
        this.queue = queue;
        this.length = length;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted())
                    break;
                Random r = new Random();
                long temp = r.nextInt(100);
                System.out.println(Thread.currentThread().getId() + " 生产了：" + temp);
                PCData data = new PCData();
                data.set(temp);

                Test.lock.lock();
                try {
                    if (queue.size() >= length) {
                        Test.NOT_FULL.wait();
                    }

                    queue.add(data);
                    Test.NOT_EMPTY.signalAll();
                } finally {
                    Test.lock.unlock();
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
