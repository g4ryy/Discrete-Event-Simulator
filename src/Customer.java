package cs2030.simulator;


/**
 * Represents a customer.
 */
public class Customer {
    private final int id;
    private final double serviceTime;
    private final double arrivalTime;

    /**
     * Creates a new Customer object.
     * 
     * @param id Customer's ID.
     * @param serviceTime Customer's service time.
     * @param arrivalTime Customer's time of arrival.
     */
    public Customer(int id, double serviceTime, double arrivalTime) {
        this.id = id;
        this.serviceTime = serviceTime;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Get customer ID.
     * 
     * @return customer ID. 
     */
    public int getId() {
        return id;
    }

    /**
     * Get customer's service time.
     * 
     * @return customer's service time.
     */

    public double getServiceTime() {
        return serviceTime;
    }

    /**
     * Get customer's arrival time.
     * 
     * @return customer's arrival time.
     */
    public double arrTime() {
        return arrivalTime;
    }

    /**
     * Finds a server that is idle.
     * 
     * @return ID of server that is idle.
     */
    public int findIdleServer() {
        return Server.getIdleServer();
    }

    /**
     * Finds a server that can be queued.
     * 
     * @return ID of the server.
     */
    public int findQueue() {
        return Server.getAvailableServer(false);
    }

    /**
     * Updates the customer's service time.
     * 
     * @param serviceTime The new service time.
     * @return customer with the updated service time.
     */
    public Customer update(double serviceTime) {
        return new Customer(id, serviceTime, arrivalTime);
    }

    /**
     * Overridden toString.
     * 
     * @return description of the customer. 
     */
    @Override
    public String toString() {
        return Integer.toString(id);
    }

}


