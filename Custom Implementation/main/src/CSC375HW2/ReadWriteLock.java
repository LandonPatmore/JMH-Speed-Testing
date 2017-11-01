package CSC375HW2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that is a custom implementation of a Read Write Lock data structure.  It is a writer preference.
 */
@SuppressWarnings("Duplicates")
class ReadWriteLock {
    private int readers;
    private int writers;
    private int waitingWriters;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition readCondition = lock.newCondition();
    private final Condition writeCondition = lock.newCondition();

    /**
     * Blocks a reader if there is a writer or waiting writer, otherwise will allow the reader to read.
     */
    void lockRead() {
        lock.lock();
        try {
            for (; ; ) {
                if (Thread.interrupted()) {
                    System.out.println("Waiting reader shutdown.");
                    return;
                }
                if (writers == 0 && waitingWriters == 0) {
                    readers++;
                    break;
                }
                readCondition.await();
            }
        } catch (InterruptedException e) {
            System.out.println("Waiting reader shutdown.");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Blocks a writer if there is a writer or readers, otherwise will allow the writer to write.
     */
    void lockWrite() {
        lock.lock();
        try {
            for (; ; ) {
                if (Thread.interrupted()) {
                    System.out.println("Waiting writer shutdown.");
                    return;
                }
                if (readers == 0 && writers == 0) {
                    writers++;
                    break;
                }
                waitingWriters++;
                writeCondition.await();
                waitingWriters--;
            }
        } catch (InterruptedException e) {
            System.out.println("Waiting writer shutdown.");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Once readers is equal to 0, will signal waiting writers, otherwise will signal waiting readers.
     */
    void unlockRead() {
        lock.lock();
        try {
            if (--readers == 0) {
                if (waitingWriters > 0) {
                    writeCondition.signal();
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
    void unlockWrite() {
        lock.lock();
        try {
            if (--writers == 0) {
                if (waitingWriters > 0) {
                    writeCondition.signal();
                } else {
                    readCondition.signalAll();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
