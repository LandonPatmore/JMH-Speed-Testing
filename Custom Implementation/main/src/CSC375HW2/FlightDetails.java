package CSC375HW2;

/**
 * Created by landon on 2/22/17.
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom class for storing keys and values in a custome HashTable
 */
public class FlightDetails {
    private String flightIdentification;
    private FlightStatus flightStatus;
    private AtomicInteger flightHappiness;

    private FlightDetails next;

    /**
     *
     * @param k key string
     * @param v String values
     */
    FlightDetails(String k, FlightStatus v) {
        this.flightIdentification = k;
        this.flightStatus = v;
        flightHappiness = new AtomicInteger(100);
    }

    boolean setFlightStatus(FlightStatus flightStatus) {
        if(this.flightStatus.equals(flightStatus)){
            return false;
        }
        this.flightStatus = flightStatus;
        setFlightHappiness(flightStatus);
        return true;
    }

    int getHappiness(){
        return flightHappiness.get();
    }

    void setFlightHappiness(FlightStatus status){
        flightHappiness.compareAndSet(flightHappiness.get(), flightHappiness.get() + happinessAmount(status));
    }

    private int happinessAmount(FlightStatus flightStatus){
        switch (flightStatus){
            case ON_TIME:
                return 1;
            case DELAYED:
                return -1;
            case CANCELLED:
                return -5;
            case NO_DATA_CURRENTLY:
                return -3;
        }
        return 0;
    }

    /**
     *
     * @return gets the key
     */

    String getFlightIdentification() {
        return flightIdentification;
    }

    /**
     *
     * @return gets the value
     */

    FlightStatus getFlightStatus() {
        return flightStatus;
    }

    /**
     *
     * @return gets the next KeyVal for the CLinkedList
     */

    FlightDetails getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next KeyVal for the CLinkedList
     */

    void setNext(FlightDetails next) {
        this.next = next;
    }
}