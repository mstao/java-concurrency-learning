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

## Phaser 常用方法

- register():将一个新的参与者(party)注册到phase中，parties个数加一，这个新的参与者将被当成没有执完本阶段的线程。如果此时onAdvance方法正在执行，此方法将会等待它执行完毕后才会返回。此方法返回当前的phase周期数，如果Phaser已经中断，将会返回负数。
- bulkRegister(int parties)：跟register一样，只是可以注册多个party
- arrive()：参与者已经到达该phaser阶段，不需要该等待其他参与者都完成当前阶段（非阻塞）。如果没有register（即已register数量为0），调用此方法将会抛出异常，此方法返回当前phase周期数，如果Phaser已经终止，则返回负数。必须小心使用这个方法，因为它不会与其他线程同步。
- arriveAndDeregister():参与者已经到达该phaser阶段，并且减少参与者即parties个数减一，不需要该等待其他参与者都完成当前阶段（非阻塞）。
- arriveAndAwaitAdvance()：参与者已经到达该phaser阶段，并且并等待其他参与者到达，才开始运行下面的代码。该方法等同于awaitAdvance(arrive());的效果
- awaitAdvance(int phase)：如果传入的阶段参数与当前阶段一致，这个方法会将当前线程至于休眠，直到这个阶段的所有参与者都运行完成。如果传入的阶段参数与当前阶段不一致，这个方法立即返回。
- awaitAdvanceInterruptibly(int phaser):这个方法跟awaitAdvance(int phase)一样，不同处是：该访问将会响应线程中断。会抛出interruptedException异常。
- awaitAdvanceInterruptibly(int phase,long timeout, TimeUnit unit):同上，可以指定一个等待时间，超时后抛出TimeoutException异常。
- forceTermination()：强制终止。当一个phaser没有参与者的时候，它就处于终止状态，使用forceTermination()方法来强制phaser进入终止状态，不管是否存在未注册的参与线程，当一个线程出现错误时，强制终止phaser是很有意义的。
- isTerminated(): 阶段是否已经结束
- onAdvance():这个后面再介绍
- forceTermination()：强制终止，此后Phaser对象将不可用，即register等方法将不再有效。此方法将会导致Queue中所有的waiter线程被唤醒。
- getArrivedParties():获取已经到达的parties个数。
- getPhase()：获取当前phase周期数。如果Phaser已经中断，则返回负值。
- getRegisteredParties()：获取已经注册的parties个数。
- getUnarrivedParties()：获取尚未到达的parties个数。
