package CSC375HW2;

import java.util.Random;

/**
 * The passengers are reading here and the controllers send information here
 */

public class Terminal implements Runnable {
    private String[] flights;
    private HashTable h;
    private Random random;

    public Terminal(String[] flights, HashTable h){
        this.flights = flights;
        this.h = h;
        random = new Random();
    }

    @Override
    public void run() {
        for(int i = 0; i < flights.length; i++){
            new Thread(new Passenger(i, flights[random.nextInt(flights.length)], h)).start();
        }

        for(int i = 0; i < 10; i ++){
            new Thread(new AirTrafficController(i, h, flights)).start();
        }

        for(;;){
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\nCURRENT TERMINAL HAPPINESS: " + h.getTotalHappinessOfTerminal() + "\n");
        }
    }
}
