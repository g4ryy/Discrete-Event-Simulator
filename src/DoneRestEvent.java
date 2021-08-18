package cs2030.simulator;

/**
 * Represents a done rest event.
 * <p>The customer associated with a done rest event is an invalid customer.</p>
 */
public class DoneRestEvent extends Event {
    /**
     * Creates a new DoneRestEvent object.
     * 
     * @param time The time at which the done rest event will occur.
     * @param server The server associated with the done rest event.
     */
    public DoneRestEvent(double time, int server) {
        super(new Customer(-1, -1, -1), time, server, true, Type.DONEREST);
    }

    /**
     * Throws an IllegalStateException since this event is a last event.
     * 
     * @throws IllegalStateException if this method is called since done rest event is a last event.
     */
    @Override 
    public Event nextEvent() {
        throw new IllegalStateException();
    }

    /**
     * Overridden toString.
     * 
     * @return description of the done rest event.
     */
    @Override 
    public String toString() {
        return String.format("Server %d Done rest at %.3f", getServer(), getTime());
    }
}
