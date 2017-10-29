package CSC375HW2;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Writer and Reader (Possibly)
 */

public class AirTrafficController implements Runnable {
    private int controllerNumber;
    private HashTable hashTable;
    private String[] flights;
    private Random random;

    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;

    AirTrafficController(int controllerNumber, HashTable hashTable, String[] flights){
        this.controllerNumber = controllerNumber;
        this.hashTable = hashTable;
        this.flights = flights;
        random = new Random();
    }

    AirTrafficController(int controllerNumber, ConcurrentHashMap<String, FlightDetails> concurrentHashMap, String[] flights){
        this.controllerNumber = controllerNumber;
        this.concurrentHashMap = concurrentHashMap;
        this.flights = flights;
        random = new Random();
    }

    void updateFlightHashMap(){
        String randomFlight = flights[random.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];
        FlightDetails flightToBeUpdated = concurrentHashMap.get(randomFlight);
        flightToBeUpdated.setFlightHappiness(status);

        concurrentHashMap.replace(randomFlight, flightToBeUpdated);
        System.out.printf("%80s %40s %40s\n", "CONTROLLER: " + controllerNumber, randomFlight, "CHANGED TO: " + status);
    }

    void updateFlight(){
        String randomFlight = flights[random.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];

        hashTable.changeFlightDetails(randomFlight, status, controllerNumber);
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
                updateFlight();
            }
        } else {
            for (; ; ) {
                try {
                    Thread.sleep(random.nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateFlightHashMap();
            }
        }
    }
}
