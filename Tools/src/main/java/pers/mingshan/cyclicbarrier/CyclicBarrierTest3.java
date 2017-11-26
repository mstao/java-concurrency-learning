package pers.mingshan.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 测试 isBroken方法
 * 
 * isBroken含义：
 * 
 * 查询此屏障是否处于损坏状态， 如果因为构造或最后一次重置而导致中断或超时，
 * 从而使一个或多个参与者摆脱此 barrier，或者因为异常而导致某个屏障操作失败，
 * 则返回 true；否则返回 false。
 *
 * @author mingshan
 *
 */
public class CyclicBarrierTest3 {

    public static void main(String[] args) {
        final CyclicBarrier cb = new CyclicBarrier(3);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("当前被阻塞的线程数 = " + cb.getNumberWaiting());
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        thread.interrupt();

        try {
            cb.await();
        } catch (Exception e) {
            System.out.println("阻塞的线程是否被中断 : " + cb.isBroken()); //打印true
        }
    }
}
