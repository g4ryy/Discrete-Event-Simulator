package cs2030.simulator;

/**
 * Represents a greedy customer.
 */
public class GreedyCustomer extends Customer {
    /**
     * Creates a new GreedyCustomer object.
     * 
     * @param id Customer's ID.
     * @param serviceTime Customer's service time.
     * @param arrivalTime Customer's arrival time.
     */
    public GreedyCustomer(int id, double serviceTime, double arrivalTime) {
        super(id, serviceTime, arrivalTime);
    }

    /**
     * Updates the customer's service time.
     * 
     * @param serviceTime The new service time.
     * @return customer with the updated service time.
     */
    @Override
    public GreedyCustomer update(double serviceTime) {
        return new GreedyCustomer(getId(), serviceTime, arrTime());
    }

    /**
     * Finds a server that can be queued and has the shortest queue length.
     * 
     * @return ID of the server.
     */
    @Override
    public int findQueue() {
        return Server.getAvailableServer(true);
    }

    /**
     * Overrides toString.
     * 
     * @return description of the greedy customer. 
     */
    @Override
    public String toString() {
        return String.format("%d(greedy)", getId());
    }
}


