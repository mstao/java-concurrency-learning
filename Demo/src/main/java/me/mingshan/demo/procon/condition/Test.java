package me.mingshan.demo.procon.condition;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mingshan
 */
public class Test {
    private static final AtomicInteger mCount = new AtomicInteger(1);

    public static void main(String[] args) {
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
