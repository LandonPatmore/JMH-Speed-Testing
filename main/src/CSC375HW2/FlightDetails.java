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
    private AtomicInteger amountOfPassengers;

    private FlightDetails next;

    /**
     *
     * @param k key string
     * @param v String values
     */
    public FlightDetails(String k, FlightStatus v) {
        this.flightIdentification = k;
        this.flightStatus = v;
        flightHappiness = new AtomicInteger(100);
        amountOfPassengers = new AtomicInteger(0);
    }

    public void addPassenger(){
        amountOfPassengers.compareAndSet(amountOfPassengers.get(), amountOfPassengers.get() + 1);
    }

    public boolean setFlightStatus(FlightStatus flightStatus) {
        if(this.flightStatus.equals(flightStatus)){
            return false;
        }
        this.flightStatus = flightStatus;
        modifyHappiness(flightStatus);
        return true;
    }

    public int getHappiness(){
        return flightHappiness.get();
    }

    private void modifyHappiness(FlightStatus flightStatus){
        switch (flightStatus){
            case ON_TIME:
                flightHappiness.compareAndSet(flightHappiness.get(), flightHappiness.get() + 1);
                break;
            case DELAYED:
                flightHappiness.compareAndSet(flightHappiness.get(), flightHappiness.get() - 1);
                break;
            case CANCELLED:
                flightHappiness.compareAndSet(flightHappiness.get(), flightHappiness.get() - 5);
                break;
            case NO_DATA_CURRENTLY:
                flightHappiness.compareAndSet(flightHappiness.get(), flightHappiness.get() - 3);
            default:
                break;
        }
    }

    /**
     *
     * @return gets the key
     */

    public String getFlightIdentification() {
        return flightIdentification;
    }

    /**
     *
     * @return gets the value
     */

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    /**
     *
     * @return gets the next KeyVal for the CLinkedList
     */

    public FlightDetails getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next KeyVal for the CLinkedList
     */

    public void setNext(FlightDetails next) {
        this.next = next;
    }

    /**
     *
     * @return custom toString method for GUI
     */

    @Override
    public String toString() {
        return flightIdentification;
    }
}