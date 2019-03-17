package me.mingshan.demo.locksupport;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author mingshan
 */
public class InterruptTest {

    @Test
    public void test1() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("thread...");
                LockSupport.park();
                System.out.println("thread done.");
            }
        });

        t.start();
        Thread.sleep(2000);

        t.interrupt(); //如果因为park而被阻塞，可以响应中断请求，并且不会抛出InterruptedException。
        System.out.println("main thread done.");
    }

}
