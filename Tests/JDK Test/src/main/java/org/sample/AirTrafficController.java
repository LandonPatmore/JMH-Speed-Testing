package org.sample;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class AirTrafficController implements Runnable {
    private String[] flights;
    private Random r;
    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;

    void initBenchMark(ConcurrentHashMap<String, FlightDetails> concurrentHashMap, String[] flights) {
        this.concurrentHashMap = concurrentHashMap;
        this.flights = flights;
        r = new Random();
    }

    @Setup
    public void init() {
        ProgramBenchmark m = new ProgramBenchmark();
        r = new Random();

        flights = ProgramBenchmark.createFlights();
        concurrentHashMap = new ConcurrentHashMap<>();

        for (String k : flights) {
            concurrentHashMap.put(k, new FlightDetails(k, FlightStatus.values()[r.nextInt(FlightStatus.values().length)]));
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void updateFlight() {
        String randomFlight = flights[r.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];
        FlightDetails flightToBeUpdated = concurrentHashMap.get(randomFlight);
        flightToBeUpdated.setFlightStatus(status);

        concurrentHashMap.replace(randomFlight, flightToBeUpdated);
    }

    @Override
    public void run() {
        if (Thread.interrupted()) {
            return;
        }
        updateFlight();
    }
}

