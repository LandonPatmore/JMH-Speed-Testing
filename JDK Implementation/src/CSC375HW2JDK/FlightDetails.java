package CSC375HW2JDK;

import java.util.concurrent.atomic.AtomicInteger;

class FlightDetails {
    private String flightIdentification;
    private FlightStatus flightStatus;
    private AtomicInteger flightHappiness;

    private FlightDetails next;

    FlightDetails(String k, FlightStatus v) {
        this.flightIdentification = k;
        this.flightStatus = v;
        flightHappiness = new AtomicInteger(100);
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

    String getFlightIdentification() {
        return flightIdentification;
    }

    FlightStatus getFlightStatus() {
        return flightStatus;
    }
}
