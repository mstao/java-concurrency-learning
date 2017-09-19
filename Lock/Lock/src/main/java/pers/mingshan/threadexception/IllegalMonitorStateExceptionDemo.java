package pers.mingshan.threadexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pers.mingshan.concurrency.lock.ReentrantLockDemo;

/**
 * IllegalMonitorStateException extends RuntimeException
 *   抛出该异常原因:
 *    表明某一线程已经试图等待对象的监视器，或者试图通知其他正在等待对象的监视器而本身没有指定监视器的线程。
 *    即当前线程不是此监视器的所有者。也就是要在当前线程锁定对象，才能用锁定的对象此行这些方法，
 *    需要用到synchronized ，锁定什么对象就用什么对象来执行notify(), notifyAll(),wait(), wait(long), wait(long, int)操作，
 *    否则就会报IllegalMonitorStateException异常。如：
 *    <code>
 *    synchronized(x) {
 *        x.notify();
 *    }
 *    </code>
 *    
 *    可以分以下几种情况进行考虑：
 *    1. 锁定方法所对应的对象实例
 *    <code>
 *    public synchronized void work() {
 *        this.notify();
 *        //或者直接写  notify();
 *    }
 *    </code>
 *
 *    2. 类锁
 *    <code>
 *    public void work() {
 *        synchronized(xx.class) {
 *            xx.notify();
 *        }
 *    }
 *    </code>
 * 
 *    3. 锁定其他对象
 *    <code>
 *    public Class Test{
 *        public Object lock = new Object();
 *        public static void method（）{
 *           synchronized (lock) {
 *            //需要调用 lock.notify();
 *           } 
 *        }
 *    }
 *    </code>
 * 
 * @author mingshan
 *
 */
public class IllegalMonitorStateExceptionDemo extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);
    private Calculator calculator;

    public IllegalMonitorStateExceptionDemo(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void run() {
        synchronized(calculator) {
            try {
                logger.info(Thread.currentThread().getName() + " 开始计算>>> ");
                calculator.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info(Thread.currentThread().getName() + " 计算结果为 == " + calculator.total);
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        for (int i = 0; i < 5; i++) {
            new IllegalMonitorStateExceptionDemo(calculator).start();
        }

        calculator.start();
    }
}

/**
 * void notify() 
 *  唤醒在此对象监视器上等待的单个线程。 
   void notifyAll() 
 *  唤醒在此对象监视器上等待的所有线程。 
 * void wait() 
 *  导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法
 *  
 * @author mingshan
 *
 */
class Calculator extends Thread {
    int total = 0;

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                total += i;
            }

            // 通知所有在此对象等待的线程
            this.notifyAll();
        }

    }
}
