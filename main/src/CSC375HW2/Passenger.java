package CSC375HW2;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Reader
 */

public class Passenger implements Runnable {
    private int passengerNumber;
    private String flight;
    private HashTable hashTable;
    private Random random;

    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;

    Passenger(int passengerNumber, String flight, HashTable hashTable){
        this.passengerNumber = passengerNumber;
        this.flight = flight;
        this.hashTable = hashTable;
        random = new Random();
    }

    Passenger(int passengerNumber, String flight, ConcurrentHashMap<String, FlightDetails> concurrentHashMap){
        this.passengerNumber = passengerNumber;
        this.flight = flight;
        this.concurrentHashMap = concurrentHashMap;
        random = new Random();
    }

    void readFlightDetailsFromHashMap(){
        FlightDetails f = concurrentHashMap.get(flight);
        System.out.printf("%-20s %-20s %-20s\n", "PASSENGER: " + passengerNumber, f.getFlightIdentification(), f.getFlightStatus());
    }

    void readFlightDetails(){
        FlightDetails f = hashTable.get(flight);
        System.out.printf("%-20s %-20s %-20s\n", "PASSENGER: " + passengerNumber, f.getFlightIdentification(), f.getFlightStatus());
    }

    @Override
    public void run() {
        if(concurrentHashMap == null) {
            for (; ; ) {
                try {
                    Thread.sleep(random.nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readFlightDetails();
            }
        } else {
            for (; ; ) {
                try {
                    Thread.sleep(random.nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readFlightDetailsFromHashMap();
            }
        }
    }
}
