package CSC375HW2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom HashTable Implementation
 */
class HashTable {
    private FlightDetails[] HT;
    private int tableSize = 200;
    private AtomicInteger count;
    private ReadWriteLock lock;

    /**
     * Creates array of Flights
     */
    HashTable() {
        HT = new FlightDetails[nextPrime(tableSize)];
        count = new AtomicInteger(0);
        lock = new ReadWriteLock();
    }

    /**
     * @param flightDetails takes a FlightDetail to put into the HashTable
     */
    void put(FlightDetails flightDetails) {
        lock.lockWrite();

        Hashing h = new Hashing(flightDetails.getFlightIdentification());

        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            count.compareAndSet(count.get(), count.get() + 1);
            HT[hash] = flightDetails;
        } else if (!indexEmpty(hash) && !HT[hash].getFlightIdentification().equals(flightDetails.getFlightIdentification())) {
            count.compareAndSet(count.get(), count.get() + 1);
            HT[hash].setNext(flightDetails);
        }

        lock.unlockWrite();
    }

    /**
     * @param flight flight identification
     * @return either FlightDetails object or null
     */
    FlightDetails get(String flight) {
        lock.lockRead();
        Hashing h = new Hashing(flight);
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            lock.unlockRead();
            return null;
        }

        FlightDetails head = HT[hash];

        while (!indexEmpty(hash) && !head.getFlightIdentification().equals(flight)) {
            head = head.getNext();
            if (head == null) {
                lock.unlockRead();
                return null;
            }
        }
        lock.unlockRead();
        return head;
    }

    /**
     * @param flight           flight identification
     * @param flightStatus     new status of the flight
     * @param controllerNumber the controller who is going to change the flight
     */
    void changeFlightDetails(String flight, FlightStatus flightStatus, int controllerNumber) {
        FlightDetails changedFlight = get(flight);
        lock.lockWrite();

        if (changedFlight == null) {
            System.out.println("Flight: " + flight + " could not be changed because it does not exist.");
            lock.unlockWrite();
            return;
        }

        if (!changedFlight.setFlightStatus(flightStatus)) {
            System.out.println("\nFlight: " + flight + " not changed because it is already: " + flightStatus + "\n");
            lock.unlockWrite();
            return;
        }
        System.out.printf("%80s %40s %40s\n", "CONTROLLER: " + controllerNumber, changedFlight.getFlightIdentification(), "CHANGED TO: " + changedFlight.getFlightStatus());
        lock.unlockWrite();

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