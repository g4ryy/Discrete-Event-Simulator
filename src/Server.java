package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * represents a Server.
 */
public class Server {
    private static final ArrayList<Server> serverList = new ArrayList<>();
    private final int queueLength;
    private final int id;
    private final boolean idle; //true if server is not serving any customer currently, else false
    private final double immediateAvailTime; // time server will finish serving current customer
    private final Queue<Pair> queue;

    private Server(int id, boolean idle, Queue<Pair> queue, 
            double immediateAvailTime, int queueLength) {
        this.id = id;
        this.idle = idle;
        this.queue = queue;
        this.immediateAvailTime = immediateAvailTime;
        this.queueLength = queueLength;
    }

    /**
     * Creates a Server object.
     * 
     * @param id The server ID.
     * @param queueLength The maximum queue length.
     */
    public Server(int id, int queueLength) {
        this(id, true, new LinkedList<Pair>(), 0, queueLength);
    }

    /**
     * Initialises all the servers in the current simulation.
     * 
     * @param numOfServers The number of servers.
     * @param queueLength The maximum queue length.
     */
    public static void initialiseServers(int numOfServers, int queueLength) {
        for (int i = 0; i < numOfServers; i++) {
            Server.serverList.add(new Server(i + 1, queueLength));
        }
    }

    /**
     * Finds an idle server and returns its ID, otherwise an invalid value is returned.
     * 
     * @return ID of idle server, otherwise an invalid number.
     */
    public static int getIdleServer() {
        for (Server server: Server.serverList) {
            if (server.isIdle()) {
                return server.getId();
            }
        }

        return -1; // returns invalid id if there is no idle server
    }

    /**
     * finds a server that is available for queueing either in a greedy manner 
     * or a non-greedy manner depending on the argument, and returns its ID, 
     * otherwise an invalid value is returned.
     * 
     * @param greedy To indicate whether to find the server in a greedy manner.
     * @return ID of server who is available for queueing, otherwise an invalud number.
     */
    public static int getAvailableServer(boolean greedy) {
        int serverAvail = -1;

        for (Server server: Server.serverList) {
            if (server.canQueue()) {
                serverAvail = server.getId();
                break;
            }
        }
      
        if (greedy && serverAvail != -1) {
            serverAvail = Server.serverList.stream()
                    .min((x, y) -> x.queue.size() - y.queue.size()).get().getId();
        }

        return serverAvail;
    }

    /**
     * Gets the time at which the server will finish serving the current customer.
     *  
     * @param server The ID of the server.
     * @return the immediate available time.
     */
    public static double getImmediateAvailTime(int server) {
        return Server.serverList.get(server - 1).immediateAvailTime;
    }

    /**
     * Gets the specified server to serve the customer.
     * 
     * @param server The ID of the server.
     * @param pair The pair containing the id and service time of the customer to be served.
     * @param time The time at which the server begin serving the customer.
     */
    static void serveAt(int server, Pair pair, double time) {
        int index = server - 1;
        Server.serverList.set(index, Server.serverList.get(index).serve(time, pair));
    }

    /**
     * Serves the customer.
     * 
     * @param time The time at which serving begins
     * @param pair The pair containing id and service time of the customer to be served.
     * @return the server with the updated state after beginning to serve. 
     */
    public Server serve(double time, Pair pair) {
        if (queue.size() > 0 && queue.peek().getId() == pair.getId()) {
            queue.poll();
        }
    
        return new Server(this.id, false, queue, time + pair.getServiceTime(), queueLength);
    }

    /**
     * The customer enters the queue of the specified server.
     * 
     * @param server The ID of the server.
     * @param pair The pair containing the id and service time of the customer.
     */
    public static void fillQueueAt(int server, Pair pair) {
        int index = server - 1;
        Server.serverList.set(index, Server.serverList.get(index).fillQueue(pair));
    }

    /**
     * The customer enters the queue.
     * 
     * @param pair The pair containing the id and service time of the customer.
     * @return the server with the updated state after the customer enters its queue.
     */
    public Server fillQueue(Pair pair) {
        queue.add(pair);

        return new Server(this.id, false, queue, immediateAvailTime, queueLength);
    }

    /**
     * Gets the specified server to stop serving.
     * 
     * @param server The ID of the server.
     */
    public static void doneServeAt(int server) {
        int index = server - 1;
        Server.serverList.set(index, Server.serverList.get(index).doneServe());
    }

    /**
     * Stops serving the current customer.
     * 
     * @return the server with the updated state after it stops serving.
     */
    public Server doneServe() {
        return new Server(this.id, true, queue, immediateAvailTime, queueLength);
    }
    
    /**
     * Gets the specified server to start resting.
     * 
     * @param server The ID of the server.
     * @param restDuration The duration of rest.
     * @param time The time at which the server begins resting.
     */
    public static void restAt(int server, double restDuration, double time) {
        int index = server - 1;
        Server.serverList.set(index, Server.serverList.get(index).rest(time, restDuration));
    }

    /**
     * Begin resting.
     * 
     * @param time The time at which the server begins resting.
     * @param restDuration The duration of rest.
     * @return the server with the updated state after it begins to rest.
     */
    public Server rest(double time, double restDuration) {
        return new Server(this.id, false, queue, time + restDuration, queueLength);
    }

    /**
     * Returns true if the specified server is idle, false otherwise.
     * 
     * @param server the ID of the server.
     * @return true if server is idle, false otherwise.
     */
    public static boolean isIdleAt(int server) {
        return Server.serverList.get(server - 1).isIdle();
    }

    /**
     * Returns true if server is currently idle, false otherwise.
     * 
     * @return true if idle, false otherwise.
     */
    public boolean isIdle() {
        return idle;
    }

    /**
     * Gets the time at which the specified server will be idle after it finishes
     * serving the current customer and all the customers in the queue.
     * 
     * @param server The ID of the server.
     * @return next available time.
     */
    public static double nextAvailTimeAt(int server) {
        return Server.serverList.get(server - 1).nextAvailTime();
    }

    /**
     * Gets the time at which the server will be idle after it finishes
     * serving the current customer and all the customers in the queue.
     * 
     * @return next available time.
     */
    double nextAvailTime() {
        double queueTotalTime = 0;

        int queueSize = queue.size();

        if (queueSize == 0) {
            return immediateAvailTime;
        }

        for (int i = 0; i < queueSize - 1; i++) {
            Pair cust = queue.poll();

            double serviceTime = cust.getServiceTime();
            
            queueTotalTime = queueTotalTime + serviceTime;

            queue.add(cust);
        }

        Pair temp = queue.poll();

        queue.add(temp);

        return immediateAvailTime + queueTotalTime;
    }

    /**
     * Gets the ID of the server.
     * 
     * @return ID of server.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns true if the queue is still available, false otherwise.
     * 
     * @return true if queue is still available, false otherwise.
     */
    public boolean canQueue() {
        return queue.size() < queueLength;
    }
}
