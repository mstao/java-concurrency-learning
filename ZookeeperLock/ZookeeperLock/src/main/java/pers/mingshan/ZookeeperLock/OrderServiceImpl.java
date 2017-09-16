package pers.mingshan.ZookeeperLock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

public class OrderServiceImpl implements Runnable {
    
    private static OrderCodeGenerator generator = new OrderCodeGenerator();
    // 同时并发的线程数
    private static final int NUM = 10;
    private Logger logger = Logger.getLogger(getClass());
    // 根据线程数初始化倒计数器
    private static CountDownLatch cdl = new CountDownLatch(NUM);
    // lock锁
    private static final Lock lock = new ZookeeperImproveDistributeLock();

    public void createOrderCode() {
        String orderCode = null;

        lock.lock();
        try {
            orderCode = generator.getOrderCode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        logger.info((Thread.currentThread().getName() + ": 成功获取锁 =====> " + orderCode));
    }

    @Override
    public void run() {
        try {
            // 等待其他线程初始化
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        createOrderCode();
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            new Thread(new OrderServiceImpl()).start();
            // 每初始化一个线程， 计数器减一
            cdl.countDown();
        }
    }
}
