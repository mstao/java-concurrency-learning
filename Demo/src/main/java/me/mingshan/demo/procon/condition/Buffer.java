package me.mingshan.demo.procon.condition;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mingshan
 */
public class Buffer {
    private ArrayList<String> queue;
    private int size;

    private final Lock lock = new ReentrantLock();
    // 读线程条件
    private final Condition notEmpty = lock.newCondition();
    // 写线程条件
    private final Condition notNull = lock.newCondition();

    public Buffer() {
        this(10);
    }

    public Buffer(int size) {
        this.size = size;
        queue = new ArrayList<>();
    }

    public void put() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == size) {
                System.out.println("[Put] Current thread " + Thread.currentThread().getName() + " is waiting");
                notNull.await();
            }

            queue.add("1");
            System.out.println("[Put] Current thread " + Thread.currentThread().getName()
                    + " add 1 item, current count: " + queue.size());
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0) {
                System.out.println("[Take] Current thread " + Thread.currentThread().getName() + " is waiting");
                notEmpty.await();
            }

            System.out.println("size = " + queue.size());

            queue.remove(queue.size() - 1);
            System.out.println("[Take] Current thread " + Thread.currentThread().getName()
                    + " remove 1 item, current count: " + queue.size());
            notNull.signal();
        } finally {
            lock.unlock();
        }

    }
}