package CSC375HW2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that is a custom implementation of a Read Write Lock data structure.  It is a writer preference.
 */
final class ReadWriteLock {
    private static int readers;
    private static int writers;
    private static int waitingReaders;
    private static int waitingWriters;

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition readCondition = lock.newCondition();
    private static final Condition writeCondition = lock.newCondition();

    private ReadWriteLock() {
    }

    /**
     * Blocks a reader if there is a writer or waiting writer, otherwise will allow the reader to read.
     */
    static void lockRead() {
        lock.lock();
        try {
            for (; ; ) {
                if (writers == 0 && waitingWriters == 0) {
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

    /**
     * Blocks a writer if there is a writer or readers, otherwise will allow the writer to write.
     */
    static void lockWrite() {
        lock.lock();
        try {
            for (; ; ) {
                if (readers == 0 && writers == 0) {
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

    /**
     * Once readers is equal to 0, will signal waiting writers, otherwise will signal waiting readers.
     */
    static void unlockRead() {
        lock.lock();
        try {
            if (--readers == 0) {
                if (waitingWriters > 0) {
                    writeCondition.signalAll();
                } else {
                    readCondition.signalAll();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Once writers is equal to 0, will signal waiting writers, otherwise will signal waiting readers.
     */
    static void unlockWrite() {
        lock.lock();
        try {
            if (--writers == 0) {
                if (waitingWriters > 0) {
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
