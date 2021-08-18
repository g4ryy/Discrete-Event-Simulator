package cs2030.simulator;

import java.util.Queue;

/**
 * Represents an arrival event.
 */
public class ArrivalEvent extends Event {
    /**
     * Creates a new ArrivalEvent object.
     * 
     * @param customer The customer associated with the arrival event.
     * @param time The time at which the arrival event will occur.
     */
    public ArrivalEvent(Customer customer, double time) {
        super(customer, time, false, Type.ARRIVAL);
    }

    /**
     * Triggers the next event following the arrival event.
       <p>If the customer can find an idle server, returns a ServeEvent.</p>
       <p>If there is no idle server and the customer can find
       a server that can be queued, returns a WaitEvent.</p>
       <p>If there is no idle server and there is no server 
       available for queueing, returns a LeaveEvent.</p>
     * 
     * @return ServeEvent if no idle server, WaitEvent if no idle server 
           but a server is available for queueing, otherwise a LeaveEvent.
     */
    @Override
    public Event nextEvent() {
        Customer customer = getCustomer();
        
        if (customer.findIdleServer() != -1) {
            return new ServeEvent(customer, getTime(), customer.findIdleServer());
        } else if (customer.findQueue() != -1) {
            return new WaitEvent(customer, getTime(), customer.findQueue());
        }

        return new LeaveEvent(customer, getTime());
    }

    /**
     * Overridden toString.
     * 
     * @return description of the arrival event.
     */
    @Override
    public String toString() {
        return String.format("%.3f %s arrives", getTime(), getCustomer());
    }
}
