package cs2030.simulator;

/**
 * Represents a done event.
 */
public class DoneEvent extends Event {
    /**
     * Creates a new DoneEvent object.
     * 
     * @param customer The customer associated with the done event.
     * @param time The time at which the done event will occur.
     * @param server The server associated with the done event.
     */
    public DoneEvent(Customer customer, double time, int server) {
        super(customer, time, server, true, Type.DONE);
    }

    /**
     * Throws an IllegalStateException since this event is a last event.
     * 
     * @throws IllegalStateException if this method is called since done event is a last event.
     */
    @Override 
    public Event nextEvent() throws IllegalStateException {
        throw new IllegalStateException();
    }

    /**
     * Overloaded nextEvent() method. This overloaded method takes 
       in a resting duration and triggers the rest event.
     * 
     * @param restDuration The resting duration.
     * @return RestEvent.
     */
    public Event nextEvent(double restDuration) {
        return new RestEvent(getTime(), getServer(), restDuration);
    }

    /**
     * Overridden toString.
     * 
     * @return description of the done event.
     */
    @Override
    public String toString() {
        return String.format("%.3f %s done serving by server %d",
                getTime(), getCustomer(), getServer());
    }
}
