package pers.mingshan.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 利用 {@link ReenTrantLock} 的lockInterruptibly方法
 * @author mingshan
 *
 */
public class LockInterruptiblyDemo {
    private static final Logger logger = LoggerFactory.getLogger(LockInterruptiblyDemo.class);
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        LockInterruptiblyDemo test = new LockInterruptiblyDemo();
        MyThread thread1 = new MyThread(test);
        MyThread thread2 = new MyThread(test);
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.interrupt();
    }  

    public void insert(Thread thread) throws InterruptedException {
        //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        lock.lockInterruptibly();
        try {  
            logger.info(thread.getName() + "得到了锁");
            long startTime = System.currentTimeMillis();
            for(; ;) {
                if (System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
                    break;
                //插入数据
            }
        } finally {
            logger.info(Thread.currentThread().getName() + "执行finally");
            lock.unlock();
            logger.info(thread.getName() + "释放了锁");
        }
    }
}

class MyThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);
    private LockInterruptiblyDemo test = null;

    public MyThread(LockInterruptiblyDemo test) {
        this.test = test;
    }

    @Override
    public void run() {

        try {
            test.insert(Thread.currentThread());
        } catch (InterruptedException e) {
            logger.info(Thread.currentThread().getName()+"被中断");
        }
    }
}
