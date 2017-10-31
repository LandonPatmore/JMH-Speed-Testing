package CSC375HW2JDK;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

class AirTrafficController implements Runnable {
    private int controllerNumber;
    private String[] flights;
    private Random random;

    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;

    AirTrafficController(int controllerNumber, ConcurrentHashMap<String, FlightDetails> concurrentHashMap, String[] flights) {
        this.controllerNumber = controllerNumber;
        this.concurrentHashMap = concurrentHashMap;
        this.flights = flights;
        random = new Random();
    }

    private void updateFlight() {
        String randomFlight = flights[random.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];
        FlightDetails flightToBeUpdated = concurrentHashMap.get(randomFlight);

        concurrentHashMap.replace(randomFlight, flightToBeUpdated);
        System.out.printf("%80s %40s %40s\n", "CONTROLLER: " + controllerNumber, randomFlight, "CHANGED TO: " + status);
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                System.out.println("Controller Interrupted. Shutting down.");
                return;
            }
            updateFlight();
        }
    }
}

