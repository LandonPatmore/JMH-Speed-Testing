package CSC375HW2JDK;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Terminal implements Runnable {
    private String[] flights;
    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;
    private Random random;
    private ExecutorService pool;

    Terminal(String[] flights, ConcurrentHashMap<String, FlightDetails> concurrentHashMap) {
        this.flights = flights;
        this.concurrentHashMap = concurrentHashMap;
        random = new Random();
        pool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        for (int i = 0; i < flights.length; i++) {
            pool.execute(new Passenger(i, flights[random.nextInt(flights.length)], concurrentHashMap));
        }

        for (int i = 0; i < 10; i++) {
            pool.execute(new AirTrafficController(i, concurrentHashMap, flights));
        }

        for (; ; ) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int totalHappiness = 0;

            for (String k : concurrentHashMap.keySet()) {
                totalHappiness += concurrentHashMap.get(k).getHappiness();
            }

            System.out.println("\nCURRENT TERMINAL HAPPINESS: " + totalHappiness + "\n");
        }
    }
}
