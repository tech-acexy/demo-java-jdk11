package juc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantRWLockerDemo {

    private static final List<String> s = new LinkedList<>();

    // 读写锁的核心概念是 读与读之间的线程访问并不会造成冲突，但是读与写，写与读，写与写之间是互斥的。读读之间可以优化性能。
    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock r = lock.readLock();
        ReentrantReadWriteLock.WriteLock w = lock.writeLock();
    }


    public String get(int index, ReentrantReadWriteLock.ReadLock r) {
        r.lock();
        try {
            return s.get(index);
        } finally {
            r.unlock();
        }
    }

    public void put(String v, ReentrantReadWriteLock.WriteLock w) {
        w.lock();
        try {
            s.add(v);
        } finally {
            w.unlock();
        }
    }

}
