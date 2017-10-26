package CSC375HW2;

import java.util.Random;

/**
 * Writer and Reader (Possibly)
 */

public class AirTrafficController implements Runnable {
    private int controllerNumber;
    private HashTable hashTable;
    private String[] flights;
    private Random random;

    public AirTrafficController(int controllerNumber, HashTable hashTable, String[] flights){
        this.controllerNumber = controllerNumber;
        this.hashTable = hashTable;
        this.flights = flights;
        random = new Random();
    }

    public void updateFlight(){
        String randomFlight = flights[random.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];
        hashTable.changeFlightDetails(randomFlight, status, controllerNumber);
    }

    @Override
    public void run() {
        for(;;){
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateFlight();
        }
    }
}
