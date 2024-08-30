package juc;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {

    /**
     * •	双向交换: 两个线程在特定的同步点互相交换数据，这样每个线程都可以从对方那里获得数据。
     * •	同步操作: Exchanger 的 exchange() 方法是阻塞的，当一个线程调用 exchange() 方法后，会等待另一个线程也调用 exchange()，并且交换数据完成后，两个线程才会继续执行。
     * •	并发环境下的简单数据交换: 适用于需要在两个线程之间共享或交换数据的场景，而不需要显式地进行锁定和等待通知。
     */
    public static void main(String[] args) {

        final Exchanger<String> exchanger = new Exchanger<>();

        // 创建两个线程，并启动它们
        new Thread(() -> {
            try {
                String data = "Thread-1 data";
                System.out.println(Thread.currentThread().getName() + "Thread-1: 准备交换数据...");
                Thread.sleep(1000); // 模拟交换前的准备时间
                // 将本线程数据发送给对方线程，并从对方线程获取到数据
                String exchangedData = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + "获取到其他线程的交换数据 " + exchangedData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                String data = "Thread-2 data";
                System.out.println(Thread.currentThread().getName() + "Thread-2: 准备交换数据...");
                Thread.sleep(2000); // 模拟交换前的准备时间
                // 将本线程数据发送给对方线程，并从对方线程获取到数据
                String exchangedData = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + "获取到其他线程的交换数据 " + exchangedData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
