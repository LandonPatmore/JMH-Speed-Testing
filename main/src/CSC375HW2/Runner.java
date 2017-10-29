package CSC375HW2;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Runner {

    public static void main(String[] args) {
        HashTable h = new HashTable();
        ConcurrentHashMap<String, FlightDetails> concurrentHashMap = new ConcurrentHashMap<>();
        Scanner s = new Scanner(System.in);
        String input;
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

        System.out.println("Select type: 0 for Custom // 1 for JDK");
        input = s.nextLine();
        switch (input) {
            case "0":
                for (String k : flights) {
                    h.put(new FlightDetails(k, FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)]));
                }
                new Thread(new Terminal(flights, h)).start();
                break;
            case "1":
                for (String k : flights) {
                    concurrentHashMap.put(k, new FlightDetails(k, FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)]));
                }
                new Thread(new Terminal(flights, concurrentHashMap)).start();
                break;
            default:
                System.out.println("Exiting.");
        }


    }
}
