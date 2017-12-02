package pers.mingshan.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockInterruptiblyDemo2 implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(LockInterruptiblyDemo2.class);
    private static Lock lock = new ReentrantLock();

    @Override
    public void run() {
        logger.info(Thread.currentThread().getName() + " 试图获取锁");

        try {
            lock.lockInterruptibly();
            Thread.sleep(5000);
            logger.info(Thread.currentThread().getName() + " 获取到了锁");
        }  catch (InterruptedException e) {
            logger.info(Thread.currentThread().getName() + " interrupted");
        } finally {
            lock.unlock();
            logger.info("有锁可用了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockInterruptiblyDemo2 demo = new LockInterruptiblyDemo2();
        Thread thread1 = new Thread(demo);
        Thread thread2 = new Thread(demo);
        thread1.start();

        Thread.sleep(100);

        thread2.start();
        thread2.interrupt();
    }

}
