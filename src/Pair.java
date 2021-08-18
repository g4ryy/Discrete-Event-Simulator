package cs2030.simulator;

/**
 * Stores the id and service time of a customer.
 */
public class Pair {
    private final int id;
    private final double serviceTime;

    /**
     * Creates a Pair object.
     * 
     * @param id ID of customer.
     * @param serviceTime Service time of customer.
     */
    public Pair(int id, double serviceTime) {
        this.id = id;
        this.serviceTime = serviceTime;
    }

    /**
     * Gets the ID. 
     * @return ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the service time.
     * @return service time.
     */
    public double getServiceTime() {
        return this.serviceTime;
    }
}
