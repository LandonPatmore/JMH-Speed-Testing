package org.sample;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class Passenger implements Runnable {
    private String flight;
    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;

    void initBenchMark(String flight, ConcurrentHashMap<String ,FlightDetails> concurrentHashMap){
        this.flight = flight;
        this.concurrentHashMap = concurrentHashMap;
    }

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
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void readFlightDetails() {
        concurrentHashMap.get(flight);
    }

    @Override
    public void run() {
        if (Thread.interrupted()) {
            return;
        }

        readFlightDetails();
    }
}
