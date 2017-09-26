package pers.mingshan.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 利用 {@link ReentrantLock} 的tryLock方法进行加锁，
 * 该方法进行尝试加锁，吐过加锁成功，返回true，
 * 加锁失败，直接返回false。
 * @author mingshan
 *
 */
public class ReentrantLockTryLockDemo implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);
    private static Lock lock = new ReentrantLock();
    private static final int NUM = 5;

    @Override
    public void run() {
        work();
    }

    public void work() {
        // 判断是否获取锁
        if (lock.tryLock()) {
            try{
                logger.info(Thread.currentThread().getName() + "获取到锁");
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               lock.unlock();
               logger.info(Thread.currentThread().getName() + "释放了锁");
           }
        } else {
            logger.info(Thread.currentThread().getName() + "获取锁失败！");
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        for (int i = 0; i < NUM; i++) {
            Thread thread = new Thread(demo);
            thread.start();
        }
    }
}
