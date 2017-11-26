## 线程同步辅助类
- Semaphore：对资源资源的并发访问控制
- CountDownLatch：用于等待多个并发事件的完成
- CyclicBarrier：功能跟CountDownLatch一样，但支持barrier重用
- Phaser：用于等待多个阶段并发事件的完成 。功能跟前面的CountDownLatch、CyclicBarrier功能一样，但是支持动态调整任务的数量。
- Exchanger：用户线程间数据交换


### CountDownLatch 常用方法
- CountDownLatch(int count) ：构造函数，需要传入一个初始值，即定义必须等待的先行完成的操作的数目
- await()：当调用await时候，调用线程处于等待挂起状态，直至计数器变成0再继续
- await(long timeout, TimeUnit unit)：可以指定等待时间，否则线程被中断
- countDown()：每个被等待的事件在完成的时候调用，计数器减一

### CyclicBarrier 常用方法

- getParties()：返回要求启动此 barrier 的参与者数目
- await()：当前线程等待，知道所有线程到达这个barrier
- await(long timeout, TimeUnit unit)：可以指定一个等待时间
- isBroken()：查询此屏障是否处于损坏状态， 如果因为构造或最后一次重置而导致中断或超时，从而使一个或多个参与者摆脱此 barrier，或者因为异常而导致某个屏障操作失败，则返回 true；否则返回 false。 
- reset()：将屏障重置为其初始状态
- getNumberWaiting()：返回当前在屏障处等待的参与者数目，只能用于调试

### Semaphore 常用方法
- acquire()：获得信号量，只有获得了信号量，才能操作共享资源。
- acquireUninterruptibly()：获得信号量，跟acquire的区别在于当线程被阻塞时，可能会被中断，acquire会抛出InterruptedException异常，而acquireUninterruptibly方法会忽略线程中断不会抛出异常。
- tryAcquire()：试图获得信号量。如果能获得就返回true，否则返回false。我们可以根据返回值来做出恰当的处理。
- release()：释信号量
- availablePermits():返回Semaphore当前可用的信号量，该方法只能用于调试测试