package pers.mingshan.concurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    Object[] items = new Object[1000];
    int putptr/*写索引*/, takeptr/*读索引*/, count/*队列中存在的数据个数*/; 

    public void put(Object obj) throws InterruptedException {
        lock.lock();
        try{
            while (items.length == count) {
                // 阻塞写
                notFull.await();
            }
            // 赋值
            items[putptr] = obj; 
            if (++putptr == items.length) {
                putptr = 0;
            }

            ++count;
            // 唤醒读线程
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object get() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                // 阻塞读
                notEmpty.await();
            }
            // 取值
            Object result = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;//如果读索引读到队列的最后一个位置了，那么置为0  
            --count;//个数--  
            notFull.signal();//唤醒写线程  
            return result;
        } finally {
            lock.unlock();
        }
    }
}
