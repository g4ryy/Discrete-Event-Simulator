package cs2030.simulator;

/**
 * Represents a leave event.
 */
public class LeaveEvent extends Event {
    /**
     * Creates a new LeaveEvent object.
     * 
     * @param customer The customer associated with the leave event.
     * @param time The time at which the leave event will occur.
     */
    public LeaveEvent(Customer customer, double time) {
        super(customer, time, true, Type.LEAVE);
    }

    /**
     * Throws an IllegalStateException since this event is a last event.
     * 
     * @throws IllegalStateException if this method is called since leave event is a last event.
     */
    @Override 
    public Event nextEvent() throws IllegalStateException {
        throw new IllegalStateException();
    }

    /**
     * Overridden toString.
     * 
     * @return description of the leave event.
     */
    @Override
    public String toString() {
        return String.format("%.3f %s leaves", getTime(), getCustomer());
    }
}
