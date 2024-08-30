package juc;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    /**
     * Semaphore 是 Java concurrent 包中的一个同步工具类，用于控制同时访问特定资源的线程数量。它通常用于限制多个线程同时访问共享资源的数量，从而避免资源竞争或过载。
     * •	许可机制: Semaphore 内部维护了一个许可计数器，表示可以同时访问共享资源的最大线程数。线程在访问资源之前必须从 Semaphore 获取一个许可，当许可数量为 0 时，其他线程必须等待直到有线程释放许可。
     * •	可伸缩性: 可以通过设定初始的许可数量来控制并发访问的线程数量。
     * •	两种操作: acquire() 用于请求一个许可，如果没有可用许可，则线程会阻塞；release() 用于释放一个许可，使其他等待的线程可以继续执行。
     */
    public static void main(String[] args) {
        // 创建一个Semaphore，最多允许3个线程同时访问
        Semaphore semaphore = new Semaphore(3);

        // 启动6个线程，每个线程都会尝试获取许可
        for (int i = 1; i <= 6; i++) {
            final int threadNumber = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadNumber + " is trying to acquire a permit...");
                    semaphore.acquire();  // 获取一个许可
                    System.out.println("Thread " + threadNumber + " acquired a permit!");

                    // 模拟线程执行任务
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Thread " + threadNumber + " is releasing a permit.");
                    semaphore.release();  // 释放许可
                    // 特别注意 release() 的错误使用会导致释放别人持有中的许可函数
                    // 例如 初始化 1 张许可， A 线程获得了许可，B 线程也尝试获取，
                    // 但是，等待一段时间后 B 线程中断等待，导致触发了 B 线程的释放功能，
                    // 结果释放了 A 线程的许可，其实 A 线程此时本身还是 holding 状态以至于整个 Semaphore 状态异常
                }
            }).start();
        }
    }
}
