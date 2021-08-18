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

class Main4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int queueLength = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        
        Server.initialiseServers(numOfServers, queueLength);

        State state = new State();

        PriorityQueue<Event> eventQueue = new PriorityQueue<>();

        for (int i = 1; i <= numOfCustomers; i++) {
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            
            eventQueue.add(new ArrivalEvent(new Customer(i, serviceTime, arrivalTime), 
                                                         arrivalTime));
        }

        while (!eventQueue.isEmpty()) {
            Event currEvent = eventQueue.poll();

            if (currEvent.getType() == Type.SERVE && !Server.isIdleAt(currEvent.getServer())) {
                eventQueue.add(((ServeEvent) currEvent).reschedule());
            } else {
                if (currEvent.getType() != Type.REST && 
                            currEvent.getType() != Type.DONEREST) {
                    System.out.println(currEvent);
                }
                
                state = state.nextState(currEvent);
                
                if (!currEvent.isLast()) { 
                    Event nextEvent = currEvent.nextEvent();
                    
                    eventQueue.add(nextEvent);
                }
                
                if (currEvent.getType() == Type.DONE && sc.hasNextDouble()) {
                    double restDuration = sc.nextDouble();
                    
                    if (restDuration != 0) {
                        DoneEvent event = (DoneEvent) currEvent;
                        eventQueue.add(event.nextEvent(restDuration));
                    }
                }
            }
        }
        
        System.out.println(state);
    }
}
