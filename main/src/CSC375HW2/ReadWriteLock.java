package CSC375HW2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class ReadWriteLock {
    private static int readers;
    private static int writers;
    private static int waitingReaders;
    private static int waitingWriters;

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition readCondition = lock.newCondition();
    private static final Condition writeCondition = lock.newCondition();

    private ReadWriteLock(){}

    static void lockRead() {
        lock.lock();
        try {
            for (;;){
                if(writers == 0 && waitingWriters == 0){
                    readers++;
                    break;
                }
                waitingReaders++;
                readCondition.await();
                waitingReaders--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static void lockWrite() {
        lock.lock();
        try {
            for (;;){
                if(readers == 0 && writers == 0){
                    writers++;
                    break;
                }
                waitingWriters++;
                writeCondition.await();
                waitingWriters--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static void unlockRead(){
        lock.lock();
        try {
            if(--readers == 0){
                if(waitingWriters > 0){
                    writeCondition.signalAll();
                } else {
                    readCondition.signalAll();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    static void unlockWrite(){
        lock.lock();
        try {
            if(--writers == 0){
                if(waitingWriters > 0){
                    writeCondition.signalAll();
                } else {
                    readCondition.signalAll();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
