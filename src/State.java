package cs2030.simulator;

/**
 * Represents the state of the system.
 */
public class State {
    private final int numServed; // current numbers of customers Served
    private final int numLeft; // current number of customers who left without being served
    private final double waitTime;  // current total waiting time

    private State(int numServed, int numLeft, double waitTime) {
        this.numServed = numServed;
        this.numLeft = numLeft;
        this.waitTime = waitTime;
    }

    /**
     * Creates a State object.
     */
    public State() {
        this(0,0,0);
    }

    /**
     * Returns the next state depending on the event that had just occured.
     * 
     * @param event The event that had just occured.
     * @return the updated state.
     */
    public State nextState(Event event) {
        int server = event.getServer();

        double eventTime = event.getTime();

        Customer customer = event.getCustomer();

        int custId = customer.getId();

        double serviceTime = customer.getServiceTime();

        Type eventType = event.getType();

        if (eventType == Type.SERVE) {
            Server.serveAt(server, new Pair(custId, serviceTime), eventTime);

            double updatedWaitTime = waitTime + (eventTime - customer.arrTime());

            return new State(numServed + 1, numLeft, updatedWaitTime);
        } else if (eventType == Type.WAIT) {
            Server.fillQueueAt(server, new Pair(custId, serviceTime));

            return new State(numServed, numLeft, waitTime);
        } else if (eventType == Type.DONE) {
            Server.doneServeAt(server);
        } else if (eventType == Type.LEAVE) {
            return new State(numServed, numLeft + 1, waitTime);
        } else if (eventType == Type.REST) {
            Server.restAt(server, eventTime, serviceTime);
            
            return new State(numServed, numLeft, waitTime);
        } else if (eventType == Type.DONEREST) {
            Server.doneServeAt(server);
        }

        return this;
    }

    /**
     * Overriden toString.
     * 
     * @return description of the current state.
     */
    @Override
    public String toString() {
        double averageWait;

        if (numServed == 0) {
            averageWait = waitTime / 1;
        } else {
            averageWait = waitTime / numServed;
        }

        return String.format("[%.3f %d %d]", averageWait, numServed, numLeft);
    }
}


