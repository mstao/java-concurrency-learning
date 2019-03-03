package me.mingshan.juc.demo.procon.blockingqueue;

/**
 * @author mingshan
 */
public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Processing consumer handle data, Current Thread: " +
                        Thread.currentThread().getName());
                // 从队列中取数据，空队列线程等待，不会一直进行for循环
                buffer.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
