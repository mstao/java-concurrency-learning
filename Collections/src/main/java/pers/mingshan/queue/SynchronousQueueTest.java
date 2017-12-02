package pers.mingshan.queue;

import java.util.concurrent.SynchronousQueue;

/**
 *  同步阻塞队列，该队列没有容量，不会存储数据
 * @author mingshan
 *
 */
public class SynchronousQueueTest {
    private static SynchronousQueue<String> queue = new SynchronousQueue<String>();

    public static void main(String[] args) {
        new Producer(1, queue).start();
        new Producer(2, queue).start();;

        new Consumer(queue).start();
        //new Consumer(queue).start();
        System.out.println("main thread is completed!");
    }
}

/**
 * 生产者
 * @author mingshan
 *
 */
class Producer extends Thread {
    private int id;
    private SynchronousQueue<String> queue;

    public Producer(int id, SynchronousQueue<String> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String data = "" + id;
            System.out.println("Producer id = " + data + ", Begin put data...");
            queue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 消费者
 * @author mingshan
 *
 */
class Consumer extends Thread {
    private SynchronousQueue<String> queue;

    public Consumer(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println("consume begin... ");
            String v = queue.take();
            System.out.println("consume success " + v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}