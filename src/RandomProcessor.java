package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;

/**
 * Utilizes a RandomGenerator object to output information for the driver.
 */
public class RandomProcessor {
    private final RandomGenerator randGen;

    /**
     * Creates a RandomProcesser object.
     * 
     * @param seed Seed for the random generator.
     * @param lambda The customer arrival rate.
     * @param mu The service rate.
     * @param rho The resting rate.
     */
    public RandomProcessor(int seed, double lambda, double mu, double rho) {
        this.randGen = new RandomGenerator(seed, lambda, mu, rho);
    }

    /**
     * Initialises all the arrival events.
     * 
     * @param numOfCustomers The number of customers.
     * @param greedProbability The probability of a greedy customer occuring.
     * @return queue of arrival events.
     */
    public Queue<ArrivalEvent> initialiseArrival(int numOfCustomers, double greedProbability) {
        double currTime = 0;

        Queue<ArrivalEvent> arriveList = new LinkedList<>();

        for (int i = 1; i <= numOfCustomers; i++) {
            boolean greedy  = randGen.genCustomerType() < greedProbability;

            if (greedy) {
                arriveList.add(new ArrivalEvent(new GreedyCustomer(i, 0, currTime), currTime));
            } else {
                arriveList.add(new ArrivalEvent(new Customer(i, 0, currTime), currTime));
            }

            currTime = currTime + randGen.genInterArrivalTime();
        }

        return arriveList;
    }

    /**
     * Generates all the service times.
     *  
     * @param numOfCustomers The number of customers.
     * @return queue of all the service times.
     */
    public Queue<Double> genServiceTimes(int numOfCustomers) {
        Queue<Double> serviceTimes = new LinkedList<>();

        for (int i = 0; i < numOfCustomers; i++) {
            serviceTimes.add(randGen.genServiceTime());
        }

        return serviceTimes;
    }

    /**
     * Returns true if server should rest, false otherwise.
     * @param restProbability The probability of resting.
     * @return true if server should rest, false otherwise.
     */
    public boolean shouldRest(double restProbability) {
        return randGen.genRandomRest() < restProbability;
    }

    /**
     * Generates a rest duration.
     * 
     * @return rest duration.
     */
    public double genRestDuration() {
        return randGen.genRestPeriod();
    }
}
