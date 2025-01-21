package juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockerDemo {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            // 获取锁
            lock.lock();
            System.out.println("获取成功锁");
            // 本线程的锁持有次数
            System.out.println("当前锁持有次数" + lock.getHoldCount());
            lock.lock();
            System.out.println("重入锁");
            System.out.println("当前锁持有次数" + lock.getHoldCount());

            lock.unlock();
            System.out.println("释放一次锁");
            System.out.println("当前锁持有次数" + lock.getHoldCount());
        }).start();
        // 保证上述代码执行完毕
        TimeUnit.SECONDS.sleep(1);
        System.out.println("其他线程尝试获取锁");
        lock.lock();
        // 尝试获取锁 永远不会执行
    }

}
