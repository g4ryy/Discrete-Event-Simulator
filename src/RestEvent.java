package cs2030.simulator;

/**
 * Represents a rest event.
 * <p>The customer associated with a rest event is an invalid customer
   that stores the rest duration as its service time.</p>
 */
public class RestEvent extends Event {
    private static final int INVALID_VALUE = -999999;
    
    /**
     * Creates a new RestEvent object.
     * 
     * @param time The time at which the rest event will occur.
     * @param server The server associated with the rest event.
     * @param restDuration The resting duration.
     */
    public RestEvent(double time, int server, double restDuration) {
        super(new Customer(INVALID_VALUE, restDuration, INVALID_VALUE), time, 
        server, false, Type.REST);
    }
    
    /**
     * Triggers the done rest event.
     * 
     * @return DoneRestEvent.
     */
    @Override 
    public Event nextEvent() {
        return new DoneRestEvent(getTime() + getCustomer().getServiceTime(), getServer());
    }

    /**
     * Overridden toString.
     * 
     * @return description of the rest event.
     */
    @Override
    public String toString() {
        return String.format("Server %d is currently resting for %.3f", 
                getServer(), getCustomer().getServiceTime());
    }
}
