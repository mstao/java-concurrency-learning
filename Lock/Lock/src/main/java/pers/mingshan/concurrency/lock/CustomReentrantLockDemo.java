package pers.mingshan.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义可重入锁
 * @author mingshan
 *
 */

public class CustomReentrantLockDemo {
    private static final Logger logger = LoggerFactory.getLogger(CustomNonReentrantLockDemo.class);
    private static LockDemo2 lock = new LockDemo2();

    public void work() {
        lock.lock();
        try {
            add();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }
    }

    public void add() {
        lock.lock();
        try {
            logger.info(Thread.currentThread().getName() + "--add再调用其它的方法，判断不可重入锁能不能运行到这里。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            CustomReentrantLockDemo demo = new CustomReentrantLockDemo();
            demo.work();
        }
    }
}

class LockDemo2 {
    // 判断是否已加锁
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockedCount = 0;

    public synchronized void lock() {
        Thread thread = Thread.currentThread();
        while (isLocked && lockedBy != thread) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isLocked = true;
        lockedCount++;
        lockedBy = thread;
    }

    public synchronized void unLock() {
        if (Thread.currentThread() == lockedBy) {
            lockedCount--;
            if (lockedCount == 0) {
                isLocked = false;
                this.notify();
            }
        }
    }
}



