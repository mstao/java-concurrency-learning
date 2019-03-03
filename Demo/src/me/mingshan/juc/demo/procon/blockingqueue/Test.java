package me.mingshan.juc.demo.procon.blockingqueue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mingshan
 */
public class Test {
    private static final AtomicInteger mCount = new AtomicInteger(1);

    public static void main(String[] args) {
        Buffer resource = new Buffer();

        for (int j = 1; j < 15; j++) {
            new Thread(new Producer(resource), "Task #" + mCount.getAndIncrement())
                    .start();
        }

        for (int j = 1; j < 15; j++) {
            new Thread(new Consumer(resource), "Task #" + mCount.getAndIncrement())
                    .start();
        }
    }
}
