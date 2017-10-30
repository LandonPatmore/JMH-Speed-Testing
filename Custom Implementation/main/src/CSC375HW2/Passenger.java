package CSC375HW2;

import java.util.Random;

/**
 * A class that simulates a Passenger inside a Terminal at an airport.  The passenger reads from a "status board" and finds their flight they are scheduled to take.
 */

public class Passenger implements Runnable {
    private int passengerNumber;
    private String flight;
    private HashTable hashTable;
    private Random random;

    /**
     * @param passengerNumber the number of the passenger
     * @param flight          flight identification
     * @param hashTable       the hashtable which will be read from
     */
    Passenger(int passengerNumber, String flight, HashTable hashTable) {
        this.passengerNumber = passengerNumber;
        this.flight = flight;
        this.hashTable = hashTable;
        random = new Random();
    }

    /**
     * Reads the flight details of the passenger's flight the are scheduled to take.  It then prints out the current status of the flight that the passenger has read from the "status board" in the terminal.
     */
    private void readFlightDetails() {
        FlightDetails f = hashTable.get(flight);
        System.out.printf("%-20s %-20s %-20s\n", "PASSENGER: " + passengerNumber, f.getFlightIdentification(), f.getFlightStatus());
    }

    /**
     * Runner method.  Inside, it chooses a random time between 0 and 10 seconds to then read their flight information.
     */
    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readFlightDetails();
        }
    }
}
