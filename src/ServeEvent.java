package cs2030.simulator;

import java.util.Queue;

/**
 * Represents a serve event.
 */
public class ServeEvent extends Event {
    /**
     * Creates a new ServeEvent object.
     * 
     * @param customer The customer associated with the serve event.
     * @param time The time at which the serve event will occur.
     * @param server The server associated with the serve event.
     */
    public ServeEvent(Customer customer, double time, int server) {
        super(customer, time, server, false, Type.SERVE);
    }

    /**
     * Triggers the done event.
     * 
     * @return DoneEvent.
     */
    @Override 
    public Event nextEvent() {
        return new DoneEvent(getCustomer(),  
                 getTime() + getCustomer().getServiceTime(), getServer());
    }

    /**
     * Reschedules this serve event to a later timing.
     * 
     * @return serve event with new timing.
     */
    public ServeEvent reschedule() {
        return new ServeEvent(getCustomer(), 
                Server.getImmediateAvailTime(getServer()), getServer());
    }

    /**
     * Overloaded reschedule() method. This overloaded method updates the service time of 
     * of the customer associated with this serve event.
     * 
     * @param serviceTimes A queue of service times
     * @return serve event with the updated customer service time.
     */
    public ServeEvent reschedule(Queue<Double> serviceTimes) {
        Customer updatedCust = getCustomer().update(serviceTimes.poll());
        return new ServeEvent(updatedCust, getTime(), getServer());
    }

    /**
     * Overridden toString.
     * 
     * @return description of the serve event.
     */
    @Override
    public String toString() {
        return String.format("%.3f %s serves by server %d", 
                  getTime(), getCustomer(), getServer());
    }
}
