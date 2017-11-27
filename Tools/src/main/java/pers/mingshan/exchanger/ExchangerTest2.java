package pers.mingshan.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 利用Exchanger模拟生产者，消费者
 * @author mingshan
 *
 */
public class ExchangerTest2 {

    public static void main(String[] args) {
        Exchanger<List<String>> exchanger = new Exchanger<>();

        List<String> queue1 = new ArrayList<>();
        List<String> queue2 = new ArrayList<>();

        new Thread(new Producer(queue1, exchanger)).start();
        new Thread(new Consumer(queue2, exchanger)).start();
    }
}

class Producer implements Runnable {
    private List<String> queue;
    private Exchanger<List<String>> exchanger;

    public Producer(List<String> queue, Exchanger<List<String>> exchanger) {
        this.queue = queue;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        // 循环三次
        for (int j = 1; j <= 3; j++) {
            
            System.out.println("--------开始第" + j + "次生产---------");
            try {
                // 每次生产5个商品
                for (int i = 1; i <= 5; i++) {
                    queue.add("商品" + j + "_" + i);
                    System.out.println("生成了商品，编号" + j + "_" + i);
                }

                queue = exchanger.exchange(queue);
                System.out.println("交换完后，生产者商品库存量为【" + queue.size() + "】");
                Thread.sleep((long)(Math.random()*5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}

class Consumer implements Runnable {
    private List<String> queue;
    private Exchanger<List<String>> exchanger;

    public Consumer(List<String> queue, Exchanger<List<String>> exchanger) {
        this.queue = queue;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            try {
                queue = exchanger.exchange(queue);
                System.out.println("交换完后，消费者获取到的商品数量为【" + queue.size() + "】");
                Thread.sleep((long)(Math.random()*5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}