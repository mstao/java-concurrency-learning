package pers.mingshan.ZookeeperLock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.log4j.Logger;

/**
 * 利用Zookeeper节点名称的唯一性进行加锁和释放锁操作。
 * 利用znode名称唯一性进行加锁，所有客户端去竞争加锁，但只有一个会加锁
 * 成功， 其他客户端需要等待加锁成功的客户端去释放锁，释放锁操作则是删除该节点，
 * 同时通知所有watch这个节点的客户端，其他的客户端再竞争加锁。
 * 由于释放锁会通知所有watch该节点的客户端，所以会出现羊群效应，
 * 造成资源浪费。
 * @author mingshan
 *
 */
public class ZookeeperDistributeLock implements Lock {
    private static Logger logger = Logger.getLogger(ZookeeperDistributeLock.class);
    // Zookeeper IP和端口
    private static final String ZK_IP_PORT = "localhost:2181";
    // Node 的名称
    private static final String LOCK_NODE = "/lockS";
    // 创建 Zookeeper 的客户端
    private ZkClient zkClient = new ZkClient(ZK_IP_PORT);

    // 减数器
    private static CountDownLatch cdl = null;

    /**
     * 阻塞式加锁
     */
    @Override
    public void lock() {
        // 先尝试加锁，加锁成功后就直接返回
        if (tryLock()) {
            return;
        }

        // 如果不成功， 需要等待其他线程 释放锁
        waitForLock();
        // 递归调用加锁
        lock();
    }

    /**
     * 等待其他线程释放锁
     */
    private void waitForLock() {
        // 给节点加 监听器
        IZkDataListener listener = new IZkDataListener() {
            
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                logger.info("----node delete event------");
                if (cdl != null) {
                    cdl.countDown();
                }
            }

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                // TODO Auto-generated method stub
                
            }
        };
        
        // 执行订阅node节点的数据变化
        zkClient.subscribeDataChanges(LOCK_NODE, listener);

        if (zkClient.exists(LOCK_NODE)) {
            try{
                cdl = new CountDownLatch(1);
                cdl.await();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 取消订阅node节点的数据变化
        zkClient.unsubscribeDataChanges(LOCK_NODE, listener);
    }

    /**
     * 实现非阻塞式加锁
     * @return
     */
    @Override
    public boolean tryLock() {
        try {
            zkClient.createPersistent(LOCK_NODE);
            return true;
        } catch (ZkNodeExistsException e) {
            logger.error("加锁失败 -- reason -" + e.getMessage());
            return false;
        }
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        zkClient.delete(LOCK_NODE);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }

}
