package cs2030.simulator;

/**
 * Represents a wait event.
 */
public class WaitEvent extends Event {
    /**
     * Creates a new WaitEvent object.
     * 
     * @param customer The customer associated with the wait event.
     * @param time The time at which the wait event will occur.
     * @param server The server associated with the wait event.
     */
    public WaitEvent(Customer customer, double time, int server) {
        super(customer, time, server, false, Type.WAIT);
    }

    /**
     * Triggers the serve event.
     * 
     * @return ServeEvent.
     */
    @Override 
    public Event nextEvent() {
        return new ServeEvent(getCustomer(), 
                Server.nextAvailTimeAt(getServer()), getServer());
    }

    /**
     * Overridden toString.
     * 
     * @return description of the wait event.
     */
    @Override
    public String toString() {
        return String.format("%.3f %s waits at server %d", 
                getTime(), getCustomer(), getServer());
    }
}
