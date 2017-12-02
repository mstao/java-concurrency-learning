package pers.mingshan.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * BlockingQueue常用操作
 * 
 * <ul>
 *   <li>add: 添加元素。和collection的add一样，如果当前没有可用的空间，则抛出 IllegalStateException异常</li>
 *   <li>put: 添加元素。如果没有可用的空间，将阻塞，直到能够有空间插入元素</li>
 *   <li>offer: 添加元素。成功时返回 true，如果当前没有可用的空间，则返回 false，不会抛异常，可以设置等待时间</li>
 *   <li>take: 获取并移除此队列的头部。没有元素时，一直阻塞等待。</li>
 *   <li>poll: 获取并移除此队列的头部。可以设置等待时间。</li>
 * </ul>
 * 
 * BlockingQueue的实现类
 * 
 * <ul>
 *   <li>ArrayBlockingQueue：一个由数组支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序。</li>
 *   <li>LinkedBlockingQueue：一个基于链接节点的有界队列。此队列按 FIFO（先进先出）排序元素。</li>
 *   <li>PriorityBlockingQueue：一个无界阻塞队列，它使用与类 PriorityQueue 相同的顺序规则，并且提供了阻塞获取操作。</li>
 *   <li>DelayQueue：一个无界阻塞队列，只有在延迟期满时才能从中提取元素。</li>
 *   <li>SynchronousQueue：一种阻塞队列，其中每个插入操作必须等待另一个线程的对应移除操作 ，反之亦然。</li>
 *   <li>DelayedWorkQueue：他是ScheduledThreadPoolExecutor的静态内部类</li>
 *   <li>TransferQueue: jdk1.7新增的接口。转移队列接口，生产者要等消费者消费的队列，生产者尝试把元素直接转移给消费者</li>
 *   <li>LinkedTransferQueue：dk1.7新增的类。它是TransferQueue接口的实现类。转移队列的链表实现，它比SynchronousQueue更快</li>
 * </ul>
 * 
 * @author mingshan
 *
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        BlockingQueue queue = new LinkedTransferQueue<String>();
    }
}
