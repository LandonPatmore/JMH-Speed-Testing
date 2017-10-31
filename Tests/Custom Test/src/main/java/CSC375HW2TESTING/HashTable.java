package CSC375HW2TESTING;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom HashTable Implementation
 */
class HashTable {
    private FlightDetails[] HT;
    private int tableSize = 200;
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * Creates array of Flights
     */
    HashTable() {
        int trueTableSize = nextPrime(tableSize);
        HT = new FlightDetails[nextPrime(trueTableSize)];
    }

    /**
     * @param flightDetails takes a FlightDetail to put into the HashTable
     */
    void put(FlightDetails flightDetails) {
        ReadWriteLock.lockWrite();

        Hashing h = new Hashing(flightDetails.getFlightIdentification());

        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            count.compareAndSet(count.get(), count.get() + 1);
            HT[hash] = flightDetails;
        } else if (!indexEmpty(hash) && !HT[hash].getFlightIdentification().equals(flightDetails.getFlightIdentification())) {
            count.compareAndSet(count.get(), count.get() + 1);
            HT[hash].setNext(flightDetails);
        }

        ReadWriteLock.unlockWrite();
    }

    /**
     * @param flight flight identification
     * @return either FlightDetails object or null
     */
    FlightDetails get(String flight) {
        ReadWriteLock.lockRead();
        Hashing h = new Hashing(flight);
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            ReadWriteLock.unlockRead();
            return null;
        }

        FlightDetails head = HT[hash];

        while (!indexEmpty(hash) && !head.getFlightIdentification().equals(flight)) {
            head = head.getNext();
            if (head == null) {
                ReadWriteLock.unlockRead();
                return null;
            }
        }
        ReadWriteLock.unlockRead();
        return head;
    }

    /**
     * @param flight           flight identification
     * @param flightStatus     new status of the flight
     */
    void changeFlightDetails(String flight, FlightStatus flightStatus) {
        FlightDetails changedFlight = get(flight);
        ReadWriteLock.lockWrite();

        if (changedFlight == null) {
            ReadWriteLock.unlockWrite();
            return;
        }

        if (!changedFlight.setFlightStatus(flightStatus)) {
            ReadWriteLock.unlockWrite();
            return;
        }
        ReadWriteLock.unlockWrite();

    }

    /**
     * @param h takes the hashValue % tableSize
     * @return if the index is actually null or not
     */

    private boolean indexEmpty(int h) { //checks to see if the index is actually empty
        return HT[h] == null;
    }

    /**
     * @param n takes an integer
     * @return if the requested integer is actually a prime or not
     */

    private boolean isPrime(int n) {
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param n takes an integer
     * @return the next possible prime
     */

    private int nextPrime(int n) {
        for (int i = n; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    /**
     * Auto resizes the HashTable when the HashTable is filled 0.75 or larger
     */

    private synchronized void resize() {
        tableSize = 2 * tableSize;
        tableSize = nextPrime(tableSize);
        FlightDetails[] old = HT;

        HT = new FlightDetails[tableSize];
        count.set(0);

        for (FlightDetails oldFlightDetails : old) {
            if (oldFlightDetails != null) {
                FlightDetails flightDetails = oldFlightDetails;
                put(flightDetails);

                while (flightDetails.getNext() != null) {
                    flightDetails = flightDetails.getNext();
                    put(flightDetails);
                }
            }
        }
    }

    /**
     * Custom hashing class to hash the keys for placement in the HashTable
     */

    private class Hashing {
        private String hashableKey;
        private int hashed;
        private int length;

        /**
         * @param hk a key
         */

        private Hashing(String hk) {
            hashableKey = hk;
            length = hashableKey.length();
        }

        /**
         * Jenkins One-At-A-Time Hash Function implementation
         *
         * @return the hash value of the given key
         */
        private int hasher() {
            int i = 0;
            int hash = 0;
            while (i != length) {
                hash += hashableKey.charAt(i++);
                hash += hash << 10;
                hash ^= hash >> 6;
            }
            hash += hash << 3;
            hash ^= hash >> 11;
            hash += hash << 15;
            return getHash(hash);
        }

        /**
         * @param h takes the hash value
         * @return the actual hash value by not allowing any negatives
         */

        private int getHash(int h) {
            hashed = (h & 0x7FFFFFFF);
            return hashed;
        }
    }
}