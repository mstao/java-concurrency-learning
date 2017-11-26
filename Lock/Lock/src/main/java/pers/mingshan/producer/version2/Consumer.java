package pers.mingshan.producer.version2;

import java.util.List;

/**
 * 消费者
 * @author mingshan
 *
 */
public class Consumer implements Runnable {
    private List<PCData> queue;
    public Consumer(List<PCData> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted())
                    break;
                PCData data = null;

                try {
                    Test.lock.lock();
                    if (queue.size() == 0){
                        Test.NOT_EMPTY.await();
                    }
                    Thread.sleep(1000);
                    data = queue.remove(0);
                    Test.NOT_FULL.signalAll();
                } finally {
                    Test.lock.unlock();
                }
                System.out.println("消费者ID:"+Thread.currentThread().getId()+" 消费了:"+data.getData()+" result:"+(data.getData()*data.getData()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
