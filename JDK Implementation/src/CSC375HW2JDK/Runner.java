package CSC375HW2JDK;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Runner {

    public static void main(String[] args) {
        ConcurrentHashMap<String, FlightDetails> concurrentHashMap = new ConcurrentHashMap<>();
        Random r = new Random();

        String[] flights = new String[50];
        createFlights(flights, r);


        for (String k : flights) {
            concurrentHashMap.put(k, new FlightDetails(k, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }
        new Thread(new Terminal(flights, concurrentHashMap)).start();
    }

    private static void createFlights(String[] flights, Random r) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

        for (int i = 0; i < flights.length; i++) {
            char firstLetter = alphabet[r.nextInt(alphabet.length)];
            char secondLetter = alphabet[r.nextInt(alphabet.length)];
            int flightNumber = r.nextInt(10000);
            String flight = firstLetter + "" + secondLetter + " " + flightNumber;
            flights[i] = flight;
        }
    }
}
