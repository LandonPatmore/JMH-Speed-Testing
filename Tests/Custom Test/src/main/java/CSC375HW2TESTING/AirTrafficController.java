package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class AirTrafficController implements Runnable {
    private HashTable hashTable;
    private String[] flights;
    private Random r = new Random();

    void initBenchMark(HashTable hashTable, String[] flights) {
        this.hashTable = hashTable;
        this.flights = flights;
    }

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
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void updateFlight() {
        String randomFlight = flights[r.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];

        hashTable.changeFlightDetails(randomFlight, status);
    }

    @Override
    public void run() {
        if (Thread.interrupted()) {
            return;
        }

        updateFlight();
    }
}
