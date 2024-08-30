package juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    /**
     * 用途: 用于一组线程互相等待，直到所有线程都到达某个共同的屏障点后再继续执行。
     * •	工作方式: CyclicBarrier 有一个初始计数器，所有线程调用 await() 方法时会等待，直到有指定数量的线程都调用了 await()，然后所有线程同时继续执行。
     * •	用法: 适用于需要多次使用的场景，如在分阶段任务中，所有线程都需要同步到一个点后再继续下一阶段的执行。
     * •	特点:
     * •	可重用: 当所有线程到达屏障点并继续执行后，CyclicBarrier 可以被重置再使用。
     * •	双向: CyclicBarrier 在达到屏障后会自动重置。
     * •	可选操作: 可以指定一个在所有线程都到达屏障点后要执行的任务（Runnable）。
     */
    public static void main(String[] args) {

        // CyclicBarrier的参与线程数为4，并且当所有线程都到达屏障后，将会执行BarrierAction任务
        CyclicBarrier barrier = new CyclicBarrier(4, new BarrierAction());

        // 创建四个线程，并启动它们
        for (int i = 0; i < 4; i++) {
            Thread worker = new Thread(new Worker(barrier), "Worker-" + (i + 1));
            worker.start();
        }
    }

    // 该类实现Runnable，用于执行CyclicBarrier的Action
    static class BarrierAction implements Runnable {
        @Override
        public void run() {
            System.out.println("All workers reached the barrier. Moving to the next phase...\n");
        }
    }

    // 该类实现Runnable，用于模拟每个工人执行的任务
    static class Worker implements Runnable {
        private final CyclicBarrier barrier;

        public Worker(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // 三个阶段的任务模拟
                for (int i = 1; i <= 3; i++) {
                    System.out.println(Thread.currentThread().getName() + " is working on phase " + i);
                    // 模拟工作的时间
                    Thread.sleep((int) (Math.random() * 3000) + 1000);

                    // 等待其他线程到达屏障
                    System.out.println(Thread.currentThread().getName() + " finished phase " + i + " and is waiting...");
                    barrier.await();  // 调用await()等待其他线程
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
