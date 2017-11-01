package CSC375HW2TESTING;

class FlightDetails {
    private String flightIdentification;
    private FlightStatus flightStatus;

    FlightDetails(String k, FlightStatus v) {
        this.flightIdentification = k;
        this.flightStatus = v;
    }
}
