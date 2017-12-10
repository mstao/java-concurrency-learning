package pers.mingshan.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * BlockingDeque 双端阻塞队列，相对于BlockingQueue支持两个额外的操作：获取元素时等待双端队列变为非空；存储元素时等待双端队列中的空间变得可用。
 * 
 * 实现类：
 *    LinkedBlockingDeque
 *  
 * @author mingshan
 *
 */
public class BlockingDequeTest {

    public static void main(String[] args) {
        BlockingDeque<String> deque = new LinkedBlockingDeque<String>();
    }
}
