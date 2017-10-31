package CSC375HW2JDK;

class FlightDetails {
    private String flightIdentification;
    private FlightStatus flightStatus;

    FlightDetails(String k, FlightStatus v) {
        this.flightIdentification = k;
        this.flightStatus = v;
    }

    String getFlightIdentification() {
        return flightIdentification;
    }

    FlightStatus getFlightStatus() {
        return flightStatus;
    }
}
