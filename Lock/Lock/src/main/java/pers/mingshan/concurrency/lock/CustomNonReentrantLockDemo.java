package pers.mingshan.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomNonReentrantLockDemo {
    private static final Logger logger = LoggerFactory.getLogger(CustomNonReentrantLockDemo.class);
    private static LockDemo lock = new LockDemo();

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
            logger.info("add再调用其它的方法，判断不可重入锁能不能运行到这里。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            CustomNonReentrantLockDemo demo = new CustomNonReentrantLockDemo();
            demo.work();
        }
    }
}

class LockDemo {
    // 判断是否已加锁
    private boolean isLocked = false;

    public synchronized void lock() {
        while (isLocked) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isLocked = true;
    }

    public synchronized void unLock() {
        isLocked = false;
        this.notify();
    }
}