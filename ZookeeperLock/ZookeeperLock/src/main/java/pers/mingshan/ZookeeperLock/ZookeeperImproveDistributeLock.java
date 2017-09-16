package pers.mingshan.ZookeeperLock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mingshan
 *
 */
public class ZookeeperImproveDistributeLock implements Lock{
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperImproveDistributeLock.class);
    // Zookeeper IP和端口
    private static final String ZK_IP_PORT = "localhost:2181";
    // Node 的名称
    private static final String LOCK_ROOT_NODE = "/lock";
    // 创建 Zookeeper 的客户端
    private ZkClient zkClient = new ZkClient(ZK_IP_PORT, 1000, 1000, new SerializableSerializer());
    // 当前创建的节点
    private String selfPath;
    // 当前节点的前一个节点
    private String beforePath;
    // 节点默认值
    private String data = "data";
    // 减数器
    private static CountDownLatch cdl = null;

    public ZookeeperImproveDistributeLock() {
        // 先创建一个主节点，以便其他线程在此节点之下创建临时顺序节点
        if (!this.zkClient.exists(LOCK_ROOT_NODE)) {
            this.zkClient.createPersistent(LOCK_ROOT_NODE);
        }
    }

    @Override
    public void lock() {
        // 先尝试加锁，加锁成功后就直接返回
        if (!tryLock()) {
            waitForLock();
            lock();
        } else {
            logger.info(Thread.currentThread().getName() + "---获取锁");
        }
    }

    private void waitForLock() {
        // 给节点加 监听器
        IZkDataListener listener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                logger.info("----before node delete event------");
                if (cdl != null) {
                    cdl.countDown();
                }
            }

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }
        };
        
        // 此时只需给前面的节点的添加wathcher即可
        zkClient.subscribeDataChanges(this.beforePath, listener);

        if (zkClient.exists(this.beforePath)) {
            try{
                cdl = new CountDownLatch(1);
                cdl.await();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 取消订阅前面的节点的变化
        zkClient.unsubscribeDataChanges(this.beforePath, listener);
    }

    @Override
    public boolean tryLock() {
        // 判断当前节点是否存在
        if (this.selfPath == null || this.selfPath.length() == 0) {
            // 在当前节点下创建临时顺序节点，例如0000000034
            this.selfPath = this.zkClient.createEphemeralSequential(LOCK_ROOT_NODE + "/", data);
            logger.info("当前节点为 ————> " + this.selfPath);
        }

        // 获取所有的临时顺序节点，并进行排序
        List<String> allESNodes = zkClient.getChildren(LOCK_ROOT_NODE);
        Collections.sort(allESNodes);
        logger.info("0  ————> "+ allESNodes.get(0));
        // 判断当前节点是否为最小节点
        if (this.selfPath.equals(LOCK_ROOT_NODE + "/" + allESNodes.get(0))) {
            // 如果当前结点为最小节点，说明当前可以加锁
            return true;
        } else {
            // 如果当前临时节点并非最小，代表当前客户端没有获取锁，需要继续等待,
            // 此时获取比当前节点序号小的节点（比当前节点小的最大节点, 将此值赋给beforePath
            // 例如： 当前节点是 /lock/000000003, 那么beforePath为 /lock/000000002，
            // 只有当beforePath获得锁并且释放锁后，当前客户端才能去获取锁
            // 这样可以 避免羊群效应
            int wz = Collections.binarySearch(allESNodes, this.selfPath.substring(6));
            this.beforePath = LOCK_ROOT_NODE + "/" + allESNodes.get(wz - 1);
        }

        return false;
    }

    @Override
    public void unlock() {
        // 删除当前节点，释放锁
        zkClient.delete(this.selfPath);
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
