import java.util.Scanner;
import cs2030.simulator.Customer;
import cs2030.simulator.Server;
import cs2030.simulator.State;
import cs2030.simulator.Type;
import cs2030.simulator.DoneEvent;
import cs2030.simulator.ServeEvent;
import cs2030.simulator.Event;
import cs2030.simulator.RandomProcessor;
import cs2030.simulator.ArrivalEvent;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int queueLength = sc.nextInt();
        
        Server.initialiseServers(numOfServers, queueLength);

        State state = new State();

        int numOfCustomers = 0;

        PriorityQueue<Event> eventQueue = new PriorityQueue<>();

        while (sc.hasNextDouble()) {
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();

            numOfCustomers++;
            
            ArrivalEvent arrEvent = new ArrivalEvent(new Customer(numOfCustomers, 
                    serviceTime, arrivalTime), arrivalTime);
         
            eventQueue.add(arrEvent); 
        }

        while (!eventQueue.isEmpty()) {
            Event currEvent = eventQueue.poll();

            System.out.println(currEvent);

            state = state.nextState(currEvent);

            if (!currEvent.isLast()) {
                eventQueue.add(currEvent.nextEvent());
            }
        }
        System.out.println(state);
    }
}
