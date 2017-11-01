package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ProgramBenchmark {
    private HashTable h;
    private Random r;
    private String[] flights;

    @Setup
    public void init(){
        h = new HashTable();
        r = new Random();
        flights = createFlights();
    }

    @Benchmark
    @GroupThreads(20)
    @Group("ReadWrite")s
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void write(){
        String randomFlight = flights[r.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];

        h.changeFlightDetails(randomFlight, status);
    }

    @Benchmark
    @GroupThreads(100)
    @Group("ReadWrite")
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void read(){
        String flight = flights[r.nextInt(flights.length)];
        h.get(flight);
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
