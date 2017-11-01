package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Passenger {
    private String flight;
    private HashTable hashTable;

    @Setup
    public void init() {
        ProgramBenchmark m = new ProgramBenchmark();
        Random r = new Random();

        String[] flights = m.createFlights();
        flight = flights[r.nextInt(flights.length)];
        hashTable = new HashTable();

        for (String f : flights) {
            hashTable.put(new FlightDetails(f, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }
    }

    @Benchmark
    @GroupThreads(100)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void readFlightDetails() {
        hashTable.get(flight);
    }

}
