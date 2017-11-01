package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class AirTrafficController {
    private String[] flights;
    private Random r;
    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;

    @Setup
    public void init() {
        ProgramBenchmark m = new ProgramBenchmark();
        r = new Random();

        flights = m.createFlights();
        concurrentHashMap = new ConcurrentHashMap<>();

        for (String k : flights) {
            concurrentHashMap.put(k, new FlightDetails(k, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }
    }

    @Benchmark
    @GroupThreads(20)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void updateFlight() {
        String randomFlight = flights[r.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];
        FlightDetails flightToBeUpdated = new FlightDetails(randomFlight, status);

        concurrentHashMap.put(randomFlight, flightToBeUpdated);
    }
}

