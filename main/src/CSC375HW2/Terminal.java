package CSC375HW2;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The passengers are reading here and the controllers send information here
 */

public class Terminal implements Runnable {
    private String[] flights;
    private HashTable h;
    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;
    private Random random;

    Terminal(String[] flights, HashTable h){
        this.flights = flights;
        this.h = h;
        random = new Random();
    }

    Terminal(String[] flights, ConcurrentHashMap<String, FlightDetails> concurrentHashMap){
        this.flights = flights;
        this.concurrentHashMap = concurrentHashMap;
        random = new Random();
    }

    @Override
    public void run() {
        if(concurrentHashMap == null) {
            for (int i = 0; i < flights.length; i++) {
                new Thread(new Passenger(i, flights[random.nextInt(flights.length)], h)).start();
            }

            for (int i = 0; i < 10; i++) {
                new Thread(new AirTrafficController(i, h, flights)).start();
            }

            for (; ; ) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\nCURRENT TERMINAL HAPPINESS: " + h.getTotalHappinessOfTerminal() + "\n");
            }
        } else {
            for (int i = 0; i < flights.length; i++) {
                new Thread(new Passenger(i, flights[random.nextInt(flights.length)], concurrentHashMap)).start();
            }

            for (int i = 0; i < 10; i++) {
                new Thread(new AirTrafficController(i, concurrentHashMap, flights)).start();
            }

            for (; ; ) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int totalHappiness = 0;

                for(String k : concurrentHashMap.keySet()){
                    totalHappiness += concurrentHashMap.get(k).getHappiness();
                }

                System.out.println("\nCURRENT TERMINAL HAPPINESS: " + totalHappiness + "\n");
            }
        }
    }
}
