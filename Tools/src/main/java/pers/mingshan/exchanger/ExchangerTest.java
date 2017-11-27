package pers.mingshan.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger 用于两个并发线程之间在一个同步点进行数据交换。
 * 两个线程都要调用exchange，否则线程会阻塞
 * 
 * @author mingshan
 *
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Exchanger<String> exchanger = new Exchanger<String>();

        service.execute(() -> {
            String data = "10块布";
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "正在来的路上");
                Thread.sleep((long) Math.random() * 10000);
                System.out.println("线程" + Thread.currentThread().getName() + "到达指定地点，准备交换");
                String data2 = exchanger.exchange(data);
                System.out.println("线程 " + Thread.currentThread().getName() + "用" + data + "换回的 " + data2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            String data = "一匹马";

            try {
                System.out.println("线程" + Thread.currentThread().getName() + "正在来的路上");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("线程" + Thread.currentThread().getName() + "到达指定地点，准备交换");
                String data2 = exchanger.exchange(data);
                System.out.println("线程 " + Thread.currentThread().getName() + "用" + data + "换回的 " + data2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        service.shutdown();
    }
}
