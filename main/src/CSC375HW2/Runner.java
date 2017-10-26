package CSC375HW2;

import java.util.Random;

public class Runner {

    public static void main(String[] args) {
        HashTable h = new HashTable();
        Random r = new Random();

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        String[] flights = new String[50];

        for (int i = 0; i < flights.length; i++) {
            char firstLetter = alphabet[r.nextInt(alphabet.length)];
            char secondLetter = alphabet[r.nextInt(alphabet.length)];
            int flightNumber = r.nextInt(10000);
            String flight = firstLetter + "" + secondLetter + " " + flightNumber;
            flights[i] = flight;
        }

        for (String k : flights) {
            h.put(new FlightDetails(k, FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)]));
        }

        new Thread(new Terminal(flights, h)).start();

    }
}
