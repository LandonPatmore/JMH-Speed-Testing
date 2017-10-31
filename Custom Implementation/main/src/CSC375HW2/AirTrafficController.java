package CSC375HW2;

import java.util.Random;

/**
 * Class that simulates an Air Traffic Controller at an airport that changes flights.  Each controller picks a random flight and then changes its status randomly.
 */
class AirTrafficController implements Runnable {
    private int controllerNumber;
    private HashTable hashTable;
    private String[] flights;
    private Random random;

    /**
     * @param controllerNumber the number of the controller
     * @param hashTable        the hashtable which will be written to
     * @param flights          array of flights that can be used to choose from
     */
    AirTrafficController(int controllerNumber, HashTable hashTable, String[] flights) {
        this.controllerNumber = controllerNumber;
        this.hashTable = hashTable;
        this.flights = flights;
        random = new Random();
    }

    /**
     * Picks a random flight and a random status.  Then calls changeFlightDetails() inside the HashTable class that will subsequently update the selected flight.
     */
    private void updateFlight() {
        String randomFlight = flights[random.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];

        hashTable.changeFlightDetails(randomFlight, status, controllerNumber);
    }

    /**
     * Runner method.  Inside, it chooses a random time between 0 and 10 seconds to then update a random flight.
     */
    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                System.out.println("Controller Interrupted. Shutting down.");
                return;
            }
            updateFlight();
        }
    }
}
