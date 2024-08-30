package juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

    /**
     * 	•	用途: 用于一个或多个线程等待其他线程完成操作。
     * 	•	工作方式:  CountDownLatch 初始化时有一个计数器，每次调用 countDown() 方法时计数器减一，计数器变为零时，所有调用 await() 的线程会继续执行。
     * 	•	用法: 适用于一个或多个线程必须等待其他线程完成某些任务的场景，例如主线程等待多个子线程执行完毕。
     * 	•	特点:
     * 	•	一次性使用: 计数器一旦到零，CountDownLatch 就不能重用。
     * 	•	单向: 计数器只能递减，不能重置。
     */
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("task 1 finish");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        }).start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("task 2 finish");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        }).start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("task 3 finish");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        }).start();

        latch.await();
        System.out.println("all done");
    }

}
