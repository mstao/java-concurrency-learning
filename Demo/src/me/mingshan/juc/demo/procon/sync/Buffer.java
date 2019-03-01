package me.mingshan.juc.demo.procon.sync;


/**
 * 共享资源
 */
public class Buffer {
    // 已使用共享资源的数量
    private int count = 0;
    // 共享资源最大数量
    private int size = 10;

    /**
     * 生产者产生资源
     *
     * @throws InterruptedException
     */
    public synchronized void put() throws InterruptedException {
        while (count == size) {
            // 共享资源满了，生产者线此时需要阻塞，等待消费者消费共享资源再进行生产
            System.out.println("[Put] Current thread " + Thread.currentThread().getName() + " is waiting");
            this.wait();
        }

        count++;
        System.out.println("[Put] Current thread " + Thread.currentThread().getName()
                + " add 1 item, current count: " + count);
        this.notifyAll();
    }

    /**
     * 消费者消费资源
     */
    public synchronized void take() throws InterruptedException {
        // 如果共享资源为0，代表共享资源已被用尽，等下生产者生产再进行消费
        while (count == 0) {
            // 共享资源满了，生产者线此时需要阻塞，等待消费者消费共享资源再进行生产
            System.out.println("[Take] Current thread " + Thread.currentThread().getName() + " is waiting");
            this.wait();
        }

        count--;
        System.out.println("[Take] Current thread " + Thread.currentThread().getName()
                + " remove 1 item, current count: " + count);
        this.notifyAll();

    }

}