package org.sample;

class FlightDetails {
    private String flightIdentification;
    private FlightStatus flightStatus;

    FlightDetails(String k, FlightStatus v) {
        this.flightIdentification = k;
        this.flightStatus = v;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    String getFlightIdentification() {
        return flightIdentification;
    }

    FlightStatus getFlightStatus() {
        return flightStatus;
    }
}
