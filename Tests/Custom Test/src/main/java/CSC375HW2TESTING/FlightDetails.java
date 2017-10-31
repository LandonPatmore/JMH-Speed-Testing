package CSC375HW2TESTING;

/**
 * Class that simulates a Flight and its details.
 */
class FlightDetails {
    private String flightIdentification;
    private FlightStatus flightStatus;
    private FlightDetails next;

    /**
     *
     * @param flightIdentification identification
     * @param flightStatus status of the flight
     */
    FlightDetails(String flightIdentification, FlightStatus flightStatus) {
        this.flightIdentification = flightIdentification;
        this.flightStatus = flightStatus;
    }

    /**
     *
     * @param flightStatus updated flight status
     * @return true if the flight status can be updated or false because the flight already has that status currently
     */
    boolean setFlightStatus(FlightStatus flightStatus) {
        if(this.flightStatus.equals(flightStatus)){
            return false;
        }
        this.flightStatus = flightStatus;
        return true;
    }

    /**
     *
     * @return flight identification
     */
    String getFlightIdentification() {
        return flightIdentification;
    }

    /**
     *
     * @return flight status
     */
    FlightStatus getFlightStatus() {
        return flightStatus;
    }

    /**
     *
     * @return next flight hashed to the same index in the linked list
     */
    FlightDetails getNext() {
        return next;
    }

    /**
     *
     * @param next flight to be added to the linked list
     */
    void setNext(FlightDetails next) {
        this.next = next;
    }
}