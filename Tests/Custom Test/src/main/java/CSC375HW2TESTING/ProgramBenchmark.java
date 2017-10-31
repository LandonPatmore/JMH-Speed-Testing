package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProgramBenchmark {

    @Benchmark @OutputTimeUnit(TimeUnit.SECONDS)
    public void testMethod() {
        HashTable h = new HashTable();
        Random r = new Random();

        String[] flights = createFlights();

        for (String k : flights) {
            h.put(new FlightDetails(k, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }

        for (int i = 0; i < 100; i++) {
            Passenger passenger = new Passenger();
            passenger.initBenchMark(flights[r.nextInt(flights.length)], h);
            new Thread(passenger).start();
        }

        for (int i = 0; i < 20; i++) {
            AirTrafficController airTrafficController = new AirTrafficController();
            airTrafficController.initBenchMark(h, flights);
            new Thread(airTrafficController).start();
        }
    }


    String[] createFlights() {
        Random r = new Random();
        String[] generatedFlights = new String[50];
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

        for (int i = 0; i < generatedFlights.length; i++) {
            char firstLetter = alphabet[r.nextInt(alphabet.length)];
            char secondLetter = alphabet[r.nextInt(alphabet.length)];
            int flightNumber = r.nextInt(10000);
            String flight = firstLetter + "" + secondLetter + " " + flightNumber;
            generatedFlights[i] = flight;
        }

        return generatedFlights;
    }

}
