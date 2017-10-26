package CSC375HW2;

import java.util.Random;

/**
 * Reader
 */

public class Passenger implements Runnable {
    private int passengerNumber;
    private String flight;
    private HashTable hashTable;
    private Random random;

    public Passenger(int passengerNumber, String flight, HashTable hashTable){
        this.passengerNumber = passengerNumber;
        this.flight = flight;
        this.hashTable = hashTable;
        random = new Random();
    }

    public void readFlightDetails(){
        FlightDetails f = hashTable.get(flight);
        System.out.printf("%-20s %-20s %-20s\n", "PASSENGER: " + passengerNumber, f.getFlightIdentification(), f.getFlightStatus());
    }

    @Override
    public void run() {
        for(;;){
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readFlightDetails();
        }
    }
}
