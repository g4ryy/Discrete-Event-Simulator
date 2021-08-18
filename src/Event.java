package cs2030.simulator;

/**
 * Abstract Event class.
 */
public abstract class Event implements Comparable<Event> {
    private final Type type;
    private final Customer customer;
    private final double time;
    private final int server;
    private final boolean last; //true if the event is a last event

    /**
     * Constructor. (For invokation by subclass constructors.)
     * 
     * @param customer The customer associated with the event.
     * @param time The time that the event will occur.
     * @param last Whether the event is a last event.
     * @param type The type of the event.
     */
    Event(Customer customer, double time, boolean last, Type type) {
        this(customer, time, -1, last, type);
    }

    /**
     * Overloaded constructor. (For invokation by subclass constructors.)
     * 
     * @param customer The customer associated with the event.
     * @param time The time that the event will occur.
     * @param server The ID of the server associated with the event.
     * @param last Whether the event is a last event.
     * @param type The type of the event.
     */
    Event(Customer customer, double time, int server, boolean last, Type type) {
        this.type = type;
        this.customer = customer;
        this.time = time;
        this.server = server;
        this.last = last;
    }

    /**
     * Gets the type of the event.
     * 
     * @return the type of the event.
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the customer associated with the event.
     * 
     * @return the customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the time at which the event occured.
     * 
     * @return the time.
     */
    double getTime() {
        return time;
    }

    /**
     * Gets the ID of the server associated with the event.
     * 
     * @return the ID of the server
     */
    public int getServer() {
        return server;
    }

    /**
     * Checks whether the event is a last event.
     * 
     * @return true if the event is a last event. 
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Triggers the next event following this event.
     * 
     * @return the next event.
     */
    public abstract Event nextEvent();

    /**
     * Compares two Event objects.
     * 
     * @param other other event
     * @return the value 0 if this Event is equal to the argument Event; 
          a value less than 0 if this Event occurs first or if the customer associated 
          with this event has a smaller ID than that of the customer associated argument Event; 
          and a value greater than 0 otherwise.
     */
    @Override
    public int compareTo(Event other) {
        if (this.time != other.time) {
            Double thisTime = this.time;
            Double otherTime = other.time;
            return thisTime.compareTo(otherTime);
        } else {
            Integer thisCust = customer.getId();
            Integer otherCust = other.customer.getId();

            return thisCust.compareTo(otherCust);
        }
    }
}
