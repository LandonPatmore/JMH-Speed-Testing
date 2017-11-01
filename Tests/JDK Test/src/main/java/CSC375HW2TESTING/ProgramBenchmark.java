/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package CSC375HW2TESTING;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@State(Scope.Benchmark)
public class ProgramBenchmark {
    private ConcurrentHashMap<String, FlightDetails> concurrentHashMap;
    private Random r;
    private String[] flights;

    @Setup
    public void init(){
        concurrentHashMap = new ConcurrentHashMap<>();
        r = new Random();
        flights = createFlights();
    }

    @Benchmark
    @GroupThreads(20)
    @Group("ReadWrite")
    public void write(){
        String randomFlight = flights[r.nextInt(flights.length)];
        FlightStatus status = FlightStatus.values()[new Random().nextInt(FlightStatus.values().length)];
        FlightDetails flightToBeUpdated = concurrentHashMap.get(randomFlight);
        flightToBeUpdated.setFlightStatus(status);

        concurrentHashMap.replace(randomFlight, flightToBeUpdated);
    }

    @Benchmark
    @GroupThreads(100)
    @Group("ReadWrite")
    public void read(){
        String flight = flights[r.nextInt(flights.length)];
        concurrentHashMap.get(flight);
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
