package pers.mingshan.concurrency.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockForCacheDemo {
    private Object data = null;
    private volatile boolean cacheValid;
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();

    public void process() {
        rwl.readLock().lock();
        if (!cacheValid) {
            // 缓存没值，向缓存写值，因为读锁与写锁互斥，
            // 所以需要先释放读锁
            rwl.readLock().unlock();
            rwl.writeLock().lock();

            try {
                // 这个时候还需要再判断一下缓存有没有值，
                // 因为读锁与读锁之间不是互斥的，虽然此时一个线程获取到了写锁，
                // 其他线程则会阻塞，当当前线程释放了写锁，那么此时另一个线程不会再去重新
                // 检测缓存中是否有值，这时还需要再进行检测一下，即双重检测
                if (!cacheValid) {
                    data = "aaa";
                    cacheValid = true;
                }
                // 通过在释放写锁之前获得读锁来进行锁降级
                rwl.readLock().lock();
            } finally {
                // 最终要把写锁释放掉
                rwl.writeLock().unlock();
            }

            try {
                use(data);
            } finally {
                rwl.readLock().unlock();
            }
        }
    }

    public void use(Object data2) {
        System.out.println(data2);
    }
}
