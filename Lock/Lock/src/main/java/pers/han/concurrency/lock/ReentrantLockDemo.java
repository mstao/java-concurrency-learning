package pers.han.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReentrantLockDemo implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);
    private static Lock lock = new ReentrantLock();
    private static final int NUM = 5;

    @Override
    public void run() {
        work();
    }

    public void work() {
        lock.lock();
        try{
             logger.info(Thread.currentThread().getName() + "获取到锁");
        } catch (Exception e) {
            
        } finally {
            lock.unlock();
            logger.info(Thread.currentThread().getName() + "释放了锁");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            ReentrantLockDemo demo = new ReentrantLockDemo();
            Thread thread = new Thread(demo);
            thread.start();
        }
    }
}
