package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class AirTrafficController {
    private HashTable hashTable;
    private String[] flights;
    private Random r = new Random();

    @Setup
    public void init() {
        ProgramBenchmark m = new ProgramBenchmark();

        flights = m.createFlights();
        hashTable = new HashTable();

        for (String f : flights) {
            hashTable.put(new FlightDetails(f, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }
    }

    @Benchmark
    @GroupThreads(20)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void updateFlight() {
        String randomFlight = flights[r.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];

        hashTable.changeFlightDetails(randomFlight, status);
    }
}
