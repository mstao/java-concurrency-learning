package me.mingshan.juc.demo.procon.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author mingshan
 */
public class Buffer<T> {
    private BlockingQueue<T> queue;

    public Buffer() {
        queue = new ArrayBlockingQueue<>(10);
    }

    /**
     * 从队列中取出一条记录，并移除
     * @return 数据
     * @throws InterruptedException
     */
    public T poll() throws InterruptedException {
        return queue.poll();
    }

    /**
     * 从队列中取出一条记录，并移除，队列为空时阻塞
     * @return
     * @throws InterruptedException
     */
    public T take() throws InterruptedException {
        return queue.take();
    }

    /**
     * 放入一条记录到队列，为防止对业务的影响，采用超时机制
     * @param t 数据
     * @return 返回{@code ture} 入队成功，返回{@code false} 入队失败
     * @throws InterruptedException
     */
    public boolean offer(T t) throws InterruptedException {
        return queue.offer(t, 2, TimeUnit.SECONDS);
    }

    /**
     * 放入一条记录到队列
     * @param t 数据
     * @return 返回{@code ture} 入队成功，返回{@code false} 入队失败
     */
    public boolean add(T t) {
        return queue.add(t);
    }

    /**
     * 判断队列是否为空
     * @return 返回{@code ture} 队列为空，返回{@code false} 队列不为空
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
