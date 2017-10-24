package pers.mingshan.concurrency.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读写锁测试
 * @author mingshan
 *
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        Queue queue = new Queue();
        for (int i = 0; i < 10; i++) {
            new Thread() {
              @Override
                public void run() {
                    queue.get();
                }  
            }.start();

            new Thread() {
                @Override
                public void run() {
                    queue.put();
                }
            }.start();
        }
    }
}

class Queue {
    private static final Logger logger = LoggerFactory.getLogger(Queue.class);
    // 共享变量
    private int data = 0;
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();

    /**
     * 读操作
     */
    public void get() {
        rwl.readLock().lock();
        try {
            logger.info(Thread.currentThread().getName() + "- 获取了读锁！");
            logger.info(Thread.currentThread().getName() + "- 读出数据为 ：" + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
    }

    /**
     * 写操作
     */
    public void put() {
        rwl.writeLock().lock();
        try {
            logger.info(Thread.currentThread().getName() + "- 获取了写锁！");
            data += 1;
            logger.info(Thread.currentThread().getName() + "- 写入数据为 ：" + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }
    }
}
