package CSC375HW2;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A class that simulates an airport terminal.  Runs AirTrafficControllers as well as Passengers.  Every 20 seconds, the terminal will read out the current total happiness of all flights.
 */

class Terminal implements Runnable {
    private String[] flights;
    private HashTable h;
    private Random random;
    private Executor pool;

    /**
     * @param flights array of flights that are passed to AirTrafficControllers as well as random flights assigned to Passengers
     * @param h       hashtable to be passed to AirTrafficControllers as well as Passengers
     */
    Terminal(String[] flights, HashTable h) {
        this.flights = flights;
        this.h = h;
        random = new Random();
        pool = Executors.newCachedThreadPool();
    }

    /**
     * Runner method.  Creates AirTrafficControllers and passengers as threads to simulate readers and writers.
     */
    @Override
    public void run() {
        for (int i = 0; i < flights.length; i++) {
            pool.execute(new Passenger(i, flights[random.nextInt(flights.length)], h));
        }

        for (int i = 0; i < 10; i++) {
            pool.execute(new AirTrafficController(i, h, flights));
        }

        for (; ; ) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\nCURRENT TERMINAL HAPPINESS: " + h.getTotalHappinessOfTerminal() + "\n");
        }
    }
}
