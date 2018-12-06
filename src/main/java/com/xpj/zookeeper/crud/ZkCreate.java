package com.xpj.zookeeper.crud;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZkCreate implements Watcher {

    // 连接地址
//    private static String host = ConfigFactory.load("spring.cloud.zookeeper.connect-string").toString();
    private static String host = "118.24.47.97";


    // 会话超时时间
    private static final int SESSION_TIMEOUT = 100000;

    private ZooKeeper zooKeeper = null;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * z这个就是监察者模式
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }

    /**
     * 连接
     *
     * @param hosts
     * @throws IOException
     * @throws InterruptedException
     */
    private ZooKeeper connect(String hosts) throws IOException, InterruptedException {
        long a = LocalDateTime.now().getSecond();
        zooKeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        countDownLatch.await();
        System.err.println("创建连接花费了: " + (LocalDateTime.now().getSecond() - a) + "秒");
        return zooKeeper;
    }

    /**
     * 创建Znode
     *
     * @param groupName
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        /**
         * 这是同步创建节点的方法
         *
         * var4:有四种类型的znode：
         *
         * 1、PERSISTENT-持久化目录节点 客户端与zookeeper断开连接后，该节点依旧存在
         *
         * 2、PERSISTENT_SEQUENTIAL-持久化顺序编号目录节点 客户端与zookeeper断开连接后，该节点依旧存在，只是Zookeeper给该节点名称进行顺序编号
         *
         * 3、EPHEMERAL-临时目录节点 客户端与zookeeper断开连接后，该节点被删除
         *
         * 4、EPHEMERAL_SEQUENTIAL-临时顺序编号目录节点 客户端与zookeeper断开连接后，该节点被删除，只是Zookeeper给该节点名称进行顺序编号
         *
         * var3:权限
         *
         *var2:Znode value
         *
         * var1: Znode key
         *
         */
        String createPath = zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.err.println("createPath: " + createPath);
    }

    /**
     * 关闭
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        if (zooKeeper != null) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                throw e;
            } finally {
                zooKeeper = null;
                System.gc();
            }
        }
    }

    static class MyStringCallBack implements AsyncCallback.StringCallback {

        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("异步创建回调结果：状态：" + rc + "；创建路径：" +
                    path + "；传递信息：" + ctx + "；实际节点名称：" + name);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZkCreate zkCreate = new ZkCreate();
        ZooKeeper zooKeeper = zkCreate.connect(host);
//        zkCreate.create("DOG!");

        // 同步创建临时节点
        String ephemeralPath = zooKeeper.create("/zk-test-create-ephemeral-", "123".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("同步创临时建节点成功：" + ephemeralPath);

        // 同步创建临时顺序节点
        String sequentialPath = zooKeeper.create("/zk-test-create-sequential-", "456".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("同步创建临时顺序节点成功：" + sequentialPath);

        /**
         * 这是异步创建节点的方法
         * 这里的创建设置了回调地址
         * Object 传到回调里面去,所以一般会设置上下文信息
         */
        // 异步创建临时节点
        zooKeeper.create("/zk-test-create-async-ephemeral-", "abc".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new MyStringCallBack(), "我是传递内容");

        // 异步创建临时顺序节点
        zooKeeper.create("/zk-test-create-async-sequential-", "def".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new MyStringCallBack(), "我是传递内容");

        Thread.sleep(100000); // 验证等待回调结果使用，可根据实际情况自行调整

//            TimeUnit.SECONDS.sleep(60);
        zkCreate.close();
    }
}

