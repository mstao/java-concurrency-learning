package me.mingshan.demo.procon.condition;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author mingshan
 */
public class Test {
    private static final AtomicInteger mCount = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        for (int j = 1; j < 15; j++) {
            new Thread(new Producer(buffer), "Task #" + mCount.getAndIncrement())
                    .start();
        }

        for (int j = 1; j < 15; j++) {
            new Thread(new Consumer(buffer), "Task #" + mCount.getAndIncrement())
                    .start();
        }

    }
}
