package pers.mingshan.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue 是基于链接节点的、线程安全的队列。并发访问不需要同步。
 * 因为它在队列的尾部添加元素并从头部删除它们，所以只要不需要知道队列的大小，
 * ConcurrentLinkedQueue 对公共集合的共享访问就可以工作得很好。收集关于队列大小的信息会很慢，需要遍历队列。
 * 
 * 注意尽量不要使用size方法,
 * 判断队列中是否有元素用isEmpty
 * @author mingshan
 *
 */
public class ConcurrentLinkedQueueTest {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");
        queue.offer("e");
        queue.peek();// 获取头结点，但并不移除
        queue.poll();// 获取元素并且在队列中移除，如果队列为空返回null

        // 将队列中的元素转为数组形式
        Object[] arr = queue.toArray();

        for (Object obj : arr) {
            System.out.println(obj);
        }
        // 不推荐使用
        System.out.println(queue.size());
    }
}
