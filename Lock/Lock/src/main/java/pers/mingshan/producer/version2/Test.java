package pers.mingshan.producer.version2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition NOT_EMPTY = lock.newCondition();
    public static Condition NOT_FULL = lock.newCondition();
    public static void main(String[] args) {
        List<PCData> queue = new ArrayList<PCData>();
        int length = 10;
        Producer2 p1 = new Producer2(queue,length);
        Producer2 p2 = new Producer2(queue,length);
        Producer2 p3 = new Producer2(queue,length);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
    }
}
