package CSC375HW2;

import java.util.Random;

class Runner {

    public static void main(String[] args) {
        HashTable h = new HashTable();
        Random r = new Random();

        String[] flights = new String[50];
        createFlights(flights, r);

        for (String k : flights) {
            h.put(new FlightDetails(k, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }

        for (int i = 0; i < flights.length; i++) {
            new Thread(new Passenger(i, flights[r.nextInt(flights.length)], h)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new AirTrafficController(i, h, flights)).start();
        }
    }

    /**
     * @param flights array to be populated with flights
     * @param r       random number generator
     */
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
