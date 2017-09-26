package pers.mingshan.concurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void m1() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("m1---");
            condition.await();
        } finally {
            lock.unlock();
        }

        condition.signal();
    }
    
    public void m2() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("m2---");
            condition.await();
        } finally {
            lock.unlock();
        }

        condition.signal();
    }
    
    public static void main(String[] args) throws InterruptedException {
        ConditionDemo demo = new ConditionDemo();

        for (int i = 1; i < 5; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        demo.m1();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    demo.m2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
