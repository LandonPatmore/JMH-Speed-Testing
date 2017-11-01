package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Passenger {
    private String flight;
    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;

    @Setup
    public void init() {
        ProgramBenchmark m = new ProgramBenchmark();
        Random r = new Random();

        String[] flights = m.createFlights();
        flight = flights[r.nextInt(flights.length)];
        concurrentHashMap = new ConcurrentHashMap<>();

        for (String k : flights) {
            concurrentHashMap.put(k, new FlightDetails(k, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }
    }

    @Benchmark
    @GroupThreads(100)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void readFlightDetails() {
        concurrentHashMap.get(flight);
    }
}
